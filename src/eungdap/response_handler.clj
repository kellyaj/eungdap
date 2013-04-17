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
        "\r\n<h1> Not Found </h1>")
    :else
      (str
        (craft-header code (get-file-extension request))
        (get-file-data (get-file-name request (get-file-extension request))))))
; change choose-response to check for "GET /"
; if so, have it tell filemanager to handle a directory display
(defn choose-response [request validity]
    (if (= "GET /" request)
      (println (craft-header 200 ("this is where display-directory goes")))
      (if (= true validity)
        (println (add-header-and-body 200 request))
        (println (add-header-and-body 404 request)))))

