import java.util.ArrayList;
import java.util.List;


//\\ read carefully!!! //\\
//\\ this is dedicated to both this assignment's Testers, and to the contributing students to go over and make sure the details below are correct !!! //\\
/**
 * contributors' info and details:
 * @adam:
 * TAU-username: adamtuby
 * id: 215334822
 * @asaf:
 * TAU-username: michaelovits
 * id: 200637270
 */

/**
 * public class AVLNode
 * <p>
 * This class represents an AVLTree with integer keys and boolean values.
 * <p>
 * IMPORTANT: do not change the signatures of any function (i.e. access modifiers, return type, function name and
 * arguments. Changing these would break the automatic tester, and would result in worse grade.
 * <p>
 * However, you are allowed (and required) to implement the given functions, and can add functions of your own
 * according to your needs.
 */

public class AVLTree {
	private final AVLNode virtual; // every tree will hold this field to be its virtual node, in order to save up memory by using the same virtual node whenever we need to use one.
	
    public AVLNode root; // the root of the tree
    /**
     * This constructor creates an empty AVLTree.
     * <p>
     * Complexity: O(1)
     */
    public AVLTree(){ 
    	virtual = new AVLNode(); // configuring the virtual node 
        this.root = virtual; // initialize the root to be a virtual node
    }

    /**
     * public boolean empty()
     * <p>
     * returns true if and only if the tree is empty
     * <p>
     * Complexity: O(1)
     */
    public boolean empty() { // just check if the root is a virtual node or not
        return !this.root.isRealNode();
    }

    /**
     * public boolean search(int k)
     * <p>
     * returns the info of an item with key k if it exists in the tree
     * otherwise, returns null
     * <p>
     * Complexity: O(log n)
     */
    public Boolean search(int k) { 
    	AVLNode out = this.plain_search(k); // catching the returned node from the method plain_search(k)
    	if (out == null) { // if out is null, then k wasn't even in the tree
    		return null;
    	} else {
    		return out.getValue();
    	}
    }

    /**
     * NEW SUPPLEMENTARY METHOD! 
     * this method's role is to return the node in the tree whose key is the @k. 
     * if there is no node like that, we return null.
     * <p>
     * Complexity: O(log n)
     */
    private AVLNode plain_search(int k) { // change back to private
        AVLNode curr = this.getRoot();
        while (curr != null) { // stops when the Node is a null
        	if (curr.getKey() == k) { // if the current Node holds a key which is equal to k, then return the Node's info. 
        		return curr;
        	} else {
        		if (curr.getKey() < k) { // if the current Node holds a key which is greater than k, then move on to check the current Node's right child.
        			curr = curr.getRight();
        		} else {
        			curr = curr.getLeft(); // if the current Node holds a key which is less than k, then move on to check the current Node's left child.
        		}
        	}
        }
        
        return null; // return null if the tree was empty or if the key was not found
    }
    
    /**
     * public int insert(int k, boolean i)
     * <p>
     * inserts an item with key k and info i to the AVL tree.
     * the tree must remain valid (keep its invariants).
	 * returns the number of nodes which require rebalancing operations (i.e. promotions or rotations).
	 * This always includes the newly-created node.
     * returns -1 if an item with key k already exists in the tree.
     * <p>
     * Complexity: O(log n)
     */
    public int insert(int k, boolean i) { // this is a wrapper method to the following 'insert_rec' method which will contain and perform all of the logic behind rotations etc.
    	if (this.plain_search(k) != null) { // checks if the node is already in the tree. if yes, then return -1.
    		return -1;
    	}
    	int[] changes = new int[1]; // configuring an array to catch up with the amount of changes (i.e. promotions or rotations) that occurred 
    	this.root = insert_rec(this.getRoot(), new AVLNode(k,i,0), changes); // initializing the recursive function and catching its returned AVLNode, making it the new root of the tree.
    	return changes[0]; // the value we need to return considering  the given 'contract'
    	
    }
    
