(ns eungdap.response-handler
  (:require [eungdap.header-forge :refer :all]
            [eungdap.filemanager :refer :all]))

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

(defn handle-valid-url [request]
  (clojure.java.io/copy (add-body request) *out*))

(defn handle-invalid-url []
  (clojure.java.io/copy (.getBytes (add-header 404 nil)) *out*)
  (clojure.java.io/copy (get-file-data 404 nil) *out*))

(defn choose-response [request validity]
   (if (= true validity)
     (handle-valid-url request)
     (handle-invalid-url)))
