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
               "/image.png"} requested-route))
