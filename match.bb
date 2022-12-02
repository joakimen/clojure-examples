#!/usr/bin/env bb
(require '[clojure.core.match :as m]
         '[clojure.string :as str])

;; patterns are described using literal strings: #"ban\w{1}na"

(comment

;; find pattern - absolute match
  (re-matches #"I'm \d+ years old" "I'm 12 years old") ;; => "I'm 12 years old"


;; find pattern - capture groups (returns vector)
  (re-matches #"group 1: (\w+), group 2: (\w+)" "group 1: donald, group 2: duck")
  (let [[orig one two] ;; destructure vector
        (re-matches #"(\d+) bottles of (\w+) on the wall" "12 bottles of beer on the wall")]
    (println "orig:" orig)    ;; orig: 12 bottles of beer on the wall
    (println "group 1:" one)  ;; group 1: 12
    (println "group 2:" two)) ;; group 2: beer

;; find pattern - substring
  (re-find #"\d+" "You have 17 gold coins!") ;;=> "17"
  (re-find #"\d{8}" "My phone number is 31418024") ;; => "31418024"

  (if (re-matches #"\d+ beers" "3 beers")
    "beer!"
    "no beer :(") ;; => "beer!"



;; find substring
  (str/includes? "Find a needle in a haystack." "needle") ;;=> true
  (re-find #"needle" "Find a needle in a haystack.") ;;=> "needle"
  (re-find #"n\w+dle" "Find a noodle in a haystack.") ;;=> "noodle"

;; can also be used as conditional (no match -> nil -> false)
  (if (re-find #"n\w+dle" "Find a nooooooooooooooodle in a haystack.")
    "match"
    "no match") ;; => "match"


;; clojure.match
;; - underscore _ is a wildcard
;; - arg 1: vector containing data to be tested
;; - arg 2: vararg of match-condition/result pairs (like a switch-statement)

;; fizzbuzz
  (doseq [n (range 1 16)]
    (println
     (m/match [(mod n 3) (mod n 5)]
       [0 0] "FizzBuzz"
       [_ 0] "Buzz"
       [0 _] "Fizz"
       [_ _] n)))
;;
  )
