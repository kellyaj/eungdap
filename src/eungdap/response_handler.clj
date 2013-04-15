(ns eungdap.response-handler
  (:require [eungdap.header-forge :refer :all]
            [eungdap.filemanager :refer :all]))

(defn get-file-extension [request]
  (if (= 1 (count (clojure.string/split request #"\.")))
    "html"
    (peek (clojure.string/split request #"\."))))

(defn add-header-and-body [code request]
  (cond
    (= code 404)
      (str
        (craft-header code (get-file-extension request))
        "<h1> Not Found </h1>")
    :else
      (str
        (craft-header code (get-file-extension request))
        (get-html (get-file-name request)))))

(defn choose-response [request validity]
  (if (= true validity)
    (add-header-and-body 200 request)
    (add-header-and-body 404 request)))

