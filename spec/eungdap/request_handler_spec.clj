(ns eungdap.request-handler-spec
  (:require [speclj.core :refer :all]
            [eungdap.request-handler :refer :all]))
(describe "request-splitter"
  (it "splits a GET route properly"
    (should= "GET /"
      (request-splitter "GET / HTTP/1.1")))

  (it "splits a POST route properly"
    (should= "POST /form"
      (request-splitter "POST /form HTTP/1.1")))

  (it "splits a PUT route properly"
    (should= "PUT /form"
      (request-splitter "PUT /form HTTP/1.1")))
  )

(describe "request handling"
  (it "gets a 200 OK from the response handler"
    (with-in-str "200 OK"
      (request-handler "GET /pretzels HTTP/1.1")))

  (it "gets a 404 Not Found from the response handler"
    (with-in-str "404 Not Found"
      (request-handler "GET /penguins HTTP/1.1")))
  )
