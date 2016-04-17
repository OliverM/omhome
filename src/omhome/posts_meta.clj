(ns omhome.posts-meta)

(def empty-post
  {:title ""
   :post-url "" ;; if set, a custom url for the post. Otherwise the post url will mirror its file-path
   :post-filepath "" ;; a path to the post data. The filename suffix determines the post format (.md, .html or .clj for hiccup). The file-path & file-name (sans suffix) sets the default post url.
   :publish false ;; set to true to include the post in the generated output, indices etc
   :date ""
   :category ""
   :tags []
   :author "Oliver Mooney" 
   })

(def posts
  [
   (assoc empty-post
          :title "Test meta hiccup post"
          :post "/resources/fragments/hicplay.clj") 
   ])
