
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author evyatar
 */
public class BTreeLatex {

    private static final File DESKTOP = new File(System.getProperty("user.home"), "Desktop");
    private static final File DIR = new File(DESKTOP, "\\BTreeLatex");
    private StringBuilder statesHolder;
    private StringBuilder buffer;
    private StringBuilder longTermBuffer;
    private File readableFile;
    private BTree tree;
    private Integer[] insertions;
    private Integer[] deletions;
    private Integer[] searchs;
    private int totalBlocks;
    private int height;

    public static void main(String args[]) {
        if (solvedLastBug()) {
            autoTest(20, 100, 20);
        } else {
            StartLastFailedTest();
        }

        //manualTest();
    }

    public static boolean manualTest() {

        boolean result = true;

        /*
        
        
        
        
        *****************Insert here the starting code before the bug****************************
        
        
        
        manualTesto.addTreeState("before bug");
        try{
        ************************************************Insert here the problematic line of code*************************
        }catch(Exception e){
            e.printStackTrace();
        }
        result=manualTesto.addTreeState("after bug");
        manualTesto.commitBufferedStates();
        manualTesto.finish();
        
         */
        return result;
    }

    public static void autoTest(int maxT, int maxNodes, int maxTests) {
        BTree testTree = null;
        BTreeLatex autoTesto = null;
        boolean flag = true;
        for (int t = 2; t <= maxT & flag; t++) {
            System.out.println("Testing trees with t = " + t);
            for (int i = 1; i <= maxNodes & flag; i++) {
                testTree = new BTree(t);
                autoTesto = new BTreeLatex(testTree, "BTreeInLatex");
                flag = autoTesto.initSimpleTree(i) && autoTesto.testSearch(i) && autoTesto.testDelete(i);
            }
        }
        if (flag) {
            new File(DIR.getAbsolutePath() + "\\lastFailedTest.evya").delete();
            System.out.println("Succeeded auto tests, well done!");
        } else {
            System.out.println("Failed test");
            autoTesto.saveLastTest();
        }
    }

