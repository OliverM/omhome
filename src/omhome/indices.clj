(ns omhome.indices
  (:require [net.cgrand.enlive-html :as html]
            [omhome.posts-meta :as posts]))

(defn enlive->html
  "Convert the enlive-transformed templates into html using a Java StringBuilder"
  [nodes]
  (let [buf (StringBuilder.)]
    (doseq [i nodes]
      (.append buf i))
    (.toString buf)))

(html/deftemplate topic-index* "fragments/index-template.html" [tag]
  [:section :> :p] (html/content "Introductory content for index placed by index generator."))

(defn topic-index
  "Generate a html index of pages for the supplied tag, or complete page index if not tags are supplied."
  ([]
   (topic-index nil))
  ([tag]
   (enlive->html (topic-index* tag))))
