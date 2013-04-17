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

  (it "gets ./public for /"
    (should= "./public/"
      (get-file-name "GET /" "html")))

  (it "properly creates a vector of file names"
    (should= true
      (contains? (create-file-list "./public") "file1")))

  (it "creates a href for a file name"
    (should= "text-file.txt"
      (re-find #"text-file.txt" (make-file-href "./public" "text-file.txt"))))

  (it "gets the correct path for a directory"
    (should= "./public"
      (stringify-path "./public")))

  (it "properly makes a directory list"
    (should= "text-file.txt"
      (re-find #"text-file.txt" (generate-directory-html "./public"))))


  )
