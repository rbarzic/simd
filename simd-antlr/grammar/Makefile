GRAMMAR=Expr
INCLUDED_GRAMMAR_FILES=Def58LexerGrammar

JAVA := $(GRAMMAR)BaseListener.java  
JAVA += $(GRAMMAR)Lexer.java  
JAVA += $(GRAMMAR)Listener.java  
JAVA += $(GRAMMAR)Parser.java
TEST  ?= def_examples/simple.def

antlr:
	antlr4 $(GRAMMAR).g4

$(JAVA): $(GRAMMAR).g4
	antlr4 $(GRAMMAR).g4


javacomp: $(JAVA)
	javac $(JAVA)

test: javacomp
	grun Def58Grammar def  -tree $(TEST) 2>&1 | tee debug.txt

test_notree: javacomp
	grun Def58Grammar def  $(TEST) 2>&1 | tee debug.txt



# test with color : make test 2>&1  | spc -c spc.cfg
#  watch --color --interval=4 "make test 2>&1  | spc -c spc.cfg"
check-syntax:
	antlr4 $(GRAMMAR).g4
