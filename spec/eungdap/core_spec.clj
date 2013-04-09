(ns eungdap.core-spec
  (:require [speclj.core :refer :all]
            [eungdap.core :refer :all]))

(describe "Eungdap"
  (it "responds 200 OK"
    (should= "200 OK"
      (start-server)))
  )
