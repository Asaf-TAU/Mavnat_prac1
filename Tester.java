import java.util.Arrays;

public class Tester {
    public static void main(String[] args) {
        AVLTree testTree = new AVLTree();
        testTree.insert(9, true);
        testTree.insert(1, true);
        testTree.insert(2, false);
        testTree.insert(8, false);
        testTree.insert(6, true);
        testTree.insert(7, true);
        testTree.printTree(testTree.getRoot());
        System.out.println(Arrays.toString(testTree.keysToArray()));
        System.out.println(Arrays.toString(testTree.infoToArray()));
        System.out.println(testTree.prefixXor(9));
//        testTree.delete(6);
//        testTree.printTree(testTree.getRoot());
//        testTree.printTree(testTree.getRoot());
//        testTree.insert(11, false);
//        testTree.printTree(testTree.getRoot());
//        testTree.insert(6, false);
//        testTree.printTree(testTree.getRoot());

    }

    public static void print_node(AVLTree.AVLNode node) {
        System.out.println("size: " + node.getSize());
        System.out.println("height: " + node.getHeight());
        System.out.println("parent: " + node.getParent());
    }
}
