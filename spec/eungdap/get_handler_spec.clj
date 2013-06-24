(ns eungdap.get-handler-spec
  (:require [speclj.core          :refer [describe it should=]]
            [eungdap.get-handler  :refer :all]
            [eungdap.post-handler :refer [associate-route-with-body-data]]))

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

(describe "retrieving stored data"
  (it "can detect if a route has data POSTed to it"
    (let [test-data (hash-map :tree "pine" :ice "water")]
      (associate-route-with-body-data "/test" test-data)
      (should= true
        (route-has-stored-data? "/test"))
      (should= false
        (route-has-stored-data? "/nodata"))))

  (it "properly formats stored data associated with a route"
    (let [test-data (hash-map :tree "pine" :coffee "iced")]
      (should= " coffee = iced tree = pine"
        (format-stored-data test-data)))))

(describe "handling partial content"

  (it "splits a range into an array with the offset and the length"
    (let [test-data (hash-map :Range "bytes=0-4")]
      (should= "0"
        (first (split-range (get test-data :Range))))
      (should= "4"
        (last (split-range (get test-data :Range))))))

  (it "gets the first 4 bytes of a file"
    (should="HTTP/1.1 206 Partial Content\r\nContent-Length: 4\r\nContent-Type: text/plain\r\nServer: Eungdap 0.1\r\n\r\nThis"
      (new String
           (partial-content-response
             (hash-map :Range "bytes=0-4" :route "/partial_content.txt" :extension "txt") 
             206 "partial_content.txt" "txt"))))

  (it "gets the first 7 bytes of a file"
    (should="HTTP/1.1 206 Partial Content\r\nContent-Length: 7\r\nContent-Type: text/plain\r\nServer: Eungdap 0.1\r\n\r\nThis is"
      (new String
           (partial-content-response
             (hash-map :Range "bytes=0-7" :route "/partial_content.txt" :extension "txt")
             206 "partial_content.txt" "txt")))))
