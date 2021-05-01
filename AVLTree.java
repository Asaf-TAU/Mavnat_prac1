import java.util.ArrayList;
import java.util.List;


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

    public AVLNode root;
    /**
     * This constructor creates an empty AVLTree.
     */
    public AVLTree(){
        this.root = new AVLNode();
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
        AVLNode curr = this.root;
        
        if (curr.getKey() != -1) {
            while (curr != null) { // stops when the Node is a null
            	if (curr.getKey() == k) { // if the current Node holds a key which is equal to k, then return the Node's info. 
            		return curr.info;
            	} else {
            		if (curr.getKey() < k) { // if the current Node holds a key which is greater than k, then move on to check the current Node's right child.
            			curr = curr.getRight();
            		} else {
            			curr = curr.getLeft(); // if the current Node holds a key which is less than k, then move on to check the current Node's left child.
            		}
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
    public int insert(int k, boolean i) {
    	AVLNode curr = this.root;
        if (curr.getKey() == -1) {
    		curr = new AVLNode(k, i, 0);
    		curr.setLeft(new AVLNode());
    		curr.setRight(new AVLNode());
    		this.root = curr;
    		this.root.setParent(null);
    		return 1;
    	}

        while (curr.getKey() != -1) { // similar process to the 'search' method, just with the feature of updating the 'direction' variable.
        	if (curr.getKey() == k) {
        		return -1;
         	}
        	else {
         		if (k > curr.getKey() && curr.getRight() != null) {
         			curr = curr.getRight();
         		}
         		else if (k > curr.getKey()) {
         		    break;
                }
         		else if (k < curr.getKey() && curr.getLeft() != null) {
         			curr = curr.getLeft();
         		}
         		else if (k < curr.getKey()) {
         		    break;
                }
         	}
        }

        if (k > curr.getKey()) {
            curr.setRight(new AVLNode(k, i, curr.getHeight() + 1));
            curr.setMax(curr.getRight());
        } else {
            curr.setLeft(new AVLNode(k, i, curr.getHeight() + 1));
            curr.setMin(curr.getLeft());
        }
        
        // after done with the insertion, will now update the parent's fields who were involved in the insertion process.
//        AVLNode curr_parent = curr.getParent();
//        AVLNode tmp = new AVLNode(k, i, 0);
        
        // making sure the inserted node has virtual sons + updating the inserted node's parents
//        tmp.setLeft(new AVLNode());
//        tmp.setRight(new AVLNode());
//        tmp.setParent(curr_parent);
        
        // initializing the output variable 'changes' which will hold the amount of rotations and height changes done due to the insertion
//        int changes = 1;
//        boolean changed = false; // determines whether the parent's height of the inserted node has changed or not

//        if (curr_parent.getLeft() == null && curr_parent.getRight() == null) {
//        	curr_parent.setHeight(curr_parent.getHeight() + 1);
//        	changes++;
//        	changed = true;
//        }

//        if (k < curr_parent.getKey()) {
//        	curr_parent.setLeft(tmp);
//        } else {
//        	curr_parent.setRight(tmp);
//        }
        	
        // we will now iterate from bottom to up through all of the parents and perform rotations on those who deviate from the Balance-factor requirement.
//        int BF;
//        curr_parent = curr_parent.getParent();
//        while (curr_parent != null) {
//        	BF = curr_parent.getBalance();
//        	if (Math.abs(BF) < 2 && !changed) {
//        		break;
//        	} else if (Math.abs(BF) < 2) {
//        		curr_parent = curr_parent.getParent();
//        	} else { // a rotation needs to be performed + higher the 'changes' (the output of this method) by one.
//        		changes++;
//        		if (BF > 0) {
//        			if (curr_parent.getKey() < curr_parent.getParent().getKey()) {
//        				curr_parent.getParent().setLeft(rightRotate(curr_parent));
//        			} else {
//        				curr_parent.getParent().setRight(rightRotate(curr_parent));
//        			}
//        		} else {
//        			if (curr_parent.getKey() < curr_parent.getParent().getKey()) {
//        				curr_parent.getParent().setLeft(leftRotate(curr_parent));
//        			} else {
//        				curr_parent.getParent().setRight(leftRotate(curr_parent));
//        			}
//        		}
//        		break;
//        	}
//        }
//        return changes;
        return 0;
    }

    
    /**
     * public int delete(int k)
     * <p>
     * deletes an item with key k from the binary tree, if it is there;
     * the tree must remain valid (keep its invariants).
     * returns the number of nodes which required rebalancing operations (i.e. demotions or rotations).
     * returns -1 if an item with key k was not found in the tree.
     */
    public int delete(int k) { // this is far from being correct 
    	AVLNode curr = this.root;
        AVLNode curr_parent = new AVLNode();
        boolean changed = false;
        int changes = 0;
        
        while (curr.getKey() != -1) { // similar process to the 'search' method, just with the feature of updating the 'direction' variable.
        	if (curr.getKey() == k) { // if the key was found, replace the node which holds the key, by a virtual node.
        		curr_parent = curr.getParent();
        		if (k < curr_parent.getKey()) { // if the removed node was a left child of its parent
        			curr_parent.setLeft(new AVLNode());
        			if (curr_parent.getRight() != null ) { 
        				curr_parent.setHeight(curr_parent.getRight().getHeight() + 1);
        			} else {
        				curr_parent.setHeight(0);
        				changed = true;
        			}
        		} else { // if the removed node was a right child of its parent
        			curr_parent.setRight(new AVLNode());
        			if (curr_parent.getLeft() != null ) {
        				curr_parent.setHeight(curr_parent.getLeft().getHeight() + 1);
        			} else {
        				curr_parent.setHeight(0);
        				changed = true;
        			}
        		}
        		break; // the wanted node was deleted, the search can now be done.
        		
         	} else { 
         		if (curr.getKey() > k) {
         			curr = curr.getRight();
         		} else {
         			curr = curr.getLeft();
         		}
         	}
        }
        
        if (curr.getKey() == -1) { // base case 
        	return 0;
        }
        
        int BF = 0;
        while (curr_parent != null) {
        	BF = curr_parent.getBalance();
        	if (Math.abs(BF) < 2 && !changed) {
        		break;
        	} else if (Math.abs(BF) < 2) {
        		curr_parent = curr_parent.getParent();
        	} else {
        		changes++;
        		if (BF > 0) {
        			if (curr_parent.getLeft().getBalance() >= 0) {
        				if (curr_parent.)
        				curr_parent.getParent().setLeft(rightRotate(curr_parent));
        			} else {
        				curr_parent.getParent().setRight(rightRotate(curr_parent));
        			}
        		} else {
        			if (curr_parent.getKey() < curr_parent.getParent().getKey()) {
        				curr_parent.getParent().setLeft(leftRotate(curr_parent));
        			} else {
        				curr_parent.getParent().setRight(leftRotate(curr_parent));
        			}
        		}
        	}
        	
        }
        
        return changes;
        
    }
    
    
    private AVLNode rightRotate(AVLNode N) {
    	AVLNode L = N.getLeft();
    	AVLNode subT2 = L.getRight();
    	
    	// rotating sub-trees and nodes
    	L.setRight(N);
    	N.setLeft(subT2);
    	subT2.setParent(N);
    	
    	// updating heights
    	N.setHeight(Math.max(N.getLeft().getHeight(), N.getParent().getHeight()) + 1);
    	L.setHeight(Math.max(L.getLeft().getHeight(), L.getRight().getHeight()) + 1);
    	
    	// updating parents
    	L.setParent(N.getParent());
    	N.setParent(L);
    	
    	//updating the min-s and max-s
    	L.setMax(N.getMax());
    	if (subT2.getMin() != null) {
    		N.setMin(subT2.getMin());
    	}
    	
    	// return the new root 
    	return L;
    	
    }
    
    
    private AVLNode leftRotate(AVLNode N) {
    	AVLNode R = N.rightChild;
    	AVLNode subT1 = R.leftChild;
    	
    	// rotating sub-trees and nodes
    	R.setLeft(N);
    	N.setRight(subT1);
    	subT1.setParent(N);
    	
    	// updating heights
    	N.setHeight(Math.max(N.getLeft().getHeight(), N.getParent().getHeight()) + 1);
    	R.setHeight(Math.max(R.getLeft().getHeight(), R.getRight().getHeight()) + 1);
    	
    	// updating parents
    	R.setParent(N.getParent());
    	N.setParent(R);
    	
    	// updating the min-s and max-s
    	R.setMin(N.getMin());
    	if (subT1.getMax() != null) {
    		N.setMax(subT1.getMax());
    	}
    	
    	// return the new root
    	return R;
    }
    
    
    /**
     * public Boolean min()
     * <p>
     * Returns the info of the item with the smallest key in the tree,
     * or null if the tree is empty
     */
    public Boolean min() {
    	if (this.root.getMin().isRealNode()) {
    		return this.root.getMin().getValue();
    	}
        return null;
    }

    /**
     * public Boolean max()
     * <p>
     * Returns the info of the item with the largest key in the tree,
     * or null if the tree is empty
     */
    public Boolean max() {
    	if (this.root.getMax().isRealNode()) {
    		return this.root.getMax().getValue();
    	}
        return null;
    }

    /**
     * public int[] keysToArray()
     * <p>
     * Returns a sorted array which contains all keys in the tree,
     * or an empty array if the tree is empty.
     */
    public int[] keysToArray() {
        int[] array = new int[this.size()];
        AVLNode curr = this.getRoot();
        keysToArray_rec(curr, array, 0);
        return array;
    }
    
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
        boolean[] array = new boolean[this.size()];
        AVLNode curr = this.getRoot();
        infoToArray_rec(curr, array, 0);
        return array;
    }

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
        return false;
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
        	return node.getRight().getMin();
        }
        AVLNode parent = node.getParent();
        while (parent != null && node == parent.getRight()) {
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
    public boolean succPrefixXor(int k){
        return false;
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
        System.out.println("Done.");
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
        private AVLNode min;
        private AVLNode max;
        private int size;
        
        public AVLNode() {  // virtual node
            this.key = -1;
            
        }

        public AVLNode(int key, boolean info, int height) {  // creates node with key and value
            this.key = key;
            this.info = info;
            this.height = height;
            this.rightChild = new AVLNode();
            this.leftChild = new AVLNode();
            this.size = 1;
            this.min = this;
            this.max = this;
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
            return;
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
            return;
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
        
        public int getBalance() {
        	if (this.getKey() == -1) {
        		return 0;
        	}
        	return this.getLeft().getHeight() - this.getRight().getHeight();
        }
        
        
        // getting and setting min and max 
        public AVLNode getMin() {
        	if (this.getKey() == -1) {
        		return this;
        	}
        	return this.min;
        }
        
        public void setMin(AVLNode min) {
        	this.min = min;
        }
        
        public AVLNode getMax() {
        	if (this.getKey() == -1) {
        		return this;
        	}
        	return this.max;
        }
        
        public void setMax(AVLNode max) {
        	this.max = max;
        }
        
        public int getSize() {
        	if (this.getKey() == -1) {
        		return 0;
        	}
        	return this.size;
        }
        
        public void setSize(int size) {
        	this.size = size;
        }
    }

}


