(ns omhome.transformations
  (:require [clojure.java.io :as io]
            [clojure.string :as s]
            [clygments.core :as pygments]
            [net.cgrand.enlive-html :as enlive])
  (:import (java.io StringReader)))

(defn- unwrap-pygments-code [highlighted]
  "Fix Pygments' over-zealous wrapping of code in <div> and <pre> tags, already present in our content"
  (-> highlighted
      StringReader.
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

(defn- includes? [s] (enlive/text-pred #(s/includes? % s)))
(defn- re= [s] (enlive/text-pred #(re-find s %)))

(def ellipsis "...")

(defn smartypants [html]
  "Convert straight quotes to curly, whether single or double, and your sets of three periods to ellipses, and, verily, your multiple dashes to their rightful selves."
  (enlive/sniptest html [(includes? ellipsis)] #(s/replace % ellipsis "\u2026")))
