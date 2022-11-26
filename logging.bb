#!/usr/bin/env bb
(require '[taoensso.timbre :as log])

;; ## requirements
;; - support log levels (info, err, ..)
;; - basic logging (one-liners)
;; - configure minimum log level
;; - structured logging (parsable, i.e. JSON)
;;
;; ## considerations
;; ### configure minimum log level
;; mutating global log level via timbre/set-min-level! doesn't
;; seem to be included in babashka. however, minlevels feel superfluous
;; for whatever use-cases babashka is made for (scripts / adhoc-scripts)
;; might be necessary if using babashka in something like AWS Lambda, when
;; spread across several environments, but for general purpose scripting
;; i don't really care
;; 
;; ### structured logging (parsable, i.e. JSON)
;; seems to achieve structured logging, we need to include 
;; https://github.com/viesti/timbre-json-appender, like how we include 
;; logback in Java to support json encoding in log4j.
;; 
;; i don't like having to pull in 3P lib for json, but as per last paragraph,
;; babashka seems mainly for scripting, and json appenders is primarily used
;; to make logs parsable by something like ELK and aws cloudwatch, so not
;; that important.

(log/debug "This is DEBUG")
(log/info "This is INFO")
(log/error "This is ERROR")

(defn set-log-level [level] ;; ugly as shit but couldn't find a better solution
  (alter-var-root #'log/*config* #(assoc %1 :min-level level)))

(set-log-level :info)

(log/debug "This is DEBUG") ;; shouldn't print
(log/info "This is INFO")
(log/error "This is ERROR")

(comment

  (log/debug "This is DEBUG")
  (log/info "This is INFO")
  (log/error "This is an ERROR")

  (log/info {:user "Kevin god damn bacon"})

;; log exception message gracefullyf
  (let [ex (RuntimeException. "Well, this didn't work")]
    (log/error (ex-message ex)))

;; blow it up
  (log/error (RuntimeException. "This is an EXCEPTION"))

  ;;
  )
