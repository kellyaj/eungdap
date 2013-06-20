(ns eungdap.response-handler
  (:require [eungdap.get-handler :refer [craft-get-response]]
            [eungdap.put-handler :refer [put-received-data]]
            [eungdap.post-handler :refer [store-body-data
                                          associate-route-with-body-data
                                          retrieve-route-data]]))

(import '[java.io OutputStreamWriter ByteArrayOutputStream BufferedOutputStream])

(defn print-the [thing]
  (binding [*out* (OutputStreamWriter. *out*)]
    (println thing)))

(defn handle-valid-url [request]
  (clojure.java.io/copy (craft-get-response request true) *out*))

(defn handle-invalid-url [request]
  (clojure.java.io/copy (craft-get-response request false) *out*))

(defn handle-get [request]
    (handle-valid-url request))

(defn handle-post [request]
  (store-body-data request)
  (associate-route-with-body-data (get request :route) (get request :body-data))
  (print-the request)
  )

(defn handle-put [request]
  (let [body-data (get request :body-data)]
  (put-received-data (first (keys body-data)) (first (vals body-data)))))

(defn choose-response [request validity http-method]
  (if (= true validity)
    (cond
      (= http-method "GET")
        (handle-get request)
      (= http-method "POST")
        (handle-post request)
      (= http-method "PUT")
         (handle-put request))
  (handle-invalid-url request)))
