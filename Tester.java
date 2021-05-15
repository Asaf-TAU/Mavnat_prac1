import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Tester {
    public static void main(String[] args) throws InterruptedException {
    		measurements_2();
    	}

    
    public static void measurements_2() throws InterruptedException {
    	Random ran = new Random();
    	
    	for (int i=1; i<6; i++) { // iterating between 1...5
            AVLTree testTree_1 = new AVLTree();    
            AVLTree testTree_2 = new AVLTree();
            
            long startTime_1 = 0;
        	long endTime_1 = 0;
        	long sum_1 = 0;
        	long diff_1 = 0;
            long startTime_2 = 0;
        	long endTime_2 = 0;
        	long sum_2 = 0;
        	long diff_2 = 0;
        	int insertKey;
            System.out.printf("this iteration is focusing on: i=%d !%n", i);
            
            // do the insertions here:
            for (int j=0; j<1000*i; j++) { 
            	insertKey = j;
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
    	Random ran = new Random();
    	
    	for (int i=1; i<6; i++) { // iterating between 1...5
            AVLTree testTree = new AVLTree();     
            System.out.printf("this iteration is focusing on: i=%d !%n", i);
            
            // do the insertions here:
            for (int j = 0; j<500*i; j++) { 
                int insertKey = j+1;
                testTree.insert(insertKey, false);
            }
            // insertion process done
            
            long sum = 0;
            int hundred = 100;
        	long diff = 0;
        	long startTime = 0;
        	long endTime = 0;
        	
            int[] keys = testTree.keysToArray();
            
            for (int key : keys) {
            	if (hundred == 0) {
            		System.out.println("average of first one hundred insertions is (in nanoseconds): " + (sum/(100)));
            	}
            	
            	startTime = System.nanoTime();
            	
            	// Xor operations
            	
            	testTree.succPrefixXor(key); // succPrefixXor(key)/prefixXor(key)


            	// end of Xor operations
            	
                endTime = System.nanoTime();
                diff = endTime - startTime;
                sum += diff;
        //\\ update of hundred //\\
                hundred--; 
            }
            
            double average = sum/(500*i);
            System.out.println("average of all of the process (in nanoseconds): " + average);
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
}