    /**
     * public AVLNode insert_rec(AVLNode node, AVLNode new_node, int[] change_info)
     * <p>
     * NEW SUPPLEMENTARY METHOD!
     * this is the recursive function which exists to supplement the wrapper method 'public int insert(int k, boolean i)' .
     * inserts an item with key k and info i to the AVL tree.
     * dynamically updates the amount of promotions or rotations that we performed after the insertion, stored as: change_info[0]
     * returns the node after some changes which should then be assigned to be the new root of the tree in the wrapper method 
     * this function gets called only when the node that we want to insret's key is not within the tree
     * <p>
     * the process occurs as follows:
     * we insert the key to the tree whose root is @node. then:
     * if abs(node.BalanceFactor) > 1 @implies we higher @change_info[0] by one, and continue to the rotations' process. after the rotations are done, we then update @node 's info by the update_info() method.
     * else: we update @node 's info by the update_info() method. if update_info() returned true, that means the the node's height was changed, then we higher @change_info[0] by one.
     * then we return @node
     * the recursive part of this method is done within the insertion process, which iterates the variable @node through all of the nodes we visited in order to insert @new_node
     * <p>
     * Complexity: O(log n)
     */
    public AVLNode insert_rec(AVLNode node, AVLNode new_node, int[] change_info) { // I'm here \\
    	if (node == null) { // if we reached the part where @node is null, that means that we reached the exact place that @new_node needs to be inserted in. thus we return new_node.
    		change_info[0]++; // we count the inserted node as a node who required change, therefore we higher change_info[0] by one
    		return new_node;
    	}
    	
    	if (new_node.getKey() < node.getKey()) { // if the node which needs to be inserted shall be in the left sub-tree of node
    		node.setLeft(insert_rec(node.getLeft(), new_node, change_info)); // insert new_node in the tree whose root is node.getLeft()
    	} else if (new_node.getKey() > node.getKey()){ // if the node which needs to be inserted shall be in the right sub-tree of node
    		node.setRight(insert_rec(node.getRight(), new_node, change_info)); // insert new_node in the tree whose root is node.getRight()
    	} else {
    		return node; // the node already exists in the tree (this case is never reached, but it's a safety precaution 
    	}
    	
    	int BF = node.BalanceFactor(); // get the balance factor of the node 
    	
    	// updating (if needed) if the node needs a rotation/height update 
    	if (Math.abs(BF) > 1) {
    		change_info[0]++;
    	} else {
        	if (node.update_info()) { // if the node doesn't require to have rotations performed on it, check if its height was changed in the insertion process. if so, we need to higher change_info[0] by one.
        		change_info[0]++;
        	}
        	return node; 
    	}
    	
    	// perform rotations: 
    	if (BF > 1 && new_node.getKey() < node.getLeft().getKey()) { // plain right rotation 
    		return rightRotation(node); 
    	} else if (BF > 1 && new_node.getKey() > node.getLeft().getKey()) { // left then right rotation 
    		node.setLeft(leftRotation(node.getLeft()));
    		node.update_info();
    		return rightRotation(node);
    	} else if (BF < -1 && new_node.getKey() > node.getRight().getKey()) { // plain left rotation 
    		return leftRotation(node);
    	} else if (BF < - 1 && new_node.getKey() < node.getRight().getKey()) { // right then left rotation 
    		node.setRight(rightRotation(node.getRight()));
    		node.update_info();
    		return leftRotation(node);
    	}
    	return node; // safety precaution 
    }
    

    /**
     * public int delete(int k)
     * <p>
     * deletes an item with key k from the binary tree, if it is there;
     * the tree must remain valid (keep its invariants).
     * returns the number of nodes which required re-balancing operations (i.e. demotions or rotations).
     * returns -1 if an item with key k was not found in the tree.
     * <p>
     * Complexity: O(log n)
     */
    public int delete(int k) {
    	int[] changes = new int[1];
    	if (this.plain_search(k) == null) {
    		return -1;
    	}
    	this.root = delete_rec(this.getRoot(), k, changes);
    	return changes[0];
    }
    
