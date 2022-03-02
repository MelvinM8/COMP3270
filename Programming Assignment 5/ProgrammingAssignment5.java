import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

public class ProgrammingAssignment5 {
    //METHODS

    /**
     * Implements the InsertSort (NaiveSort) algorithm to sort an array.
     * Pseudocode provided via instructional resources slide.
     * @param A Sequence A
     */
    public static void insertionSort(int A[]) {
        for (int j = 1; j < A.length; ++j) {
            int key = A[j];
            //Insert A[j] into the sorted sequence A[1...j-1]
            int i = j - 1;
            while (i >= 0 && A[i] > key) {
                A[i + 1] = A[i];
                i = i - 1;
            }
            A[i + 1] = key;
        }
    }

    /**
     * Implements the MergeSort algorithm to sort an array.
     * Pseudocode copied from previous Programming Assignment (Assignment 3).
     *
     * @param A Sequence A
     * @param p Index p of the first element
     * @param q midpoint
     * @param r Index r of last element
     */
    public static void merge(int A[], int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;

        // Let L[1..n1 + 1] and R[1..n2 + 1] be new arrays
        int L[] = new int[n1];
        int R[] = new int[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = A[p + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = A[q + j + 1];
            // R[j] = A[q + j];
        }

        //Use 0x0fff ffff for infinity

        int i = 0;
        int j = 0;
        int k = p;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                A[k] = L[i];
                i++;
            } else {
                A[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            A[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            A[k] = R[j];
            j++;
            k++;
        }
    }

    /**
     * Implements the mergeSort algorithm
     * @param A Sequence A
     * @param p index p of first element
     * @param r index r of last element
     */
    public static void mergeSort(int A[], int p, int r) {
        if (p < r) {
            // This should automatically floor, as its integer division
            int q = (p + r) / 2;

            mergeSort(A, p, q);
            mergeSort(A, q+1, r);
            merge(A, p, q, r);
        }
    }

    /**
     * Generates a random array
     * @param n Length of the random array
     * @return Pointer to the array
     */
    public static int[] generateRandomArray(int n, int max_value){
        int out[] = new int[n];

        Random random = new Random();
        for (int i = 0; i < out.length; ++i){
            out[i] = random.nextInt(max_value);
        }
        return out;
    }

    public static void testRun(Scanner stdin){
        System.out.print("Input how many elements are in the random array: ");
        int n = stdin.nextInt();
        int randArray[] = generateRandomArray(n, n*5);

        System.out.println("Unsorted Array: ");
        System.out.println("\t"+Arrays.toString(randArray));

        int currentProblem[] = new int[n];
        for (int i = 0; i < currentProblem.length; i++){
            currentProblem[i] = randArray[i];
        }
        insertionSort(currentProblem);
        System.out.println("After insert sorting the Array is: ");
        System.out.println("\t"+Arrays.toString(currentProblem));


        currentProblem = new int[n];
        for (int i = 0; i < currentProblem.length; i++){
            currentProblem[i] = randArray[i];
        }
        insertionSort(currentProblem);
        mergeSort(currentProblem, 0, currentProblem.length-1);
        System.out.println("After merge sorting the Array is: ");
        System.out.println("\t"+Arrays.toString(currentProblem));

        stdin.close();
        return;
    }

    public static void dataRun(Scanner stdin){
        String FILENAME = "output.csv";
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(new File(FILENAME));
        }
        catch (FileNotFoundException e){
            System.out.println("An error occured when making output.csv");
            e.printStackTrace();
            stdin.close();
            return;
        }

        System.out.print("Enter the size of the largest array to sort (max 2^31): ");
        int maxValue = stdin.nextInt();
        int largeRandom[] = generateRandomArray(maxValue, Integer.MAX_VALUE);
        String result = "n, insert sort time (ns), merge sort time(ns)\n";
        for (int n = 1000; n <= maxValue; n+=1000 ){

            int currentProblem[] = new int[n];
            for (int i = 0; i < currentProblem.length; i++){
                currentProblem[i] = largeRandom[i];
            }
            long start = System.nanoTime();
            mergeSort(currentProblem, 0, currentProblem.length-1);
            long end = System.nanoTime();
            long mergeTime = end-start;

            currentProblem = new int[n];
            for (int i = 0; i < currentProblem.length; i++){
                currentProblem[i] = largeRandom[i];
            }
            start = System.nanoTime();
            insertionSort(currentProblem);
            end = System.nanoTime();
            long insertTime = end-start;

            result +=  n+ ", " + insertTime + ", " + mergeTime + "\n";
        }

        try {
            outputStream.write(result.getBytes());
            outputStream.close();
        } catch (IOException e) {
            System.out.println("An error occured when printing: ");
            e.printStackTrace();
            stdin.close();
            return;
        }
        stdin.close();
    }

    /**
     * Main method which will compare both our NaiveSort and MergeSort algorithms.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("This program has two sort algorithms, and runs both at the same time.");
        System.out.println("Select the number option that you wish to run.");
        System.out.println("\t1) Test - Showing sorting done with both algorithms");
        System.out.println("\t2) Data - Measures the time taken to sort arrays with both algorithm.");
        System.out.print("Enter 1 or 2: ");

        Scanner stdin = new Scanner(System.in);
        int result = stdin.nextInt();

        // CHANGE
        if (result == 1){
            testRun(stdin);
        }
        else if (result == 2){
            dataRun(stdin);
        }
        else {
            System.out.println("Bad input, exiting.");
        }
    }
}


