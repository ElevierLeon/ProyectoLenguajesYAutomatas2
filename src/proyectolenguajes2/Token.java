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
