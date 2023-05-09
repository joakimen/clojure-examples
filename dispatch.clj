(ns dispatch
  "example of using babashka.cli/dispatch"
  (:require [babashka.cli :as cli]
            [clojure.string :as str]))

(defn greet [{:keys [opts]}]
  (println "Greetings," (:name opts)))

(defn reverse [{:keys [opts]}]
  (println (str/reverse (:name opts))))

(defn help [_]
  (println "help!"))

(def subcommands
  [{:cmds ["greet"] :fn greet :args->opts [:name]}
   {:cmds ["reverse"] :fn reverse}
   {:cmds [] :fn help}])

(defn -main [& args]
  (cli/dispatch subcommands args))

(apply -main *command-line-args*)

(comment

  (apply -main ["greet" "kevin"])
  ;;
  )
