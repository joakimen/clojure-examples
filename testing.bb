#!/usr/bin/env bb
(require '[clojure.test :as t]
         '[clojure.test.check :as tc]
         '[clojure.test.check.properties :as prop]
         '[clojure.test.check.generators :as gen])

;; ideas
;; - assertions
;; - running tests from current file. since this is for bb and not full clojure i'm not
;;   gonna care about classpaths, namespaces and project hierarchies
;; - property/generative test
;; - generative tests based on defined spec
;;   clojure.spec is currently not bundled with bb, but borkdude is working on it

(defn sum
  "return sum of x and y"
  [x y]
  (+ x y))

(comment

;; traditional assert
  (t/is (= 3 (sum 1 2))) ;; => true

;; table driven test (works, but only returns false if all tests fail)
  (t/are [x y out] (= (sum x y) out)
    2 2 4
    2 5 7
    -1 -2 -3)

;; define a test
  (t/deftest simple-test
    (t/is (= 3 (sum 1 2))))

;;   run all tests in current namespace
  (t/run-tests) ;; => {:test 2, :pass 3, :fail 1, :error 0, :type :summary}

;; define a test with context-strings
  (t/deftest sum-tests
    (t/testing "arithmetic"
      (t/testing "with positive nums"
        (t/is (= 3 (sum 1 2)))
        (t/is (= 3 (sum 2 8)))) ;; fails
      (t/testing "with negative nums"
        (t/is (= -4 (sum -1 -3))))))

;; run it and we get a nice output
  (t/run-test sum-tests) ;; => {:test 1, :pass 2, :fail 1, :error 0, :type :summary}
;; FAIL in (sum-tests) (/Users/../testing.bb:1)
;; arithmetic with positive nums
;; expected: (= 3 (sum 2 8))
;;   actual: (not (= 3 10))


;;   PROPERTY TESTING

;; generate random numbers in a range (inclusive)
  (let [nums (gen/choose 1 5)]
    (prn
     (gen/sample nums) ;; => (5 3 5 4 3 1 1 2 1 3)
     (gen/sample nums 3))) ;; => (2 3 2)

;; selecting samples from defined list
  (let [editors (gen/elements ["emacs" "elvis" "nano" "vi" "vim" "nvim" "helix"])]
    (gen/sample editors 3)) ;; ("elvis" "nano" "vi")            

;; generate a random map
  (let [rand-map (gen/map (gen/one-of [gen/keyword]) gen/string)]
    (gen/sample rand-map 3))
;; ({}
;;  {:n "\"nÙ", :fI. "\""}
;;  {:ci2 "Ø!", :P.- "Õ", :* "", :iF "@ð", :q69 "3>"})

  (gen/sample (gen/vector gen/int))

;; run ad-hoc property test, using generated input
  (tc/quick-check 1000
                  (prop/for-all [x gen/int y gen/int]
                                (= (+ x y)
                                   (sum x y))))
;; => {:result true, :pass? true, :num-tests 1000, :time-elapsed-ms 4, :seed 1669983102435}

;; Later, when clojure.spec is included with babashka
;; - specify, validate and conform data structure
;; - generate test-data based on defined spec
  )
