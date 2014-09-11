grammar Expr;

/** The start rule; begin parsing here. */
prog:   stat+ ;

stat:   expr NEWLINE        
    |   affectation NEWLINE
    |   var_decl NEWLINE         
    |   func_decl NEWLINE
    |   for_statement NEWLINE 
    |   return_statement NEWLINE
    |   NEWLINE             
    ;

expr:   expr ('*'|'/') expr                         # mul_div_expr
    |   expr ('+'|'-') expr                         # plus_minus_expr
    |   expr ('<'|'>'|'=='|'<='|'>='|'!=') expr     # comp_expr
    |   INT                                         # int_expr
    |   ID                                          # id_expr
    |   '(' expr ')'                                # complex_expr
    ;

affectation: ID ':=' expr ; 

block: '{' stat* '}';

// for_statement: 'for' affectation ';' expr ';' affectation  block;  
for_statement: 'for' affectation ';' expr ';' affectation  block;  

identifier_list: ID  (',' ID)*;

return_statement: 'return' expr?;
var_decl: 'var' identifier_list type ;

func_parameter: identifier_list type;
func_parameter_list: func_parameter (',' func_parameter)*;

func_decl: 'func' ID '(' func_parameter_list  ')' type block?;

type: builtin_type |  user_type ;

builtin_type : 'uint8' | 'uint16' | 'uint32' | 'int8' | 'int16' | 'int32' ;
user_type : ID;

ID  :   [a-zA-Z]+ ;      // match identifiers <label id="code.tour.expr.3"/>
INT :   [0-9]+ ;         // match integers
NEWLINE:'\r'? '\n' ;     // return newlines to parser (is end-statement signal)
WS  :   [ \t]+ -> skip ; // toss out whitespace
