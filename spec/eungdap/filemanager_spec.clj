(ns eungdap.filemanager-spec
  (:require [speclj.core :refer :all]
            [eungdap.filemanager :refer :all]))

(describe "Filemanager"
  (it "grabs basic HTML and converts it to a string"
    (should= "making me thirsty"
      (re-find #"making me thirsty" (get-file-data "pretzels.html"))))

  (it "converts a route to an html file name"
    (should= "pretzels.html"
      (get-file-name "GET /pretzels" "html")))

  (it "gets index.html for /"
    (should= "index.html"
      (get-file-name "GET /" "html")))
  )
