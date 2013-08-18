(ns eungdap.route-manager)

(defn check-route-validity [requested-route]
  (contains? #{
               "/"
               "/form"
               "/form.html"
               "/redirect"
               "/parameters"} requested-route))

(defn method-allowed? [route http-method]
  (cond
    (= route "/file1")
      (contains? #{"GET"} http-method)
    (= route "/file2")
      (contains? #{"GET"} http-method)
    (= route "/text-file.txt")
      (contains? #{"GET"} http-method)
    :else
      true))
