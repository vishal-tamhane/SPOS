import java.util.Scanner;

public class WorstFit {
    public static void WorstFit(int[] jb, int[] mem, int jobs, int blocks, float tot) {
        int[] vis = new int[blocks];
        float space = 0;

        for (int i = 0; i < jobs; i++) {
            int worstIdx = -1;
            for (int j = 0; j < blocks; j++) {
                if (vis[j] == 0 && jb[i] <= mem[j]) {
                    if (worstIdx == -1 || mem[j] > mem[worstIdx]) {
                        worstIdx = j;
                    }
                }
            }
            if (worstIdx != -1) {
                vis[worstIdx] = 1;
                System.out.println("Job " + (i + 1) + " Allocated to Block " + (worstIdx + 1));
                space += jb[i];
            }
        }
        System.out.println("Space Utilization is: " + (space / tot));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of memory blocks: ");
        int blocks = sc.nextInt();

        System.out.print("Enter number of jobs: ");
        int jobs = sc.nextInt();

        int[] jb = new int[jobs];
        int[] mem = new int[blocks];
        float tot = 0;

        System.out.println("Enter memory required for each job: ");
        for (int i = 0; i < jobs; i++) jb[i] = sc.nextInt();

        System.out.println("Enter memory space of each block: ");
        for (int i = 0; i < blocks; i++) {
            mem[i] = sc.nextInt();
            tot += mem[i];
        }

        WorstFit(jb, mem, jobs, blocks, tot);
        sc.close();
    }
}
