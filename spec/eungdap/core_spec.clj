(ns eungdap.core-spec
  (:require [speclj.core :refer :all]
            [eungdap.core :refer :all]))

(describe "route-manager"
  (it "returns true for a known route"
    (should= true
      (route-manager "GET / HTTP/1.1")))

  (it "returns false if the route is not known"
    (should= false
      (route-manager "GET /penguins HTTP/1.1")))
)

