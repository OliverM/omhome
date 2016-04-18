(ns omhome.ssgen
  (:require [stasis.core :as stasis]
            [hiccup.page :refer [html5]]
            [me.raynes.cegdown :as md]
            [omhome.highlight :refer [highlight-code-blocks]]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [optimus.export]
            [optimus.assets :as assets]
            [optimus.link :as link]
            [optimus.html :as html]
            [optimus.optimizations :as optimisations]
            [optimus.prime :as optimus]
            [optimus.strategies :refer [serve-live-assets]]

            [omhome.helpers :refer :all]
            [omhome.posts-meta :refer [posts]])
  (:import [java.io.File]))

(defn get-assets []
  (concat
    (assets/load-assets "public/css" [#".*\.(eot$|svg$|ttf$|woff$)"])
    (assets/load-bundle "public/css" "blog.css" [#".*\.css$"])
    ))

(defn layout-page
  ([req page] (layout-page req page {}))
  ([req page options]
   (html5
     [:head
      [:meta {:http-equiv "Content-Type" :content "text/html; charset=UTF-8"}]
      [:meta {:name    "viewport"
              :content "width=device-width, initial-scale=1.0"}]
      (if (:title options)
        [:title (:title options)]
        [:title "Everything is Placeholder"])
      (html/link-to-css-bundles req ["blog.css"])
      ]
     [:body
      [:div.logo "olivermooney.com"]
      [:div.body (binding [*ns* (find-ns (symbol "omhome.ssgen"))] (eval page))]])))

(defn content->pages [pages]
  (zipmap (keys pages)
          (map #(fn [req] (layout-page req %))
               (vals pages))))

(def pegdown-options ;; see https://github.com/sirthias/pegdown for supported list, and https://github.com/Raynes/cegdown for examples
  [:autolinks :fenced-code-blocks :strikethrough :smartypants])

(defn meta-post->page-loc
  "Convert a meta-post to a pair of a HTML page and a URI fragment. See omhome.meta-post/empty-post for a default post structure."
  [meta-post]
  [(-> meta-post :post-url
       ;; File. .getAbsolutePath
       ;; (str/replace  #"\.clj$" "/")
       ) ;; Stasis' page map requires absolute paths to files
   (fn [req] (layout-page req (read-string (slurp (:post-filepath meta-post))) meta-post))])

(defn meta-posts->page-map
  "Convert a vector of meta-posts into a map of URI fragment and HTML page pairs, suitable for inclusion in Stasis' page map."
  [meta-posts]
  (into {} (map meta-post->page-loc meta-posts)))

(defn hiccup->pages
  "Generate HTML pages from the supplied Hiccup fragments."
  [hiccup-content]
  (zipmap (map  #(str/replace % #"\.clj$" "/") (keys hiccup-content))
          (map #(fn [req] (layout-page req (-> % read-string ))) (vals hiccup-content))))

;; rework the stasis map of filenames to source content created using markdown to use paths and html
(defn markdown->pages [markdown-content]
  (zipmap (map #(str/replace % #"\.md$" "/") (keys markdown-content)) ;; convert paths ending in .md to paths ending in /
          (map #(fn [req] (layout-page req (md/to-html % pegdown-options)))
               (vals markdown-content)))) ;; convert markdown to html

(defn get-basic-pages []
  (stasis/merge-page-sources
   {:templated-pages (content->pages (stasis/slurp-directory "resources/fragments" #".*\.html$"))
    :templated-hiccup (meta-posts->page-map posts)
     ;; :templated-js-pages (js-content->pages (stasis/slurp-directory "resources/cljspages" #".*\.html$"))
     :markdown-pages (markdown->pages (stasis/slurp-directory "resources/markdown" #".*\.md$"))}))

(defn prepare-page [page req]
  (-> (if (string? page) page (page req))
      highlight-code-blocks))

(defn prepare-pages [pages]
  (zipmap (keys pages)
          (map #(partial prepare-page %) (vals pages))))

(defn final-pages []
  (prepare-pages (get-basic-pages)))

(def app
  (optimus/wrap (stasis/serve-pages final-pages)
                get-assets
                optimisations/all
                serve-live-assets))

(def export-dir "dist")

(defn export []
  (let [assets (optimisations/all (get-assets) {})]
    (stasis/empty-directory! export-dir)
    (optimus.export/save-assets assets export-dir)
    (stasis/export-pages (final-pages) export-dir {:optimus-assets assets})))
