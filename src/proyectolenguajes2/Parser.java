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
    private TreeNode root;
    private boolean syntaxError;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
        this.root = null;
        this.syntaxError = false;
    }

    public TreeNode parse() {
        root = program();
        if (root != null && currentTokenIndex == tokens.size() && !syntaxError) {
            System.out.println("Program successfully parsed.");
            return root;
        } else {
            System.out.println("Syntax error: Program is syntactically incorrect.");
            if (syntaxError) {
                int errorTokenIndex = currentTokenIndex - 1;
                if (errorTokenIndex >= 0 && errorTokenIndex < tokens.size()) {
                    Token errorToken = tokens.get(errorTokenIndex);
                    System.out.println("Error at token: " + errorToken);
                }
            }
            return null;
        }
    }

    private TreeNode program() {
        TreeNode declarationListNode = declarationList();
        if (declarationListNode != null) {
            return new TreeNode("program", declarationListNode);
        }
        return null;
    }

    private TreeNode declarationList() {
        TreeNode declarationNode = declaration();
        if (declarationNode != null) {
            TreeNode declarationListNode = declarationList();
            if (declarationListNode != null) {
                return new TreeNode("declaration_list", declarationNode, declarationListNode);
            } else {
                return new TreeNode("declaration_list", declarationNode);
            }
        }
        return null;
    }

    private TreeNode declaration() {
        TreeNode typeSpecifierNode = typeSpecifier();
        if (typeSpecifierNode != null) {
            TreeNode identifierNode = identifier();
            if (identifierNode != null && match(Token.SEMICOLON)) {
                return new TreeNode("declaration", typeSpecifierNode, identifierNode);
            } else {
                syntaxError = true;
            }
        } else {
            syntaxError = true;
        }
        return null;
    }

    private TreeNode typeSpecifier() {
        if (match(Token.INT) || match(Token.FLOAT) || match(Token.CHAR) || match(Token.DOUBLE)) {
            return new TreeNode("type_specifier", previousToken().getTokenValue());
        }
        return null;
    }

    private TreeNode identifier() {
        if (match(Token.IDENTIFIER)) {
            return new TreeNode("identifier", previousToken().getTokenValue());
        }
        return null;
    }

    private TreeNode statement() {
        if (match(Token.IF)) {
            if (match(Token.OPEN_PAREN)) {
                TreeNode conditionNode = condition();
                if (conditionNode != null && match(Token.CLOSE_PAREN) && match(Token.OPEN_BRACE)) {
                    TreeNode trueStatementListNode = statementList();
                    if (match(Token.CLOSE_BRACE) && match(Token.ELSE) && match(Token.OPEN_BRACE)) {
                        TreeNode falseStatementListNode = statementList();
                        if (match(Token.CLOSE_BRACE)) {
                            return new TreeNode("if_statement", conditionNode, trueStatementListNode, falseStatementListNode);
                        }
                    }
                }
            }
        } else if (match(Token.WHILE)) {
            if (match(Token.OPEN_PAREN)) {
                TreeNode conditionNode = condition();
                if (conditionNode != null && match(Token.CLOSE_PAREN) && match(Token.OPEN_BRACE)) {
                    TreeNode statementListNode = statementList();
                    if (match(Token.CLOSE_BRACE)) {
                        return new TreeNode("while_statement", conditionNode, statementListNode);
                    }
                }
            }
        } else {
            return assignmentStatement();
        }
        return null;
    }

    private TreeNode assignmentStatement() {
        TreeNode identifierNode = identifier();
        if (identifierNode != null && match(Token.ASSIGNMENT)) {
            TreeNode expressionNode = expression();
            if (expressionNode != null && match(Token.SEMICOLON)) {
                return new TreeNode("assignment_statement", identifierNode, expressionNode);
            } else {
                syntaxError = true;
            }
        } else {
            syntaxError = true;
        }
        return null;
    }

    private TreeNode condition() {
        TreeNode expression1Node = expression();
        if (expression1Node != null) {
            TreeNode operatorNode = relationalOperator();
            if (operatorNode != null) {
                TreeNode expression2Node = expression();
                if (expression2Node != null) {
                    return new TreeNode("condition", expression1Node, operatorNode, expression2Node);
                }
            }
        }
        return null;
    }

    private TreeNode relationalOperator() {
        if (match(Token.LESS_THAN) || match(Token.GREATER_THAN) || match(Token.EQUALS) ||
                match(Token.NOT_EQUALS) || match(Token.LESS_THAN_OR_EQUAL) || match(Token.GREATER_THAN_OR_EQUAL)) {
            return new TreeNode("relational_operator", previousToken().getTokenValue());
        }
        return null;
    }

    private TreeNode expression() {
        TreeNode termNode = term();
        if (termNode != null) {
            TreeNode expressionNode = expression();
            if (expressionNode != null) {
                return new TreeNode("expression", expressionNode, termNode);
            } else {
                return new TreeNode("expression", termNode);
            }
        }
        return null;
    }

    private TreeNode term() {
        TreeNode factorNode = factor();
        if (factorNode != null) {
            TreeNode termNode = term();
            if (termNode != null) {
                return new TreeNode("term", termNode, factorNode);
            } else {
                return new TreeNode("term", factorNode);
            }
        }
        return null;
    }

    private TreeNode factor() {
        if (match(Token.IDENTIFIER)) {
            return new TreeNode("identifier", previousToken().getTokenValue());
        } else if (match(Token.CONSTANT)) {
            return new TreeNode("constant", previousToken().getTokenValue());
        } else if (match(Token.OPEN_PAREN)) {
            TreeNode expressionNode = expression();
            if (expressionNode != null && match(Token.CLOSE_PAREN)) {
                return new TreeNode("factor", expressionNode);
            } else {
                syntaxError = true;
            }
        } else {
            syntaxError = true;
        }
        return null;
    }

    private TreeNode statementList() {
        TreeNode statementNode = statement();
        if (statementNode != null) {
            TreeNode statementListNode = statementList();
            if (statementListNode != null) {
                return new TreeNode("statement_list", statementNode, statementListNode);
            } else {
                return new TreeNode("statement_list", statementNode);
            }
        }
        return null;
    }

    private boolean match(String expectedType) {
        if (currentTokenIndex < tokens.size()) {
            Token currentToken = tokens.get(currentTokenIndex);
            if (currentToken.getTokenType().equals(expectedType)) {
                currentTokenIndex++;
                return true;
            }
        }
        return false;
    }

    private Token previousToken() {
        if (currentTokenIndex > 0) {
            return tokens.get(currentTokenIndex - 1);
        }
        return null;
    }
}
