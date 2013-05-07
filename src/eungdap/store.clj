(ns eungdap.store)

(def storage-map (atom {}))

(defn get-data [data]
  (get @storage-map data))

(defn post-data [posted-data]
  (reset! storage-map (merge @storage-map (hash-map posted-data posted-data))))

(defn put-data [previous-data new-data]
  (swap! storage-map dissoc previous-data)
  (reset! storage-map (merge @storage-map (hash-map new-data new-data))))
