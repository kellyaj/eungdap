(ns eungdap.header-forge-spec
  (:require [speclj.core :refer :all]
            [eungdap.header-forge :refer :all]))

(describe "content type selection"
  (it "selects text/html"
    (should= "Content-Type: text/html"
      (choose-mime-type ".html")))

  (it "selects image/gif"
    (should= "Content-Type: image/png"
      (choose-mime-type ".png")))

  (it "selects image/jpeg"
    (should= "Content-Type: image/jpeg"
      (choose-mime-type ".jpeg")))

  (it "selects image/gif"
    (should= "Content-Type: image/gif"
      (choose-mime-type ".gif")))

  (it "selects text/plain"
    (should= "Content-Type: text/plain"
      (choose-mime-type ".txt")))
  )

(describe "adding response code"
  (it "selects 200"
    (should= "HTTP/1.1 200 OK"
      (add-response 200)))

  (it "selects 404"
    (should= "HTTP/1.1 404 Not Found"
      (add-response 404)))

  )

(describe "crafting the response"
  (it "properls crafts a 200"
    (should= "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n"
      (craft-header 200 ".html")))

  (it "properly crafts a 404"
    (should= "HTTP/1.1 404 Not Found\r\nContent-Type: text/html\r\n"
      (craft-header 404 ".html")))

  )
