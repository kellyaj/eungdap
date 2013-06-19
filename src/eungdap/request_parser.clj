(ns eungdap.request-parser
  (:require [clojure.string :refer [split]]))

(import '[java.io OutputStreamWriter ByteArrayOutputStream BufferedOutputStream])

(defn print-the [thing]
  (binding [*out* (OutputStreamWriter. *out*)]
    (println thing)))

(defn identify-file-extension [route]
  (if (= 1 (count (split route #"\.")))
    nil
    (last (split route #"\."))))

(defn split-on-space [targeted-string]
  (split targeted-string #"\s+"))

(defn get-body-data [request-map]
  (loop [map-with-body (hash-map)
         line (read-line)]
    (if (empty? line)
      map-with-body
      (recur
        (assoc map-with-body
               (keyword (first (split line #"\=")))
               (last (split line #"\=")))
        (read-line)))))

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
    (if (empty? line)
      (if (= "POST" (get parsed-request :http-method))
        (assoc parsed-request :body-data (get-body-data parsed-request))
        parsed-request)
      (recur
        (assoc parsed-request
          (keyword (first (split-on-space line)))
          (last (split-on-space line)))
        (read-line)))))
