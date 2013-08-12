(ns eungdap.request-handler
  (:require [eungdap.route-manager :refer [check-route-validity method-allowed?]]
            [eungdap.get-handler :refer [craft-method-not-allowed]]
            [eungdap.filemanager :refer [check-file-availability]]
            [eungdap.response-handler :refer [choose-response]]
            [clojure.string :refer [split]]
            [clojure.tools.logging :refer [info]]
            [ring.util.codec :refer [form-decode]]))

(defn parse-query-string [query]
  (form-decode query "UTF-8"))

(defn decode-query-string [request-map]
  (let [cleansed-route (first (split (get request-map :route) #"\?"))
        query-string (last (split (get request-map :route) #"\?"))
        altered-map (assoc request-map :route cleansed-route)]
    (assoc altered-map :params (parse-query-string query-string))))

(defn validate [route]
  (= true
    (or
      (check-route-validity route)
      (check-file-availability route))))

(defn handle-request [unaltered-request-map]
  (info "\r\n\r\nRequest parsed:\r\n"
        "Route: " (get unaltered-request-map :route)
        "\r\n Extension: " (get unaltered-request-map :extension)
        "\r\n Method: " (get unaltered-request-map :http-method)
        "\r\n Body: " (get unaltered-request-map :body-data)
        "\r\n Full Request: " (str unaltered-request-map "\r\n"))
  (let [request-map (decode-query-string unaltered-request-map)]
    (if (method-allowed? (get request-map :route) (get request-map :http-method))
      (choose-response
        request-map
        (validate (get request-map :route))
        (get request-map :http-method))
      (clojure.java.io/copy "HTTP/1.1 405 Method Not Allowed\r\nAllow: GET\r\n\r\n" *out*))))
