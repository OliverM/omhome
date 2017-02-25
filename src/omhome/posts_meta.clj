(ns omhome.posts-meta
  (:require [me.raynes.cegdown :as md]
            [omhome.helpers :refer :all]
            ))

(def pegdown-options ;; see https://github.com/sirthias/pegdown for supported
                     ;; list, and https://github.com/Raynes/cegdown for examples
  [:autolinks :fenced-code-blocks :strikethrough :smartypants])

(def markdown-parser (md/make-pegdown pegdown-options))

(defn hiccup->html
  "Convert a post in hiccup format to HTML. Evaluates helpers in the file to
  transform Hiccup as required."
  [hiccup-post]
  (binding [*ns* (find-ns (symbol "omhome.posts-meta"))]
    (eval (read-string  hiccup-post))))

(defn- gen-post
  "Generate the post content from the post file location and suffix."
  [post]
  (let [raw-post (slurp (:post-filepath post))
        filepath (:post-filepath post)
        post-content
        (cond
          (re-find #"\.md$" filepath) (md/to-html raw-post markdown-parser)
          (re-find #"\.clj$" filepath) (hiccup->html raw-post)
          (re-find #"\.html$" filepath) raw-post
          :else raw-post)]
    (assoc post :post post-content)))

(def empty-post
  {:title ""
   :post-url "" ;; a custom url for the post
   :post-filepath "" ;; a path to the post data. The filename suffix determines
                     ;; the post format (.md, .html or .clj for hiccup).
   :post nil ;; the post content, transformed from its underlying representation
             ;; by gen-post
   :publish false ;; set to true to include the post in the generated output,
                  ;; indices etc
   :date nil ;; TODO: should be a datetime representation easily transformed
             ;; into the html4 datetime type
   :category ""
   :tags []
   :author "Oliver Mooney"
   })

(def tagset #{}) ;; TODO any tags cited in meta post data but not found here
                 ;; will raise an error (to help catch tag typos)

(def raw-posts
  [(assoc empty-post
          :title "Markdown that's only HTML"
          :post-url "/markdown-thats-only-html/"
          :post-filepath "resources/markdown/mdthatsonlyhtml.md")
   (assoc empty-post
          :title "Markdown Playground"
          :post-url "/markdown-playground/"
          :post-filepath "resources/markdown/playground.md")
   (assoc empty-post
          :title "Sample Markdown post"
          :post-url "/sample-markdown-post/"
          :post-filepath "resources/markdown/sample-markdown-post.md")
   (assoc empty-post
          :title "About"
          :post-url "/about/"
          :post-filepath "resources/fragments/about.html")
   (assoc empty-post
          :title "HTML Playground"
          :post-url "/html-playground/"
          :post-filepath "resources/fragments/htmlplayground.html")
   (assoc empty-post
          :title "On Twitter"
          :post-url "/on-twitter/"
          :post-filepath "resources/fragments/ontwitter.html"
          :publish true)
   (assoc empty-post
          :title "Test meta hiccup post"
          :post-url "/hicplay/"
          :post-filepath "resources/fragments/hicplay.clj")
   (assoc empty-post
          :title "On VIM"
          :post-url "/onvim/"
          :post-filepath "resources/fragments/onvim.clj"
          :publish true)
   (assoc empty-post
          :title "Freeform expression in online editors"
          :post-url "/online-editors-curtailing-expression/"
          :post-filepath "resources/fragments/are-editors-good.clj"
          :publish true)])

(defn posts*
  "Generate the post list with converted content from the raw posts vector"
  [raw-posts]
  (doall  (map gen-post raw-posts )))

(def posts (posts* raw-posts)) ;; if a hiccup post is malformed, the error
                               ;; bubbles up to here. Catch it closer to the
                               ;; post? FIXME
