(ns eungdap.header-forge)

(defn choose-mime-type [extension]
  (get (hash-map
    "html"       "Content-Type: text/html"
    "png"        "Content-Type: image/png"
    "jpg"        "Content-Type: image/jpeg"
    "jpeg"       "Content-Type: image/jpeg"
    "gif"        "Content-Type: image/gif"
    "txt"        "Content-Type: text/plain"
    nil          "Content-Type: text/html"
         )
       extension))

(defn add-response [code]
  (str "HTTP/1.1 "
       (cond
         (= code 200) "200 OK"
         (= code 206) "206 Partial Content"
         (= code 301) "301 Moved Permanently"
         (= code 404) "404 Not Found"
         :else "500 Internal Server Error")))

(defn craft-header [code extension]
  (apply str [
              (add-response code) "\r\n"
              (choose-mime-type extension) "\r\n"
              "Server: Eungdap 0.1" "\r\n\r\n"
              ]))
