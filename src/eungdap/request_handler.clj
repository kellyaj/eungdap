(ns eungdap.request-handler
  (:require [eungdap.route-manager :refer [check-route-validity]]
            [eungdap.response-handler :refer [choose-response]]))

(import '[java.io OutputStreamWriter])

(defn request-splitter [request]
 ((clojure.string/split request #" HTTP") 0))

(defn get-http-method [request]
  (first (clojure.string/split request #" /")))

(defn handle-request [request-map]
  (choose-response
    request-map
    (check-route-validity (get request-map :route))
    (get request-map :http-method)))



