(ns eungdap.core
  (:require
        [server.socket :refer :all]
        [eungdap.request-handler :refer [handle-request]]
        [eungdap.request-parser :refer [read-and-parse-request]]))

(import '[java.io BufferedOutputStream BufferedReader InputStreamReader OutputStreamWriter])

(defn print-the [thing]
  (binding [*out* (OutputStreamWriter. *out*)]
    (println thing)))

(defn start-server [in out]
  (binding [*in* (BufferedReader. (InputStreamReader. in))
            *out* (BufferedOutputStream. out)]
    (handle-request (read-and-parse-request))))

(defn -main []
  (create-server 5000 start-server))
