(ns eungdap.route-manager-spec
  (:require [speclj.core :refer [describe it should=]]
            [eungdap.route-manager :refer [check-route-validity method-allowed?]]))

(describe "route-manager"
  (it "returns true for a known route"
    (should= true
      (check-route-validity "/")))

  (it "returns false if the route is not known"
    (should= false
      (check-route-validity "/penguins")))

  (it "properly assigns method allowed"
    (should= true
      (method-allowed? "/file1" "GET"))
    (should= true
      (method-allowed? "/form" "GET"))
    (should= true
      (method-allowed? "/form" "POST"))
    (should= false
      (method-allowed? "/file1" "POST"))
    (should= false
      (method-allowed? "/file1" "PUT"))))
