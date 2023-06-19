(ns yaml
  (:require [clj-yaml.core :as yaml]))

;; parse yaml
(yaml/parse-string (slurp "yaml/movie.yaml"))

;; produce yaml
(let [movie
      {:title "home alone"
       :characters [{:name "kevin" :age 8}
                    {:name "marv" :age 42}]}]
  #_{:clj-kondo/ignore [:unresolved-var]}
  (yaml/generate-string movie))
