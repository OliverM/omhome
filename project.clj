(defproject omhome "Oliver's online home"
  :description "A personal website. Everything is placeholder."
  :url "http://olivermooney.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [stasis "2.3.0"]
                 [ring "1.4.0"]
                 [hiccup "1.0.5"]
                 [me.raynes/cegdown "0.1.1"]
                 [enlive "1.1.6"]
                 [clygments "0.1.1"]
                 [optimus "0.18.4"]
                 [midje "1.8.3"]
                 [aprint "0.1.3"]

                 [org.clojure/clojurescript "1.8.40"]
                 [devcards "0.2.1-6"]
                 ]
  :ring {:handler omhome.ssgen/app}
  :main ^:skip-aot omhome.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:plugins [[lein-ring "0.9.7"]
                             [lein-figwheel "0.5.1"]
                             [lein-midje "3.2"]]}}
  :cljsbuild {:builds [{:id "devcards"
                        :source-paths ["src/cljs"]
                        :figwheel {:devcards true}
                        :compiler {:main "omguest.core"
                                   :asset-path "js/out"
                                   :output-to "resources/cljspages/js/omhome.js"
                                   :output-dir "resources/cljspages/js/out"}}
                       ]}
  :aliases {"export-site" ["run" "-m" "omhome.ssgen/export"]})
