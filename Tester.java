import java.util.Random;
import java.util.Arrays;

public class Tester {
	public static int ind = 0;
    public static long startTime_1 = 0;
	public static long endTime_1 = 0;
	public static long sum_1 = 0;
	public static long diff_1 = 0;
	public static long startTime_2 = 0;
	public static long endTime_2 = 0;
	public static long sum_2 = 0;
	public static long diff_2 = 0;
	
    public static void main(String[] args) throws InterruptedException {
    	AVLTree testTree = new AVLTree();
    	for (int i=1; i<9; i++) {
    		testTree.insert(i, false);
    	}
    	testTree.printTree(testTree.getRoot());
    	for (int i=1; i<8; i++) {
    		testTree.delete(i);
    		testTree.printTree(testTree.getRoot());
    	}
    	
    }

    
    
    public static void measurements_ordered() throws InterruptedException {
    	AVLTree testTree;
    	for (int i=10; i>0; i--) {
    		sum_1 = 0;
    		sum_2 = 0;
    		ind = 0;
    		testTree = treeFromArr(generate_arr(1, 1000*i, new int[1000*i]));
    		ind = 0;
    		testTree = treeFromArr_2(generate_arr(1, 1000*i, new int[1000*i]));
    		System.out.println(testTree.keysToArray().length);
    		System.out.println("the average duration of execution of an insertion in an AVL Tree is, on average, for i="+i+" (in nanoseconds): " + sum_1/(1000*i));
    		System.out.println("the average duration of execution of an insertion in an regular BST  is, on average, for i="+i+" (in nanoseconds): " + sum_2/(1000*i));
    	}
    	
    	return;
    }
    
    public static int[] generate_arr(int start, int end, int[] arr) {
    	if (end < start) {
    		return null;
    	}
    	int mid = Math.floorDiv((start+end), 2);
    	arr[ind++] = mid;
    	generate_arr(start, mid-1, arr);
    	generate_arr(mid+1, end, arr);
    	if (start == 1 && end == arr.length) {
    		return arr;
    	}
    	return null;
    }
    
    public static AVLTree treeFromArr(int[] arr) {
    	AVLTree testTree = new AVLTree();
    	for (int key : arr) {
    		// measure execution time
    		startTime_1 = System.nanoTime();
    		testTree.insert(key, false);
    		endTime_1 = System.nanoTime();
    		
    		// analyze
    		diff_1 = endTime_1 - startTime_1;
    		sum_1 += diff_1;
    		
    		// reset times
            startTime_1 = 0;
        	endTime_1 = 0;
        	diff_1 = 0;
    	}
    	return testTree;
    }
    
    public static AVLTree treeFromArr_2(int[] arr) {
    	AVLTree testTree = new AVLTree();
    	for (int key : arr) {
    		// measure execution time
    		startTime_2 = System.nanoTime();
    		testTree.insert_2(key, false);
    		endTime_2 = System.nanoTime();
    		
    		// analyze
    		diff_2 = endTime_2 - startTime_2;
    		sum_2 += diff_2;
    		
    		// reset times
            startTime_2 = 0;
        	endTime_2 = 0;
        	diff_2 = 0;
    		
    	}
    	return testTree;
    }

