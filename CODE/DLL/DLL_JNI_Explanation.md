# JNI (Java Native Interface) - DLL Implementation Guide

## Overview

This project demonstrates **JNI (Java Native Interface)** - a framework that allows Java code to call native code (C/C++) compiled into shared libraries (`.so` on Linux, `.dll` on Windows, `.dylib` on macOS).

**Purpose:** Enable Java applications to leverage platform-specific native code for performance-critical operations or system-level tasks.

---

## What is JNI?

**Java Native Interface (JNI)** is a programming framework that enables Java code running in the JVM to call and be called by native applications and libraries written in other languages like C, C++, and assembly.

### Why Use JNI?

1. **Performance:** Native code (C/C++) can be faster for compute-intensive operations
2. **System Access:** Direct access to operating system APIs
3. **Legacy Integration:** Reuse existing C/C++ libraries
4. **Hardware Control:** Low-level hardware access
5. **Platform-Specific Features:** Use OS-specific functionality

---

## Project Structure

```
DLL/
├── ArithmeticJNI.java      # Java class with native method declarations
├── ArithmeticJNI.h         # Auto-generated C header file
├── ArithmeticJNI.c         # C implementation of native methods
├── ArithmeticJava.java     # Pure Java alternative (no JNI)
└── ArithmeticLib.so        # Compiled shared library (Linux)
    ArithmeticLib.dll       # Compiled DLL (Windows)
```

---

## Components Explained

### 1. **ArithmeticJNI.java** - Java Interface

```java
public class ArithmeticJNI {
    // Declare native methods (implemented in C)
    public native int add(int a, int b);
    public native int subtract(int a, int b);
    public native int multiply(int a, int b);
    public native double divide(int a, int b);

    // Load the shared library
    static {
        System.loadLibrary("ArithmeticLib"); // loads libArithmeticLib.so on Linux
    }

    public static void main(String[] args) {
        ArithmeticJNI obj = new ArithmeticJNI();
        // Call native methods like regular Java methods
        System.out.println("Addition: " + obj.add(10, 5));
    }
}
```

**Key Points:**
- `native` keyword declares methods implemented in native code
- `System.loadLibrary()` loads the compiled shared library at runtime
- Native methods are called like regular Java methods

---

### 2. **ArithmeticJNI.h** - C Header File

Auto-generated header containing function signatures:

```c
JNIEXPORT jint JNICALL Java_ArithmeticJNI_add
  (JNIEnv *, jobject, jint, jint);

JNIEXPORT jint JNICALL Java_ArithmeticJNI_subtract
  (JNIEnv *, jobject, jint, jint);
```

**Naming Convention:** `Java_<ClassName>_<methodName>`

**JNI Types:**
- `jint` → Java `int`
- `jdouble` → Java `double`
- `JNIEnv*` → JNI environment pointer (interface to JVM)
- `jobject` → Reference to the calling Java object

---

### 3. **ArithmeticJNI.c** - Native Implementation

```c
#include <jni.h>
#include "ArithmeticJNI.h"

JNIEXPORT jint JNICALL Java_ArithmeticJNI_add
  (JNIEnv *env, jobject obj, jint a, jint b) {
    return a + b;  // Actual implementation in C
}

JNIEXPORT jint JNICALL Java_ArithmeticJNI_subtract
  (JNIEnv *env, jobject obj, jint a, jint b) {
    return a - b;
}
```

**Parameters:**
- `JNIEnv *env` - Pointer to JNI environment (for calling JVM functions)
- `jobject obj` - Reference to the Java object that called this method
- `jint a, jint b` - Method parameters

---

## How It Works - Complete Flow

```
┌─────────────────────────────────────────────────────────────┐
│ Step 1: Java Code (ArithmeticJNI.java)                     │
│         - Declares native methods with 'native' keyword     │
│         - Loads shared library at runtime                   │
└────────────────────┬────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────┐
│ Step 2: Compile Java & Generate Header                     │
│         Command: javac -h . ArithmeticJNI.java             │
│         Output: ArithmeticJNI.class + ArithmeticJNI.h      │
└────────────────────┬────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────┐
│ Step 3: Implement in C (ArithmeticJNI.c)                   │
│         - Include generated header                          │
│         - Implement functions using JNI types               │
└────────────────────┬────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────┐
│ Step 4: Compile C to Shared Library                        │
│         Linux: gcc -shared -fPIC -o libArithmeticLib.so    │
│         Windows: gcc -shared -o ArithmeticLib.dll          │
└────────────────────┬────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────┐
│ Step 5: Run Java Program                                    │
│         Command: java -Djava.library.path=. ArithmeticJNI  │
│         - JVM loads shared library                          │
│         - Native methods execute C code                     │
└─────────────────────────────────────────────────────────────┘
```