    /**
     * NEW SUPPLEMENTARY METHOD!
     * this is the recursive function which exists to supplement the wrapper method 'public int delete(int k)' .
     * deletes an item with key k from the binary tree, if it is there
     * returns the node after some changes which should then be assigned to be the new root of the tree in the wrapper method
     * this method gets called only when the node that we want to delete is within the tree, else we return -1 in the wrapper method.
     * <p>
     * the process occurs as follows:
     * we delete the key from the tree whose root is @node, then:
     * if abs(node.BalanceFactor) > 1 @implies we higher @change_info[0] by one, and continue to the rotations' process. after the rotations are done, we then update @node 's info by the update_info() method.
     * else: we update @node 's info by the update_info() method. if update_info() returned true, that means the the node's height was changed, then we higher @change_info[0] by one.
     * then we return @node
     * the recursive part of this method is done within the deletion process, which iterates the variable @node through all of the nodes we visited in order to delete the node with the wanted key
     * <p>
     * the deletion process:
     * when we arrive at the node which holds the wanted key to be deleted, we do as follows:
     * 1) if the node doesn't have any children - delete it (i.e. assign it to be a virtual node)
     * 2) if the node has only one child, update it to be its child. 
     * 3) if the node has two children, delete it and replace it with its successor (the minimum node of its right child).
     * this would be done by creating a new node with the same contents as the deleted node, but just with the key and info of its successor. 
     * this will make sure that anyone who held the pointer to this node, isn't going to be able to use it again.
     * after we so called 'replace' @node with its successor, we then need to remove the successor from the tree.
     * that is done with another recursive call to delete the successor from its right sub-tree.
     * we also send a completely new array as the third argument when deleting successors in this case, due to the fact that we don't want them counted as needed-to-be-updated nodes.
     * <p>
     * after we delete the node with the wanted key, we want to check if the node was affected from this process (i.e. needs a demotion/rotation)
     * if @node does need a demotion/rotation, we higher change_info[0] by one.
     * <p>
     * Complexity: O(log n)
     */
    public AVLNode delete_rec(AVLNode node, int key, int[] change_info) {
    	
    	if (node == null) { // if the node is null, then we didn't find k in the tree. 
    		return virtual; // we return node (if the node was null then the node was virtual. this occurs due to the mechanism of the getters of the left and right child of a node)
    	} else if (!node.isRealNode()) { // if the node is a virtual node, return it. this is just a safety precaution of any bugs in this method.
    		return node;
    	}
    	
    	if (key < node.getKey()) { // if the wanted key is smaller than the key of node, move the process onto the left sub-tree of node. 
    		node.setLeft(delete_rec(node.getLeft(), key, change_info));
    	} else if (key > node.getKey()) { // if the wanted key is smaller than the key of node, move the process onto the right sub-tree of node. 
    		node.setRight(delete_rec(node.getRight(), key, change_info));
    	} else { // if the wanted key is within the current node, preform a deletion:
    		if (node.getLeft() == null || node.getRight() == null) { // if @node has only one or less children 
    			AVLNode tmp;
    			if (node.getLeft() == null) { 
    				tmp = node.getRight();
    			} else {
    				tmp = node.getLeft();
    			}
    			if (tmp == null) { // if node doesn't have any children, replace node with a virtual node.
    				node = virtual; 
    			} else { // if node has only one child, perform a replacement between node and its child. since it's an AVLTree, node is of height 1, since it has only one child.
    				AVLNode parent = node.parent; // store the node's parent
					node = new AVLNode(tmp.getKey(), tmp.getValue(), tmp.getHeight()); // replace the node with its child
					node.setParent(parent); // restore the parent 
					if (parent != null) { // if the node wasn't the root (the root's parent is always a null)
						if (parent.isRealNode()) { // if the parent isn't a virtual node (safety precaution)
							if (parent.getKey() > node.getKey()) { // node was a left child
								parent.setLeft(node);
							} else { // node was a right child 
								parent.setRight(node);
							}
						}
					}
    			}
    		} else { // if @node has both children
    			AVLNode tmp = successor(node); // find the successor of node. it's supposed to be the node with the minimum key in its right sub-tree
    			node = replace(node, tmp); // delete node, but replace its role (placement) in the tree with its successor. deleting any pointers to node.
    			node.setRight(delete_rec(node.getRight(), tmp.getKey(), new int[1])); // delete the old successor's placement in the tree
    		}
    	}
    	
    	if (!node.isRealNode()) { // if the current node was deleted and had no children, it should be a virtual node now, therefore this test is necessary.
    		return node;
    	}
    	
    	int BF = node.BalanceFactor(); // get the balance factor of node
    	
    	if (Math.abs(BF) > 1) { // the node requires performing a rotation on it, thus we need to higher change_info[0] by one
    		change_info[0]++;
    	} else {
        	if (node.update_info()) { // the node didn't require a rotation performed on it, but it did have its height changed, therefor we need to higher change_info[0] by one
        		change_info[0]++;
        	}
        	return node; // the node doesn't need to have a rotation performed on it
    	}
    	
    	if (BF > 1 && node.getLeft().BalanceFactor() >= 0) { // plain right rotation 
    		return rightRotation(node);
    	} else if (BF > 1 && node.getLeft().BalanceFactor() < 0) { // left then right rotation 
    		node.setLeft(leftRotation(node.getLeft()));
    		node.update_info();
    		return rightRotation(node);
    	} else if (BF < -1 && node.getRight().BalanceFactor() <= 0) { // plain right rotation 
    		return leftRotation(node);
    	} else if (BF < -1 && node.getRight().BalanceFactor() > 0) { // right then left rotation 
    		node.setRight(rightRotation(node.getRight()));
    		node.update_info();
    		return leftRotation(node);
    	}
    	return node; // safety precaution 
    }
    
