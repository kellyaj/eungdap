(ns eungdap.request-handler-spec
  (:require [speclj.core :refer [describe it should=]]
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

  (it "splits another GET route properly"
    (should= "GET /penguins"
      (request-splitter "GET /penguins HTTP/1.1")))
  )

(describe "get-http-method"
  (it "returns GET for GET /index.html"
    (should= "GET"
      (get-http-method "GET /index.html")))

  (it "returns POST for POST /form.html"
    (should= "POST"
      (get-http-method "POST /index.html"))))

