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
       
        String cppProgram = "int main() { \n" +
                            "  int x = 5 ; \n" +
                            "  if ( x > 0 ) { \n" +
                            "    x = x + 1 ; \n" +
                            "  } else { \n" +
                            "    x = x - 1 ; \n" +
                            "  } \n" +
                            "  return 0 ; \n" +
                            "}";
        
        ArrayList<Token> tokens = tokenize(cppProgram);
        
        tokens.forEach((token) -> {
            System.out.println(token);
       });
        
        Parser parser = new Parser(tokens);
        TreeNode syntaxTree = parser.parse();
        if (syntaxTree != null) {
            syntaxTree.printTree();
        }
        
    }
}
