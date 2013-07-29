(ns eungdap.request-handler
  (:require [eungdap.route-manager :refer [check-route-validity method-allowed?]]
            [eungdap.get-handler :refer [craft-method-not-allowed]]
            [eungdap.response-handler :refer [choose-response]]
            [clojure.string :refer [split]]
            [ring.util.codec :refer [form-decode]])
  (:import [java.io OutputStreamWriter ByteArrayOutputStream BufferedOutputStream]))

(defn parse-query-string [query]
  (form-decode query "UTF-8"))

(defn alter-request-map [request-map]
  (let [cleansed-route (first (split (get request-map :route) #"\?"))
        query-string (last (split (get request-map :route) #"\?"))
        altered-map (assoc request-map :route cleansed-route)]
    (assoc altered-map :params (parse-query-string query-string))))

(defn handle-request [request-map]
  (if (method-allowed? (get request-map :route) (get request-map :http-method))
    (choose-response
      request-map
      (check-route-validity (get request-map :route))
      (get request-map :http-method))
    (clojure.java.io/copy "HTTP/1.1 405 Method Not Allowed\r\nAllow: GET\r\n\r\n" *out*)))
