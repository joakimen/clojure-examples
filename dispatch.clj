(ns dispatch
  "example of using babashka.cli/dispatch"
  (:require [babashka.cli :as cli]
            [clojure.string :as str]))

(defn greet [{:keys [opts]}]
  (println "Greetings," (:name opts)))

(defn rev [{:keys [opts]}]
  (println (str/reverse (:name opts))))

(defn help [_]
  (println "help!"))

(def subcommands
  [{:cmds ["greet"] :fn greet :args->opts [:name]}
   {:cmds ["reverse"] :fn rev}
   {:cmds [] :fn help}])

(defn -main [& args]
  (cli/dispatch subcommands args))

(apply -main *command-line-args*)

(comment

  (apply -main ["greet" "kevin"])
  ;;
  )
