/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectolenguajes2;

import java.util.ArrayList;

public class Parser {
    private final ArrayList<Token> tokens;
    private int currentTokenIndex;
    private Token currentToken;
    private boolean syntaxError;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
        this.currentToken = null;
        this.syntaxError = false;
    }

    public void parse() {
        declarationList();

        if (!syntaxError && currentTokenIndex == tokens.size()) {
            System.out.println("Program successfully parsed.");
        } else {
            System.out.println("Syntax error: Program is syntactically incorrect.");
        }
    }

    private void declarationList() {
        // declaration_list -> declaration declaration_list | ε
        while (currentTokenIndex < tokens.size()) {
            currentToken = tokens.get(currentTokenIndex);
         
            // Check for main function
            if (match(Token.KEYWORD, "main")) {
                System.out.println("Found main function");
                match(Token.OPERATOR, "(");
                match(Token.OPERATOR, ")");
                match(Token.OPERATOR, "{");
                statementList();
                match(Token.OPERATOR, "}");
            } else {
                declaration();
            }
        }
    }

    private void declaration() {
        // declaration -> type_specifier identifier ';'
        typeSpecifier();
        identifier();
        match(Token.OPERATOR, ";");
    }

    private void typeSpecifier() {
        // type_specifier -> 'int' | 'float' | 'char' | 'double'
        if (match(Token.TYPE_SPECIFIER)) {
            System.out.println("Found type specifier: " + currentToken.getTokenValue());
        } else {
            syntaxError = true;
        }
    }

    private void identifier() {
        // identifier -> [a-zA-Z_][a-zA-Z0-9_]*
        if (match(Token.IDENTIFIER)) {
            System.out.println("Found identifier: " + currentToken.getTokenValue());
        } else {
            syntaxError = true;
        }
    }

    private void statementList() {
        // statement_list -> statement statement_list | ε
        while (currentTokenIndex < tokens.size() && !currentToken.getTokenValue().equals("}")) {
            statement();
        }
    }

    private void statement() {
        // statement -> assignment_statement | if_statement | while_statement | declaration
        if (match(Token.KEYWORD, "if")) {
            ifStatement();
        } else if (match(Token.KEYWORD, "while")) {
            whileStatement();
        } else if (match(Token.TYPE_SPECIFIER)) {
            declaration();
        } else {
            assignmentStatement();
        }
    }

    private void assignmentStatement() {
        // assignment_statement -> identifier '=' expression ';'
        identifier();
        match(Token.OPERATOR, "=");
        expression();
        match(Token.OPERATOR, ";");
    }

    private void ifStatement() {
        // if_statement -> 'if' '(' condition ')' '{' statement_list '}' 'else' '{' statement_list '}'
        match(Token.OPERATOR, "(");
        condition();
        match(Token.OPERATOR, ")");
        match(Token.OPERATOR, "{");
        statementList();
        match(Token.OPERATOR, "}");
        match(Token.KEYWORD, "else");
        match(Token.OPERATOR, "{");
        statementList();
        match(Token.OPERATOR, "}");
    }

    private void whileStatement() {
        // while_statement -> 'while' '(' condition ')' '{' statement_list '}'
        match(Token.OPERATOR, "(");
        condition();
        match(Token.OPERATOR, ")");
        match(Token.OPERATOR, "{");
        statementList();
        match(Token.OPERATOR, "}");
    }

    private void condition() {
        // condition -> expression relational_operator expression
        expression();
        relationalOperator();
        expression();
    }

    private void expression() {
        // expression -> term | expression additive_operator term
        term();
        while (match(Token.OPERATOR, "+") || match(Token.OPERATOR, "-")) {
            term();
        }
    }

    private void term() {
        // term -> factor | term multiplicative_operator factor
        factor();
        while (match(Token.OPERATOR, "*") || match(Token.OPERATOR, "/")) {
            factor();
        }
    }

    private void factor() {
        // factor -> identifier | constant | '(' expression ')'
        if (match(Token.IDENTIFIER) || match(Token.CONSTANT)) {
            // Do nothing
        } else if (match(Token.OPERATOR, "(")) {
            expression();
            match(Token.OPERATOR, ")");
        } else {
            syntaxError = true;
        }
    }

    private void relationalOperator() {
        // relational_operator -> '<' | '>' | '==' | '!=' | '<=' | '>='
        match(Token.OPERATOR);
    }

    private boolean match(String expectedType, String expectedValue) {
        if (currentTokenIndex < tokens.size()) {
            Token currentToken = tokens.get(currentTokenIndex);
            

            if (currentToken.getTokenType().equals(expectedType) && currentToken.getTokenValue().equals(expectedValue)) {
                System.out.println(currentToken.getTokenType().equals(expectedType));
                 System.out.println(currentToken.getTokenValue().equals(expectedValue));
                currentTokenIndex++;
                
                return true;
            }
        }
        return false;
    }

    private boolean match(String expectedType) {
        if (currentTokenIndex < tokens.size()) {
            Token currentToken = tokens.get(currentTokenIndex);
            if (currentToken.getTokenType().equals(expectedType)) {
                currentTokenIndex++;
                return true;
            }
        }
        return false;
    }
}
