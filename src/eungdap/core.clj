(ns eungdap.core
  (:use server.socket
        eungdap.request-handler))

(import '[java.io BufferedOutputStream BufferedReader InputStreamReader])

(defn start-server [in out]
  (binding [*in* (BufferedReader. (InputStreamReader. in))
            *out* (BufferedOutputStream. out)]
        (request-handler (read-line))
        (flush)))

(defn -main []
  (create-server 5000 start-server))

