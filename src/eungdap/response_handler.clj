(ns eungdap.response-handler
  (:require [eungdap.headers :refer :all]
            [eungdap.filemanager :refer :all]))

(defn add-header-and-body [request]
  (str (two-hundred) (get-html (get-file-name request))))

(defn choose-response [request validity]
  (if (= true validity)
    (add-header-and-body request)
    (four-zero-four)))

