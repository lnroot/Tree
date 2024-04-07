/*
 * Lela Root 10/3/2023 Tree Implementation
 * LinkedBinaryTree
 * An implementation of a binary tree using doubly linked nodes
 */

import java.util.Iterator;	
import net.datastructures.*;
	

/**
 * @param <E>
 */
public class LinkedBinaryTree<E> implements BinaryTree<E>{

	protected static class Node<E> implements Position<E> {
		
		private E element;
		private Node<E> parent;
		private Node<E> left;
		private Node<E> right;
		
		/* Node constructor method
		 * @param e: the element
		 * @param above: parent, if there is one
		 * @param leftChild: the left child, if there is one
		 * @param rightChild: the right child, if there is one
		 */
		public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild) {
			element = e;
			parent = above;
			left = leftChild;
			right = rightChild;
		}
		
		//accessor methods
		public E getElement() {
			return element;
		}
		
		public Node<E> getParent() {
			return parent;
		}
		
		public Node<E> getLeft() {
			return left;
		}
		
		public Node<E> getRight() {
			return right;
		}
		
		//update methods
		public void setElement(E e) {
			element = e;
		}
		
		public void setParent(Node<E> parentNode) {
			parent = parentNode;
		}
		
		public void setLeft(Node<E> leftChild) {
			left = leftChild;
		}
		
