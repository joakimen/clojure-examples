#!/usr/bin/env bb
(ns json
  (:require [cheshire.core :as json]))

(defn parse-json [s]
  (json/parse-string s true))

(let [content (slurp "cheshire/movie.json")]
  (->> content parse-json :characters (mapv :name)))
