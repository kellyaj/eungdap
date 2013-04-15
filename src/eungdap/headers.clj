(ns eungdap.headers)

(defn two-hundred []
  "HTTP/1.1 200 OK Content-Type: text/html \r\n")

(defn four-zero-four []
    (println "HTTP/1.1 404 Not Found")
    (println "Content-Type: text/html")
    (println "")
    (println "Error 404, mang.")
    (println "<h1> page not found! </h1>"))

