#!/usr/bin/env bb
(require '[babashka.wait :as wait]
         '[clojure.java.io :as io])

;; create file to satisfy the test
(spit "/tmp/test-file" "content-123")

;; Wait indefinitely for file
(wait/wait-for-path "/tmp/test-file")
;; => {:path "/tmp/test-file", :took 0}

;; Wait with options
(wait/wait-for-path "/tmp/test-file"
                    {:timeout 1000 ;; timeout ms
                     :pause 500 ;; interval ms between retries
                     :default "timed out"}) ;; returned if timeout is reached
;; cleanup the file
(io/delete-file "/tmp/test-file")

;; Wait for a port to be available
(wait/wait-for-port "localhost" 8000 {:timeout 1000
                                      :pause 500
                                      :default "timed out"})
