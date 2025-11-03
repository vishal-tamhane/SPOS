import java.util.*;

public class ArithmeticJava {
    // Pure Java implementation (no JNI needed)
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        if (b == 0) {
            return 0.0;
        }
        return (double)a / (double)b;
    }

    public static void main(String[] args) {
        ArithmeticJava obj = new ArithmeticJava();
        Scanner sc = new Scanner(System.in);
        
        System.out.println("=== Arithmetic Operations (Pure Java Implementation) ===");
        System.out.println("Enter any two numbers:");
        int x = sc.nextInt();
        int y = sc.nextInt();

        System.out.println("\nResults:");
        System.out.println("Addition: " + obj.add(x, y));
        System.out.println("Subtraction: " + obj.subtract(x, y));
        System.out.println("Multiplication: " + obj.multiply(x, y));
        System.out.println("Division: " + obj.divide(x, y));
        
        System.out.println("\nNote: This is a pure Java implementation demonstrating the same functionality.");
        System.out.println("For actual JNI/DLL implementation, you need a 64-bit compiled DLL.");
        
        sc.close();
    }
}
