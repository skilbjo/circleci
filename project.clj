(defproject gilded-rose "0.1.0-SNAPSHOT"
  :description "CircleCI tech screen"
  :url "http://github.com/skilbjo/circleci"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :profiles {:dev {:dependencies [[speclj "3.3.2"]]
                   :plugins [[lein-cljfmt "0.5.7"]
                             [lein-kibit "0.1.6"]]}
             :uberjar {:aot :all}}
  :plugins [[speclj "3.3.2"]]
  :main gilded-rose.core
  :test-paths ["spec"])
