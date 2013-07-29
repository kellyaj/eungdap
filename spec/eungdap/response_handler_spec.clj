(ns eungdap.response-handler-spec
  (:require [speclj.core :refer [describe it should=]]
            [eungdap.store :refer [post-data get-data]]
            [eungdap.response-handler :refer :all]))

(describe "handling params"
  (it "serializes hash maps"
    (let [params {"dog" "cat", "tree" "evergreen"}]
      (should= "dog = cat tree = evergreen "
        (serialize params)))))
