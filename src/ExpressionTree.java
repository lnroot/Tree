/*
 * Lela Root 10/3/2021 Tree Application
 * Expression Tree problem solved using LinkedBinaryTree
 */


import net.datastructures.*;

public class ExpressionTree {
	
	//Given a binary tree associated with an arithmetic expressions with operators: +,-,*,/ and decimal numbers
	//Evaluate the result and return the result. You may assume the given tree is in correct form. 
	
	//Example 1:
	//    +
	//  /  \
	// 2  4.5
	//
	// (2+4.5) = 6.5
	// Expected output: 6.5
	
	
	//Example 2:
	//
	//        +
	//      /   \
	//    *       *
	//  /  \     /  \
	//  2   -   3   2
	//     / \
	//     5  1 
	//
	// ((2*(5-1))+(3*2)) = 14
	// Expected output: 14
	
	
	public static double eval(BinaryTree<String> tree) {
		return recursiveEval((LinkedBinaryTree<String>) tree, tree.root());
	}
	
	/*
	 * Helper method to recursively traverse the tree to evaluate the expression
	 * @param tree: the tree being traversed
	 * @param root: the root of the subtree being traversed
	 */
	private static double recursiveEval(LinkedBinaryTree<String> tree, Position<String> root) {
		if (tree.isInternal(root)) {
	        double leftExp = recursiveEval(tree, tree.left(root));
	        double rightExp = recursiveEval(tree, tree.right(root));
	        
	        String operator = root.getElement();
	        
	        switch (operator) {
            case "+":
                return leftExp + rightExp;
            case "-":
                return leftExp - rightExp;
            case "*":
                return leftExp * rightExp;
            case "/":
                if (rightExp == 0.0) {
                    throw new ArithmeticException("Division by zero");
                }
                return leftExp / rightExp;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
	        
	    } else {
	        return Double.parseDouble(root.getElement());
	    }
	}
	
	//Given a binary tree associated with an arithmetic expressions with operators: +,-,*,/ 
	//and decimal numbers or variables
	
	// Generate the expression with parenthesis around all sub expressions except the leave nodes.  
	// You may assume the given tree is in correct form. 
	// Example:
	//        +
	//      /   \
	//    *       *
	//  /  \     /  \
	//  2   -   3   b
	//     / \
	//     a  1 
	//
	// Expected output: ((2*(a-1))+(3*b)) 
	
	public static String toExpression(BinaryTree<String> tree) {
		String str = "";
		return recursiveExp((LinkedBinaryTree<String>) tree, (tree.root()));
	}
	
	
	/*
	 * helper method to recursively concatenate expressions into a string 
	 * @param tree being traversed
	 * @param root node of the subtree
	 */
	private static String recursiveExp(LinkedBinaryTree<String> tree, Position<String> root) {
		if (tree.isInternal(root)) {
	        String leftExp = recursiveExp(tree, tree.left(root));
	        String rightExp = recursiveExp(tree, tree.right(root));
	        return "(" + leftExp + root.getElement() + rightExp + ")";
	    } else {
	        return root.getElement();
	    }
	}


}
