#!/usr/bin/env bb
(require '[babashka.cli :as cli])

;; basic requirements:
;; - named arguments
;; - positional arguments
;; - short and long argument names
;; - bool (verbose, force, ..)
;; - int (port, threads, ..)
;; - string (hostname, username, ..)
;;
;; bonus capabilities:
;; - positional args 
;; - validation function

;; these are the rules that will be applied to cli args
(defn port? [port]
  (<= 1 port 65535))

(def rules
  {:args->opts [:hostname] ;; positional args (hostname is first arg)
   :alias {:p :port ;; adds shorthand (long is kept)
           :v :verbose}
   :coerce {:hostname :string ;; type coercion
            :port :long
            :verbose :boolean}
   :exec-args {:verbose false ;; default-values
               :hostname "localhost"}
   :require [:port] ;; required args
   :validate {:port port?}
;;   :restrict [:port :verbose :hostname] ;; throw error if user supplies opts not in this list
   })

(defn parse-opts [opts rules]
  "wrapper-func so we can supply test-args in (comment) -block"
  (cli/parse-opts opts rules))

;; supplied from cli
(println (parse-opts *command-line-args* rules))

(comment

  ;; default args
  (parse-opts ["localhost" "--port" "1" "--verbose"] rules)
  ;; => {:verbose true, :hostname "localhost", :port 1}

  ;; disallow unspecified args
  (parse-opts ["localhost" "--port" "1" "--verbose" "--user" "kevin bacon"]
              (merge rules {:restrict [:port :verbose :hostname]}))
  ;; => clojure.lang.ExceptionInfo: Unknown option: :user user xyz/babashka-examples/tools-cli.bb:3:3

  ;;
  )