		public void setRight(Node<E> rightChild) {
			right = rightChild;
		}
		
	}
	
	//instance variables
	protected Node<E> root = null;
	private int size = 0;
	
	/* createNode Constructs new nodes
	 * @param e: the element
	 * @param parent: if there is one
	 * @param left: the left child, if there is one
	 * @param right: the right child, if there is one
	 * @return the new node
	 */
	protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
		return new Node<E>(e, parent, left, right);
	}
	
	//Constructor for an empty tree
	public  LinkedBinaryTree( ) {}


	//returns the root node
	@Override
	public Position<E> root() {
		return root;
	}
	
	//returns the parent node
	@Override
	public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		Node<E> node = (Node<E>) p;
		return node.getParent();
	}

	//returns the left child
	@Override
	public Position<E> left(Position<E> p) throws IllegalArgumentException {
		Node<E> node = (Node<E>) p;
		return node.getLeft();
	}

	//returns right child
	@Override
	public Position<E> right(Position<E> p) throws IllegalArgumentException {
		Node<E> node = (Node<E>) p;
		return node.getRight();
	}
	
	//returns whether the given node is internal
	@Override
	public boolean isInternal(Position<E> p) throws IllegalArgumentException {
		return !isExternal(p);
	}

	//returns whether the given node is external
	@Override
	public boolean isExternal(Position<E> p) throws IllegalArgumentException {
		Node<E> node = (Node<E>) p;
		return (node.left == null && node.right == null);
	}

	//returns size of the tree
	@Override
	public int size() {
		return size;
	}

	//returns whether the tree is empty
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	/* creates a root for an empty tree, storing e as element, and returns the 
	 * position of that root. An error occurs if tree is not empty. 
	 */
	public Position<E> addRoot(E e) throws IllegalStateException {
		if (!isEmpty( )) throw new IllegalStateException("Tree is not empty");
		root = createNode(e, null, null, null);
		size = 1;
		return root;
	}
	
	
	/* creates a new left child of Position p storing element e, return the left child's position.
	 * If p has a left child already, throw exception IllegalArgumentExeption. 
	 */
	public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = (Node<E>) p;
		
		if(node == null) {
			throw new IllegalArgumentException("p does not exist");
		}
		
		if(node.left != null) {
			throw new IllegalArgumentException("p already has a left child");
		}
		Node<E> child = createNode(e, node, null, null);
		node.setLeft(child);
		size++;
		return child;
	}

	/* creates a new right child of Position p storing element e, return the right child's position.
	 * If p has a right child already, throw exception IllegalArgumentExeption. 
	 */
	public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = (Node<E>) p;
		
		if(node == null) {
			throw new IllegalArgumentException("p does not exist");
		}
		
		if(node.right != null) {
			throw new IllegalArgumentException("p already has a right child");
		}
		Node<E> child = createNode(e, node, null, null);
		node.setRight(child);
		size++;
		return child;
	}
		
	
	/* Set element in p.
	 * @return the old element in p. 
	 */
	public E setElement(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = (Node<E>) p;
		
		if(node == null) {
			throw new IllegalArgumentException("p does not exist");
		}
		
		E old = node.getElement();
		node.setElement(e);
		return old;
	}
	

	
	/**
	 * If p has two children, throw IllegalAugumentException. 
	 * If p is an external node ( that is it has no child), remove it from the tree.
	 * If p has one child, replace it with its child. 
	 * If p is root node, update the root accordingly. 
	 * @param p who has at most one child. 
	 * @return the element stored at position p if p was removed.
	 */
	public E remove(Position<E> p) throws IllegalArgumentException {
		Node<E> node = (Node<E>) p;
		if((node.left != null) && (node.right != null)) {
			throw new IllegalArgumentException("p has two children");
		}
		
		Node<E> child = (node.getLeft( ) != null ? node.getLeft( ) : node.getRight( ) );
		if (child != null) {
			child.setParent(node.getParent());
		}
		
		if (node == root) {
			root = child;
		} else {
			Node<E> parent = node.getParent();
			if(node == parent.getLeft()) {
				parent.setLeft(child);
			} else {
				parent.setRight(child);
			}
		}
		
		size--;
		E temp = node.getElement();
		node.setElement(null);
		node.setLeft(null);
		node.setRight(null);
		node.setParent(node);
		return temp;
	}
	
	
	/**
	 * Return the elements in the subtree of node P, including the data in node P. 
	 * The data in the return list need to match the in-order traversal.  
	 * @param p who has at most one child. 
	 * @return the List of elements in subtree of P following the in-order traversal. 
	 */
	public List<E> inOrderElements(Position<E> p) {
		ArrayList<E> list = new ArrayList<E>();
		Node<E> node = (Node<E>) p;
		recursiveInOrder(node, list);
		return list;
	}
	
	/*
	 * recursive helper method for returning inorder
	 * @param node: root of subtree
	 * @param list: output ArrayList
	 */
	private void recursiveInOrder(Node<E> node, List<E> list) {
		if (node == null) {
			return;
		}
		
		recursiveInOrder(node.left, list);
		((ArrayList<E>) list).addLast(node.getElement());
		recursiveInOrder(node.right, list);
		
		
	}
	
	
	/**
	 * Return the elements in the subtree of node P, including the data in node P. 
	 * The data in the return list need to match the pre-order traversal.  
	 * @param p who has at most one child. 
	 * @return the List of elements in subtree of P following the in-order traversal. 
	 */
	public List<E> preOrderElements(Position<E> p) {
		ArrayList<E> list = new ArrayList<E>();
		Node<E> node = (Node<E>) p;
		recursivePreOrder(node, list);
		return list;
	}
	
	/*
	 * recursive helper method for returning preorder
	 * @param node: root of subtree
	 * @param list: output ArrayList
	 */
	private void recursivePreOrder(Node<E> node, List<E> list) {
		if (node == null) {
			return;
		}
		((ArrayList<E>) list).addLast(node.getElement());
		recursivePreOrder(node.left, list);
		recursivePreOrder(node.right, list);
	}
	
	/**
	 * Return the elements in the subtree of node P, including the data in node P. 
	 * The data in the return list need to match the post-order traversal.  
	 * @param p who has at most one child. 
	 * @return the List of elements in subtree of P following the in-order traversal. 
	 */
	public List<E> postOrderElements(Position<E> p) {
		ArrayList<E> list = new ArrayList<E>();
		Node<E> node = (Node<E>) p;
		recursivePostOrder(node, list);
		return list;
	}
	
	/*
	 * recursive helper method for returning postorder
	 * @param node: root of subtree
	 * @param list: output ArrayList
	 */
	private void recursivePostOrder(Node<E> node, List<E> list) {
		if (node == null) {
			return;
		}
		
		recursivePostOrder(node.left, list);
		recursivePostOrder(node.right, list);
		((ArrayList<E>) list).addLast(node.getElement());
	}
	
	/**
	 * Return the elements in the subtree of node P, including the data in node P. 
	 * The data in the return list need to match the level-order traversal.  
	 * @param p who has at most one child. 
	 * @return the List of elements in subtree of P following the in-order traversal. 
	 */
	public List<E> levelOrderElements(Position<E> p) {
		ArrayList<E> list = new ArrayList<E>();
		Node<E> node = (Node<E>) p;
		
		//create a queue to take the level order
		CircularArrayQueue<Node<E>> q = new CircularArrayQueue<Node<E>>(size);
		q.enqueue(node);

		//enqueue each successive level, adding to the output list when we dequeue
		while (q.size > 0) {
			Node<E> current = q.dequeue();
			list.addLast(current.getElement());
			if (current.left != null) {
				q.enqueue(current.left);
			}
			if (current.right != null) {
				q.enqueue(current.right);
			}
		}
			
		return list;
	}

}