---

## Complete Setup & Execution Guide

### **For Ubuntu/Linux Systems**

#### Prerequisites

```bash
# Install JDK (if not already installed)
sudo apt update
sudo apt install default-jdk

# Install GCC compiler
sudo apt install gcc

# Verify installations
java -version
javac -version
gcc --version
```

---

### **Step-by-Step Execution**

#### **Step 1: Navigate to Project Directory**

```bash
cd "/path/to/DLL/folder"
# Example: cd "d:/study-acadmic Notes/5th sem/SPOS/SPOS/CODE/DLL/DLL"
```

#### **Step 2: Compile Java and Generate Header**

```bash
# Compile Java file and generate C header
javac -h . ArithmeticJNI.java
```

**Output:**
- `ArithmeticJNI.class` (compiled Java bytecode)
- `ArithmeticJNI.h` (C header file with JNI function signatures)

---

#### **Step 3: Find JDK Include Path**

```bash
# Find your JDK installation path
java -XshowSettings:properties -version 2>&1 | grep "java.home"

# Common paths:
# Ubuntu: /usr/lib/jvm/default-java
# Or: /usr/lib/jvm/java-11-openjdk-amd64
```

Set the JDK path (replace with your actual path):

```bash
export JAVA_HOME=/usr/lib/jvm/default-java
```

---

#### **Step 4: Compile C Code to Shared Library (.so)**

```bash
# Compile C code to shared library on Linux
gcc -shared -fPIC -o libArithmeticLib.so ArithmeticJNI.c \
    -I"${JAVA_HOME}/include" \
    -I"${JAVA_HOME}/include/linux"
```

**Explanation:**
- `-shared` - Create a shared library
- `-fPIC` - Position Independent Code (required for shared libraries)
- `-o libArithmeticLib.so` - Output filename (must start with `lib` on Linux)
- `-I` - Include directories for JNI headers

**Output:** `libArithmeticLib.so` (shared library)

---

#### **Step 5: Run the Java Program**

```bash
# Run with library path specified
java -Djava.library.path=. ArithmeticJNI
```

**OR** set the library path permanently:

```bash
export LD_LIBRARY_PATH=.:$LD_LIBRARY_PATH
java ArithmeticJNI
```

---

### **Alternative: Pure Java Version (No JNI)**

If you can't compile the shared library, use the pure Java version:

```bash
javac ArithmeticJava.java
java ArithmeticJava
```

This provides the same functionality without requiring native code compilation.

---

## Expected Output

```
Enter Any two no.:
15
5
Addition: 20
Subtraction: 10
Multiplication: 75
Division: 3.0
```

---

## Complete Command Summary (Ubuntu)

```bash
# 1. Compile Java and generate header
javac -h . ArithmeticJNI.java

# 2. Set JAVA_HOME (find your path first)
export JAVA_HOME=/usr/lib/jvm/default-java

# 3. Compile C to shared library
gcc -shared -fPIC -o libArithmeticLib.so ArithmeticJNI.c \
    -I"${JAVA_HOME}/include" \
    -I"${JAVA_HOME}/include/linux"

# 4. Run the program
java -Djava.library.path=. ArithmeticJNI
```

---

## For Windows Systems

```powershell
# 1. Compile Java and generate header
javac -h . ArithmeticJNI.java

# 2. Compile C to DLL (using MinGW/GCC)
gcc -shared -o ArithmeticLib.dll ArithmeticJNI.c `
    -I"C:\Program Files\Java\jdk-17\include" `
    -I"C:\Program Files\Java\jdk-17\include\win32"

# 3. Run the program
java -Djava.library.path=. ArithmeticJNI
```

---

## Troubleshooting

### **Error: UnsatisfiedLinkError: no ArithmeticLib in java.library.path**

**Solution:**
```bash
# Make sure the .so file is in the current directory
ls -l libArithmeticLib.so

# Run with explicit library path
java -Djava.library.path=. ArithmeticJNI

# Or set LD_LIBRARY_PATH
export LD_LIBRARY_PATH=.:$LD_LIBRARY_PATH
```

