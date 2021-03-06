(ns eungdap.filemanager
  (:import java.io.File java.nio.file.Paths java.io.ByteArrayOutputStream)
  (:require [clojure.string :refer [split]]))

(defn get-file-name [request]
  (if (= request "/")
      "public"
      (str (peek (clojure.string/split request #"/")))))

(defn check-file-availability [file]
  (.isFile (-> (str "public/" file) java.io.File. .getAbsoluteFile)))

(defn make-file-href [file-path file]
  (str "<a href=\"/" file "\">" file "</a><br>"))

(defn stringify-path [file-or-directory]
  (-> file-or-directory java.io.File. .getAbsolutePath))

(defn create-file-list [directory-name]
  (into #{}
    (for [file (-> directory-name java.io.File. .listFiles)]
      (.getName file))))

(defn generate-directory-html [directory-name]
  (.getBytes (apply str "<h1> Index of " directory-name "</h1>""<hr>"
    (into []
      (for [file (create-file-list directory-name)]
        (make-file-href (stringify-path directory-name) file))))))

(defn read-unextensioned [file]
  (if (.isFile (-> (str "public/" file) java.io.File. .getAbsoluteFile))
    (java.nio.file.Files/readAllBytes (Paths/get (.toURI (-> (str "public/" file) java.io.File. .getAbsoluteFile))))
    (java.nio.file.Files/readAllBytes (Paths/get (.toURI (-> (str "public/" file ".html") java.io.File. .getAbsoluteFile))))))

(defn get-file-size [file]
  (java.nio.file.Files/size
    (Paths/get (.toURI (-> (str "public/" file) java.io.File. .getAbsoluteFile)))))

(defn read-partial-file [file file-extension offset length]
  (let [outputstream (ByteArrayOutputStream.)]
    (.write outputstream
      (java.nio.file.Files/readAllBytes (Paths/get (.toURI (-> (str "public/" file) java.io.File. .getAbsoluteFile)))) offset length)
  (.toByteArray outputstream)))

(defn get-file-data [file file-extension]
  (cond
    (= 404 file)
      (java.nio.file.Files/readAllBytes (Paths/get (.toURI (-> (str "public/404.html") java.io.File. .getAbsoluteFile))))
    (= true (-> (stringify-path file) java.io.File. .isDirectory))
      (generate-directory-html file)
    (= nil file-extension)
      (read-unextensioned file)
    :else
      (java.nio.file.Files/readAllBytes (Paths/get (.toURI (-> (str "public/" file) java.io.File. .getAbsoluteFile))))))
