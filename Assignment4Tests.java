import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by nadav on 5/19/2017.
 */
public class Assignment4Tests {

    public static void main(String[] args) {
        System.out.println("-------------Start of Tests-------------");
        testInsert();
        testSearch();
        testCreateMBT();
        testDelete();
        System.out.println("-------------End of Tests-------------");
    }

    private static void testInsert() {
        BTree firstTree = new BTree(3);
        initTree(firstTree, 40);
        ArrayList<Integer> arrayListFirst = inorder(new ArrayList<>(), firstTree.getRoot());
        ArrayList<Integer> expectedFirst = getIntegers(41);
        Test(true, arrayListFirst.containsAll(expectedFirst), "Insert - Test 1 of 7", "some keys were not inserted ");
        Test(expectedFirst, arrayListFirst, "Insert - Test 2 of 7", "keys are in the wrong place in the tree");
        Test(true, checkDegree(firstTree, firstTree.getRoot()), "Insert - Test 3 of 7", "found nodes with less than t-1 blocks or with more than 2t-1 blocks");
        Test(true, checkNumOfChildren(firstTree, firstTree.getRoot()), "Insert - Test 4 of 7", "found nodes with less or more than numOfBlocks+1 children");

        //same test as the previous but with more nodes.
        BTree SecondTree = new BTree(15);
        initTree(SecondTree, 10000);
        ArrayList<Integer> arrayListSecond = inorder(new ArrayList<>(), SecondTree.getRoot());
        ArrayList<Integer> expectedSecond = getIntegers(10001);
        Test(expectedSecond, arrayListSecond, "Insert - Test 5 of 7", "keys are in the wrong place in the tree");
        Test(true, checkDegree(SecondTree, SecondTree.getRoot()), "Insert - Test 6 of 7", "found nodes with less than t-1 blocks or with more than 2t-1 blocks");
        Test(true, checkNumOfChildren(SecondTree, SecondTree.getRoot()), "Insert - Test 7 of 7", "found nodes with less or more than numOfBlocks+1 children");

    }

    private static void testSearch() {
        BTree Tree = new BTree(3);
        initTree(Tree, 40);
        ArrayList<Integer> arrayList = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>();
        for (int i = 1; i < 41; i++) {
            expected.add(i);
            arrayList.add(Tree.search(i).getKey());
        }

        Test(expected, arrayList, "Search - Test 1 of 2", "didn't find some keys");
        Test(null, Tree.search(1000), "Search - Test 2 of 2", "the key is not in the tree. null should be returned");
    }

    private static void testCreateMBT() {
        ArrayList<byte[]> output = new ArrayList<>();
        String s = "abcdefghijklmnopqrstuvwxyz";
        BTree tree = new BTree(3);
        for (int i = 0; i < s.length(); i++) {
            output.add(s.substring(i, i + 1).getBytes());
            tree.insert(new Block(i, s.substring(i, i + 1).getBytes()));
        }
        MerkleBNode MBTRoot = tree.createMBT();
        ArrayList<String> StringArray= getHashValues(MBTRoot, new ArrayList<>());
        String[] array = new String[StringArray.size()];
        StringArray.toArray(array);
        String expected = "[[-38, 35, 97, 78, 2, 70, -102, 13, 124, 123, -47, -67, -85, 92, -100, 71, 75, 25, 4, -36], [96, 12, -51, 27, 113, 86, -110, 50, -48, 29, 17, 11, -58, 62, -112, 107, -22, -80, 77, -116], [16, 65, 23, -100, -67, -38, 54, 111, -41, -80, 52, 127, 9, 37, 95, 119, 81, 112, -31, 3], [-128, 34, -18, -114, -104, 1, -91, -87, 15, -117, -93, 119, -56, 106, 54, -39, 71, 57, 111, -33], [104, -90, 88, -76, 75, -70, -17, -85, 62, -11, -51, 35, 25, 103, 25, 95, -64, -1, -99, 105], [120, -36, 12, -45, -25, -17, 71, 93, 44, 62, 102, 64, 120, -27, -15, -41, -77, -73, 25, -85], [87, -5, -11, 86, 23, -106, -84, 63, 64, -41, -5, -8, -26, -74, 73, -19, 86, 56, 85, 46], [-101, 2, -39, -105, 76, 20, -26, 35, -55, -1, -66, -41, 54, 11, -22, -53, -16, -36, -71, 95], [125, 31, 19, 48, -18, 12, -1, 68, 34, 101, -88, 47, 38, -41, 21, -44, 19, 47, 94, 17], [-94, 14, -40, 51, 57, -85, 50, -118, -24, 23, -84, -53, 63, -123, -102, -23, -24, -7, 48, -7], [-61, 91, 53, 73, 3, -82, -76, -68, -76, 13, -68, 84, -30, -120, 89, 122, -104, 51, -127, -123]]";
        Test(expected,Arrays.toString(array),"CreateMBT - Test 1 of 1");
    }

