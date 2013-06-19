(ns eungdap.store-spec
  (:require [speclj.core :refer [describe it should=]]
            [eungdap.store :refer :all]))

(describe "store"
  (it "POSTs data to the storage map"
    (post-data :aqua "lung")
    (should= "lung"
      (get-data :aqua)))

  (it "PUTs data, changing it in the storage map"
    (post-data :data "cosby")
    (put-data "qwerty" "zxcv")
    (should= "zxcv"
      (get-data "zxcv"))
    (should= nil
      (get-data "qwerty")))
  )
