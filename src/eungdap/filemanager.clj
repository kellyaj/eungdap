(ns eungdap.filemanager)

(defn get-file-name [request]
  (if (= "GET /" request)
    "index.html"
    (str (peek (clojure.string/split request #"/")) ".html")))

(defn get-html [file]
  (slurp (str "public/" file)))
