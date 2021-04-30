public class Tester {
    public static void main(String[] args) {
        AVLTree testTree = new AVLTree();
        System.out.println(testTree);
        System.out.println(testTree.empty());
        testTree.insert(9, false);
        testTree.insert(1, false);
        testTree.insert(2, false);
        testTree.insert(4, false);
        testTree.insert(8, false);
        testTree.insert(11, false);
        testTree.insert(6, false);
        System.out.println(testTree);

    }
}
