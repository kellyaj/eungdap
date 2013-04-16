(ns eungdap.filemanager)

(defn get-file-name [request extension]
  (if (= "GET /" request)
    "index.html"
    (if (= extension (re-find (re-pattern extension) request))
      (str (peek (clojure.string/split request #"/")))
      (str (peek (clojure.string/split request #"/")) (str "." extension)))))

(defn get-file-data [file]
  (slurp (str "public/" file)))
