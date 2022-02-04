import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

/**
* Authors: Melvin Moreno and Matthew Freestone
* This is a program we made for Introduction to Algorithms with Dr. Biaz
* It uses the merge-sort algorithm to sort a large array and outputs the time it took
* to sort.
*/
public class ProgrammingAssignment3 {
    //METHODS

    //Implemented merge pseudocode from document.
    public static void merge(int A[], int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;

        // Let L[1..n1 + 1] and R[1..n2 + 1] be new arrays
        int L[] = new int [n1];
        int R[] = new int [n2];

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
            }
            else {
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

    //Implemented MergeSort pseudocode from document.
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
        int testArray[] = generateRandomArray(n, n);
        
        System.out.println("Unsorted Array: ");
        System.out.println(Arrays.toString(testArray));

        long start = System.currentTimeMillis();
        mergeSort(testArray, 0, testArray.length-1);
        long end = System.currentTimeMillis();

        System.out.println("After Sorting the Array is: ");
        System.out.println(Arrays.toString(testArray));


        System.out.println("It took " + (end - start) + " ms to sort.");
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
            e.printStackTrace();
            stdin.close();
            return;
        }

        System.out.print("Enter the size of the largest array to sort (max 2^31): ");
        int max_value = stdin.nextInt();
        int largeRandom[] = generateRandomArray(max_value, Integer.MAX_VALUE);
        String result = "n, time (ms)\n";
        for (int n = 1000; n <= max_value; n+=1000 ){
            int currentProblem[] = new int[n];
            for (int i = 0; i < currentProblem.length; i++){
                currentProblem[i] = largeRandom[i];
            }

            long start = System.currentTimeMillis();
            ProgrammingAssignment3.mergeSort(currentProblem, 0, currentProblem.length-1);
            long end = System.currentTimeMillis();
            result +=  n+ ", " + (end-start)+"\n";
        }
        
        try {
            outputStream.write(result.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            stdin.close();
            return;
        }
        stdin.close();
    }

    public static void main(String[] args) {
        System.out.println("This program has two modes, select the one you want.");
        System.out.println("\t1) Test - enter an array size, and the program will sort a random array of that size.");
        System.out.println("\t2) Data - the program will sort very large arrays and store the time taken in a file.");
        System.out.print("Enter 1 or 2: ");

        Scanner stdin = new Scanner(System.in);
        int result = stdin.nextInt();
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
