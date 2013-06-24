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
