(ns omhome.ssgen
  (:require [stasis.core :as stasis]
            [hiccup.page :refer [html5]]
            [clojure.java.io :as io]))

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

(defn get-pages []
  (merge (stasis/slurp-directory "resources/pages" #".*\.(html|css|js$)")
         (content->pages (stasis/slurp-directory "resources/fragments" #".*\.html$"))))

(def app (stasis/serve-pages get-pages))

