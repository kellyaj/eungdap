(ns eungdap.response-handler
  (:require [eungdap.header-forge :refer :all]
            [eungdap.filemanager :refer :all]))

(import '[java.io OutputStreamWriter ByteArrayOutputStream])

(defn get-file-extension [request]
  (if (not= 1 (count (clojure.string/split request #"\.")))
    (if (not= true (-> (peek (clojure.string/split request #"\.")) java.io.File. .isDirectory))
      (if (= "." (re-find #"\." request))
        (peek (clojure.string/split request #"\."))
        "html"))))

(defn add-header [code request]
  (cond
    (= code 404)
      (str
        (craft-header code nil))
    :else
      (str
        (craft-header code (get-file-extension request)))))


(defn concat-byte-array [code request file file-extension]
  (byte-array
    (concat
      (.getBytes (add-header code request))
      (get-file-data (get-file-name file) file-extension))))

(defn make-binary-response [request code file file-extension]
  (cond
    (contains? #{"jpg" "png" "jpeg" "gif"} file-extension)
      (concat-byte-array code request file file-extension)
    (= true (= nil file-extension) (= false (-> file java.io.File. .isDirectory)))
      (new String (concat-byte-array code request (str file ".html") "html"))
    :else
      (new String (concat-byte-array code request file file-extension))))

(defn handle-valid-url [request]
  (clojure.java.io/copy (make-binary-response request 200 (get-file-name request) (get-file-extension request)) *out*))

(defn handle-invalid-url [request]
  (clojure.java.io/copy (make-binary-response request 404 "404.html" "html") *out*))

(defn choose-response [request validity]
   (if (= true validity)
     (handle-valid-url request)
     (handle-invalid-url request)))
