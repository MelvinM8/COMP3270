import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * This is Programming Assignment 13 for COMP3270 Introduction to Algorithms with Saad Biaz.
 * Authors: Melvin Moreno and Matthew Freestone
 * This program will allow us to compare the recursive and iterative approach of Activity-Selector algorithms.
 */
public class ProgrammingAssignment13 {
    /**
     * This is the recursive activity selector method
     * @param s array of start time.
     * @param f array of finish time.
     * @param k index of the subproblem Sk to solve.
     * @param n number of activities in the original problem.
     * @param A List set of mutually compatible activities in Sk.
     * @return a maximum-size set of mutually compatible activities in Sk.
     * Time Analysis: O(nlgn)
     */
    public static List<Integer> recursiveActivitySelector(int[] s, int[] f, int k, int n, List<Integer> A) {
        int m = k + 1;
        while (m <= n && s[m] < f[k]) { //Skip overlapping activiites with ak
            m = m + 1;
        }
        if (m <= n) {
            A.add(m);
            recursiveActivitySelector(s, f, m, n, A);
            return A;
        }
        else {
            return null;
        }
    }

    /**
     * This is the greedy iterative activity selector method
     * @param s array of start time.
     * @param f array of finish time.
     * @return a maximum-size set of mutually compatible activities in Sk.
     * Time Analysis: O(nlgn)
     */
    public static List<Integer> greedyActivitySelector(int[] s, int[] f) {
        List<Integer> A = new ArrayList<>();
        A.add(1);
        int n = s.length;
        int k = 1;
        for (int m = 2; m < n; m++) {
            if (s[m] >= f[k]) {
                A.add(m);
                k = m;
            }
        }
        return A;
    }
    /**
     * StudyOverhead  program to collect data to plot and analyze.
     * @param numberPoints
     * @param n
     */
    public static void studyOverhead(int numberPoints, int n) {
        int[]s = new int[n];
        int[] f = new int[n];
        initializeArrays(n, s, f);

        for (int i = 1; i < numberPoints; i++) {
            long timeForRecursive = 0;
            long timeForIterative = 0;
            for (int j = 1; j <= numberPoints; j++) {
                List<Integer> A = new ArrayList<>();
                long recursiveStartTime;
                long recursiveFinishTime;
                long iterativeStartTime;
                long iterativeFinishTime;

                recursiveStartTime = System.nanoTime();
                recursiveActivitySelector(s, f, 0, n - 1, A);
                recursiveFinishTime = System.nanoTime();
                timeForRecursive += recursiveFinishTime - recursiveStartTime;

                iterativeStartTime = System.nanoTime();
                greedyActivitySelector(s, f);
                iterativeFinishTime = System.nanoTime();
                timeForIterative += iterativeFinishTime - iterativeStartTime;
            }
            System.out.println(i + ", " + (double) timeForRecursive / (double) timeForIterative);
        }
    }
    /**
     * Initializes arrays.
     * Create about n/2 mutually compatible activities
     */
    public static void initializeArrays(int n, int[] s, int[] f) {
        for (int i = 1; i < n - 1; i++) {
            if (i % 2 == 0) { // Checks if i is even.
                s[i] = f[i-2];
                f[i] = s[i] + 2;
            }
            else {
                s[i] = f[i-1] - 1; // s[1] will be negative, but that is fine.
                f[i] = f[i-1] + 1;
            }
        }
    }
    /**
     * Driver.
     */
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter a number of activities: ");
        int n = myObj.nextInt();
        System.out.println("First Column is i, Second Column is M[i] (timeForRecursive/timeForIterative)");
        //RUNNNN
        studyOverhead(n, n);
    }
}
