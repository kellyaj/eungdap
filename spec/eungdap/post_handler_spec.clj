(ns eungdap.post-handler-spec
  (:require [speclj.core :refer [describe it should=]]
            [eungdap.post-handler :refer :all]))

(describe "data and route storage"
  (it "stores the data"
    (store-body-data
      (hash-map :junk "ksjgkjdfgkjsd"
                :body-data (hash-map :aqua "lung" :frank "enstein")))
    (should= "lung"
      (get-data-wrapper :aqua))
    (should= "enstein"
      (get-data-wrapper :frank)))

  (it "associates the route with the posted data"
    (let [test-data (hash-map :tree "pine" :ice "water")]
      (associate-route-with-body-data "/test" test-data)
      (should= test-data
        (get-data-wrapper "/test"))))

  (it "retrieves the stored data for a route"
     (let [test-data (hash-map :tree "pine" :ice "water")]
      (associate-route-with-body-data "/test" test-data)
       (should= test-data
         (retrieve-route-data "/test")))))

