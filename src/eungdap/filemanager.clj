(ns eungdap.filemanager
  (:import java.io.File))

(defn get-file-name [request]
  (if (= request "GET /")
      "public"
      (str (peek (clojure.string/split request #"/")))))

(defn make-file-href [file-path file]
  (str "<a href=\"/" file "\">" file "</a><br>"))

(defn stringify-path [file-or-directory]
  (-> file-or-directory java.io.File. .getAbsolutePath))

(defn create-file-list [directory-name]
    (into #{}
          (for [file (-> directory-name java.io.File. .listFiles)]
            (.getName file))))

(defn generate-directory-html [directory-name]
  (apply str "<h1> Index of " directory-name "</h1>"
       "<hr>"
       (let [x []]
       (into x
       (for [file (create-file-list directory-name)]
        (make-file-href (stringify-path directory-name) file))))))

(defn get-file-data [file file-extension]
  (cond
    (= true (-> (stringify-path file) java.io.File. .isDirectory))
      (generate-directory-html file)
    (contains? #{"png" "gif" "jpeg" "jpg"} file-extension)
      ;(-> "public/image.gif" java.io.ByteArrayInputStream. .read)
       "images not yet supported"
    (contains? (create-file-list "public") file)
       (slurp (str "public/" (get (create-file-list "./public") file)))
    (contains? (create-file-list "public") file)
       (slurp (str "public/" file ".html"))))


;FileInputStream to get byte array after reading file 
