(ns eungdap.core
  (:use server.socket))

(import '[java.io OutputStreamWriter BufferedReader InputStreamReader])

(defn twohundred-okay []
    (println "HTTP/1.1 200 OK")
    (println "Content-Type: text/html")
    (println "")
    (println "<h1> hi there </h1>"))

(defn fourohfour-idunno []
    (println "HTTP/1.1 404 Not Found")
    (println "Content-Type: text/html")
    (println "")
    )

(defn route-manager [requested-route]
  (contains? #{"GET / HTTP/1.1" "GET /form HTTP/1.1" "GET /pretzels HTTP/1.1"} requested-route))

(defn request-handler [request]
  (if (route-manager request)
    (twohundred-okay)
    (fourohfour-idunno)))

(defn start-server [in out]
  (binding [*in* (BufferedReader. (InputStreamReader. in))
            *out* (OutputStreamWriter. out)]
        (request-handler (read-line))
        (flush)))

(defn -main []
  (create-server 5000 start-server))

