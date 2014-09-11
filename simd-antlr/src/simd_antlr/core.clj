(ns simd-antlr.core
  (:require [clj-antlr.core :as antlr])
  (:require [clojure.pprint :as pp] )
  (:gen-class))


(def simd-parser (antlr/parser "grammar/Expr.g4"))

(simd-parser 
" var y uint32
  x := y*4
  z := 2/3
  func square(x int) int
  func square(x int) int {
    return x*x
  }
  for x := 0 ; x<0 ; x := x+1  {


 }
")

(def a (list "a" "b"))
(with-meta a {:position 10})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
