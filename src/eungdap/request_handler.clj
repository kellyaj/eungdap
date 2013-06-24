(ns eungdap.request-handler
  (:require [eungdap.route-manager :refer [check-route-validity method-allowed?]]
            [eungdap.get-handler :refer [craft-method-not-allowed]]
            [eungdap.response-handler :refer [choose-response]]))

(import '[java.io OutputStreamWriter ByteArrayOutputStream BufferedOutputStream])

(defn handle-request [request-map]
  (if (method-allowed? (get request-map :route) (get request-map :http-method))
  (choose-response
    request-map
    (check-route-validity (get request-map :route))
    (get request-map :http-method))
    (clojure.java.io/copy "HTTP/1.1 405 Method Not Allowed\r\nAllow: GET\r\n\r\n" *out*)))
