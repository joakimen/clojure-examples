(require '[clojure.spec.alpha :as s]
         '[clojure.spec.test.alpha :as stest])

;; value validation (true / false)
(s/valid? string? 3) ;; => false
(s/valid? #(> % 0) 1) ;; => true

;; value conformation (value / :clojure.spec.alpha/invalid)
(s/def ::small-num (s/and int? #(< % 10)))
(s/valid? ::small-num 5) ;; => true
(s/conform ::small-num 9) ;; => 9
(s/conform ::small-num 10) ;; => :clojure.spec.alpha/invalid

;; map spec
(def email-regex #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$")
(s/def :acct/email-type (s/and string? #(re-matches email-regex %)))
(s/def :acct/first-name string?)
(s/def :acct/last-name string?)
(s/def :acct/email :acct/email-type)

(s/def :acct/person (s/keys :req [:acct/first-name :acct/last-name :acct/email]
                            :opt [:acct/phone]))

;; map validation (namespaced keys)
(s/valid? :acct/person {:acct/first-name "Joakim"
                        :acct/last-name "Engeset"
                        :acct/email "hey@there.no"}) ;; => true

;; explain validation problems
(s/explain :acct/person {:acct/first-name "Joakim"})
;; #:acct{:first-name "Joakim"} - failed: (contains? % :acct/email) spec: :acct/person

;; map validation (unnamespaced keys)
(s/def :unq/person (s/keys :req-un [:acct/first-name :acct/last-name :acct/email]
                           :opt-un [:acct/phone]))

(s/valid? :unq/person {:first-name "Joakim"
                       :last-name "Engeset"
                       :email "hey@there.no"}) ;; => true

;; validating a spec in a function
(defn persist-person
  [person] {:pre [(s/valid? :acct/person person)]
            :post [s/valid? string?]}
  (str "persisting person to db: " (:acct/email person)))

(persist-person {:acct/first-name "Joakim"
                 :acct/last-name "Engeset"
                 :acct/email "joakim@mail.com"})

;; validating functions
(defn double-it [n]
  (* n 2))
(s/fdef double-it
  :args (s/cat :n (s/and int? #(< 0 %))))
(stest/instrument `double-it)
(stest/abbrev-result (first (stest/check `add-one)))
(stest/check `add-one)
(double-it nil) ;; nil - failed: int? at: [:n]
(double-it 0) ;; 0 - failed: (< 0 %) at: [:n] 
(double-it 3) ;; => 6
