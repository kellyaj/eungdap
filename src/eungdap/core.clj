(ns eungdap.core
  (:use server.socket))

(import '[java.io OutputStreamWriter])

(defn start-server [in out]
  (binding [*out* (OutputStreamWriter. out)]
    (println "HTTP/1.1 200 OK")
    (println "Content-Type: text/html")
    (println "")
    (println "<h1> hi there </h1>")))

(defn -main []
  (create-server 5000 start-server))
