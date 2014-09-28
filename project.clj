(defproject omhome "Oliver's online home"
  :description "A personal website. Everything is placeholder."
  :url "http://olivermooney.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot omhome.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
