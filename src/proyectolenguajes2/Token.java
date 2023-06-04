/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectolenguajes2;

/**
 *
 * @author Elevier
 */
public class Token {
    public static final String TYPE_SPECIFIER = "Type Specifier";
    public static final String IDENTIFIER = "Identifier";
    public static final String KEYWORD = "Keyword";
    public static final String OPERATOR = "Operator";
    public static final String CONSTANT = "Constant";
    public static final String MAIN_FUNCTION = "Main Function";
    public static final String UNKNOWN = "Unknown";
    public static final String PUNCTUATION = "Punctuation";

    // Tipos de token específicos para los tipos de datos
    public static final String INT = "int";
    public static final String FLOAT = "float";
    public static final String CHAR = "char";
    public static final String DOUBLE = "double";
    
    // Palabras clave
    public static final String IF = "if";
    public static final String WHILE = "while";
    public static final String ELSE = "else";
    
    // Símbolos de puntuación
    public static final String OPEN_PAREN = "(";
    public static final String CLOSE_PAREN = ")";
    public static final String OPEN_BRACE = "{";
    public static final String CLOSE_BRACE = "}";
    public static final String SEMICOLON = ";";
    
    // Operadores
    public static final String ASSIGNMENT = "=";
    public static final String GREATER_THAN = ">";
    public static final String EQUALS = "==";
    public static final String LESS_THAN = "<";
    public static final String GREATER_THAN_OR_EQUAL = ">=";
    public static final String NOT_EQUALS = "!=";
    public static final String LESS_THAN_OR_EQUAL = "<=";

    private String tokenType;
    private String tokenValue;

    public Token(String tokenType, String tokenValue) {
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    @Override
    public String toString() {
        return "Token [Type: " + tokenType + ", Value: " + tokenValue + "]";
    }
}
