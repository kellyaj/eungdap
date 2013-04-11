(ns eungdap.route-manager-spec
  (:require [speclj.core :refer :all]
            [eungdap.route-manager :refer :all]))

(describe "route-manager"
  (it "returns true for a known route"
    (should= true
      (route-manager "GET /")))

  (it "returns false if the route is not known"
    (should= false
      (route-manager "GET /penguins")))
)
