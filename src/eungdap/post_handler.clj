(ns eungdap.post-handler
  (:require [eungdap.store :refer [get-data
                                   get-all-stored-data-keys
                                   post-data]]))

(defn store-body-data [request]
  (let [body-data-map (get request :body-data)]
    (loop [remaining-keys (keys body-data-map)
           remaining-vals (vals body-data-map)]
      (let [current-key (first remaining-keys)
            current-val (first remaining-vals)]
        (post-data current-key current-val))
        (if (= 1 (count remaining-keys))
          true
          (recur
            (rest remaining-keys)
            (rest remaining-vals))))))

(defn route-has-stored-data? [route]
  (true? (string? (some #{route} (get-all-stored-data-keys)))))

(defn associate-route-with-body-data [route body-data]
  (post-data route body-data))

(defn retrieve-route-data [route]
  (get-data route))

(defn get-data-wrapper [data-key]
  (get-data data-key))
