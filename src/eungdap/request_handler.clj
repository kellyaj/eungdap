(ns eungdap.request-handler
  (:require [eungdap.route-manager :refer :all]
            [eungdap.response-handler :refer :all]))

(import '[java.io OutputStreamWriter])

(defn request-splitter [request]
 ((clojure.string/split request #" HTTP") 0))

(defn get-http-method [request]
  (first (clojure.string/split request #" /")))

(defn handle-request [request]
  (binding [*out* (OutputStreamWriter. *out*)]
    (println request)))
