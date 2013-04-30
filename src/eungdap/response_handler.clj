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

(defn add-body [request]
  (get-file-data (get-file-name request) (get-file-extension request)))

(defn make-binary-response [request code file file-extension]
  (byte-array
    (concat
      (.getBytes (add-header code request))
      (get-file-data file file-extension))))

(defn respond-with-image [request]
  (binding [*out* (OutputStreamWriter. *out*)]
    (println (str add-header 200 request)))
    (clojure.java.io/copy (add-body request) *out*))

(defn handle-valid-url [request]
  (cond
    (contains? #{"jpg" "jpeg" "gif" "png"} (get-file-extension request))
      (respond-with-image request)
    :else
      (binding [*out* (OutputStreamWriter. *out*)]
        (println
          (str
            (add-header 200 request)
            (get-file-data (get-file-name request) (get-file-extension request)))))))

(defn handle-invalid-url []
  (binding [*out* (OutputStreamWriter. *out*)]
    (println
      (str
        (add-header 404 nil)
        (get-file-data 404 nil)))))

(defn choose-response [request validity]
   (if (= true validity)
     (handle-valid-url request)
     (handle-invalid-url)))
