(ns omhome.highlight
  (:require [clojure.java.io :as io]
            [clygments.core :as pygments]
            [net.cgrand.enlive-html :as enlive]))

(defn- unwrap-pygments-code [highlighted]
  "Fix Pygments' over-zealous wrapping of code in <div> and <pre> tags, already present in our content"
  (-> highlighted
      java.io.StringReader.
      enlive/html-resource
      (enlive/select [:pre])
      first
      :content))

(defn- highlight [enlive-node]
  "Apply Pygments code highlighting to the supplied enlive node, picking up the desired language from applied CSS"
  (let [code (->> enlive-node :content (apply str))
        lang (->> enlive-node :attrs :class keyword)]
    (assoc enlive-node :content (-> code
                                    (pygments/highlight lang :html)
                                    unwrap-pygments-code))))

(defn highlight-code-blocks [html]
  "Extract code blocks and apply code-highlighting to them."
  (enlive/sniptest html
                   [:pre :code] highlight
                   [:pre :code] #(assoc-in % [:attrs :class] "codehilite")))
