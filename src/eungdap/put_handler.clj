(ns eungdap.put-handler
  (:require [eungdap.store :refer [put-data]]))

(defn put-received-data [targeted-key new-data]
  (put-data targeted-key new-data))
