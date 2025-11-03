

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


ArithmeticJNI.c


// File: ArithmeticJNI.c
#include <jni.h>
#include "ArithmeticJNI.h"

JNIEXPORT jint JNICALL Java_ArithmeticJNI_add
  (JNIEnv *env, jobject obj, jint a, jint b) {
    return a + b;
}

JNIEXPORT jint JNICALL Java_ArithmeticJNI_subtract
  (JNIEnv *env, jobject obj, jint a, jint b) {
    return a - b;
}

JNIEXPORT jint JNICALL Java_ArithmeticJNI_multiply
  (JNIEnv *env, jobject obj, jint a, jint b) {
    return a * b;
}

JNIEXPORT jdouble JNICALL Java_ArithmeticJNI_divide
  (JNIEnv *env, jobject obj, jint a, jint b) {
    if (b == 0) {
        // Prevent divide by zero (return special value)
        return 0.0;
    }
    return (double)a / (double)b;
}






OUTPUT:


PRACTICAL\CODE\DLL on main [!?] via C v6.3.0-gcc via v24.0.2$ javac ArithmeticJNI.java
PRACTICAL\CODE\DLL on main [!?] via C v6.3.0-gcc via v24.0.2$ javac -h . ArithmeticJNI.java
PRACTICAL\CODE\DLL on main [!?] via C v6.3.0-gcc via v24.0.2$ gcc -shared -fPIC -o libArithmeticLib.so     -I"$JAVA_HOME/include"     -I"$JAVA_HOME/include/linux"     ArithmeticJNI.c
PRACTICAL\CODE\DLL onmain [!?] via C v6.3.0-gcc via v24.0.2$ java -Djava.library.path=. 

ArithmeticJNI
Enter Any two no.:
50
20
Addition: 70
Subtraction: 30
Multiplication: 1000
Division: 2.5

