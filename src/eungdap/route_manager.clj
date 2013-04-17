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
               "GET /image.png"} requested-route))
