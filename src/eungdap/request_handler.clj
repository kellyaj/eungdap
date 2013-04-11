(ns eungdap.request-handler
  (:require [eungdap.headers :refer :all]
            [eungdap.route-manager :refer :all]))


(defn request-splitter [request]
 ((clojure.string/split request #" HTTP") 0))

(defn request-handler [request]
  (if (route-manager (request-splitter request))
    (two-hundred)
    (four-zero-four)))
