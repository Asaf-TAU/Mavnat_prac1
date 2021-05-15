import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Tester {
    public static void main(String[] args) throws InterruptedException {
    	Random ran = new Random();
    	
    	for (int i=5; i>0; i--) { // iterating between 1...5
            AVLTree testTree = new AVLTree();     
            System.out.printf("this iteration is focusing on: %d !%n", i);
            
            // do the insertions here:
            for (int j = 0; j<500*i; j++) { 
                int insertKey = ran.nextInt(1000000);
                testTree.insert(insertKey, ran.nextBoolean());
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
            	
            	testTree.prefixXor(key);


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
        
    public static void print_node(AVLTree.AVLNode node) {
        System.out.println("size: " + node.getSize());
        System.out.println("height: " + node.getHeight());
        System.out.println("parent: " + node.getParent());
    }
}
