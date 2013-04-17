(ns eungdap.response-handler
  (:require [eungdap.header-forge :refer :all]
            [eungdap.filemanager :refer :all]))

(defn get-file-extension [request]
  (if (not= 1 (count (clojure.string/split request #"\.")))
    (if (not= true (-> (peek (clojure.string/split request #"\.")) java.io.File. .isDirectory))
      (if (= "." (re-find #"\." request))
        (peek (clojure.string/split request #"\."))
        "html"))))

(defn add-header-and-body [code request]
  (cond
    (= code 404)
      (str
        (craft-header code (get-file-extension request))
        "\r\n<h1> Not Found </h1>")
    :else
      (str
        (craft-header code (get-file-extension request))
        (get-file-data (get-file-name request)))))

(defn choose-response [request validity]
    (if (= true validity)
      (println (add-header-and-body 200 request))
      (println (add-header-and-body 404 request))))

