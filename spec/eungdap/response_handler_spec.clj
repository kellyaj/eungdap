(ns eungdap.response-handler-spec
  (:require [speclj.core :refer :all]
            [eungdap.response-handler :refer :all]
            [eungdap.headers :refer :all]))

(describe "response handler"
  (it "properly responds with a 200 OK"
    (should= "200 OK"
      (re-find #"200 OK" (choose-response "GET /" true))))

  (it "properly responds with a 404 Not Found"
    (should= "404 Not Found"
      (re-find #"404 Not Found" (choose-response "GET /penguins" false))))

  (it "properly combines a 200 OK header with a requested route body"
    (should= "making me thirsty"
      (re-find #"making me thirsty" (choose-response "GET /pretzels" true))))

  (it "sucessfully responds to /"
    (should= "ex nihilo"
      (re-find #"ex nihilo" (choose-response "GET /" true))))

)

(describe "file extension"
  (it "properly identifies .html"
    (should= "html"
      (get-file-extension "GET /penguins.html")))

  (it "properly identifies a non-filetyped request"
    (should= "html"
      (get-file-extension "GET /penguins")))

  (it "properly identifies a .png"
    (should= "png"
      (get-file-extension "GET /penguins.png")))

  )
