(defproject omhome "Oliver's online home"
  :description "A personal website. Everything is placeholder."
  :url "http://olivermooney.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [stasis "2.2.2"]
                 [ring "1.3.1"]]
  :ring {:handler omhome.ssgen/app}
  :main ^:skip-aot omhome.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:plugins [[lein-ring "0.8.12"]]}})
