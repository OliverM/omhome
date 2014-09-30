(ns omhome.ssgen
  (:require [stasis.core :as stasis]
            [hiccup.page :refer [html5]]
            [me.raynes.cegdown :as md]
            [omhome.highlight :refer [highlight-code-blocks]]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [ring.util.response :as r]))

(defn layout-page [page]
  (html5
    [:head
     [:meta {:http-equiv "Content-Type" :content "text/html; charset=UTF-8"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1.0"}]
     [:title "Everything is Placeholder"]
     [:link {:rel "stylesheet" :href "/css/pygments.css"}]]
    [:body
     [:div.logo "olivermooney.com"]
     [:div.body page]]))

(defn content->pages [pages]
  (zipmap (keys pages)
          (map layout-page (vals pages))))

(def pegdown-options ;; see https://github.com/sirthias/pegdown for supported list, and https://github.com/Raynes/cegdown for examples
  [:autolinks :fenced-code-blocks :strikethrough])          ;; TODO: leaving off the :smartypants option for now (circle back when the site is live)

;; rework the stasis map of filenames to source content created using markdown to use paths and html
(defn markdown->pages [markdown-content]
  (zipmap (map #(str/replace % #"\.md$" "/") (keys markdown-content)) ;; convert paths ending in .md to paths ending in /
          (map #(layout-page (md/to-html % pegdown-options)) (vals markdown-content)))) ;; convert markdown to html

(defn get-basic-pages []
  (stasis/merge-page-sources
    {:pages (stasis/slurp-directory "resources/pages" #".*\.(html|css|js$)")
     :templated-pages (content->pages (stasis/slurp-directory "resources/fragments" #".*\.html$"))
     :markdown-pages (markdown->pages (stasis/slurp-directory "resources/markdown" #".*\.md$"))}))

(defn highlight-code [pages]
  (zipmap (keys pages)
          (map #(highlight-code-blocks %) (vals pages))))

(defn prepare-pages []
  (highlight-code (get-basic-pages)))

(def app (stasis/serve-pages prepare-pages))

