(ns omhome.posts-meta)

(def empty-post
  {:title ""
   :post-url "" ;; a custom url for the post
   :post-filepath "" ;; a path to the post data. The filename suffix determines the post format (.md, .html or .clj for hiccup). 
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
          :post-url "/hicplay/"
          :post-filepath "resources/fragments/hicplay.clj") 
   (assoc empty-post
          :title "On VIM"
          :post-url "/onvim/"
          :post-filepath"resources/fragments/onvim.clj")])
