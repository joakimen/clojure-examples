(ns core)

(defn start
  "start a feature"
  [args]
  (println "starting feature with args:" args))

(defn conclude
  "conclude a feature"
  [args]
  (println "concluding feature " (:feature args)))
