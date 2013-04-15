(ns eungdap.request-handler
  (:require [eungdap.route-manager :refer :all]
            [eungdap.response-handler :refer :all]))

(defn request-splitter [request]
 ((clojure.string/split request #" HTTP") 0))

(defn request-handler [request]
  (choose-response
    (request-splitter request) (route-manager (request-splitter request))))
