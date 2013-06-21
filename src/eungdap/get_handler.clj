(ns eungdap.get-handler
  (:require [eungdap.store        :refer [get-all-stored-data-keys
                                          get-data]]
            [eungdap.header-forge :refer [choose-mime-type
                                          add-response
                                          craft-header]]
            [eungdap.filemanager  :refer [get-file-data
                                          get-file-name
                                          get-file-size]]
            [clojure.string       :refer [split join]]))

(import '[java.io OutputStreamWriter ByteArrayOutputStream BufferedOutputStream])

(defn add-header [code request content-length]
  (cond
    (= code 404)
      (str
        (craft-header code nil content-length))
    :else
      (str
        (craft-header code (get request :extension) content-length))))

(defn format-stored-data [stored-data-map]
   (loop [formatted-string ""
          stringified-data-keys-map (zipmap
                       (map
                         #(name %) (keys stored-data-map))
                         (vals stored-data-map))]
     (if (empty? stringified-data-keys-map)
       formatted-string
       (recur
         (str formatted-string " "
              (first (first stringified-data-keys-map))
              " = "
              (last (first stringified-data-keys-map)))
         (rest stringified-data-keys-map)))))

(defn route-has-stored-data? [route]
  (true? (string? (some #{route} (get-all-stored-data-keys)))))

(defn get-content-length [file file-extension]
  (if (= file-extension "directory")
    "directory"
    (alength (get-file-data (get-file-name file) file-extension))))

(defn total-content-length [file file-extension formatted-data]
  (let [file-content-length (get-content-length file file-extension)
        data-content-length (alength (.getBytes formatted-data))]
    (+ file-content-length data-content-length)))

(defn create-dynamic-byte-array [code request file file-extension]
  (let [formatted-data (format-stored-data (get-data (get request :route)))]
    (byte-array
      (concat
        (.getBytes
          (add-header code request
                      (total-content-length file file-extension formatted-data)))
        (.getBytes formatted-data)
        (get-file-data (get-file-name file) file-extension)))))

(defn concat-byte-array [code request file file-extension]
  (if (route-has-stored-data? (get request :route))
    (create-dynamic-byte-array code request file file-extension)
    (byte-array
      (concat
        (.getBytes (add-header code request (get-content-length file file-extension)))
        (get-file-data (get-file-name file) file-extension)))))

(defn write-image [code request file file-extension]
  (binding [*out* (BufferedOutputStream. *out* (get-file-size file))]
    (.write *out* (concat-byte-array code request file file-extension))))

(defn make-binary-response [request code file file-extension]
  (cond
    (contains? #{"jpg" "png" "jpeg" "gif"} file-extension)
      (write-image code request file file-extension)
    (= nil file-extension)
      (new String (concat-byte-array code request file nil))
    (= true (= nil file-extension) (= false (-> file java.io.File. .isDirectory)))
      (new String (concat-byte-array code request (str file ".html") "html"))
    :else
      (str (new String (concat-byte-array code request file file-extension)))))

(defn craft-method-not-allowed []
  (new String
   (byte-array
     (.getBytes
      (craft-header 405 "txt"
        (alength (byte-array (.getBytes (craft-header 405 "txt" 1024)))))))))

(defn craft-get-response [request validity]
  (cond
    (= "/partial_content.txt" (get request :route))
      (make-binary-response request 206
        (get-file-name (get request :route)) (get request :extension))
    (true? validity)
      (make-binary-response request 200
        (get-file-name (get request :route)) (get request :extension))
    (false? validity)
      (make-binary-response request 404 "404.html" "html")
    (= "POST" validity)
      (craft-header 200 "html" 1024)
    (= "PUT" validity)
      (craft-header 200 "html" 30)))

