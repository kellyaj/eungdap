(ns eungdap.response-handler-spec
  (:require [speclj.core :refer :all]
            [eungdap.response-handler :refer :all]
            [eungdap.headers :refer :all]))

(describe "response handler"
  (it "properly responds with a 200 OK"
    (contains? "200 OK"
      (choose-response true "GET /")))

  (it "properly responds with a 404 Not Found"
    (contains? "404 Not Found"
      (choose-response false "GET /penguins")))

  (it "properly combines a 200 OK header with a requested route body"
    (contains? "making me thirsty"
      (add-header-and-body "GET /pretzels")))

  (it "successfully integrates header-and-body for an html file"
    (contains? "making me thirsty"
      (choose-response "GET /pretzels" true)))

  (it "sucessfully responds to /"
    (contains? "ex nihilo facto"
      (choose-response true "GET /")))

)
