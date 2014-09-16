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
   program = statement+
   <statement> = var_decl | affectation | for_statement | while_statement | if_statement | func_decl | return_statement | func_call

  primaryexpr = operand | func_call
  operand = identifier | int_lit | float_lit
 

  expression = unaryexpr | expression binary_op unaryexpr 
  unaryexpr  = primaryexpr | unary_op unaryexpr 

  binary_op  = '||' | '&&' | rel_op | add_op | mul_op 
  rel_op     = '==' | '!=' | '<' | '<=' | '>' | '>=' 
  add_op     = '+' | '-' | '|' | '^' .
  mul_op     = '*' | '/' | '%' | '<<' | '>>' | '&' | '&^' 

  unary_op   = '+' | '-' | '!' | '^' | '*' | '&' | '<-' 
  
  var_decl = <'var'>  identifier_list type_name [<'='>  expression] 
  identifier_list = identifier { <','> identifier } 

  type_name :   user_type | builtin_type  

  builtin_type : 'uint8' | 'uint16' | 'uint32' | 'int8' | 'int16' | 'int32' 
  user_type : identifier
  
  
  block = <'{'> statement* <'}'>
  affectation = identifier  ':=' expression

  for_statement = 'for' affectation ';' expression ';' affectation  block
  while_statement = 'while' expression   block
  if_statement = 'if' expression   block else_statement?
  else_statement = 'else'    block

  func_parameter =  identifier_list type_name
  func_parameter_list: func_parameter {',' func_parameter}

  func_decl =  'func'  identifier <'('> func_parameter_list  <')'> type_name block?

  return_statement ='return' expression

   func_call =identifier <'('>  identifier_list <')'>

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

;;(go-parser "for go 1000 0xCAFE 0777  1.8  a .25  \"this is astring\" 
;;var i uint32
;;") ;; 1.e2 valid go, not here

(go-parser "var i uint32")
(go-parser "var i uint32 = 3")
(go-parser "
var i,j  uint32 = 3*4
var k int
k := i+3

for i :=i ; i<10 ; i := i+1 {
  k := 2*i

}



")

(go-parser "
if i<3 {
 x := 1
} else {
 x := 2
}
")


(go-parser "var i uint8
")

(insta/visualize (go-parser "func test(i uint8,j uint8) uint8 {
    print(i)
    return i*j
}
"))

(go-parser "func test(i uint8,j uint8) uint8 {
   print(j)
   return i*j
}
")

