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
        // Iniciar el análisis sintáctico
        program();

        // Verificar si se ha analizado toda la entrada sin errores
        if (!syntaxError && currentTokenIndex == tokens.size()) {
            System.out.println("Programa analizado sintácticamente correctamente.");
        } else {
            System.out.println("Error de sintaxis: Programa incorrecto.");
        }
    }

    private void program() {
        // program -> declaration_list
        declarationList();
    }

    private void declarationList() {
        // declaration_list -> declaration declaration_list | ε
        while (currentTokenIndex < tokens.size()) {
            declaration();
        }
    }

    private void declaration() {
        // declaration -> type_specifier identifier ';'
        typeSpecifier();
        identifier();
        match(Token.PUNCTUATION, ";");
    }

    private void typeSpecifier() {
        // type_specifier -> 'int' | 'float' | 'char' | 'double'
        if (currentToken.getTokenType().equals(Token.TYPE_SPECIFIER)) {
            currentTokenIndex++;
        } else {
            syntaxError();
        }
    }

    private void identifier() {
        // identifier -> [a-zA-Z_][a-zA-Z0-9_]*
        if (currentToken.getTokenType().equals(Token.IDENTIFIER)) {
            currentTokenIndex++;
        } else {
            syntaxError();
        }
    }

    private void match(String expectedType, String expectedValue) {
        // Verificar si el tipo y valor del token actual coinciden con los esperados
        if (currentTokenIndex < tokens.size()) {
            Token token = tokens.get(currentTokenIndex);
            if (token.getTokenType().equals(expectedType) && token.getTokenValue().equals(expectedValue)) {
                currentTokenIndex++;
            } else {
                syntaxError();
            }
        } else {
            syntaxError();
        }
    }

    private void syntaxError() {
        // Se ha encontrado un error de sintaxis
        syntaxError = true;
        System.out.println("Error de sintaxis en el token: " + currentToken);
        // Aquí puedes manejar el error de sintaxis de acuerdo a tus necesidades
    }
}
