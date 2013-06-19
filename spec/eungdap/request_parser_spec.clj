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

  (it "properly identifies the route"
    (should= "/form"
      (with-in-str "GET /form HTTP/1.1"
        (get (split-main-request) :route))))

  (it "properly identifies the file extension"
    (should= "png"
      (identify-file-extension "/cosby.png")))

  (it "defaults to html for un-extensioned routes"
    (should= nil
      (identify-file-extension "/home")))

  (it "adds the file extension to the map"
    (should= "png"
      (with-in-str "GET /cosby.png"
        (get (split-main-request) :extension))))


)