---

### **Error: Cannot find jni.h**

**Solution:**
```bash
# Find JDK include directory
find /usr/lib/jvm -name "jni.h"

# Use the correct path in gcc command
gcc -shared -fPIC -o libArithmeticLib.so ArithmeticJNI.c \
    -I"/usr/lib/jvm/java-11-openjdk-amd64/include" \
    -I"/usr/lib/jvm/java-11-openjdk-amd64/include/linux"
```

---

### **Error: Wrong ELF class or architecture mismatch**

**Cause:** 32-bit/64-bit mismatch between Java and compiled library

**Solution:**
```bash
# Check Java architecture
java -version  # Look for 64-bit or 32-bit

# Ensure gcc compiles for the same architecture
# For 64-bit:
gcc -shared -fPIC -m64 -o libArithmeticLib.so ...

# For 32-bit:
gcc -shared -fPIC -m32 -o libArithmeticLib.so ...
```

---

## Key Concepts

### **JNI Data Type Mapping**

| Java Type | JNI Type   | C Type          |
|-----------|------------|-----------------|
| boolean   | jboolean   | unsigned char   |
| byte      | jbyte      | signed char     |
| char      | jchar      | unsigned short  |
| short     | jshort     | short           |
| int       | jint       | int             |
| long      | jlong      | long long       |
| float     | jfloat     | float           |
| double    | jdouble    | double          |
| void      | void       | void            |

### **Function Naming Convention**

Native method names follow: `Java_<FullyQualifiedClassName>_<methodName>`

Example:
- Java class: `ArithmeticJNI`
- Java method: `add`
- C function: `Java_ArithmeticJNI_add`

For nested classes: `Java_<package>_<OuterClass>_00024<InnerClass>_<methodName>`

---

## Performance Considerations

### **When to Use JNI:**
- ✅ CPU-intensive algorithms (image processing, cryptography)
- ✅ Accessing OS-specific features
- ✅ Integrating existing C/C++ libraries
- ✅ Real-time systems requiring low latency

### **When NOT to Use JNI:**
- ❌ Simple operations (use pure Java)
- ❌ I/O bound tasks (Java is sufficient)
- ❌ Cross-platform portability is priority
- ❌ Development speed matters more than raw performance

**Overhead:** JNI calls have overhead due to context switching between JVM and native code. Use for operations where the native execution time significantly exceeds the call overhead.

---

## Security Considerations

1. **Memory Safety:** Native code can crash the entire JVM
2. **Buffer Overflows:** C code is susceptible to buffer overflow attacks
3. **Pointer Errors:** Incorrect pointer usage can cause segmentation faults
4. **Resource Leaks:** Must manually manage memory in C

**Best Practice:** Minimize JNI usage and thoroughly test native code.

---

## Real-World Use Cases

1. **Android NDK:** High-performance games and apps
2. **Database Drivers:** JDBC drivers often use JNI
3. **Scientific Computing:** MATLAB, NumPy-like operations
4. **Hardware Integration:** USB device drivers, sensor access
5. **Legacy System Integration:** Calling COBOL/Fortran code

---

## Additional Resources

- **Official JNI Documentation:** https://docs.oracle.com/javase/8/docs/technotes/guides/jni/
- **JNI Programming Guide:** https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/jniTOC.html
- **JNI Tips:** https://developer.android.com/training/articles/perf-jni

---

## Summary

This project demonstrates:
1. How to declare native methods in Java using the `native` keyword
2. How to generate JNI header files automatically
3. How to implement native methods in C
4. How to compile C code into shared libraries
5. How to load and call native methods from Java

**Key Takeaway:** JNI bridges Java and native code, enabling performance optimization and system-level access while maintaining Java's portability for non-critical parts of the application.

---

## Convert This File to PDF

### **Method 1: Using Pandoc (Ubuntu)**

```bash
# Install pandoc
sudo apt install pandoc texlive-latex-base texlive-fonts-recommended

# Convert to PDF
pandoc DLL_JNI_Explanation.md -o DLL_JNI_Explanation.pdf
```

### **Method 2: Using VS Code Extension**

1. Install "Markdown PDF" extension in VS Code
2. Open this markdown file
3. Press `Ctrl+Shift+P` → Type "Markdown PDF: Export (pdf)"

### **Method 3: Online Converter**

Visit: https://www.markdowntopdf.com/

---

**Last Updated:** November 2025
