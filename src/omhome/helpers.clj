(ns omhome.helpers
  (:require [hiccup.core :refer [html]]
            ))

(defn- make-hiccup-name
  "Construct a hiccup keyword name from the supplied classes & ids"
  [& fragments]
  (keyword (apply str fragments)))

(defn- margin-note
  "Return the Hiccup for a margin note based on the supplied text and note id"
  [text note-id]
  [:label.margin-toggle.sidenote-number {:for note-id}]
  [(make-hiccup-name "input" "#" note-id "." "margin-toggle") {:type "checkbox"}]
  [:span.sidenote text]
  )

(defn make-note 
  "Given note text, create a margin-note, or, if a note-id is present, a side-note, following the Tufte.CSS conventions, in Hiccup format."
  [text note-id numbered?]
  (html (if numbered?
           [:label.margin-toggle.sidenote-number {:for note-id}]
           [:label.margin-toggle {:for note-id} "⊕"])
         [(make-hiccup-name "input" "#" note-id "." "margin-toggle") {:type "checkbox"}]
         (if numbered?
           [:span.sidenote text]
           [:span.marginnote text]))
  )

(defn make-margin-note [text note-id] (make-note text note-id false))
(defn make-side-note [text note-id] (make-note text note-id true))
