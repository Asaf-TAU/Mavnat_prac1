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
	private final AVLNode virtual;
	
    public AVLNode root;
    /**
     * This constructor creates an empty AVLTree.
     */
    public AVLTree(){
    	virtual = new AVLNode(); 
        this.root = virtual; 
    }

    /**
     * public boolean empty()
     * <p>
     * returns true if and only if the tree is empty
     */
    public boolean empty() {
        return !this.root.isRealNode();
    }

    /**
     * public boolean search(int k)
     * <p>
     * returns the info of an item with key k if it exists in the tree
     * otherwise, returns null
     */
    public Boolean search(int k) {
    	AVLNode out = this.plain_search(k);
    	if (out == null) {
    		return null;
    	} else {
    		return out.getValue();
    	}
    }

    public AVLNode plain_search(int k) { // change back to private
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
     * NEW METHOD!
     * this is the recursive function which exists to satisfy the wrapper method 'public int insert(int k, boolean i)' .
     * inserts an item with key k and info i to the AVL tree.
     * dynamically updates the amount of promotions or rotations that we performed after the insertion, stored as: change_info[0]
     * returns the new node which shall be updated as the new root of the tree
     * this function gets called only
     */
    public AVLNode insert_rec(AVLNode node, AVLNode new_node, int[] change_info) {
    	if (node == null) {
    		return new_node;
    	}
    	
    	if (new_node.getKey() < node.getKey()) {
    		node.setLeft(insert_rec(node.getLeft(), new_node, change_info));
    	} else if (new_node.getKey() > node.getKey()){
    		node.setRight(insert_rec(node.getRight(), new_node, change_info));
    	} else {
    		return node;
    	}
    	
    	int BF = node.BalanceFactor();
    	
    	// updating (if needed) if the node needs a rotation/height update 
    	if (Math.abs(BF) > 1) {
    		change_info[0]++;
    	} else {
        	if (node.update_info()) {
        		change_info[0]++;
        	}
        	return node;
    	}
    	
    	if (BF > 1 && new_node.getKey() < node.getLeft().getKey()) { 
    		return rightRotation(node);
    	} else if (BF > 1 && new_node.getKey() > node.getLeft().getKey()) {
    		node.setLeft(leftRotation(node.getLeft()));
    		node.update_info();
    		return rightRotation(node);
    	} else if (BF < -1 && new_node.getKey() > node.getRight().getKey()) {
    		return leftRotation(node);
    	} else if (BF < - 1 && new_node.getKey() < node.getRight().getKey()) {
    		node.setRight(rightRotation(node.getRight()));
    		node.update_info();
    		return leftRotation(node);
    	}
    	return node;
    }
    
    
    public int insert_2(int k, boolean i) { // this is a wrapper method to the following 'insert_rec' method which will contain and perform all of the logic behind rotations etc.
    	if (this.plain_search(k) != null) { // checks if the node is already in the tree. if yes, then return -1.
    		return -1;
    	}
    	int[] changes = new int[1]; // configuring an array to catch up with the amount of changes (i.e. promotions or rotations) that occurred 
    	this.root = insert_rec_2(this.getRoot(), new AVLNode(k,i,0), changes); // initializing the recursive function and catching its returned AVLNode, making it the new root of the tree.
    	return changes[0]; // the value we need to return considering  the given 'contract'
    	
    }
    
    /**
     * public AVLNode insert_rec(AVLNode node, AVLNode new_node, int[] change_info)
     * <p>
     * NEW METHOD!
     * this is the recursive function which exists to satisfy the wrapper method 'public int insert(int k, boolean i)' .
     * inserts an item with key k and info i to the AVL tree.
     * dynamically updates the amount of promotions or rotations that we performed after the insertion, stored as: change_info[0]
     * returns the new node which shall be updated as the new root of the tree
     * this function gets called only
     */
    public AVLNode insert_rec_2(AVLNode node, AVLNode new_node, int[] change_info) {
    	if (node == null) {
    		return new_node;
    	}
    	
    	if (new_node.getKey() < node.getKey()) {
    		node.setLeft(insert_rec(node.getLeft(), new_node, change_info));
    	} else if (new_node.getKey() > node.getKey()){
    		node.setRight(insert_rec(node.getRight(), new_node, change_info));
    	} else {
    		return node;
    	}
    	
    	node.update_info();
    	
    	return node;
    }
    

    /**
     * public int delete(int k)
     * <p>
     * deletes an item with key k from the binary tree, if it is there;
     * the tree must remain valid (keep its invariants).
     * returns the number of nodes which required rebalancing operations (i.e. demotions or rotations).
     * returns -1 if an item with key k was not found in the tree.
     */
    public int delete(int k) {
    	int[] changes = new int[1];
    	if (this.plain_search(k) == null) {
    		return -1;
    	}
    	this.root = delete_rec(this.getRoot(), k, changes);
    	return changes[0];
    }
    
    public AVLNode delete_rec(AVLNode node, int key, int[] change_info) {
    	
    	if (node == null) {
    		return virtual; 
    	} else if (!node.isRealNode()) {
    		return node;
    	}
    	
    	if (node.update_info()) {
    		change_info[0]++;
    	}
    	
    	if (key < node.getKey()) {
    		node.setLeft(delete_rec(node.getLeft(), key, change_info));
    		node.update_info();
    	} else if (key > node.getKey()) {
    		node.setRight(delete_rec(node.getRight(), key, change_info));
    		node.update_info();
    	} else {
    		if (node.getLeft() == null || node.getRight() == null) {
    			AVLNode tmp;
    			if (node.getLeft() == null) {
    				tmp = node.getRight();
    			} else {
    				tmp = node.getLeft();
    			}
    			if (tmp == null) {
    				node = virtual; 
    			} else {
    				AVLNode parent = node.parent;
					node = new AVLNode(tmp.getKey(), tmp.getValue(), tmp.getHeight());
					node.setParent(parent);
					if (parent != null) {
						if (parent.isRealNode()) {
							if (parent.getKey() > node.getKey()) {
								parent.setLeft(node);
							} else {
								parent.setRight(node);
							}
						}
					}
    			}
    		} else {
    			AVLNode tmp = successor(node);
    			node = replace(node, tmp);
    			node.setRight(delete_rec(node.getRight(), tmp.getKey(), change_info));
    		}
    	}
    	
    	if (!node.isRealNode()) {
    		return node;
    	}
    	
    	node.update_info();
    	
    	int BF = node.BalanceFactor();
    	
    	if (Math.abs(BF) > 1) {
    		change_info[0]++;
    	} else {
        	if (node.update_info()) {
        		change_info[0]++;
        	}
        	return node;
    	}
    	
    	if (BF > 1 && node.getLeft().BalanceFactor() >= 0) {
    		change_info[0]++;
    		return rightRotation(node);
    	} else if (BF > 1 && node.getLeft().BalanceFactor() < 0) {
    		change_info[0]++;
    		node.setLeft(leftRotation(node.getLeft()));
    		node.update_info();
    		return rightRotation(node);
    	} else if (BF < -1 && node.getRight().BalanceFactor() <= 0) {
    		change_info[0]++;
    		return leftRotation(node);
    	} else if (BF < -1 && node.getRight().BalanceFactor() > 0) {
    		change_info[0]++;
    		node.setRight(rightRotation(node.getRight()));
    		node.update_info();
    		return leftRotation(node);
    	}
    	return node;
    }
    
    /**
     * NEW METHOD!
     * @param node
     * @param tmp
     * @return
     */
    private AVLNode replace(AVLNode node, AVLNode tmp) {
    	AVLNode parent = node.parent;
    	AVLNode left = node.leftChild;
    	AVLNode right = node.rightChild;
    	int key = node.getKey();
    	
    	node = new AVLNode(tmp.getKey(), tmp.getValue(), tmp.getHeight());
    	node.setLeft(left);
    	node.setRight(right);
    	node.setParent(parent);
    	node.update_info();
    	
    	if (parent == null) {
    		this.root = node;
    		return node;
    	} else if (!parent.isRealNode()) {
    		return node;
    	}

    	if (parent.getLeft().getKey() == key) {
    		parent.setLeft(node);
    	} else {
    		parent.setRight(node);
    	}
    	return node;
    }
    
    /**
     * NEW METHOD!
     * @param N
     * @return
     */
    private AVLNode rightRotation(AVLNode N) {
    	AVLNode L = N.getLeft();
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
     * NEW METHOD!
     * @param N
     * @return
     */
    private AVLNode leftRotation(AVLNode N) {
    	AVLNode R = N.getRight();
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
     */
    public Boolean min() {
    	if (this.empty()) {
    		return null;
    	}
    	AVLNode curr = this.getRoot();
    	while (curr.getLeft() != null) {
    		curr = curr.getLeft();
    	}
    	return curr.getValue();
    }

    /**
     * public Boolean max()
     * <p>
     * Returns the info of the item with the largest key in the tree,
     * or null if the tree is empty
     */
    public Boolean max() {
    	if (this.empty()) {
    		return null;
    	}
    	AVLNode curr = this.getRoot();
    	while (curr.getRight() != null) {
    		curr = curr.getRight();
    	}
    	return curr.getValue();
    }

    /**
     * public int[] keysToArray()
     * <p>
     * Returns a sorted array which contains all keys in the tree,
     * or an empty array if the tree is empty.
     */
    public int[] keysToArray() {
    	if (this.empty()) {
    		return new int[] {};
    	}
        int[] array = new int[this.size()];
        AVLNode curr = this.getRoot();
        keysToArray_rec(curr, array, 0);
        return array;
    }
    
    /**
     * NEW METHOD!
     * @param N
     * @param arr
     * @param pos
     * @return
     */
    private int keysToArray_rec(AVLNode N, int[] arr, int pos) {
    	if (N.getLeft() != null) {
    		pos = keysToArray_rec(N.getLeft(), arr, pos);
    	}
    	arr[pos++] = N.getKey();
    	if (N.getRight() != null) {
    		pos = keysToArray_rec(N.getRight(), arr, pos);
    	}
    	return pos;
    }

    /**
     * public boolean[] infoToArray()
     * <p>
     * Returns an array which contains all info in the tree,
     * sorted by their respective keys,
     * or an empty array if the tree is empty.
     */
    public boolean[] infoToArray() {
    	if (this.empty()) {
    		return new boolean[] {};
    	}
        boolean[] array = new boolean[this.size()];
        AVLNode curr = this.getRoot();
        infoToArray_rec(curr, array, 0);
        return array;
    }

    /**
     * NEW METHOD!
     * @param N
     * @param arr
     * @param pos
     * @return
     */
    private int infoToArray_rec(AVLNode N, boolean[] arr, int pos) {
    	if (N.getLeft() != null) {
    		pos = infoToArray_rec(N.getLeft(), arr, pos);
    	}
    	arr[pos++] = N.getValue();
    	if (N.getRight() != null) {
    		pos = infoToArray_rec(N.getRight(), arr, pos);
    	}
    	return pos;
    }
    
    
    /**
     * public int size()
     * <p>
     * Returns the number of nodes in the tree.
     */
    public int size() {
        return this.root.getSize();
    }

    /**
     * public int getRoot()
     * <p>
     * Returns the root AVL node, or null if the tree is empty
     */
    public AVLNode getRoot() {
    	if (this.root.getKey() == -1) {
    		return null;
    	}
        if (this.empty()) {
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
     */
    public boolean prefixXor(int k){
    	boolean out = false;
    	AVLNode curr = this.getRoot();
    	while (curr != null) {
    		if (curr.getKey() == k) {
    			out = Boolean.logicalXor(out, curr.getValue());
    			if (curr.getLeft() != null) {
        			out = Boolean.logicalXor(out, curr.getLeft().getXOR());
    			}
    			break;
    		} else if (k < curr.getKey()) {
    			curr = curr.getLeft();
    		} else {
    			out = Boolean.logicalXor(out, curr.getValue());
    			if (curr.getLeft() != null) {
        			out = Boolean.logicalXor(out, curr.getLeft().getXOR());
    			}
    			curr = curr.getRight();
    		}
    	}
    	
    	return out;
    }
    
    	
    /**
     * public AVLNode successor
     *
     * given a node 'node' in the tree, return the successor of 'node' in the tree (or null if successor doesn't exist)
     *
     * @param node - the node whose successor should be returned
     * @return the successor of 'node' if exists, null otherwise
     */
    public AVLNode successor(AVLNode node){
        if (node.getRight() != null) {
        	AVLNode curr = node.getRight();
        	while (curr.getLeft() != null) {
        		curr = curr.getLeft();
        	}
        	return curr;
        }
        AVLNode parent = node.getParent();
        while (parent != null) {
        	if (node != parent.getRight()) {
        		break;
        	}
        	node = parent;
        	parent = parent.getParent();
        }
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
     */
    public boolean succPrefixXor(int k){ // O(log(n) + N), while N is the amount of nodes whose keys are lower or equal to k, and while n is the size of the tree (so O(log(n)) is the height of the tree)
    	AVLNode curr = this.root;
    	boolean output = false;
    	while (curr.getLeft() != null) {
    		curr = curr.getLeft();
    	}
    	while (curr != null) {
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
     */
    public class AVLNode {
        private int key;
        private boolean info;
        private int height;
        private AVLNode leftChild;
        private AVLNode rightChild;
        private AVLNode parent;
        private int size;
        private boolean XOR = false ; // tracks the XOR of all of the nodes in the tree whose root is this AVLNode
        
        public AVLNode() {  // virtual node
            this.key = -1;
            
        }

        public AVLNode(int key, boolean info, int height) {  // creates node with key and value
            this.key = key;
            this.info = info;
            this.height = height;
            this.rightChild = virtual; 
            this.leftChild = virtual; 
            this.size = 1;
            this.XOR = info;
        }

        //returns node's key (for virtual node return -1)
        public int getKey() {
            return this.key;
        }

        //returns node's value [info] (for virtual node return null)
        public Boolean getValue() {
            if (!this.isRealNode()) {
                return null;
            }
            return this.info;
        }

        //sets left child
        public void setLeft(AVLNode node) {
            this.leftChild = node;
            this.leftChild.setParent(this);
            this.update_info();
        }

        //returns left child (if there is no left child return null)
        public AVLNode getLeft() {
            if (!this.leftChild.isRealNode()) {
                return null;
            }
            return this.leftChild;
        }

        //sets right child
        public void setRight(AVLNode node) {
            this.rightChild = node;
        	this.rightChild.setParent(this);
        	this.update_info();
        }

        //returns right child (if there is no right child return null)
        public AVLNode getRight() {
            if (!this.rightChild.isRealNode()) {
                return null;
            }
            return this.rightChild;
        }

        //sets parent
        public void setParent(AVLNode node) {
            this.parent = node;
            return;
        }

        
        //returns the parent (if there is no parent return null)
        public AVLNode getParent() {
            return this.parent;
        }

        // Returns True if this is a non-virtual AVL node
        public boolean isRealNode() {
            if (this.getKey() != -1) {
                return true;
            }
            return false;
        }

        // sets the height of the node
        public void setHeight(int height) {
            this.height = height;
            return;
        }

        // Returns the height of the node (-1 for virtual nodes)
        public int getHeight() {
            if (!this.isRealNode()) {
                return -1;
            }
            return this.height;
        }
        
        // setting and getting the 'size' variable of a node
        public int getSize() {
        	if (this.getKey() == -1) {
        		return 0;
        	}
        	return this.size;
        }
        
        public void setSize(int size) {
        	this.size = size;
        }
        
        
        
        // setting and getting the XOR of a node
        public void setXOR(boolean out) {
        	this.XOR = out;
        }
        
        public boolean getXOR() {
        	return this.XOR;
        }
        
        
        // updating the height of the node based on the heights of its sub-trees + if the height of the node was changed, return true.
        private boolean updateHeight() {
        	int tmp = this.getHeight();
        	this.height = Math.max(this.rightChild.getHeight(), this.leftChild.getHeight()) + 1;
        	if (tmp != this.getHeight()) {
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
        	this.updateSize();
        	this.updateXOR();
        	return this.updateHeight();
        }
        
        
        
        // getting the balance factor of a node
        public int BalanceFactor() {
        	if (this.getKey() == -1) {
        		return 0;
        	}
        	return this.leftChild.getHeight() - this.rightChild.getHeight();
        }
    }

}