    /**
     * NEW SUPPLEMENTARY METHOD!
     * <p>
     * this method's role is to replace between a node and its successor, while keeping the wanted structure of an AVLNode.
     * <p>
     * Complexity: O(1)
     */
    private AVLNode replace(AVLNode node, AVLNode tmp) {
    	// stores the parent, left child, right child, and key of the node before it's assigned to a completely new AVLNode.
    	AVLNode parent = node.parent;
    	AVLNode left = node.leftChild;
    	AVLNode right = node.rightChild;
    	int key = node.getKey();
    	
    	node = new AVLNode(tmp.getKey(), tmp.getValue(), tmp.getHeight()); // configuring a new AVLNode with tmp's contents.
    	
    	// restoring the parents, children, and the various other fields of the node such as: XOR, size, and such with the update_info() method.
    	node.setLeft(left);
    	node.setRight(right);
    	node.setParent(parent);
    	node.update_info();
    	
    	if (parent == null) { // if the parent was null, then the node was the root of the tree. special treatment require in this case.
    		this.root = node;
    		return node;
    	} else if (!parent.isRealNode()) { // if the parent was a virtual node.
    		return node;
    	}

    	if (parent.getLeft().getKey() == key) { // if the node was a left child 
    		parent.setLeft(node);
    	} else { // if the node was a right child
    		parent.setRight(node);
    	}
    	return node; // return the new node after it has gone through its replacement process
    }
    
    /**
     * NEW SUPPLEMENTARY METHOD!
     * this method's role is to perform a right rotation on the given node N, just as we saw in class.
     * basic pointer changes.
     * <p>
     * Complexity: O(1)
     */
    private AVLNode rightRotation(AVLNode N) {
    	// storing the needed nodes to perform a rotation process
    	AVLNode L = N.getLeft();
    	// if the right child of L was a virtual node, because of our children 'getters' methods' mechanism, they return a null. so we want to care for that case with a ternary-if
    	AVLNode subT2 = L.getRight() != null ? L.getRight() : virtual; 
    	
    	// rotating sub-trees and nodes
    	L.setParent(N.getParent());
    	L.setRight(N);
    	N.setLeft(subT2);
    	
    	// updating the height, size, XOR variables of the nodes based of its sub-trees values
    	N.update_info();
    	L.update_info();
    	
    	// return the replacement of the node N (the replacement in the tree-structure)
    	return L;

    }
    
    /**
     * NEW SUPPLEMENTARY METHOD!
     * this method's role is to perform a left rotation on the given node N, just as we saw in class.
     * basic pointer changes.
     * <p>
     * Complexity: O(1)
     */
    private AVLNode leftRotation(AVLNode N) {
    	// storing the needed nodes to perform a rotation process
    	AVLNode R = N.getRight();
    	// if the left child of L was a virtual node, because of our children 'getters' methods' mechanism, they return a null. so we want to care for that case with a ternary-if
    	AVLNode subT1 = R.getLeft() != null ? R.getLeft() : virtual; 
    	
    	// rotating sub-trees and nodes
    	R.setParent(N.getParent());
    	R.setLeft(N);
    	N.setRight(subT1);
    	
    	// updating the height, size, XOR variables of the nodes based of its sub-trees values
    	N.update_info();
    	R.update_info();
    	
    	// return the replacement of the node N (the replacement in the tree-structure)
    	return R;
    }
    
    
    /**
     * public Boolean min()
     * <p>
     * Returns the info of the item with the smallest key in the tree,
     * or null if the tree is empty
     * <p>
     * Complexity: O(log n)
     */
    public Boolean min() {
    	if (this.empty()) { // if the tree is empty, return null
    		return null;
    	}
    	AVLNode curr = this.getRoot();
    	while (curr.getLeft() != null) { // iterate from the root up to the most left child we can reach
    		curr = curr.getLeft();
    	}
    	return curr.getValue(); // return the boolean value of the minimal-key node
    }

