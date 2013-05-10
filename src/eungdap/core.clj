(ns eungdap.core
  (:use server.socket
        eungdap.request-handler
        eungdap.request-parser))

(import '[java.io BufferedOutputStream BufferedReader InputStreamReader])

(defn start-server [in out]
  (binding [*in* (BufferedReader. (InputStreamReader. in))
            *out* (BufferedOutputStream. out)]
    (handle-request (read-and-parse-request))))

(defn -main []
  (create-server 5000 start-server))
