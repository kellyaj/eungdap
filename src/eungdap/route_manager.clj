(ns eungdap.route-manager)

(defn route-manager [requested-route]
  (contains? #{
               "GET /"
               "GET /form"
               "GET /pretzels"
               "PUT /form"
               "POST /form"
               "GET /text-file.txt"
               "GET /file1"
               "GET /file2"
               "GET /pretzels.html"
               "GET /index.html"
               "GET /public"
               "GET /image.jpeg"
               "GET /image.gif"
               "GET /image.png"} requested-route))
