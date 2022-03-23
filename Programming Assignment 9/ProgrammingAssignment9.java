import java.io.*;
import java.util.Scanner;

public class ProgrammingAssignment9 {
    //variables
    static TreeNode root;   // Pointer to the binary sort tree.
    /**
     * A TreeNode Object that represents on node in a Binary Search Tree
     */
    static class TreeNode {
        int data;      // The data in this node.
        TreeNode left;    // Pointer to left subtree.
        TreeNode right;   // Pointer to right subtree.

        /**
         * Constructor -- makes a node containing x.
         * @param x is the data in the node
         */
        TreeNode(int x) {
            data = x;
        }
    }
    /**
     * Add x to the binary sort tree to which the global variable "root" refers.
     */
    static void treeInsert(int x) {
        if (root == null) { // The tree is empty. Set root to point to a new node containing the new item.
            root = new TreeNode(x);
            return;
        }
        TreeNode current; // Runs down the tree to find a place for newItem.
        current = root;   // Start at the root.
        while (true) {
            if (x < current.data) {
                if (current.left == null) {
                    current.left = new TreeNode(x);
                    return; // New item has been added to the tree.
                } else
                    current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new TreeNode(x);
                    return; // New item has been added to the tree.
                } else
                    current = current.right;
            }
        }
    }

    /**
     * Measures the height of the tree.
     * @param start where there TreeNode starts.
     * @return returns the height.
     */
    static int treeHeight(TreeNode start) {
        if (start == null) {
            return 0; //Tree is empty so the height is 0
        }
        int leftHeight = treeHeight(start.left);
        int rightHeight = treeHeight(start.right);

        //Choose the larger one and add the root to it.
        if (leftHeight > rightHeight) {
            return (leftHeight +1);
        }
        else {
            return (rightHeight + 1);
        }
    }

    //main
    public static void main(String[] args) {
        System.out.println("*** This program calculates the average height of a BST containing n elements ***");

        // // Used for Testing
        // for (int i = 1; i <= 10; i++){

        //     treeInsert((int)(Math.random()*100));
        // }
        // System.out.println(inOrder(root));
        // System.out.println("height: " + treeHeight(root));

        Scanner stdin = new Scanner(System.in);
        String FILENAME = "P9_output.csv";
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(FILENAME);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred when making output.csv");
            e.printStackTrace();
            stdin.close();
            return;
        }
        // Readd if you want to choose the number of n distinct elements in the bst.
        // System.out.print("Enter number of n distinct elements in the binary search tree: ");
        // int n = stdin.nextInt();

        StringBuilder result = new StringBuilder("n, tree height\n");
        root = null; // Start with an empty tree.
        // Insert random items.
        for (int n = 250; n <= 20000; n += 250) {
            int sum_height = 0;
            for (int j = 0; j < 5; j++) { //Take 5 measurements m_j for j=1 to 5
                for (int i = 0; i < n; i++) { //pick randomly a number p in the range [0-40,000]
                    int random = (int) Math.floor((Math.random() * 40000.0));
                    treeInsert(random);
                }
            sum_height += treeHeight(root);
            // allow garbage collection to get the tree
            root = null;
            }
            result.append(n).append(", ").append(sum_height / 5.0).append(", ").append("\n"); //Average height for n
        }
        System.out.println(result);

        try {
            outputStream.write(result.toString().getBytes());
            outputStream.close();
        } catch (IOException e) {
            System.out.println("An error occurred when printing: ");
            e.printStackTrace();
            stdin.close();
            return;
        }
        stdin.close();
    }
}