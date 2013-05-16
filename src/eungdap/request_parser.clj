(ns eungdap.request-parser
  (:require [clojure.string :refer [split]]))

(defn identify-file-extension [route]
  (if (= 1 (count (split route #"\.")))
    "html"
    (last (split route #"\."))))

(defn split-on-space [targeted-string]
  (split targeted-string #"\s+"))

(defn split-main-request []
  (let [request (split-on-space (read-line))]
    (hash-map
      :http-method (first request)
      :route (nth request 1)
      :http_version (last request)
      :extension (identify-file-extension (nth request 1)))))

(defn read-and-parse-request []
  (loop
    [parsed-request (split-main-request)
     line (read-line)]
    (if (= "" line)
      parsed-request
      (recur
        (assoc parsed-request
          (keyword (first (split-on-space line)))
          (last (split-on-space line)))
        (read-line)))))
