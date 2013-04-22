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
       (into []
       (for [file (create-file-list directory-name)]
        (make-file-href (stringify-path directory-name) file)))))

(defn convert-file-to-byte-array [file]
  (byte-array 
    (->
      (-> "public/image.gif" java.io.FileInputStream. .read))))

(defn get-file-data [file file-extension]
  (cond
    (= true (-> (stringify-path file) java.io.File. .isDirectory))
      (generate-directory-html file)
   (contains? #{"png" "gif" "jpeg" "jpg"} file-extension)
      (-> (convert-file-to-byte-array file) java.io.ByteArrayInputStream. .read)
    (contains? (create-file-list "public") file)
       (slurp (str "public/" (get (create-file-list "./public") file)))
    :else
       (slurp (str "public/" file ".html"))))
