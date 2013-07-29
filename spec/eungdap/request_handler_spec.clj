(ns eungdap.request-handler-spec
  (:require [speclj.core :refer [describe it should=]]
            [eungdap.request-handler :refer :all]))

(describe "method not allowed"
  (it "returns a 405 header when the method is not allowed"
    (let
      [request-map
       (hash-map :route "/file1" :http-method "PUT" :body-data "data=cosby")]
    (should= "HTTP/1.1 405 Method Not Allowed\r\nAllow: GET\r\n\r\n"
      (with-out-str
      (handle-request request-map))))))

(describe "parameter decode"

  (it "decodes parameters into a params hash"
    (let [query-string "name=andrew&frank=enstein"]
      (let [params (parse-query-string query-string)]
        (should= "enstein"
          (get params "frank")))))

  (it "puts the parameters into the request hash"
    (let
      [request-map
       (hash-map :route "/?name=andrew&frank=enstein" :http-method "GET")]
      (should= "/"
        (get (alter-request-map request-map) :route)))))
