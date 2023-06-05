/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectolenguajes2;

import java.util.ArrayList;

/**
 *
 * @author Elevier
 */
public class Main {

    
   public static void main(String[] args) {

    ArrayList<Token> tokens = new ArrayList<>();
    tokens.add(new Token(Token.KEYWORD, "int"));
    tokens.add(new Token(Token.IDENTIFIER, "x"));
    tokens.add(new Token(Token.OPERATOR, "="));
    tokens.add(new Token(Token.CONSTANT, "5"));
    tokens.add(new Token(Token.OPERATOR, ";"));

    tokens.add(new Token(Token.KEYWORD, "float"));
    tokens.add(new Token(Token.IDENTIFIER, "y"));
    tokens.add(new Token(Token.OPERATOR, "="));
    tokens.add(new Token(Token.CONSTANT, "3.14"));
    tokens.add(new Token(Token.OPERATOR, ";"));

    // Crear un objeto Parser
    Parser parser = new Parser(tokens);

    // Realizar el análisis sintáctico
    boolean success = parser.parse();
    parser.printSyntaxTree();
    
}
}