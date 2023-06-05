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
        if (declarationList(rootNode)) {
            return currentTokenIndex == tokens.size();
        }
        return false;
    }

    public void printSyntaxTree() {
        if (rootNode != null) {
            printSyntaxTree(rootNode, 0);
        }
    }

    private boolean match(String expectedTokenType, String expectedTokenValue) {
        if (currentTokenIndex < tokens.size()) {
            Token currentToken = tokens.get(currentTokenIndex);
            if (currentToken.getTokenType().equals(expectedTokenType) &&
                    currentToken.getTokenValue().equals(expectedTokenValue)) {
                currentTokenIndex++;
                return true;
            } else {
                System.out.println("Error sintáctico: Se esperaba '" + expectedTokenValue + "' en lugar de '" + currentToken.getTokenValue() + "'");
                return false;
            }
        } else {
            System.out.println("Error sintáctico: Se esperaba '" + expectedTokenValue + "' pero no hay más tokens disponibles");
            return false;
        }
    }

    private boolean match(String expectedTokenType) {
        if (currentTokenIndex < tokens.size()) {
            Token currentToken = tokens.get(currentTokenIndex);
            if (currentToken.getTokenType().equals(expectedTokenType)) {
                currentTokenIndex++;
                return true;
            } else {
                System.out.println("Error sintáctico: Se esperaba un token de tipo '" + expectedTokenType + "' en lugar de un token de tipo '" + currentToken.getTokenType() + "'");
                return false;
            }
        } else {
            System.out.println("Error sintáctico: Se esperaba un token de tipo '" + expectedTokenType + "' pero no hay más tokens disponibles");
            return false;
        }
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
        if (typeSpecifier(declarationNode) && match(Token.IDENTIFIER)) {
            if (match(Token.OPERATOR, "=") && expression(declarationNode) && match(Token.OPERATOR, ";")) {
                parentNode.addChild(declarationNode);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean expression(TreeNode parentNode) {
        TreeNode expressionNode = new TreeNode("expression");

        if (simpleExpression(expressionNode)) {
            if (relationalOperator(expressionNode) && simpleExpression(expressionNode)) {
                parentNode.addChild(expressionNode);
                return true;
            } else {
                parentNode.addChild(expressionNode);
                return true;
            }
        }

        return false;
    }

    private boolean simpleExpression(TreeNode parentNode) {
        TreeNode simpleExpressionNode = new TreeNode("simple_expression");

        if (term(simpleExpressionNode)) {
            while (additiveOperator(simpleExpressionNode) && term(simpleExpressionNode)) {
                // Loop para procesar términos adicionales con operadores aditivos
            }

            parentNode.addChild(simpleExpressionNode);
            return true;
        }

        return false;
    }

    private boolean term(TreeNode parentNode) {
        TreeNode termNode = new TreeNode("term");

        if (factor(termNode)) {
            while (multiplicativeOperator(termNode) && factor(termNode)) {
                // Loop para procesar factores adicionales con operadores multiplicativos
            }

            parentNode.addChild(termNode);
            return true;
        }

        return false;
    }

    private boolean factor(TreeNode parentNode) {
        TreeNode factorNode = new TreeNode("factor");

        if (match(Token.IDENTIFIER) || match(Token.CONSTANT)) {
            parentNode.addChild(factorNode);
            return true;
        } else if (match(Token.OPERATOR, "(") && expression(factorNode) && match(Token.OPERATOR, ")")) {
            parentNode.addChild(factorNode);
            return true;
        }

        return false;
    }

    private boolean additiveOperator(TreeNode parentNode) {
        return match(Token.OPERATOR, "+") || match(Token.OPERATOR, "-");
    }

    private boolean multiplicativeOperator(TreeNode parentNode) {
        return match(Token.OPERATOR, "*") || match(Token.OPERATOR, "/");
    }

    private boolean relationalOperator(TreeNode parentNode) {
        return match(Token.OPERATOR, "<") || match(Token.OPERATOR, ">") ||
                match(Token.OPERATOR, "<=") || match(Token.OPERATOR, ">=") ||
                match(Token.OPERATOR, "==") || match(Token.OPERATOR, "!=");
    }

    private boolean typeSpecifier(TreeNode parentNode) {
        return match(Token.KEYWORD, "int") || match(Token.KEYWORD, "float");
    }

    private void printSyntaxTree(TreeNode node, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        System.out.println(node.getName());

        for (TreeNode child : node.getChildren()) {
            printSyntaxTree(child, depth + 1);
        }
    }

    private class TreeNode {
        private String name;
        private ArrayList<TreeNode> children;

        public TreeNode(String name) {
            this.name = name;
            this.children = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public ArrayList<TreeNode> getChildren() {
            return children;
        }

        public void addChild(TreeNode child) {
            children.add(child);
        }
    }
}
