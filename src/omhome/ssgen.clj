(ns omhome.ssgen
  (:require [stasis.core :as stasis]
            [ring.middleware.default-charset :refer [wrap-default-charset]]
            [hiccup.page :refer [html5]]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [optimus.export]
            [optimus.assets :as assets]
            [optimus.link :as link]
            [optimus.html :as html]
            [optimus.optimizations :as optimisations]
            [optimus.prime :as optimus]
            [optimus.strategies :refer [serve-live-assets]]
            [omhome.transformations :refer [highlight-code-blocks smartypants]]
            [omhome.indices :refer [topic-index]]
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
      [:meta {:charset "UTF-8"}]
      [:meta {:name    "viewport"
              :content "width=device-width, initial-scale=1.0"}]
      (if (:title options)
        [:title (:title options)]
        [:title "Everything is Placeholder"])
      (html/link-to-css-bundles req ["blog.css"])]
     [:body
      [:div.logo "olivermooney.com"]
      [:div.body page]])))

(defn meta-post->page-loc
  "Convert a meta-post to a pair of a HTML page and a URI fragment. See omhome.meta-post/empty-post for a default post structure."
  [meta-post]
  [(meta-post :post-url)
   (fn [req] (layout-page req (:post meta-post) ))])

(defn meta-posts->page-map
  "Convert a vector of meta-posts into a map of URI fragment and HTML page pairs, suitable for inclusion in Stasis' page map."
  [meta-posts]
  (into {} (map meta-post->page-loc meta-posts)))

(defn indices
  "Generate a map of index URLs to topic indices & the general index"
  []
  {"/index.html" (fn [req] (layout-page req (topic-index))) })

(defn get-basic-pages []
  (stasis/merge-page-sources
   {:indices (indices)
    :posts (meta-posts->page-map posts)
    }))

(defn prepare-page [page req]
  "Final steps applied to a HTML post."
  (-> (if (string? page) page (page req))
      smartypants
      highlight-code-blocks ;; order is important! Smartypants ignores children of pre and code blocks; code-transform mucks with this structure, so must be subsequent.
      ))

(defn prepare-pages [pages]
  "Apply a final processing step to all generated HTML posts."
  (zipmap (keys pages)
          (map #(partial prepare-page %) (vals pages))))

(defn final-pages []
  (prepare-pages (get-basic-pages)))

(def app
  (-> (stasis/serve-pages final-pages)
      (optimus/wrap get-assets optimisations/none serve-live-assets)
      (wrap-default-charset "UTF-8")))

(def export-dir "dist")

(defn export []
  (let [assets (optimisations/all (get-assets) {})]
    (stasis/empty-directory! export-dir)
    (optimus.export/save-assets assets export-dir)
    (stasis/export-pages (final-pages) export-dir {:optimus-assets assets})))
