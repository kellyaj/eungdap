(ns eungdap.post-handler-spec
  (:require [speclj.core :refer [describe it should=]]
            [eungdap.post-handler :refer :all]
            [eungdap.store :refer [get-data]]))

(describe "data and route storage"
  (it "stores the data"
    (store-body-data
      (hash-map :junk "ksjgkjdfgkjsd"
                :body-data (hash-map :aqua "lung" :frank "enstein")))
    (should= "lung"
      (get-data :aqua))
    (should= "enstein"
      (get-data :frank)))

  (it "associates the route with the posted data"
    (let [test-data (hash-map :tree "pine" :ice "water")]
      (associate-route-with-body-data "/test" test-data)
      (should= test-data
        (get-data "/test"))))

  (it "retrieves the stored data for a route"
     (let [test-data (hash-map :tree "pine" :ice "water")]
      (associate-route-with-body-data "/test" test-data)
       (should= test-data
         (retrieve-route-data "/test")))))

