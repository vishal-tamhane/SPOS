import java.io.*;
import java.util.*;

// Helper class for MNT entries. Can be in the same file.
class MntEntry {
    String name;
    int mdtAddress;
    int argCount;

    public MntEntry(String name, int mdtAddress, int argCount) {
        this.name = name;
        this.mdtAddress = mdtAddress;
        this.argCount = argCount;
    }
    @Override
    public String toString() {
        return name + "\t" + mdtAddress + "\t" + argCount;
    }
}

public class MPass1 {

    public static void main(String[] args) throws IOException {
        List<MntEntry> mnt = new ArrayList<>();
        List<String> mdt = new ArrayList<>();
        Map<String, Integer> argList = new HashMap<>();
        List<String> formalArgListOutput = new ArrayList<>(); // To store lines for the new file
        int mdtc = 0;

        // Ensure input file exists in the same directory
        File inputFile = new File("input_pass1.txt");
        if (!inputFile.exists()) {
            System.err.println("Error: input_pass1.txt not found. Please create it in the same directory.");
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        BufferedWriter mntWriter = new BufferedWriter(new FileWriter("MNT.txt"));
        BufferedWriter mdtWriter = new BufferedWriter(new FileWriter("MDT.txt"));
        // *** NEW: Writer for the Formal Argument List file ***
        BufferedWriter argWriter = new BufferedWriter(new FileWriter("FormalARGLIST.txt"));

        String line;
        boolean inMacro = false;

        while ((line = br.readLine()) != null) {
            line = line.trim().replaceAll(",", "");
            String[] tokens = line.split("\\s+");

            if (tokens[0].equalsIgnoreCase("MACRO")) {
                inMacro = true;
                // The next line is the macro prototype
                line = br.readLine().trim().replaceAll(",", "");
                tokens = line.split("\\s+");

                String macroName = tokens[0];
                argList.clear();

                // *** NEW: Build the argument string for output ***
                StringBuilder argStringForFile = new StringBuilder(macroName + ":");

                for (int i = 1; i < tokens.length; i++) {
                    argList.put(tokens[i], i); // Store argument name and its position
                    argStringForFile.append(" ").append(tokens[i]);
                }
                formalArgListOutput.add(argStringForFile.toString());


                mnt.add(new MntEntry(macroName, mdtc, argList.size()));
                mdt.add(line);
                mdtc++;

            } else if (tokens[0].equalsIgnoreCase("MEND")) {
                mdt.add(tokens[0]);
                mdtc++;
                inMacro = false;
                argList.clear();
            } else if (inMacro) {
                StringBuilder processedLine = new StringBuilder();
                processedLine.append(tokens[0]);
                for (int i = 1; i < tokens.length; i++) {
                    if (argList.containsKey(tokens[i])) {
                        processedLine.append("\t#").append(argList.get(tokens[i]));
                    } else {
                        processedLine.append("\t").append(tokens[i]);
                    }
                }
                mdt.add(processedLine.toString());
                mdtc++;
            }
        }

        // --- Writing MNT to console and file ---
        System.out.println("\n----------- MACRO NAME TABLE (MNT) -----------");
        System.out.println("Index\tName\tMDT_Addr\t#Args");
        for (int i = 0; i < mnt.size(); i++) {
            MntEntry entry = mnt.get(i);
            System.out.println(i + "\t" + entry.name + "\t" + entry.mdtAddress + "\t\t" + entry.argCount);
            mntWriter.write(entry.toString() + "\n");
        }

        // --- Writing MDT to console and file ---
        System.out.println("\n----------- MACRO DEFINITION TABLE (MDT) -----------");
        System.out.println("Index\tStatement");
        for (int i = 0; i < mdt.size(); i++) {
            System.out.println(i + "\t" + mdt.get(i));
            mdtWriter.write(mdt.get(i) + "\n");
        }

        // *** NEW: Writing Formal Argument List to console and file ***
        System.out.println("\n----------- FORMAL ARGUMENT LIST -----------");
        for(String argLine : formalArgListOutput) {
            System.out.println(argLine);
            argWriter.write(argLine + "\n");
        }


        br.close();
        mntWriter.close();
        mdtWriter.close();
        argWriter.close(); // Close the new writer
        System.out.println("\nPass 1 processing complete. MNT.txt, MDT.txt, and FormalARGLIST.txt generated.");
    }
}