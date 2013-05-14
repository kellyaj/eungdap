(ns eungdap.request-handler
  (:require [eungdap.route-manager :refer [check-route-validity]]
            [eungdap.response-handler :refer [choose-response]]))

(defn handle-request [request-map]
  (choose-response
    request-map
    (check-route-validity (get request-map :route))
    (get request-map :http-method)))
