import AVLTree.AVLNode;

public class Tester {
    public static void main(String[] args) {
        AVLTree testTree = new AVLTree();
        System.out.println(testTree);
        System.out.println(testTree.empty());
        testTree.insert(9, false);
        testTree.insert(1, false);
        testTree.insert(2, false);
        AVLTree.AVLNode curr = testTree.getRoot();
        System.out.println(curr.getKey());
        System.out.println(curr.getLeft());
        System.out.println(curr.getRight());
        System.out.println(curr.getHeight());
        System.out.println(curr.getSize());
        System.out.println(curr.getParent().getKey());
        testTree.printTree(testTree.getRoot());
//        testTree.insert(8, false);
//        testTree.printTree(testTree.getRoot());
//        testTree.insert(11, false);
//        testTree.printTree(testTree.getRoot());
//        testTree.insert(6, false);
//        testTree.printTree(testTree.getRoot());

    }
}
