(ns eungdap.filemanager
  (:import java.io.File))

(defn get-file-name [request extension]
  (if (= "GET /" request)
    "index.html"
    (if (= extension (re-find (re-pattern extension) request))
      (str (peek (clojure.string/split request #"/")))
      (str (peek (clojure.string/split request #"/")) (str "." extension)))))

(defn get-file-data [file]
  (slurp (str "public/" file)))

(defn make-file-href [file-path file]
  (str "<a href=\"" file-path "/" file "\">"  file "</a><br>"))

(defn stringify-path [directory-name]
  (-> directory-name java.io.File. .getPath))

(defn create-file-list [directory-name]
  (let [x #{}]
    (into x
          (for [file (-> directory-name java.io.File. .listFiles)]
            (.getName file)))))

(defn generate-directory-html [directory-name]
  (apply str "<h1> Index of " directory-name "</h1>"
       "<hr>"
       (let [x []]
       (into x
       (for [file (create-file-list directory-name)]
        (make-file-href (stringify-path directory-name) file))))))