    public static void measurements_2_plain() throws InterruptedException {
    	Random ran = new Random();
    	
    	for (int i=10; i>0; i--) { // iterating between 1...5
            AVLTree testTree_1 = new AVLTree();    
            AVLTree testTree_2 = new AVLTree();
            
            startTime_1 = 0;
        	endTime_1 = 0;
        	sum_1 = 0;
        	diff_1 = 0;
            startTime_2 = 0;
        	endTime_2 = 0;
        	sum_2 = 0;
        	diff_2 = 0;
        	int insertKey = 0;
            System.out.printf("this iteration is focusing on: i=%d !%n", i);
            
            // do the insertions here:
            for (int j=1; j<1000*i; j++) { 
            	while (testTree_1.plain_search(insertKey) != null) {
            		insertKey = ran.nextInt(1000*i - 1);
            		insertKey++;
            	}

            	// measure the following operations:
            	startTime_1 = System.nanoTime();
            	testTree_1.insert(insertKey, false);
                endTime_1 = System.nanoTime();
                
            	startTime_2 = System.nanoTime();
            	testTree_2.insert_2(insertKey, false);
                endTime_2 = System.nanoTime();
                
                // calculate the execution time:
                diff_1 = endTime_1 - startTime_1;
                diff_2 = endTime_2 - startTime_2;
                		
                // add the execution time to the overall execution time
                sum_1 += diff_1;
                sum_2 += diff_2;
            }
            // insertion process done
            
            // analyze the execution time 
            System.out.println("the average duration of an insertion sums up to, using an AVLTree (in nanoseconds), is: " + sum_1/(1000*i));
            System.out.println("the average duration of an insertion sums up to, using a regular Binary Tree (in nanoseconds), is: " + sum_2/(1000*i));
            System.out.printf("%n");
    	}
    }   
    
   
    public static void measurements_XOR() throws InterruptedException {
    	Random ran = new Random(); // no actual need
    	
    	for (int i=10; i>0; i--) { // iterating between 1...5
            AVLTree testTree = new AVLTree();     
            System.out.printf("this iteration is focusing on: i=%d !%n", i);
            
            // do the insertions here:
            for (int j = 0; j<500*i; j++) { 
                int insertKey = ran.nextInt(1000000);
                testTree.insert(insertKey, ran.nextBoolean());
            }
            // insertion process done
            
            int hundred = 100;
            startTime_1 = 0;
        	endTime_1 = 0;
        	sum_1 = 0;
        	diff_1 = 0;
            startTime_2 = 0;
        	endTime_2 = 0;
        	sum_2 = 0;
        	diff_2 = 0;
        	
            int[] keys = testTree.keysToArray();
            
            for (int key : keys) {
            	if (hundred == 0) {
            		System.out.println("average of first one hundred insertions is (in nanoseconds) for prefixXor: " + (sum_1/(100)));
            		System.out.println("average of first one hundred insertions is (in nanoseconds) for succPrefixXor: " + (sum_2/(100)));
            	}
            	
            	startTime_1 = System.nanoTime();
            	// Xor operations
            	testTree.prefixXor(key); 
            	// end of Xor operations
                endTime_1 = System.nanoTime();
                
            	startTime_2 = System.nanoTime();
            	// Xor operations
            	testTree.succPrefixXor(key); 
            	// end of Xor operations
                endTime_2 = System.nanoTime();
                
                
                diff_1 = endTime_1 - startTime_1;
                sum_1 += diff_1;
                diff_2 = endTime_2 - startTime_2;
                sum_2 += diff_2;
        //\\ update of hundred //\\
                hundred--; 
            }

            System.out.println("average of all of the process (in nanoseconds) for prefixXor: " + sum_1/(500*i));
            System.out.println("average of all of the process (in nanoseconds) for succPrefixXor: " + sum_2/(500*i));
            System.out.printf("%n");
    	}
    }
    	
    public static void prefix_to_succ_test() {
        AVLTree testTree = new AVLTree();
        testTree.insert(9, true);
        testTree.insert(1, true);
        testTree.insert(2, false);
        testTree.insert(8, false);
        testTree.insert(6, true);
        testTree.insert(7, true);
        
        // testing if prefixXor and succPrefixXor are matching: (they are)
    	int[] keys = testTree.keysToArray();
    	for (int key : keys) {
    		if (testTree.prefixXor(key) != testTree.succPrefixXor(key)) {
    			System.out.println("problem with: " + key);
    		}
    	}
    }
    
    
    
    public static void print_node(AVLTree.AVLNode node) {
        System.out.println("size: " + node.getSize());
        System.out.println("height: " + node.getHeight());
        System.out.println("parent: " + node.getParent());
    }
    
    
    
