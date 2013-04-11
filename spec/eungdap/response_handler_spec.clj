(ns eungdap.response-handler-spec
  (:require [speclj.core :refer :all]
            [eungdap.response-handler :refer :all]
            [eungdap.headers :refer :all]))

(describe "response handler"
  (it "properly responds with a 200 OK"
    (with-in-str "200 OK"
      (choose-response true "GET /")))

  (it "properly responds with a 404 Not Found"
    (with-in-str "404 Not Found"
      (choose-response false "GET /penguins")))

)