    /**
     * public Boolean max()
     * <p>
     * Returns the info of the item with the largest key in the tree,
     * or null if the tree is empty
     * <p>
     * Complexity: O(log n)
     */
    public Boolean max() {
    	if (this.empty()) { // if the tree is empty, return null
    		return null;
    	}
    	AVLNode curr = this.getRoot();
    	while (curr.getRight() != null) { // iterate from the root up to the most right child we can reach
    		curr = curr.getRight();
    	}
    	return curr.getValue(); // return the boolean value of the maximal-key node
    }

    /**
     * public int[] keysToArray()
     * <p>
     * Returns a sorted array which contains all keys in the tree,
     * or an empty array if the tree is empty.
     * <p>
     * this is a wrapper method to the recursive method nodesToArray
     * <p>
     * Complexity: O(n)
     */
    public int[] keysToArray() {
    	if (this.empty()) { // if the tree is empty return an empty array
    		return new int[] {};
    	}
    	
    	// initialize an array the size of the tree
        int[] array = new int[this.size()]; 
        
        // initialize an array to be sent out to get filled with the nodes of the tree in an in-ordered way with the recursive method nodesToArray
        AVLNode[] arr = new AVLNode[this.size()]; 
        
        // call nodesToArray to get the nodes of the tree in an in-ordered array
        nodesToArray(this.getRoot(), arr, 0);
        
        // iterate through the in-ordered array of nodes and extract the wanted info
        for (int i=0; i<array.length; i++) {
        	array[i] = arr[i].getKey();
        }
        return array;
    }

    /**
     * public boolean[] infoToArray()
     * <p>
     * Returns an array which contains all info in the tree,
     * sorted by their respective keys,
     * or an empty array if the tree is empty.
     * <p>
     * this is a wrapper method to the recursive method nodesToArray
     * <p>
     * Complexity: O(n)
     */
    public boolean[] infoToArray() {
    	if (this.empty()) { // if the tree is empty return an empty array
    		return new boolean[] {};
    	}
    	
    	// initialize an array the size of the tree
        boolean[] array = new boolean[this.size()];
        
        // initialize an array to be sent out to get filled with the nodes of the tree in an in-ordered way with the recursive method nodesToArray
        AVLNode[] arr = new AVLNode[this.size()];
        
        // call nodesToArray to get the nodes of the tree in an in-ordered array
        nodesToArray(this.getRoot(), arr, 0);
        
        // iterate through the in-ordered array of nodes and extract the wanted info
        for (int i=0; i<array.length; i++) {
        	array[i] = arr[i].getValue();
        }
        return array;
    }

    /**
     * NEW SUPPLEMENTARY METHOD!
     * this method's role is to return an array of all of the nodes in the tree, ordered by their keys.
     * it does it in a recursive way.
     * it basically does an in-order process of all of the tree.
     * <p>
     * this method's existence is mainly to fulfill keyToArray, and infoToArray's needs. they will be the wrapper methods.
     * <p>
     * Complexity: O(n)
     */
    private int nodesToArray(AVLNode N, AVLNode[] arr, int pos) {
    	// first, assign the left sub-tree in an in-ordered way to the array 
    	if (N.getLeft() != null) { 
    		pos = nodesToArray(N.getLeft(), arr, pos);
    	}

    	// assign the node to the array after the left sub-tree was assigned in an in-orderd way
    	arr[pos++] = N; 
    	
    	// last, assign the right sub-tree in an in-ordered way to the rest of the array, after both the node N and its left sub-tree were assigned in an in-ordered way to the array
    	if (N.getRight() != null) { 
    		pos = nodesToArray(N.getRight(), arr ,pos);
    	}
    	
    	// return the amount of occupied space in the array
    	return pos; 
    }
    
    
    
    /**
     * public int size()
     * <p>
     * Returns the number of nodes in the tree.
     * <p>
     * Complexity: O(1)
     */
    public int size() {
        return this.root.getSize();
    }
    
    

