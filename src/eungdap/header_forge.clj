(ns eungdap.header-forge)

(defn choose-mime-type [extension]
  (get (hash-map
    ".html"       "Content-Type: text/html"
    ".png"        "Content-Type: image/png"
    ".jpg"        "Content-Type: image/jpeg"
    ".jpeg"       "Content-Type: image/jpeg"
    ".gif"        "Content-Type: image/gif"
    ".txt"        "Content-Type: text/plain"
         )
       extension))
