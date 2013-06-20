(ns eungdap.store)

(def storage-map (atom {}))

(defn get-all-stored-data-keys []
  (keys @storage-map))

(defn get-data [data-key]
  (get @storage-map data-key))

(defn post-data [data-key data-value]
  (swap! storage-map assoc data-key data-value))

;(defn put-data [previous-data new-data]
;  (swap! storage-map #(-> % (dissoc previous-data) (assoc new-data new-data))))

(defn put-data [data-key new-data]
  (swap! storage-map assoc data-key new-data))