    /**
     * public int getRoot()
     * <p>
     * Returns the root AVL node, or null if the tree is empty
     * <p>
     * Complexity: O(1)
     */
    public AVLNode getRoot() {
    	if (this.root.getKey() == -1) { // if the root is a virtual node 
    		return null;
    	}
        if (this.empty()) { // if the tree is empty 
            return null;
        }
        return this.root;
    }

    /**
     * public boolean prefixXor(int k)
     *
     * Given an argument k which is a key in the tree, calculate the xor of the values of nodes whose keys are
     * smaller or equal to k.
     *
     * precondition: this.search(k) != null
     *
     * <p>
     * the way we are going to implement this method in a logarithmic run-time complexity, is as follows:
     * we are going to perform a search process of the key k in the tree. every node @curr we visit in the search process, that holds a key smaller than k, we are going to:
     * perform a XOR operation between out, curr's info (the boolean value it holds), and the XOR value of curr's left child (if there is one).
     * <p>
     * Complexity: O(log n)
     */
    public boolean prefixXor(int k){
    	// initialize the variable that we are going to return 
    	boolean out = false;
    	
    	// perform a search process
    	AVLNode curr = this.getRoot();
    	while (curr != null) {
    		if (curr.getKey() == k) { // if we reached the wanted node, do as follows:
    			// update out to be the XOR between out, curr.getValue(), and curr.getLeft.getXOR()
    			out = Boolean.logicalXor(out, curr.getValue());
    			if (curr.getLeft() != null) {
        			out = Boolean.logicalXor(out, curr.getLeft().getXOR());
    			}
    			break;
    		} else if (k < curr.getKey()) { // if node which holds the key @k is in the left sub-tree of curr, continue on with the searching process
    			curr = curr.getLeft();
    		} else { // if the node which holds the key @k is in the right sub-tree of curr, do as follows:
    			// update out to be the XOR between out, curr.getValue(), and curr.getLeft.getXOR()
    			out = Boolean.logicalXor(out, curr.getValue());
    			if (curr.getLeft() != null) {
        			out = Boolean.logicalXor(out, curr.getLeft().getXOR());
    			}
    			curr = curr.getRight(); // continue on with the searching process
    		}
    	}
    	
    	return out; 
    }
    
    	
    /**
     * public AVLNode successor
     * <p>
     * given a node 'node' in the tree, return the successor of 'node' in the tree (or null if successor doesn't exist)
     * <p>
     * if the right child of @node exists, then return the node with the minimal key in the right sub-tree of @node
     * else: 
     * iterate through curr's parents until you reach a parent that its key is bigger than curr's key.
     * <p>
     * Complexity: O(log n)
     */
    public AVLNode successor(AVLNode node){
    	// initialize the curr variable 
        if (node.getRight() != null) { // if node has a right child
        	AVLNode curr = node.getRight();
        	while (curr.getLeft() != null) {
        		curr = curr.getLeft();
        	}
        	return curr;
        }
        AVLNode parent = node.getParent(); // initialize the parent variable 
        
        // if node doesn't have a right child
        while (parent != null) { // break the loop once we reach the root (the root is the only node in the tree whose parent is a null)
        	if (node != parent.getRight()) { // if curr is paren't left child, break
        		break;
        	}
        	// prepare for the next iteration
        	node = parent; 
        	parent = parent.getParent();
        }
        // return the parent. if there was no successor, the while loop should have continued until it has reached the root, therefore the parent would be a null in that case, else the parent is the successor
        return parent;
    }

    /**
     * public boolean succPrefixXor(int k)
     *
     * This function is identical to prefixXor(int k) in terms of input/output. However, the implementation of
     * succPrefixXor should be the following: starting from the minimum-key node, iteratively call successor until
     * you reach the node of key k. Return the xor of all visited nodes.
     *
     * precondition: this.search(k) != null
     * 
     * <p>
     * Complexity: O(log(n) + k)
     */
    public boolean succPrefixXor(int k){
    	// initializing the variable that we are going to return 
    	boolean output = false;
    	
    	// iterate until we reach the node with the minimal key in the tree
    	AVLNode curr = this.root; 
    	while (curr.getLeft() != null) {
    		curr = curr.getLeft();
    	}
    	
    	// perform a successor call until we reach a node that holds a key greater than k
    	while (curr != null) { // in each iteration, we are going to perform a XOR between output and the node 'curr', and assign the returned boolean value to output.
    		if (curr.getKey() > k) {
    			break;
    		}
    		output = Boolean.logicalXor(output, curr.getValue());
    		curr = successor(curr);
    	}
    	return output;
    }

	
    
