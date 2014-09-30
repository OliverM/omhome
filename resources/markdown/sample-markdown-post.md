# Markdown is almost perfect

I mean, it's handy for bashing stuff out but ideally I'd like to be able to apply class and id styling to elements; other than that the selection of tags available seems reasonable.

Actually it looks like there are MarkDown extensions that support that in some limited cases; I'll have to see if (C|P)egdown supports them.

Anyhow, for the code-highlighting test, we're dropping in this project's project.clj file:

```clj
(defproject omhome "Oliver's online home"
  :description "A personal website. Everything is placeholder."
  :url "http://olivermooney.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [stasis "2.2.2"]
                 [ring "1.3.1"]
                 [hiccup "1.0.5"]
                 [me.raynes/cegdown "0.1.1"]]
  :ring {:handler omhome.ssgen/app}
  :main ^:skip-aot omhome.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:plugins [[lein-ring "0.8.12"]]}})
```

Here's some smartypants tests:

*  converting an ellipsis to a single character ...
*  smart opening and closing double quotes "She said."
*  smart apostrophes: "It wasn't mine!"
*  smart opening and closing single quotes 'Horrific!'