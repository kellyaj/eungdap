(ns eungdap.response-handler-spec
  (:require [speclj.core :refer [describe it should=]]
            [eungdap.store :refer [post-data get-data]]
            [eungdap.response-handler :refer :all]))

(describe "handling PUT requests"
  (it "makes a put request, properly getting body-data from request itself"
    (let [request (hash-map :junk "afdkgjdksgkdsjfg"
                            :route "/hive"
                            :body-data (hash-map :bees "wax"))
          put-request (hash-map :junk "sdfgsdfgdsgsdgd"
                                :body-data (hash-map :bees "honey"))]
      (post-data :bees "wax")
      (handle-put put-request)
      (should= "honey"
        (get-data :bees)))))

