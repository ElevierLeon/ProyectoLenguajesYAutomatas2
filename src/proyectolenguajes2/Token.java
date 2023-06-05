package proyectolenguajes2;

public class Token {
    public static final String TYPE_SPECIFIER = "Type Specifier";
    public static final String IDENTIFIER = "Identifier";
    public static final String KEYWORD = "Keyword";
    public static final String OPERATOR = "Operator";
    public static final String CONSTANT = "Constant";
    public static final String UNKNOWN = "Unknown";

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
