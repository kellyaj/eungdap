(ns eungdap.route-manager-spec
  (:require [speclj.core :refer [describe it should=]]
            [eungdap.route-manager :refer [check-route-validity]]))

(describe "route-manager"
  (it "returns true for a known route"
    (should= true
      (check-route-validity "/")))

  (it "returns false if the route is not known"
    (should= false
      (check-route-validity "/penguins")))
)