    public static void general_test() {
    	int[] arr1 = {3,8,9,2,0,4,7,11,22,33,44,55,66,77,88,99,100,23,24,25,26};
		AVLTree t1 =  new AVLTree();
		if (t1.empty() != true) {
			System.out.println("empty func false negative");
		}
		if (t1.search(3) != null) {
			System.out.println("Problem with search func");
		}
		for (int i=0; i<arr1.length; i++) {
			int temp = arr1[i];
			if (temp%2 == 1) {
				t1.insert(temp, false);
			}
			else {
				t1.insert(temp, true);
			}
		}
		if (t1.getRoot().getKey() != 8) {
			System.out.println("Problem with insert/balance: root should be 8 here");
		}
		//Check height tests
		int[] a = {0,7,9,22,24,26,44,66,88,100};
		for (int i=0; i<a.length; i++) {
			int temp = a[i];
			if (t1.plain_search(temp).getHeight() != 0) {
				System.out.println("Hight problem in height 0 leaves");
			}
		}
		int[] b = {2,4,11,25,55,99};
		for (int i=0; i<b.length; i++) {
			int temp = b[i];
			if (t1.plain_search(temp).getHeight() != 1) {
				System.out.println("Hight problem in height 1 nodes");
			}
		}
		int[] c = {3,23,77};
		for (int i=0; i<c.length; i++) {
			int temp = c[i];
			if (t1.plain_search(temp).getHeight() != 2) {
				System.out.println("Hight problem in height 2 nodes");
			}
		}
		if (t1.plain_search(33).getHeight() != 3) {
			System.out.println("Hight problem in height 3 nodes");
		}
		if (t1.plain_search(8).getHeight() != 4) {
			System.out.println("Hight problem in height 4 nodes");
		}
		if (t1.min() != true || t1.max() != true) {
			System.out.println("Min/Max problem");
		}
		
		//Check insert and rebalances
		t1.insert(101, false);
		if (t1.getRoot().getKey() !=33) {
			System.out.println("Problem with insert/balance: root should be 33 here");
		}

		if (t1.min() != true || t1.max() != false) {
			System.out.println("Min/Max problem");
		}
		
		//Check height tests
		int[] d = {0,7,9,22,24,26,44,66,88,101};
		for (int i=0; i<d.length; i++) {
			int temp = d[i];
			if (t1.plain_search(temp).getHeight() != 0) {
				System.out.println("Hight problem in height 0 leaves");
			}
		}
		int[] e = {2,4,11,25,55,100};
		for (int i=0; i<e.length; i++) {
			int temp = e[i];
			if (t1.plain_search(temp).getHeight() != 1) {
				System.out.println("Hight problem in height 1 nodes: height of" + temp + "is" + t1.plain_search(temp).getHeight());
			}
		}
		int[] f = {3,23,99};
		for (int i=0; i<f.length; i++) {
			int temp = f[i];
			if (t1.plain_search(temp).getHeight() != 2) {
				System.out.println("Hight problem in height 2 nodes");
			}
		}
		if (t1.plain_search(8).getHeight() != 3 || t1.plain_search(77).getHeight() != 3 ) {
			System.out.println("Hight problem in height 3 nodes");
		}
		if (t1.plain_search(33).getHeight() != 4) {
			System.out.println("Hight problem in height 4 nodes");
		}
		if( t1.getRoot().getKey() != 33) {
			System.out.println("root problem");
		}
//		t1.print(t1.getRoot());
//		System.out.println("\nNow delete 33\n");
		t1.delete(33);
		if (t1.getRoot().getKey() != 44) {
			System.out.println("Root should be succesor of deleted node: should be 44");
		}
//		t1.print(t1.getRoot());
		t1.insert(102, true);
		t1.insert(103, false);
		if (t1.plain_search(101).getParent() != t1.plain_search(77)) {
			System.out.println("Problem with rebalance");
		}
		int[] g = {0,2,3,4,7,8,9,11,22,23,24,25,26,44,55,66,77,88,99,100,101,102,103};
		int[] gt = t1.keysToArray();
		if (g.length != gt.length) {
			System.out.println("Keys to array error");
			System.out.println("Keys to array supposed to be" + Arrays.toString(g));
			System.out.println("Keys to array is            " + Arrays.toString(gt));
		}
		else {
			int len = Math.max(g.length, gt.length);
			for (int i=0; i<len; i++) {
				if (g[i] != gt[i]) {
					System.out.println("Keys to array error: meant to be" + g[i] + " but is" + gt[i]);
				}
				if (g[i]%2 == 1) {
					if (t1.search(g[i])!= false){
						System.out.println("Problem with search or get key for" + g[i]);
					}
				}
				else {
					if (t1.search(g[i])!= true) {
						System.out.println("Problem with search or get key for" + g[i]);
					}
				}
			}
		}
		boolean[] h = {true, true, false, true, false, true, false, false, true, false, true, false, true, true, false, true, false, true, false, true, false, true, false};
		boolean[] ht = t1.infoToArray();
		if (h.length != ht.length) {
			System.out.println("info to array error");
			System.out.println("info to array supposed to be" + Arrays.toString(g));
			System.out.println("info to array is            " + Arrays.toString(gt));
		}
		else {
			int len = Math.max(h.length, ht.length);
			for (int i=0; i<len; i++) {
				if (h[i] != ht[i]) {
					System.out.println("Keys to array error: meant to be " + h[i] + " but is" + ht[i]);
				}
			}
		}
		if (t1.size() != g.length) {
			System.out.println("Size problem");
		}
		if (t1.successor(t1.plain_search(26)).getKey() != 44 || t1.successor(t1.plain_search(55)).getKey() != 66 || t1.successor(t1.plain_search(100)).getKey() != 101) {
			System.out.println("Successor error");
		}
		
		for (int i=0; i<g.length; i++) {
			if (t1.succPrefixXor(g[i]) != t1.prefixXor(g[i])) {
				System.out.println("Problem with succPrefixXor or PrefixXor");
			}
		}
		
		if (t1.prefixXor(4) != true || t1.prefixXor(3) != false || t1.prefixXor(2) != false || t1.prefixXor(8) != false || t1.prefixXor(23) != true) {
			System.out.println("PrefixXor problem");
		}
		if (t1.succPrefixXor(4) != true || t1.succPrefixXor(3) != false || t1.succPrefixXor(2) != false || t1.succPrefixXor(8) != false || t1.succPrefixXor(23) != true) {
			System.out.println("succPrefixXor problem");
		}
//		AVLTree.print(t1.getRoot());
		for (int i =0; i<100; i++) {
			t1.insert(500+i, false);
		}
//		AVLTree.print(t1.getRoot());
		if (t1.getRoot().getHeight() != 6) {
			System.out.println("height of root supposed to be 6, is " + t1.getRoot().getHeight());
		}
		int[] j = t1.keysToArray();
		for (int i =0; i<j.length; i++) {
			if (t1.succPrefixXor(j[i]) != t1.prefixXor(j[i])) {
				System.out.println("Problem with succPrefixXor or PrefixXor");
			}
		}
		System.out.println("Done running. If no errors printed - great! If errors printed, fix as needed");
    }
}
