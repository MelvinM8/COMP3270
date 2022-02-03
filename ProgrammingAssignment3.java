package com.company;
import java.util.Scanner;
import java.util.*;
import java.text.*;
import java.text.DecimalFormat;

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
            int q = (p + r) / 2;
            mergeSort(A, p, q);
            mergeSort(A, q+1, r);
            merge(A, p, q, r);
        }
    }    //Check to see if sorts an array of 10.
    public static void array(int arrayTest[]) {
        int n = arrayTest.length;
        for (int i = 0; i < n; ++i) {
            System.out.print(arrayTest[i] + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        int i, n;
        long start = System.currentTimeMillis();
        System.out.println("Input how many elements are in the array: ");
        n = stdin.nextInt();
        int arrayTest[] = new int[n];
        System.out.println("Enter the elements of the array: " );
        for (i = 0; i < n; i++) {
            arrayTest[i] = stdin.nextInt();
        }
        System.out.println("Unsorted Array: ");
        array(arrayTest);
        ProgrammingAssignment3 obj = new ProgrammingAssignment3();
        obj.mergeSort(arrayTest, 0, n-1);
        long end = System.currentTimeMillis();
        System.out.println("After Sorting the Array is: ");;
        array(arrayTest);
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.print("It took " + formatter.format((end - start) / 1000d) + " seconds to sort.");
    }
}
