/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectolenguajes2;

import java.util.ArrayList;


public class Tokenizer {
 
    
    public static ArrayList<Token> tokenize(String cppProgram) {
        ArrayList<Token> tokens = new ArrayList<>();
        
        String[] lines = cppProgram.split("\n");
        for (String line : lines) {
            String[] words = line.split("\\s+");
            for (String word : words) {
                if (isTypeSpecifier(word)) {
                    tokens.add(new Token(Token.TYPE_SPECIFIER, word));
                } else if (isIdentifier(word)) {
                    tokens.add(new Token(Token.IDENTIFIER, word));
                } else if (isKeyword(word)) {
                    tokens.add(new Token(Token.KEYWORD, word));
                } else if (isOperator(word)) {
                    tokens.add(new Token(Token.OPERATOR, word));
                } else if (isConstant(word)) {
                    tokens.add(new Token(Token.CONSTANT, word));
                } else if (isMainFunction(word)) {
                    tokens.add(new Token(Token.MAIN_FUNCTION, word));
                } else if (!word.isEmpty()) {
                    tokens.add(new Token(Token.UNKNOWN, word));
                }
            }
        }
        
        return tokens;
    }
    
    public static boolean isTypeSpecifier(String word) {
        String pattern = "(int|float|char|double)";
        return word.matches(pattern);
    }
    
    public static boolean isIdentifier(String word) {
        String pattern = "[a-zA-Z_][a-zA-Z0-9_]*";
        return word.matches(pattern);
    }
    
    public static boolean isKeyword(String word) {
        String pattern = "(if|else|while|return)";
        return word.matches(pattern);
    }
    
    public static boolean isOperator(String word) {
        String pattern = "(=|<|>|==|!=|<=|>=|\\+|-|\\*|/|\\(|\\)|\\{|\\}|;)";
        return word.matches(pattern);
    }
    
    public static boolean isConstant(String word) {
        String pattern = "[0-9]+";
        return word.matches(pattern);
    }
    
    public static boolean isMainFunction(String word) {
        String pattern = "main\\(\\)";
        return word.matches(pattern);
    }
}
