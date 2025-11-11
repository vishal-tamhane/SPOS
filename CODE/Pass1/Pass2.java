import java.io.*;
import java.util.Scanner;

public class Pass2 {

    static Obj[] symb_table = new Obj[10];
    static Obj[] literal_table = new Obj[10];
    static int symb_found = 0;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("ENTER TOTAL NUMBER OF SYMBOLS: ");
        int total_symb = sc.nextInt();
        for (int i = 0; i < total_symb; i++) {
            symb_table[i] = new Obj("", 0);
            System.out.println("ENTER SYMBOL NAME: ");
            symb_table[i].name = sc.next();
            System.out.println("ENTER SYMBOL ADDRESS: ");
            symb_table[i].addr = sc.nextInt();
        }

        System.out.println("ENTER TOTAL NUMBER OF LITRALS: ");
        int total_ltr = sc.nextInt();
        for (int i = 0; i < total_ltr; i++) {
            literal_table[i] = new Obj("", 0);
            System.out.println("ENTER LITERAL NAME: ");
            literal_table[i].name = sc.next();
            System.out.println("ENTER LITERAL ADDRESS: ");
            literal_table[i].addr = sc.nextInt();
        }

        System.out.println("\n****SYMBOL TABLE****");
        System.out.println("\nSYMBOL\tADDRESS");
        for (int i = 0; i < total_symb; i++) {
            System.out.println(symb_table[i].name + "\t" + symb_table[i].addr);
        }

        System.out.println("\n******LITERAL TABLE******");
        System.out.println("\nIndex\tLITERAL\tADDRESS");
        for (int i = 0; i < total_ltr; i++) {
            System.out.println((i + 1) + "\t" + literal_table[i].name + "\t" + literal_table[i].addr);
        }

        BufferedReader br2 = new BufferedReader(new FileReader("Output.txt"));
        String line;
        boolean symbol_error = false, undef_mnemonic = false;
        System.out.println("\n****************OUTPUT FILE****************\n\n");

        while ((line = br2.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue; // skip blank lines
            }
            
            String[] token_list = line.split("\\s+");
            symbol_error = false;
            undef_mnemonic = false;
            StringBuilder outputLine = new StringBuilder();
            boolean hasContent = false;
            boolean skipLine = false;

            for (String token : token_list) {
                if (token.isEmpty()) {
                    continue;
                }

                if (token.matches("[0-9]+")) {
                    
                    if (!hasContent && !skipLine) {
                        outputLine.append(token);
                        hasContent = true;
                    } else if (!skipLine) {
                        outputLine.append("\t").append(token);
                    }
                } else if (token.startsWith("(") && token.endsWith(")")) {
                    String content = token.substring(1, token.length() - 1);
                    
                    String[] parts = content.split(",");
                    
                    if (parts.length == 2) {
                        String letters = parts[0].trim();
                        int num = Integer.parseInt(parts[1].trim());
                        
                        switch (letters.toUpperCase()) {
                            case "S":
                                if (num > 0 && num <= total_symb && symb_table[num - 1].addr != 0) {
                                    outputLine.append("\t").append(symb_table[num - 1].addr);
                                    hasContent = true;
                                } else {
                                    outputLine.append("\t---");
                                    symbol_error = true;
                                    hasContent = true;
                                }
                                break;
                            case "L":
                                if (num > 0 && num <= total_ltr) {
                                    outputLine.append("\t").append(literal_table[num - 1].addr);
                                    hasContent = true;
                                } else {
                                    outputLine.append("\t---");
                                    symbol_error = true;
                                    hasContent = true;
                                }
                                break;
                            case "AD":
                                // Assembler directive - skip this entire line
                                skipLine = true;
                                break;
                            case "DL":
                                switch (num) {
                                    case 1:
                                        // DC - skip
                                        break;
                                    case 2:
                                        outputLine.append("\t 00 \t 00");
                                        hasContent = true;
                                        break;
                                }
                                break;
                            case "IS":
                            case "C":
                            case "RG":
                                if (!skipLine) {
                                    outputLine.append(String.format("\t%03d", num));
                                    hasContent = true;
                                }
                                break;
                            default: 
                                if (!skipLine) {
                                    outputLine.append(String.format("\t%03d", num));
                                    hasContent = true;
                                }
                                break;
                        }
                    }
                }
            }
            // Print the complete line only if it has content and shouldn't be skipped
            if (hasContent && !skipLine) {
                System.out.println(outputLine.toString());
            }
        }
        
        System.out.println(); 
        
        if (symbol_error) {
            System.out.print("\n\n************SYMBOL IS NOT DEFINED*****");
        }
        if (undef_mnemonic) {
            System.out.print("\n\n****************INVALID MNEMONIC******");
        }

        int[] flag = new int[total_symb];
        for (int i = 0; i < total_symb; i++) {
            symb_found = 0;
            for (int j = 0; j < total_symb; j++) {
                if (symb_table[i].name.equalsIgnoreCase(symb_table[j].name) && flag[j] == 0) {
                    symb_found++;
                    if(symb_found > 1) flag[j] = 1;
                }
            }
            if (symb_found > 1) {
                System.out.print("\n\n*******" + symb_table[i].name + "\" IS DUPLICATE SYMBOL");
            }
        }
        br2.close();
        sc.close();
    }
}

class Obj {
    String name;
    int addr;

    Obj(String nm, int address) {
        this.name = nm;
        this.addr = address;
    }
}