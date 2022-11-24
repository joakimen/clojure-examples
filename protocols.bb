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
  (quack donald)

  (defprotocol Walks
    (quack [_] "should walk like a duck"))

;;   add Walk to Duck
  (extend-type Duck Walks
               (walk [_] "waddle waddle"))

;; make donald walk
  (walk donald))
