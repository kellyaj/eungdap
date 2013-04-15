(ns eungdap.filemanager-spec
  (:require [speclj.core :refer :all]
            [eungdap.filemanager :refer :all]))

(describe "Filemanager"
  (it "grabs basic HTML and converts it to a string"
    (with-in-str "making me thirsty"
      (get-html "pretzels.html")))

  (it "converts a route to an html file name"
    (should= "pretzels.html"
      (get-file-name "GET /pretzels")))

  (it "gets index.html for /"
    (should= "index.html"
      (get-file-name "GET /")))
  )
