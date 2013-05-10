(ns eungdap.response-handler
  (:require [eungdap.header-forge :refer [choose-mime-type add-response craft-header]]
            [eungdap.filemanager :refer [get-file-data]]
            [eungdap.store :refer :all]))

(import '[java.io OutputStreamWriter ByteArrayOutputStream])

(defn get-file-extension [request]
  (cond
    (= true (-> (peek (clojure.string/split request #"\.")) java.io.File. .isDirectory))
      "directory"
    (= "." (re-find #"\." request))
      (peek (clojure.string/split request #"\."))
    (not= 1 (count (clojure.string/split request #"\.")))
      "nil"
    (not= "." (re-find #"\." request))
      nil
    :else
       nil))


(defn add-header [code request content-length]
  (cond
    (= code 404)
      (str
        (craft-header code nil content-length))
    :else
      (str
        (craft-header code (get-file-extension request) content-length))))

(defn get-content-length [file file-extension]
  (if (= file-extension "directory")
    "directory"
    (alength (get-file-data (get-file-name file) file-extension))))

(defn concat-byte-array [code request file file-extension]
  (byte-array
    (concat
      (.getBytes (add-header code request (get-content-length file file-extension)))
      (get-file-data (get-file-name file) file-extension))))

(defn make-binary-response [request code file file-extension]
  (cond
    (contains? #{"jpg" "png" "jpeg" "gif"} file-extension)
      (concat-byte-array code request file file-extension)
    (= nil file-extension)
      (new String (concat-byte-array code request file nil))
    (= true (= nil file-extension) (= false (-> file java.io.File. .isDirectory)))
      (new String (concat-byte-array code request (str file ".html") "html"))
    :else
      (str (new String (concat-byte-array code request file file-extension)))))

(defn handle-valid-url [request]
  (clojure.java.io/copy
    (make-binary-response request 200 (get-file-name request) (get-file-extension request)) *out*))

(defn handle-invalid-url [request]
  (clojure.java.io/copy
    (make-binary-response request 404 "404.html" "html") *out*))

(defn handle-get [request]
    (handle-valid-url request))

(defn handle-post [request]
  (binding [*out* (OutputStreamWriter. *out*)]
    (println request)))


(defn handle-put [request]
  )

(defn choose-response [request validity http-method]
  (if (= true validity)
    (cond
      (= http-method "GET")
        (handle-get request)
      (= http-method "POST")
        (handle-post request)
      (= http-method "PUT")
         (handle-put request))
  (handle-invalid-url request)))
