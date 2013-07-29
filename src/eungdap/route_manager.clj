(ns eungdap.route-manager)

(defn check-route-validity [requested-route]
  (contains? #{
               "/redirect"
               "/"
               "/form"
               "/form.html"
               "/pretzels"
               "/text-file.txt"
               "/file1"
               "/file2"
               "/pretzels.html"
               "/index.html"
               "/public"
               "/image.jpeg"
               "/image.gif"
               "/partial_content.txt"
               "/parameters"
               "/image.png"} requested-route))

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
