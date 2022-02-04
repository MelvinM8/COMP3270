/**
* Authors: Melvin Moreno & Matthew Freestone
* This is a program we created for our programming assignment 1 in Dr. Biaz's class
* It computes the sum powers and outputs the time it took to compute depending on your input of size n.
*/

import java.util.Scanner;
import java.text.DecimalFormat;

public class ProgrammingAssignment1 {
    //Methods
    public static double ComputeSumPowers(double x, double n) {
        double sum = 0;
        for (int i = 0; i <= n; i++) {
            double prod = 1;
            for (int j = 1; j <= i; j++) {
                prod = prod * x;
            }
            sum = sum + prod;
        }
        return sum;
    }

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter a value for x: ");
        final double x = stdin.nextDouble();

        for (int n = 100; n < 50000; n+=100) { // Edit your n here
            long startTime = System.currentTimeMillis();
            double answer = ComputeSumPowers(x, n);
            long endTime = System.currentTimeMillis();

            DecimalFormat timeFormat = new DecimalFormat("#.00");
            String output = n + "," + timeFormat.format(endTime-startTime);
            System.out.println(output);
        }
        stdin.close();
    }
}
