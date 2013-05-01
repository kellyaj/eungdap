(ns eungdap.response-handler-spec
  (:require [speclj.core :refer :all]
            [eungdap.response-handler :refer :all]))

(describe "file extension"
  (it "properly identifies .html"
    (should= "html"
      (get-file-extension "GET /penguins.html")))

  (it "properly identifies a non-filetyped request"
    (should= nil
      (get-file-extension "GET /penguins")))

  (it "properly identifies a .png"
    (should= "png"
      (get-file-extension "GET /penguins.png")))

  (it "properly identifies a directory"
    (should= "directory"
      (get-file-extension "/"))))

(describe "add-header"
  (it "creates a 404 properly"
    (should= "Not Found"
      (re-find #"Not Found" (add-header 404 "GET /penguins"))))

  (it "creates a 200 properly"
    (should= "200 OK"
      (re-find #"200 OK" (add-header 200 "GET /index.html")))))

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
        (new String (concat-byte-array 200 "GET /image.png" "image.png" "png")))))

  )

