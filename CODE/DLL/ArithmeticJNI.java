
import java.util.*;
public class ArithmeticJNI {
    // Declare native methods
    public native int add(int a, int b);
    public native int subtract(int a, int b);
    public native int multiply(int a, int b);
    public native double divide(int a, int b);

    // Load the DLL
    static {
        System.loadLibrary("ArithmeticLib"); // loads ArithmeticLib.dll
    }

    public static void main(String[] args) {
        ArithmeticJNI obj = new ArithmeticJNI();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Any two no.:");
        int x=sc.nextInt();
        int y=sc.nextInt();

        System.out.println("Addition: " + obj.add(x, y));
        System.out.println("Subtraction: " + obj.subtract(x, y));
        System.out.println("Multiplication: " + obj.multiply(x, y));
        System.out.println("Division: " + obj.divide(x, y));
        sc.close();
    }
}
