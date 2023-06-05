/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectolenguajes2;

import java.util.ArrayList;
import static proyectolenguajes2.Tokenizer.tokenize;


/**
 *
 * @author Elevier
 */
public class Main {
   public static void main(String[] args) {
       
    ArrayList<Token> tokens = new ArrayList<>();
    tokens.add(new Token(Token.KEYWORD, "int"));
    tokens.add(new Token(Token.IDENTIFIER, "main"));
    tokens.add(new Token(Token.OPERATOR, "("));
    tokens.add(new Token(Token.OPERATOR, ")"));
    tokens.add(new Token(Token.OPERATOR, "{"));
    tokens.add(new Token(Token.KEYWORD, "int"));
    tokens.add(new Token(Token.IDENTIFIER, "x"));
    tokens.add(new Token(Token.OPERATOR, "="));
    tokens.add(new Token(Token.CONSTANT, "5"));
    tokens.add(new Token(Token.OPERATOR, ";"));
    tokens.add(new Token(Token.KEYWORD, "if"));
    tokens.add(new Token(Token.OPERATOR, "("));
    tokens.add(new Token(Token.IDENTIFIER, "x"));
    tokens.add(new Token(Token.OPERATOR, ">"));
    tokens.add(new Token(Token.CONSTANT, "0"));
    tokens.add(new Token(Token.OPERATOR, ")"));
    tokens.add(new Token(Token.OPERATOR, "{"));
    tokens.add(new Token(Token.IDENTIFIER, "x"));
    tokens.add(new Token(Token.OPERATOR, "="));
    tokens.add(new Token(Token.IDENTIFIER, "x"));
    tokens.add(new Token(Token.OPERATOR, "+"));
    tokens.add(new Token(Token.CONSTANT, "1"));
    tokens.add(new Token(Token.OPERATOR, ";"));
    tokens.add(new Token(Token.OPERATOR, "}"));
    tokens.add(new Token(Token.KEYWORD, "else"));
    tokens.add(new Token(Token.OPERATOR, "{"));
    tokens.add(new Token(Token.IDENTIFIER, "x"));
    tokens.add(new Token(Token.OPERATOR, "="));
    tokens.add(new Token(Token.IDENTIFIER, "x"));
    tokens.add(new Token(Token.OPERATOR, "-"));
    tokens.add(new Token(Token.CONSTANT, "1"));
    tokens.add(new Token(Token.OPERATOR, ";"));
    tokens.add(new Token(Token.OPERATOR, "}"));
    tokens.add(new Token(Token.KEYWORD, "return"));
    tokens.add(new Token(Token.CONSTANT, "0"));
    tokens.add(new Token(Token.OPERATOR, ";"));
    tokens.add(new Token(Token.OPERATOR, "}"));

    Parser parser = new Parser(tokens);
    
    boolean isSyntaxCorrect = parser.parse();
    if (isSyntaxCorrect) {
        System.out.println("El código es sintácticamente correcto.");
    } else {
        System.out.println("El código contiene errores sintácticos.");
    }
        
    }
}