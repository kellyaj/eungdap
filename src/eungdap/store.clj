(ns eungdap.store)

(def storage-map (atom {}))

(defn get-all-stored-data-keys []
  (keys @storage-map))

(defn get-data [data-key]
  (get @storage-map data-key))

;(defn post-data [posted-data]
;  (reset! storage-map (merge @storage-map (hash-map posted-data posted-data))))

;(defn put-data [previous-data new-data]
;  (swap! storage-map dissoc previous-data)
;  (reset! storage-map (merge @storage-map (hash-map new-data new-data))))

(defn post-data [data-key data-value]
  (swap! storage-map assoc data-key data-value))

(defn put-data [previous-data new-data]
  (swap! storage-map #(-> % (dissoc previous-data) (assoc new-data new-data))))
