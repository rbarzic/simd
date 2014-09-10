(ns insta-simd.core
  (:require [instaparse.core :as insta]))




;;First we try to define a small parser for a go-like language...(Under construction)

; know deviations from go language :
; - no unicode support
; - floating point parsing : 1.e2 not recognize
; - ...
(def go-parser
  (insta/parser
   "
   program = token+
   <token> = var_decl 

  PrimaryExpr = Operand
  Operand = identifier | int_lit | float_lit


  Expression = UnaryExpr | Expression binary_op UnaryExpr 
  UnaryExpr  = PrimaryExpr | unary_op UnaryExpr 

  binary_op  = '||' | '&&' | rel_op | add_op | mul_op 
  rel_op     = '==' | '!=' | '<' | '<=' | '>' | '>=' 
  add_op     = '+' | '-' | '|' | '^' .
  mul_op     = '*' | '/' | '%' | '<<' | '>>' | '&' | '&^' 

  unary_op   = '+' | '-' | '!' | '^' | '*' | '&' | '<-' 
  
  var_decl = <'var'>  identifier_list type_name [<'='>  Expression] 
  identifier_list = identifier { <','> identifier } 
  type_name = identifier
  

  letter = #'[a-zA-Z_]'
   identifier = #'[a-zA-Z0-9_]+'
   decimal_digit = #'[0-9]'
   <int_lit>     = decimal_lit | octal_lit | hex_lit 
   
   (* Go grammar : decimals '.' [decimals].... *)
   float_lit = decimals '.' decimals  [ exponent ] | decimals exponent | '.'  decimals [ exponent ] 
   <decimals>  = #'[0-9]+'
   <exponent>  = ( 'e' | 'E' ) [ '+' | '-' ] decimals 
   

   decimal_lit = #'[0-9]+'
   octal_lit   = #'0[0-7]+'
   hex_lit     = #'0x[0-9a-fA-F]+'

  string_lit = #'\"[\\S|\\s]+\"'  
  



"
   :auto-whitespace :standard
))

(test-parser "x and y")

(words-and-numbers "klm")
;;(go-parser "for go 1000 0xCAFE 0777  1.8  a .25  \"this is astring\" 
;;var i uint32
;;") ;; 1.e2 valid go, not here

(go-parser "var i uint32")
(go-parser "var i uint32 = 3")
(go-parser "var i,j  uint32 = 3*4")
