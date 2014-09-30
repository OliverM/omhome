(ns omhome.ssgen
  (:require [stasis.core :as stasis]
            [hiccup.page :refer [html5]]
            [me.raynes.cegdown :as md]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(defn layout-page [page]
  (html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale 1.0"}]
     [:title "Everything is Placeholder"]
     [:link {:rel "stylesheet" :href "/css/pages.css"}]]
    [:body
     [:div.logo "olivermooney.com"]
     [:div.body page]]))

(defn content->pages [pages]
  (zipmap (keys pages)
          (map layout-page (vals pages))))

;; rework the stasis map of filenames to source content created using markdown to use paths and html
(defn markdown->pages [markdown-content]
  (zipmap (map #(str/replace % #"\.md$" "/") (keys markdown-content)) ;; convert paths ending in .md to paths ending in /
          (map #(layout-page (md/to-html %)) (vals markdown-content)))) ;; convert markdown to html

(defn get-pages []
  (stasis/merge-page-sources
    {:pages (stasis/slurp-directory "resources/pages" #".*\.(html|css|js$)")
     :templated-pages (content->pages (stasis/slurp-directory "resources/fragments" #".*\.html$"))
     :markdown-pages (markdown->pages (stasis/slurp-directory "resources/markdown" #".*\.md$"))}))

(def app (stasis/serve-pages get-pages))

