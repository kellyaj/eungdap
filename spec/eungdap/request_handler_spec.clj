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
        (get (decode-query-string request-map) :route))))

  (it "decodes the the cob_spec parameters"
    (let
      [query-string "variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff"
       params (parse-query-string query-string)]
      (should= "stuff"
        (get params "variable_2"))
      (should= "Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?"
        (get params "variable_1")))))
