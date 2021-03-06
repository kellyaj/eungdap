(defproject eungdap "0.1.0-SNAPSHOT"
  :description "HTTP server for 8th Light Apprenticeship"
  :url "http://github.com/kellyaj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/tools.logging "0.2.6"]
                 [server-socket "1.0.0"]
                 [ring/ring-codec "1.0.0"]]
  :profiles {:dev {:dependencies [[speclj "2.5.0"]]}}
  :plugins [[speclj "2.5.0"]]
  :test-paths ["spec"]
  :main eungdap.core)
