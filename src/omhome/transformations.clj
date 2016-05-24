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
(defn- re= [s] (enlive/text-pred #(boolean (re-find s %))))

(defn smartypants [html]
  "Convert straight quotes to curly, whether single or double, and your sets of three periods to ellipses, and, verily, your multiple dashes to their rightful selves. Regular expressions derived from smartquotesjs.com via https://github.com/kellym/smartquotesjs/blob/master/src/smartquotes.js"
  (let [ellipsis "..."
        triple-prime "'''"
        double-prime "''"
        em-dash "---"
        en-dash "--"
        opening-double-quote #"(\W|^)\"(\S)"
        closing-double-quote #"(\u201c[^\"]*)\"([^\"]*$|[^\u201c\"]*\u201c)"
        closing-double-quote-supp #"([^0-9])\""
        opening-apostrophe #"(\W|^)'(\S)"
        possessive-apostrophe #"i?([a-z])'([a-z])"
        closing-apostrophe #"i?((\u2018[^']*)|[a-z])'([^0-9]|$)"
        backwards-apostrophe #"i?(\B|^)\u2018(?=([^\u2019]*\u2019\b)*([^\u2019\u2018]*\W[\u2019\u2018]\b|[^\u2019\u2018]*$))"
        shortened-years-apostrophe #"i?(\u2018)([0-9]{2}[^\u2019]*)(\u2018([^0-9]|$)|$|\u2019[a-z])"
        standalone-apostrophe "'"]
    (enlive/sniptest html
                     [(includes? ellipsis)] #(s/replace % ellipsis "\u2026")
                     [(includes? triple-prime)] #(s/replace % triple-prime "\u2034")
                     [(includes? double-prime)] #(s/replace % double-prime "\u2033")
                     [(includes? em-dash)] #(s/replace % em-dash "\u2014")
                     [(includes? en-dash)] #(s/replace % en-dash "\u2013")
                     [(re= opening-double-quote)] #(s/replace % opening-double-quote "$1\u201c$2")
                     [(re= closing-double-quote)] #(s/replace % closing-double-quote "$1\u201d$2")
                     [(re= closing-double-quote-supp)] #(s/replace % closing-double-quote-supp "$1\u201d")
                     [(re= opening-apostrophe)] #(s/replace % opening-apostrophe "$1\u2018$2")
                     [(re= possessive-apostrophe)] #(s/replace % possessive-apostrophe "$1\u2019$2")
                     [(re= closing-apostrophe)] #(s/replace % closing-apostrophe "$1\u2019$3")
                     [(re= shortened-years-apostrophe)] #(s/replace % shortened-years-apostrophe "\u2019$2$3")
                     [(re= backwards-apostrophe)] #(s/replace % backwards-apostrophe "$1\u2019")
                     [(includes? standalone-apostrophe)] #(s/replace % standalone-apostrophe "\u2032")
                     )))
