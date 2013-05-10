(ns eungdap.request-reader)

(import '[java.io BufferedReader InputStreamReader])

(defn read-request [input-stream]
  (let [buffer (BufferedReader. input-stream)
        total-request ""
        myseq (line-seq buffer)]
    (println myseq)))

