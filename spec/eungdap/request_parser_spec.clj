(ns eungdap.request-parser-spec
  (:require [speclj.core :refer [describe it should=]]
            [eungdap.request-parser :refer :all]))

(describe "Request-parser"

  (it "splits a string properly for parsing"
    (should= "cadillacs"
      (last (split-on-space "bourbons and cadillacs"))))

  (it "properly identifies the http-method"
    (should= "GET"
      (with-in-str "GET /test HTTP/1.1"
      (get (split-main-request) :http-method))))

  (it "properly identifdies the route"
    (should= "/form"
      (with-in-str "GET /form HTTP/1.1"
        (get (split-main-request) :route))))

  (it "creates a hash-map for a response"
    (should= map?
      (with-in-str "GET /test HTTP/1.1"
      (type (read-and-parse-request)))))


)