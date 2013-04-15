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