    public BTreeLatex(BTree tree, String filename) {
        deletions = new Integer[0];
        insertions = new Integer[0];
        searchs = new Integer[0];
        statesHolder = new StringBuilder();
        buffer = new StringBuilder();
        longTermBuffer = new StringBuilder();
        this.tree = tree;
        readableFile = new File(DIR.getAbsolutePath() + "\\" + filename + ".txt");
        try {
            DIR.mkdir();
            readableFile.createNewFile();
            statesHolder.append("\\documentclass{standalone}\n"
                    + " \\usepackage{forest}\n"
                    + " \\usepackage{xcolor,colortbl}"
                    + "\n"
                    + "\\begin{document}\n"
                    + "\n"
                    + "\\begin{tabular}{@{}c@{}}\n"
                    + "\\\\");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean testDelete(int bound) {
        boolean result = true;
        PrintWriter writer;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(new File(DIR.getAbsolutePath() + "\\manualCodeOfLastTest.txt"), true)));
            deletions = arrScrambler(bound);
            for (int i = 0; i < deletions.length - 1 & result; i++) {
                clearBuffer();
                addTreeState("before deleting " + deletions[i]);
                writer.println("tree.delete(" + deletions[i] + ");");
                tree.delete(deletions[i]);
                result = addTreeState("after deleting " + deletions[i]) && tree.search(deletions[i]) == null;
                if (totalBlocks != bound - (i + 1) & result) {
                    System.out.println("Total blocks in tree is " + totalBlocks + " and supposed to be " + (bound - (i + 1)));
                    result = false;
                }
            }

            if (result & deletions.length > 0) {
                clearBuffer();
                addTreeState("before deleting " + deletions[deletions.length - 1]);
                tree.delete(deletions[deletions.length - 1]);
                addTreeState("after deleting " + deletions[deletions.length - 1]);
                result = tree.getRoot() == null || (tree.getRoot().getBlocksList().isEmpty()&tree.getRoot().getChildrenList().isEmpty());
                if (totalBlocks != 0 & result) {
                    System.out.println("Total blocks in tree is " + totalBlocks + " and supposed to be " + 0);
                    result = false;
                }
            }
            if (!result) {
                commitBufferedStates();
                finish();
            }
            writer.close();
            return result;
        } catch (Exception e) {
            commitBufferedStates();
            finish();
            e.printStackTrace();
            return false;
        }
        //return true;
    }

    private static Integer[] arrScrambler(int bound) {
        Integer[] arr = new Integer[bound];
        for (int i = 0; i < bound; i++) {
            arr[i] = i + 1;
        }
        List<Integer> list = Arrays.asList(arr);
        Collections.shuffle(list);
        arr = (Integer[]) list.toArray();
        return arr;
    }

    public boolean testSearch(int bound) {
        boolean result = true;
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(new File(DIR.getAbsolutePath() + "\\manualCodeOfLastTest.txt"), true)));
            searchs = arrScrambler(bound);
            for (int i = 0; i < bound & result; i++) {
                clearBuffer();
                writer.println("tree.search(" + searchs[i] + ");");
                Block block = tree.search(searchs[i]);
                result = (block != null && block.getKey() == searchs[i]);
                String searchResults = block == null ? "null" : "" + block.getKey();
                addTreeState("searched" + searchs[i] + " got " + searchResults);
            }
            if (!result) {
                commitBufferedStates();
                finish();
            }
            writer.close();
        } catch (Exception e) {
            commitBufferedStates();
            finish();
            e.printStackTrace();
            return false;
        }
        return result;
    }

    public boolean initSimpleTree(int bound) {
        boolean result = true;
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(new File(DIR.getAbsolutePath() + "\\manualCodeOfLastTest.txt"))));
            writer.println("BTree tree = new BTree(" + tree.getT() + "); \n BTreeLatex manualTesto=new BTreeLatex(tree,\"manualTest\");");
            insertions = arrScrambler(bound);
            for (int i = 0; i < bound & result; i++) {
                clearBuffer();
                addTreeState("before inserting " + insertions[i]);
                writer.println("tree.insert(new Block(" + insertions[i] + ",null));");
                tree.insert(new Block(insertions[i], null));
                result = addTreeState("after inserting " + insertions[i]);
                if (totalBlocks != i + 1 & result) {
                    System.out.println("Total blocks in tree is " + totalBlocks + " and supposed to be " + (i + 1));
                    result = false;
                }
            }
            if (!result) {
                commitBufferedStates();
                finish();
            }
            writer.close();
        } catch (Exception e) {
            commitBufferedStates();
            finish();
            e.printStackTrace();
            return false;
        }

        return result;
    }

    public void finish() {
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(readableFile)))) {
                writer.println(statesHolder + "\\end{tabular}\n"
                        + "\n"
                        + "\\end{document}");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addTreeState(String stateName) {
        height = -1;
        buffer.setLength(0);
        buffer.append("\\qquad \\begin{forest}\nlabel={").append(stateName).append("},\nfor tree={draw,l=20+level*5mm, s sep=3mm,anchor=north,child anchor=north,if={isodd(n_children())}{\n      for children={\n        if={\n          equal(n,int((1+n_children(\"!u\"))/2))\n        }{calign with current}{}\n      }\n    }{},align=center}");
        totalBlocks = 0;
        boolean result = createNodes(tree.getRoot(), Integer.MIN_VALUE, Integer.MAX_VALUE, true, 0);
        buffer.append(" \\end{forest}"
                + "\n \\qquad \\mbox{} \\\\[0.4in] ");
        longTermBuffer.append(buffer);
        return result;
    }

    private boolean createNodes(BNode node, int min, int max, boolean isRoot, int currentHeight) {
        boolean result = true;
        if (node == null) {
            buffer.append("[NULL ,color=red]");
            return false;
        }
        totalBlocks += node.getBlocksList().size();
        int currentKey = Integer.MIN_VALUE;
        if (node.getBlocksList().size() > 0) {
            currentKey = node.getBlockAt(0).getKey();
        } else {
            result = false;
        }

        String misplaced = "";
        if (currentKey > max | currentKey < min) {
            misplaced = "\\cellcolor{red!25}";
            result = false;
        }
        buffer.append("[\\begin{tabular}{@{}c@{}} \\ {\\begin{tabular}{|*{").append(Math.max(1, node.getBlocksList().size())).append("}{c|}} \\hline ");
        if (node.getBlocksList().isEmpty()) {
            buffer.append("\\cellcolor{red!25}");
        } else {
            buffer.append(currentKey).append(" ").append(misplaced);
        }
        int localMin = "".equals(misplaced) ? currentKey : min;
        misplaced = "";
        for (int i = 1; i < node.getBlocksList().size(); i++) {
            currentKey = node.getBlockAt(i).getKey();
            if (currentKey > max | currentKey < min | currentKey < localMin) {
                misplaced = "\\cellcolor{red!25}";
                result = false;
            }
            buffer.append("& ").append(misplaced).append(node.getBlockAt(i).getKey()).append(" ");
            localMin = "".equals(misplaced) ? currentKey : localMin;
            misplaced = "";
        }

        String error = "";
        if ((!node.getChildrenList().isEmpty() & node.isLeaf()) | (node.getChildrenList().isEmpty() & !node.isLeaf())) {
            error += "\\\\ \\colorbox{red!50}{Leaf state and children}";
            result = false;
        }
        if (node.getNumOfBlocks() != node.getBlocksList().size()) {
            error += "\\\\ \\colorbox{red!50}{blocks $\\neq$ numOfBlocks}";
            result = false;
        }
        if ((node.getChildrenList().size() != node.getNumOfBlocks() + 1) & !node.isLeaf()) {
            error += "\\\\ \\colorbox{red!50}{children $\\neq$ numOfBlocks+1}";
            result = false;
        }
        if ((node.getChildrenList().size() != node.getBlocksList().size() + 1) & !node.isLeaf()) {
            error += "\\\\ \\colorbox{red!50}{children $\\neq$ blocks+1}";
            result = false;
        }
        if (node.getChildrenList().size() > 2 * tree.getRoot().getT()) {
            error += "\\\\ \\colorbox{red!50}{children $>$ 2t}";
            result = false;
        }
        if (node.getBlocksList().size() > 2 * tree.getRoot().getT() - 1) {
            error += "\\\\ \\colorbox{red!50}{blocks $>$ 2t-1}";
            result = false;
        }
        if (node.getBlocksList().size() < tree.getRoot().getT() - 1 & !isRoot) {
            error += "\\\\ \\colorbox{red!50}{blocks $<$ t-1}";
            result = false;
        }
        if (currentKey == Integer.MIN_VALUE) {
            error += "\\\\ \\colorbox{red!50}{blocks=0}";
            result = false;
        }
        if (node.isLeaf()) {
            if (height != -1 & height != currentHeight) {
                error += "\\\\ \\colorbox{red!50}{height of leaf differs}";
                result = false;
            } else
                height=currentHeight;
        }

        buffer.append("\\\\ \\hline \\end{tabular}} ").append(dataOfNode(node)).append(error).append(" \\end{tabular}");
        if (!node.isLeaf()) {
            for (int i = 0; i < node.getChildrenList().size(); i++) {
                int keyBefore = (node.getBlocksList().size() >=i & i > 0) ? node.getBlockKeyAt(i - 1) : min;
                int keyAfter = (node.getBlocksList().size() > 0 & i < node.getBlocksList().size()) ? node.getBlockKeyAt(i) : max;
                result = result & createNodes(node.getChildAt(i), keyBefore, keyAfter, false, currentHeight + 1);
            }
        }

        buffer.append("]\n");
        return result;
    }

    public void clearBuffer() {
        longTermBuffer.setLength(0);
    }

    public void commitBufferedStates() {
        statesHolder.append(longTermBuffer);
    }

    private static boolean solvedLastBug() {
        return !new File(DIR.getAbsolutePath() + "\\lastFailedTest.evya").exists();
    }

    private static String dataOfNode(BNode node) {
        String result = "";
        result += "\\\\  \\footnotesize{numOfBlocks: " + node.getNumOfBlocks() + "} "
                + "\\\\ \\footnotesize{blocks: " + node.getBlocksList().size() + "} "
                + "\\\\  \\footnotesize{children: " + node.getChildrenList().size() + "} "
                + " \\\\ \\footnotesize{isLeaf: " + node.isLeaf() + "} ";
        return result;
    }

    private void saveLastTest() {
            File file = new File(DIR.getAbsolutePath() + "\\lastFailedTest.evya");
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                file.createNewFile();
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeInt(tree.getT());
                out.writeInt(insertions.length);
                for (int i = 0; i < insertions.length; i++) {
                    out.writeInt(insertions[i]);
                }
                out.writeInt(searchs.length);
                for (int i = 0; i < searchs.length; i++) {
                    out.writeInt(searchs[i]);
                }
                out.writeInt(deletions.length);
                for (int i = 0; i < deletions.length; i++) {
                    out.writeInt(deletions[i]);
                }
                out.close();
            System.out.println("Saved last failed test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLastFailedTest() {
        try(FileInputStream fileIn = new FileInputStream(new File(DIR.getAbsolutePath() + "\\lastFailedTest.evya"))) {
            ObjectInputStream in = new ObjectInputStream(fileIn);
            tree = new BTree(in.readInt());
            insertions = new Integer[in.readInt()];
            for (int i = 0; i < insertions.length; i++) {
                insertions[i] = in.readInt();
            }

            searchs = new Integer[in.readInt()];
            for (int i = 0; i < searchs.length; i++) {
                searchs[i] = in.readInt();
            }

            deletions = new Integer[in.readInt()];
            for (int i = 0; i < deletions.length; i++) {
                deletions[i] = in.readInt();
            }
            in.close();
            fileIn.close();
            System.out.println("Loaded last failed test");
        } catch (Exception e) {
            new File(DIR.getAbsolutePath() + "\\lastFailedTest.evya").delete();
            BTreeLatex.main(null);
        }
    }

    private boolean useInsertions() {
        boolean result = true;
        for (int i = 0; i < insertions.length & result; i++) {
            clearBuffer();
            addTreeState("before inserting " + insertions[i]);
            tree.insert(new Block(insertions[i], null));
            result = addTreeState("after inserting " + insertions[i]);
            
            if (totalBlocks != i + 1 & result) {
                System.out.println("Total blocks in tree is " + totalBlocks + " and supposed to be " + (i + 1));
                result = false;
            }
        }
        return result;
    }

    private boolean useSearchs() {
        boolean result = true;
        for (int i = 0; i < searchs.length & result; i++) {
            clearBuffer();
            addTreeState("before searching " + searchs[i]);
            Block block = tree.search(searchs[i]);
            result = (block != null && block.getKey() == searchs[i]);
            String searchResults = block == null ? "null" : "" + block.getKey();
            addTreeState("searched" + searchs[i] + " got " + searchResults);
        }
        if (!result) {
            commitBufferedStates();
            finish();
        }
        return result;
    }

    private boolean useDeletions() {
        boolean result = true;
        for (int i = 0; i < deletions.length - 1 & result; i++) {
            clearBuffer();
            addTreeState("before deleting " + deletions[i]);
            tree.delete(deletions[i]);
            result = addTreeState("after deleting " + deletions[i]) && tree.search(deletions[i]) == null;
            if (totalBlocks != (deletions.length - (i + 1)) & result) {
                System.out.println("Total blocks in tree is " + totalBlocks + " and supposed to be " + (deletions.length - (i + 1)));
                result = false;
            }
        }
        if (result & deletions.length > 0) {
            clearBuffer();
            addTreeState("before deleting " + deletions[deletions.length - 1]);
            tree.delete(deletions[deletions.length - 1]);
            addTreeState("after deleting " + deletions[deletions.length - 1]);
            result = tree.getRoot() == null || (tree.getRoot().getBlocksList().isEmpty()&tree.getRoot().getChildrenList().isEmpty());
            if (totalBlocks != 0 & result) {
                System.out.println("Total blocks in tree is " + totalBlocks + " and supposed to be " + 0);
                result = false;
            }
        }

        return result;
    }

    public static void StartLastFailedTest() {
        BTreeLatex latx = new BTreeLatex(null, "BTreeInLatex");
        latx.loadLastFailedTest();
        if (latx.useInsertions() & latx.useSearchs() & latx.useDeletions()) {
            System.out.println("Succeeded the saved test! \n Starting auto tests again.");
            new File(DIR.getAbsolutePath() + "\\lastFailedTest.evya").delete();
            autoTest(20, 100, 20);
        } else {
            latx.commitBufferedStates();
            latx.finish();
            System.out.println("Failed saved test");
        }
    }
}
