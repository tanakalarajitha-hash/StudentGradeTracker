import java.util.Scanner;
public class StudentGradeTracker {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Get number of students
        System.out.print("Enter number of students: ");
        int n = sc.nextInt();

        // Arrays to store student details
        String[] names = new String[n];
        double[] grades = new double[n];

        // Input student details
        for (int i = 0; i < n; i++) {

            System.out.println("\nEnter details for Student " + (i + 1));

            System.out.print("Enter student name: ");
            names[i] = sc.next();

            System.out.print("Enter grade: ");
            grades[i] = sc.nextDouble();

            // Validate grade
            while (grades[i] < 0 || grades[i] > 100) {
                System.out.println("Grade must be between 0 and 100.");
                System.out.print("Enter grade again: ");
                grades[i] = sc.nextDouble();
            }
        }

        // Calculate total
        double total = 0;

        for (int i = 0; i < n; i++) {
            total = total + grades[i];
        }

        // Calculate average
        double average = total / n;

        // Find highest and lowest
        double highest = grades[0];
        double lowest = grades[0];

        String highestStudent = names[0];
        String lowestStudent = names[0];

        for (int i = 1; i < n; i++) {

            if (grades[i] > highest) {
                highest = grades[i];
                highestStudent = names[i];
            }

            if (grades[i] < lowest) {
                lowest = grades[i];
                lowestStudent = names[i];
            }
        }

        // Display summary report
        System.out.println("\n========================================");
        System.out.println("       STUDENT GRADE SUMMARY");
        System.out.println("========================================");

        System.out.printf("%-20s %-10s%n", "Student Name", "Grade");
        System.out.println("----------------------------------------");

        for (int i = 0; i < n; i++) {
            System.out.printf("%-20s %-10.2f%n", names[i], grades[i]);
        }

        System.out.println("----------------------------------------");

        System.out.printf("Average Grade       : %.2f%n", average);
        System.out.printf("Highest Grade       : %.2f%n", highest);
        System.out.println("Highest Student     : " + highestStudent);
        System.out.printf("Lowest Grade        : %.2f%n", lowest);
        System.out.println("Lowest Student      : " + lowestStudent);

        System.out.println("========================================");

        sc.close();
    }
}