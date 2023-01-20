#!/usr/bin/env bb
(ns fzf
  (:require [babashka.process :refer [process]]
            [clojure.string :as str]))

(defn- _fzf
  "pass input-string to fzf, deref the process and return stdout"
  [in]
  (let [proc (process ["fzf" "-m"]
                      {:in in :err :inherit
                       :out :string})]
    (-> @proc :out)))

(defn fzf
  "pass a vector of elements to choose between to [[_fzf]]"
  [vec]
  (_fzf (str/join "\n" vec)))

(comment
  ;; to call from cli, read from stdin like this
  (fzf (slurp *in*))

;;   
  )
