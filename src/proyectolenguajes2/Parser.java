/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectolenguajes2;

import java.util.ArrayList;
public class Parser {
    private final ArrayList<Token> tokens;  // Lista de tokens de entrada
    private int currentTokenIndex;  // Índice del token actual
    private TreeNode root;  // Nodo raíz del árbol de análisis sintáctico
    private boolean syntaxError;  // Variable que indica si se ha encontrado un error de sintaxis

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
                    //System.out.println("Error at token: " + errorToken);
                }
            }
            return null;
        }
    }

    private TreeNode program() {
        // El método program() representa la producción "program -> declarationList"
        TreeNode declarationListNode = declarationList();
        if (declarationListNode != null) {
            // Se crea un nuevo nodo con etiqueta "program" y se agrega el nodo de declaración
            return new TreeNode("program", declarationListNode);
        }
        return null;
    }

    private TreeNode declarationList() {
        // El método declarationList() representa la producción "declarationList -> declaration declarationList"
        TreeNode declarationNode = declaration();
        if (declarationNode != null) {
            TreeNode declarationListNode = declarationList();
            if (declarationListNode != null) {
                // Se crea un nuevo nodo con etiqueta "declaration_list" y se agregan los nodos de declaración y de lista de declaración
                return new TreeNode("declaration_list", declarationNode, declarationListNode);
            } else {
                // Solo hay un nodo de declaración, no hay más nodos de lista de declaración
                return new TreeNode("declaration_list", declarationNode);
            }
        }
        return null;
    }

    private TreeNode declaration() {
        // El método declaration() representa la producción "declaration -> typeSpecifier identifier"
        TreeNode typeSpecifierNode = typeSpecifier();
        if (typeSpecifierNode != null) {
            TreeNode identifierNode = identifier();
            if (identifierNode != null && match(Token.TYPE_SPECIFIER)) {
                // Se crea un nuevo nodo con etiqueta "declaration" y se agregan los nodos de typeSpecifier e identifier
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
        // El método typeSpecifier() representa la producción "typeSpecifier -> TYPE_SPECIFIER"
        if (match(Token.TYPE_SPECIFIER)) {
            // Se crea un nuevo nodo con etiqueta "type_specifier" y el valor del token actual
            return new TreeNode("type_specifier", previousToken().getTokenValue());
        }
        return null;
    }

    private TreeNode identifier() {
        // El método identifier() representa la producción "identifier -> IDENTIFIER"
        if (match(Token.IDENTIFIER)) {
            // Se crea un nuevo nodo con etiqueta "identifier" y el valor del token actual
            return new TreeNode("identifier", previousToken().getTokenValue());
        }
        return null;
    }

    private boolean match(String expectedType) {
        // El método match() verifica si el tipo del token actual coincide con el tipo esperado
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
        // El método previousToken() obtiene el token anterior al token actual
        if (currentTokenIndex > 0) {
            return tokens.get(currentTokenIndex - 1);
        }
        return null;
    }
}