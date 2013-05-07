(ns eungdap.store-spec
  (:require [speclj.core :refer :all]
            [eungdap.store :refer :all]))

(describe "store"
  (it "POSTs data to the storage map"
    (post-data "qwerty")
    (should= "qwerty"
      (get-data "qwerty")))

  (it "PUTs data, changing it in the storage map"
    (post-data "qwerty")
    (put-data "qwerty" "zxcv")
    (should= "zxcv"
      (get-data "zxcv"))
    (should= nil
      (get-data "qwerty")))
  )