    public int getTreeHeight(AVLNode node) {
        if (node == null) {
            return -1;
        }
        else {
            return 1 + Math.max(getTreeHeight(node.leftChild), getTreeHeight(node.rightChild));
        }
    }

    public void printTree(AVLNode root) {
        if (this.empty()) {
            System.out.println("Tree is empty.");
        }
        int treeHeight =getTreeHeight(root);
        int treeWidth = (int) Math.pow(2, treeHeight);

        List<AVLNode> curr = new ArrayList<AVLNode>(1), next = new ArrayList<AVLNode>(2);
        curr.add(root);

        final int maxHalfLength = 4;
        int elements = 1;

        StringBuilder sb = new StringBuilder(maxHalfLength * treeWidth);
        for(int i = 0; i < maxHalfLength * treeWidth; i++)
            sb.append(' ');
        String textBuffer;

        // Iterating through height levels.
        for(int i = 0; i < treeHeight; i++) {

            sb.setLength(maxHalfLength * ((int)Math.pow(2, treeHeight-1-i) - 1));

            // Creating spacer space indicator.
            textBuffer = sb.toString();

            // Print tree node elements
            for(AVLNode n : curr) {

                System.out.print(textBuffer);

                if(n == null) {
                	
                    System.out.print("        ");
                    next.add(null);
                    next.add(null);

                } else {

                    if (n.getKey() != -1) {
                        System.out.printf("(%6d)", n.getKey());
                    }
                    else {
                        System.out.printf("        ");
                    }
                    next.add(n.leftChild);
                    next.add(n.rightChild);

                }

                System.out.print(textBuffer);

            }

            System.out.println();
            // Print tree node extensions for next level.
            if(i < treeHeight - 1) {

                for(AVLNode n : curr) {

                    System.out.print(textBuffer);

                    if(n == null)
                        System.out.print("        ");
                    else
                        System.out.printf("%s      %s",
                                n.leftChild == null ? " " : "/", n.rightChild == null ? " " : "\\");

                    System.out.print(textBuffer);

                }

                System.out.println();

            }

            // Renewing indicators for next run.
            elements *= 2;
            curr = next;
            next = new ArrayList<AVLNode>(elements);

        }
        System.out.println("\n\n");
    }

	
	
	
	
	
	

    /**
     * public class AVLNode
     * <p>
     * This class represents a node in the AVL tree.
     * <p>
     * IMPORTANT: do not change the signatures of any function (i.e. access modifiers, return type, function name and
     * arguments. Changing these would break the automatic tester, and would result in worse grade.
     * <p>
     * However, you are allowed (and required) to implement the given functions, and can add functions of your own
     * according to your needs.
     * <p>
     * !IMPORTANT
     * ALL, but ALL of the functions within this class, without any exceptions, hold the run-time complexity of O(1)
     */
    public class AVLNode { // the official class of a node in the tree
        private int key; // the key of the node 
        private boolean info; // the value that this node holds
        private int height; // the height of this node 
        private AVLNode leftChild; // the left child of the node 
        private AVLNode rightChild; // the right child of the node
        private AVLNode parent; // the parent of the node
        private int size; // the amount of nodes in the tree whose root is the node holding this variable 
        private boolean XOR = false; // tracks the XOR of all of the nodes in the tree whose root is this AVLNode
        
        /**
         * this constructor creates a virtual node
         */
        public AVLNode() {
            this.key = -1;
            
        }

        /**
         * this constructor creates a new node whose key is @key, whose info is @info, and whose height is @height
         */
        public AVLNode(int key, boolean info, int height) {  // creates node with key and value. also initializes the other fields as needed.
            this.key = key;
            this.info = info;
            this.height = height;
            this.rightChild = virtual; // initialize the right child to be a virtual node, as required in the instructions of this assignment
            this.leftChild = virtual; // initialize the left child to be a virtual node, as required in the instructions of this assignment
            this.size = 1; // initialize size to be 1 (the node was just created, therefore it doesn't have a left or right child)
            this.XOR = info; // initialize size to be the info of the node (the node was just created, therefore it doesn't have a left or right child)
        }

        //returns node's key (for virtual node return -1)
        public int getKey() {
            return this.key;
        }

