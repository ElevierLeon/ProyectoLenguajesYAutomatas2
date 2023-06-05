/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectolenguajes2;

import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> tokens;
    private int currentTokenIndex;
    private TreeNode rootNode;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
        this.rootNode = null;
    }

    public boolean parse() {
        rootNode = new TreeNode("program");
        if (match(Token.KEYWORD, "int") && match(Token.IDENTIFIER, "main") && match(Token.OPERATOR, "(") &&
                match(Token.OPERATOR, ")") && match(Token.OPERATOR, "{") && declarationList(rootNode) &&
                match(Token.KEYWORD, "return") && match(Token.CONSTANT, "0") && match(Token.OPERATOR, ";") &&
                match(Token.OPERATOR, "}")) {
            return currentTokenIndex == tokens.size();
        }
        return false;
    }

    private boolean match(String expectedTokenType, String expectedTokenValue) {
        if (currentTokenIndex < tokens.size()) {
            Token currentToken = tokens.get(currentTokenIndex);
            if (currentToken.getTokenType().equals(expectedTokenType) &&
                    currentToken.getTokenValue().equals(expectedTokenValue)) {
                currentTokenIndex++;
                return true;
            }
        }
        return false;
    }
    
    private boolean match(String expectedTokenType) {
        if (currentTokenIndex < tokens.size()) {
            Token currentToken = tokens.get(currentTokenIndex);
            if (currentToken.getTokenType().equals(expectedTokenType)) {
                currentTokenIndex++;
                return true;
            }
        }
        return false;
    }

    private boolean declarationList(TreeNode parentNode) {
        TreeNode declarationListNode = new TreeNode("declaration_list");
        if (declaration(declarationListNode)) {
            parentNode.addChild(declarationListNode);
            if (declarationList(parentNode)) {
                return true;
            }
            return true;
        }
        return false;
    }

    private boolean declaration(TreeNode parentNode) {
        TreeNode declarationNode = new TreeNode("declaration");
        if (typeSpecifier(declarationNode) && match(Token.IDENTIFIER) && match(Token.OPERATOR, "=") &&
                match(Token.CONSTANT) && match(Token.OPERATOR, ";")) {
            parentNode.addChild(declarationNode);
            return true;
        }
        return false;
    }

    private boolean typeSpecifier(TreeNode parentNode) {
        Token currentToken = getCurrentToken();
        if (match(Token.KEYWORD, "int")) {
            parentNode.addChild(new TreeNode(currentToken.getTokenValue()));
            return true;
        }
        return false;
    }

    private Token getCurrentToken() {
        if (currentTokenIndex < tokens.size()) {
            return tokens.get(currentTokenIndex);
        }
        return null;
    }

    private static class TreeNode {
        private String label;
        private ArrayList<TreeNode> children;

        public TreeNode(String label) {
            this.label = label;
            this.children = new ArrayList<>();
        }

        public String getLabel() {
            return label;
        }

        public ArrayList<TreeNode> getChildren() {
            return children;
        }

        public void addChild(TreeNode child) {
            children.add(child);
        }
    }
}