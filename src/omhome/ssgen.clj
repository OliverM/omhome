(ns omhome.ssgen
  (:require [stasis.core :as stasis]))

(defn get-pages []
  (stasis/slurp-directory "resources/pages" #".*\.(html|css|js)"))

(def app (stasis/serve-pages get-pages))

