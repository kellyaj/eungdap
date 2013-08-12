(ns eungdap.request-parser
  (:require [eungdap.route-manager :refer [method-allowed?]]
            [clojure.tools.logging :refer :all]
            [clojure.string :refer [split]])
  (:import java.io.PrintWriter))

(defn identify-file-extension [route]
  (if (= 1 (count (split route #"\.")))
    nil
    (last (split route #"\."))))

(defn split-on-space [targeted-string]
  (split targeted-string #"\s+"))

(defn format-body-data [raw-body-data]
  (let [split-data (split raw-body-data #"\=")]
    (hash-map (first split-data) (last split-data))))

(defn get-body-data [length]
  (let [body-char-array (char-array length)]
    (.read *in* body-char-array 0 length)
    (format-body-data (apply str body-char-array))))

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
      (cond
        (= false
           (method-allowed?
             (get parsed-request :route) (get parsed-request :http-method)))
          parsed-request
        (contains? #{"POST" "PUT"} (get parsed-request :http-method))
          (assoc parsed-request :body-data (get-body-data (read-string (get parsed-request :Content-Length))))
       :else
          parsed-request)
      (recur
        (assoc parsed-request
          (keyword (first (split-on-space line)))
          (last (split-on-space line)))
        (clojure.string/replace (read-line) #"\:" "")))))
