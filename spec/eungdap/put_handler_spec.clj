(ns eungdap.put-handler-spec
  (:require [speclj.core :refer [describe it should=]]
            [eungdap.put-handler :refer :all]
            [eungdap.store :refer [post-data get-data]]))

(describe "handling puts"
  (it "properly changes originally posted data"
    (post-data :salmon "sockeye")
    (should= "sockeye"
      (get-data :salmon))
    (put-received-data :salmon "chinook")
    (should= "chinook"
      (get-data :salmon))))
