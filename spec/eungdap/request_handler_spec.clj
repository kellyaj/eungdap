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
