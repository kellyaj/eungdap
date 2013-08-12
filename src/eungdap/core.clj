(ns eungdap.core
  (:require
        [server.socket :refer :all]
        [clojure.tools.logging :refer [info]]
        [eungdap.request-handler :refer [handle-request]]
        [eungdap.request-parser :refer [read-and-parse-request]])
  (:import java.io.BufferedOutputStream java.io.BufferedReader java.io.InputStreamReader))

(defn start-server [in out]
  (binding [*in* (BufferedReader. (InputStreamReader. in))
            *out* (BufferedOutputStream. out)]
    (handle-request (read-and-parse-request)))
  (flush))

(defn -main []
  (info "Starting the server on port 5000...")
  (create-server 5000 start-server))