        /**
         * the getter of the 'info' field of a node. 
         * if the node is a virtual node, return null.
         */
        public Boolean getValue() {
            if (!this.isRealNode()) { // the node is a virtual node
                return null;
            }
            return this.info;
        }

        
        // setting and getting the left and right childs of a node
        /**
         * the left child's setter
         */
        public void setLeft(AVLNode node) {
            this.leftChild = node;
            this.leftChild.setParent(this); // update the parent of the left child 
            this.update_info(); // update the fields, such as: XOR, size, height, due to the fact that we just changed one of its childs.
        }

        /**
         * the left child's getter
         * if the node is a virtual node, return null.
         */
        public AVLNode getLeft() {
            if (!this.leftChild.isRealNode()) { // the node is a virtual node
                return null;
            }
            return this.leftChild;
        }

        /**
         * the right child's setter
         */
        public void setRight(AVLNode node) {
            this.rightChild = node;
        	this.rightChild.setParent(this); /// update the parent of the right child 
        	this.update_info(); // update the fields, such as: XOR, size, height, due to the fact that we just changed one of its childs.
        }

        /**
         * the right child's getter
         * if the node is a virtual node, return null.
         */
        public AVLNode getRight() {
            if (!this.rightChild.isRealNode()) {  // the node is a virtual node
                return null;
            }
            return this.rightChild;
        }

        // setting and getting the 'parent' of a node
        /**
         * the parent's setter
         */
        public void setParent(AVLNode node) {
            this.parent = node;
            return;
        }
        
        /**
         * the parent's getter
         */
        public AVLNode getParent() {
            return this.parent;
        }

        /**
         * checks if the node is a virtual node or not.
         * Returns True if this is a non-virtual AVL node
         */
        public boolean isRealNode() {
            if (this.getKey() != -1) {
                return true;
            }
            return false;
        }

        // setting and getting the 'height' variable of a node
        /**
         * the height's setter
         */
        public void setHeight(int height) {
            this.height = height;
            return;
        }

        /**
         * the height's getter
         * if the node is a virtual node, return -1.
         */
        public int getHeight() {
            if (!this.isRealNode()) { // if the node is a virtual node, return -1. just as a premise to the implementation of an AVLTree.
                return -1;
            }
            return this.height; // if the node is not a virtual node, return its height.
        }
        
        
        
        // setting and getting the 'size' variable of a node
        /**
         * the size's getter
         * if the node is a virtual node, return 0.
         */
        public int getSize() {
        	if (this.getKey() == -1) { // the node is a virtual node 
        		return 0;
        	}
        	return this.size;
        }
        
        /**
         * the size's setter
         */
        public void setSize(int size) {
        	this.size = size;
        }
        
        
        
        // setting and getting the XOR of a node:
        
        /**
         * XOR's setter
         */
        public void setXOR(boolean out) {
        	this.XOR = out;
        }
        
        /**
         * XOR's getter
         */
        public boolean getXOR() { 
        	return this.XOR;
        }
        
        
        // updating the height of the node based on the heights of its sub-trees + if the height of the node was changed, return true.
        private boolean updateHeight() {
        	int tmp = this.getHeight();
        	this.height = Math.max(this.rightChild.getHeight(), this.leftChild.getHeight()) + 1; 
        	if (tmp != this.getHeight()) { // if the height is different after the update, return true, else return false.
        		return true;
        	} else {
        		return false;
        	}
        }
        
        // updating the size of the node based on the sizes of its sub-trees
        private void updateSize() {
        	this.size = this.rightChild.size + this.leftChild.size + 1;
        }
        
        // updating the XOR of the node's tree based on the info of the node and the XOR-s of its sub-trees
        private void updateXOR() {
        	this.XOR = Boolean.logicalXor(Boolean.logicalXor(this.rightChild.XOR, this.leftChild.XOR), this.info);
        }
        
        
        
        // updates the overall info about the node based with the other 'update' functions we implemented + returns true if the height of the node was changed
        public boolean update_info() {
        	this.updateSize(); // updating the size
        	this.updateXOR(); // updating the XOR
        	return this.updateHeight(); // updating the height, and returning true if the height was changed
        }
        
        
        /**
         * getting the balance factor of a node
         * if the node is a virtual node, return -1.
         * @return
         */
        public int BalanceFactor() {
        	if (this.getKey() == -1) { // the node is a virtual node 
        		return 0;
        	}
        	return this.leftChild.getHeight() - this.rightChild.getHeight();
        }
    }

}


