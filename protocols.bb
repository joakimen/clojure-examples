#!/usr/bin/env bb

(comment
  "
  A protocol is a named set of named methods and their 
   signatures that a datatype should support.

  In OOP terms:
  - defprotocol: Defines an interface (protocol)
  - defrecord: Defines a class that implements interface (protocol)
  - def: Instantiates a class (record)
  - reify: Instantiates a class (record) and overriding certain behaviour (functions)

  Note:
   A record is basically treated as a map with rules and behaviour attached to it
   (:name donald) => 'Donald'
   (walk donald) => 'waddle waddle'
"
;; define "interface" (first param is self/this, unused here)
  (defprotocol Quacks
    (quack [_] "should say something in ducky fashion"))

;; define "class"
  (defrecord Duck [name]
    Quacks
    (quack [_] (str "I'm " name " and I quack! QUACK!")))

;;   create a duck, then quack 
  (def donald (->Duck "Donald"))
  (quack donald) ;; => "I'm Donald and I quack! QUACK!"


  (defprotocol Walks
    (quack [_] "should walk like a duck"))

;;   add Walk to Duck
  (extend-type Duck Walks
               (walk [_] "waddle waddle"))

;; make donald walk (note, donald can now walk, even though 
;;   we extended Duck with Walk after donald was instantiated)
  (walk donald) ;; => "waddle waddle"
  )
