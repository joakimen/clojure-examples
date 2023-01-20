#!/usr/bin/env bb
(ns process
  (:require [babashka.process :as p]))

;; basic usage (synchronous command/response)
(p/sh "ls -al") ;; returns response object 
(p/shell "ls -al") ;; prints result to stdout/stderr

;; starting and killing a background task
(let [proc (p/process "python3 -m http.server")]
  (println "starting server")
  (Thread/sleep 5000)
  (println "shutting down server")
  (p/destroy-tree proc)) ;; kill process and descendants

;; piping between stdout and stdin
(-> (p/pipeline
     (p/pb "echo hey")
     (p/pb "rev"))
    last :out slurp) ;; => "yeh\n"

;; pass data directly to stdin
(let [proc (p/process ["rev"]
                      {:in "reverse me" :err :inherit
                       :out :string})]
  (:out @proc))
