(ns eungdap.get-handler-spec
  (:require [speclj.core :refer [describe it should=]]
            [eungdap.get-handler :refer :all]))

(describe "add-header"
  (it "creates a 404 properly"
    (should= "Not Found"
      (re-find #"Not Found" (add-header 404 "GET /penguins" 123))))

  (it "creates a 200 properly"
    (should= "200 OK"
      (re-find #"200 OK" (add-header 200 "GET /index.html" 123)))))

(describe "concat-byte-array"
  (it "properly retrieves a byte array for a supported HTML file"
    (should= (.getClass (.getBytes "abc"))
      (.getClass
        (concat-byte-array 200 "GET /index.html" "index.html" "html"))))

  (it "properly retrieves a byte array for a supported image file"
    (should= (.getClass (.getBytes "abc"))
      (.getClass
        (concat-byte-array 200 "GET /image.gif" "image.gif" "gif"))))

  (it "properly adds a header for a supported HTML file"
    (should= "200 OK"
      (re-find #"200 OK"
        (new String (concat-byte-array 200 "GET /index.html" "index.html" "html")))))

  (it "properly gets the content of a supported HTML file"
    (should= "making me thirsty"
      (re-find #"making me thirsty"
        (new String (concat-byte-array 200 "GET /pretzels.html" "pretzels.html" "html")))))

  (it "properly adds a header for a supported image file"
    (should= "200 OK"
      (re-find #"200 OK"
        (new String (concat-byte-array 200 "GET /image.png" "image.png" "png"))))))

