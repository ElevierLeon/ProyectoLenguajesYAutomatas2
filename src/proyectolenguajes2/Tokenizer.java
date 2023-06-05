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
                Token token = createToken(word);
                if (token != null) {
                    tokens.add(token);
                }
            }
        }

        return tokens;
    }

    private static Token createToken(String word) {
        if (isTypeSpecifier(word)) {
            return new Token(Token.TYPE_SPECIFIER, word);
        } else if (isIdentifier(word)) {
            return new Token(Token.IDENTIFIER, word);
        } else if (isKeyword(word)) {
            return new Token(Token.KEYWORD, word);
        } else if (isOperator(word)) {
            return new Token(Token.OPERATOR, word);
        } else if (isConstant(word)) {
            return new Token(Token.CONSTANT, word);
        } else if (isMain(word)) {
            return new Token(Token.KEYWORD, word);
        } else if (!word.isEmpty()) {
            return new Token(Token.UNKNOWN, word);
        }
    
        return null;
    }

    private static boolean isTypeSpecifier(String word) {
        String pattern = "(int|float|char|double)";
        return word.matches(pattern);
    }

    private static boolean isIdentifier(String word) {
        String pattern = "[a-zA-Z_][a-zA-Z0-9_]*";
        return word.matches(pattern);
    }

    private static boolean isKeyword(String word) {
        String pattern = "(if|else|while|return)";
        return word.matches(pattern);
    }

    private static boolean isOperator(String word) {
        String pattern = "(=|<|>|==|!=|<=|>=|\\+|-|\\*|/|\\(|\\)|\\{|\\}|;)";
        return word.matches(pattern);
    }

    private static boolean isConstant(String word) {
        String pattern = "[0-9]+";
        return word.matches(pattern);
    }
    private static boolean isMain(String word) {
        String pattern = "main";
        return word.equals(pattern);
    }
    

}