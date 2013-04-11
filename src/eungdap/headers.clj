(ns eungdap.headers)

(defn two-hundred []
    (println "HTTP/1.1 200 OK")
    (println "Content-Type: text/html")
    (println "")
    (println "<h1> hi there </h1>"))

(defn four-zero-four []
    (println "HTTP/1.1 404 Not Found")
    (println "Content-Type: text/html")
    (println "")
    (println "Error 404, mang.")
    (println "<h1> page not found! </h1>"))
