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
  (if (contains? #{"jpg" "gif" "png" "jpeg"} (get request :extension))
    (craft-get-response request true)
    (clojure.java.io/copy (craft-get-response request true) *out*)))

(defn handle-invalid-url [request]
  (clojure.java.io/copy (craft-get-response request false) *out*))

(defn serialize-recur [all-keys all-vals the-string]
  (let [the-key (first all-keys)
        the-val (first all-vals)]
    (if (empty? the-key)
      the-string
      (recur
        (rest all-keys)
        (rest all-vals)
        (str the-string the-key " = " the-val " ")))))

(defn serialize [params]
  (serialize-recur (keys params) (vals params) ""))

(defn handle-parameters [request]
  (clojure.java.io/copy (str "HTTP/1.1 200 OK\r\n\r\n" (serialize (get request :params))) *out*))

(defn handle-get [request]
  (if (= "/parameters" (get request :route))
    (handle-parameters request)
    (handle-valid-url request)))

(defn handle-post [request]
  (store-body-data request)
  (associate-route-with-body-data (get request :route) (get request :body-data))
  (clojure.java.io/copy "HTTP/1.1 200 OK" *out*))

(defn handle-put [request]
  (handle-post request))

(defn choose-response [request validity http-method]
  (if (= true validity)
    (cond
      (= "/redirect" (get request :route))
        (handle-get (hash-map :route "/" :extension nil))
      (= http-method "GET")
        (handle-get request)
      (= http-method "POST")
        (handle-post request)
      (= http-method "PUT")
        (handle-put request))
  (handle-invalid-url request)))