    private static void testDelete() {
        BTree tree = new BTree(5);
        initTree(tree, 100);
        tree.delete(74);
        tree.delete(89);
        tree.delete(32);
        tree.delete(103);//should not change anything in the tree because the key is not in the tree.
        ArrayList<Integer> arrayList = inorder(new ArrayList<>(), tree.getRoot());
        ArrayList<Integer> expected = getIntegers(101);
        Test(null, tree.search(74), "Delete - Test 1 of 6", "the key 74 is still in the tree");
        Test(null, tree.search(32), "Delete - Test 2 of 6", "the key 32 is still in the tree");
        Test(null, tree.search(89), "Delete - Test 3 of 6", "the key 89 is still in the tree");
        expected.remove(88);
        expected.remove(73);
        expected.remove(31);
        Test(expected, arrayList, "Delete - Test 4 of 6", "keys are in the wrong place in the tree");
        tree.delete(42);
        tree.delete(75);
        tree.delete(12);
        tree.delete(2);
        tree.delete(90);
        tree.delete(23);
        tree.delete(99);
        tree.delete(85);
        tree.delete(13);
        tree.delete(5);
        tree.delete(65);
        tree.delete(43);
        tree.delete(8);
        tree.delete(39);
        tree.delete(61);
        tree.delete(97);
        tree.delete(18);
        tree.delete(59);
        tree.delete(45);
        tree.delete(76);
        tree.delete(91);
        tree.delete(48);
        tree.delete(27);
        tree.delete(25);
        Test(true, checkDegree(tree, tree.getRoot()), "Delete - Test 5 of 6", "found nodes with less than t-1 blocks or with more than 2t-1 blocks");
        Test(true, checkNumOfChildren(tree, tree.getRoot()), "Delete - Test 6 of 6", "found nodes with less or more than numOfBlocks+1 children");

    }


    private static ArrayList<Integer> getIntegers(int size) {
        ArrayList<Integer> expected = new ArrayList<>();
        for (int i = 1; i < size; i++) {
            expected.add(i);
        }
        return expected;
    }

    private static void initTree(BTree secondTree, int toKey) {
        ArrayList<Block> SecondBlocks = Block.blockFactory(1, toKey);
        Collections.shuffle(SecondBlocks);
        for (Block block : SecondBlocks) {
            secondTree.insert(block);
        }
    }

    private static <T> void Test(T expected, T actual, String CallingMethod) {
        if (expected == null) {
            if (actual != null) {
                System.err.println(CallingMethod + ": was <" + actual + "> but should be <" + null + ">");
                return;
            }
            System.out.println(CallingMethod + "-Passed");
            return;
        }
        if (!(actual.equals(expected))) {
            System.err.println(CallingMethod + ": was <" + actual + "> but should be <" + expected + ">");
        } else System.out.println(CallingMethod + "-Passed");
    }

    private static <T> void Test(T expected, T actual, String CallingMethod, String ErrorMessage) {
        if (expected == null) {
            if (actual != null) {
                System.err.println(CallingMethod + " failed- " + ErrorMessage);
                return;
            }
            System.out.println(CallingMethod + "-Passed");
            return;
        }
        if (!(actual.equals(expected))) {
            System.err.println(CallingMethod + " failed- " + ErrorMessage);
        } else System.out.println(CallingMethod + "-Passed");

    }

    public static ArrayList<Integer> inorder(ArrayList<Integer> arrayList, BNode node) {
        if (node.isLeaf()) {
            for (int i = 0; i < node.getNumOfBlocks(); i++) {
                arrayList.add(node.getBlockKeyAt(i));
            }
        } else {
            for (int i = 0; i < node.getNumOfBlocks(); i++) {
                inorder(arrayList, node.getChildAt(i));
                arrayList.add(node.getBlockKeyAt(i));
            }
            inorder(arrayList, node.getChildAt(node.getNumOfBlocks()));
        }
        return arrayList;
    }

    public static boolean checkDegree(BTree tree, BNode node) {
        if (node.isLeaf())
           return (node.equals(tree.getRoot())&node.getBlocksList().size()<(2*node.getT())) || DegreeRange(node);
        boolean validDegree = true;
        for (int i = 0; i <= node.getBlocksList().size(); i++) {
            validDegree = checkDegree(tree, node.getChildAt(i)) && (node.equals(tree.getRoot()) || DegreeRange(node));
        }
        return validDegree;
    }

    public static boolean checkNumOfChildren(BTree tree, BNode node) {
        if (node.isLeaf())
            return true;
        boolean validNumOfChildren = true;
        for (int i = 0; i <= node.getBlocksList().size(); i++) {
            validNumOfChildren = checkNumOfChildren(tree, node.getChildAt(i)) && (node.equals(tree.getRoot()) || node.getChildrenList().size() == node.getBlocksList().size() + 1);
        }
        return validNumOfChildren;
    }

    private static boolean DegreeRange(BNode node) {
        return (node.getBlocksList().size() >= node.getT() - 1) & ((node.getBlocksList().size()) <= (2 * (node.getT())) - 1);
    }

    private static ArrayList<String> getHashValues(MerkleBNode merkleBNode,ArrayList<String> arrayList) {
        if (merkleBNode.isLeaf()) {
                arrayList.add(Arrays.toString(merkleBNode.getHashValue()));
        } else {
            for (int i = 0; i < merkleBNode.getChildrenList().size(); i++) {
                getHashValues(merkleBNode.getChildrenList().get(i), arrayList);
            }
            arrayList.add(Arrays.toString(merkleBNode.getHashValue()));
        }
        return arrayList;
    }
}


