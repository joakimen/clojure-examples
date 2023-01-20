(ns yaml)

;; parse yaml
(clj-yaml.core/parse-string (slurp "yaml/movie.yaml"))

;; produce yaml
(let [movie
      {:title "home alone"
       :characters [{:name "kevin" :age 8}
                    {:name "marv" :age 42}]}]
  (clj-yaml.core/generate-string movie))
