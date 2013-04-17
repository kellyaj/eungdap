(ns eungdap.response-handler-spec
  (:require [speclj.core :refer :all]
            [eungdap.response-handler :refer :all]))

(describe "response handler"
  (it "properly responds with a 200 OK"
    (should= "200 OK"
      (re-find #"200 OK" (add-header-and-body 200 "GET /"))))

  (it "properly responds with a 404 Not Found"
    (should= "404 Not Found"
      (re-find #"404 Not Found" (add-header-and-body 404 "GET /penguins"))))

  (it "properly combines a 200 OK header with a requested route body"
    (should= "making me thirsty"
      (re-find #"making me thirsty" (add-header-and-body 200 "GET /pretzels"))))

  (it "sucessfully responds to /"
    (should= "text-file.txt"
      (re-find #"text-file.txt" (add-header-and-body 200 "GET /"))))

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
