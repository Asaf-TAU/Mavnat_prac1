
public class Tester {
    public static void main(String[] args) {
        AVLTree testTree = new AVLTree();
        testTree.insert2(9, false);
        testTree.insert2(1, false);
        testTree.insert2(2, false);
        testTree.insert2(8, false);
        testTree.insert2(6, false);
        testTree.insert2(7, false);
        System.out.println(testTree.getRoot().getXOR());
//        testTree.printTree(testTree.getRoot());
//        testTree.insert2(11, false);
//        testTree.printTree(testTree.getRoot());
//        testTree.insert2(6, false);
//        testTree.printTree(testTree.getRoot());

    }

    public static void print_node(AVLTree.AVLNode node) {
        System.out.println("size: " + node.getSize());
        System.out.println("height: " + node.getHeight());
        System.out.println("parent: " + node.getParent());
    }
}
