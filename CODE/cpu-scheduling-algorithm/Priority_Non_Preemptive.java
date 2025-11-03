
import java.util.*;

public class Priority_Non_Preemptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter no. of processes: ");
        int n = sc.nextInt();
        int[] at = new int[n], bt = new int[n], pr = new int[n];
        String[] pid = new String[n];
        for (int i = 0; i < n; i++) {
            pid[i]="P"+(i+1);
            System.out.print("Arrival time for " + pid[i] + ": ");
            at[i] = sc.nextInt();
            System.out.print("Burst time for " + pid[i] + ": ");
            bt[i] = sc.nextInt();
            System.out.print("Priority for " + pid[i] + ": ");
            pr[i] = sc.nextInt();
        }
        boolean[] done = new boolean[n];
        int[] wt = new int[n], tat = new int[n];
        int time = 0, completed = 0;
        while (completed < n) {
            int idx = -1, mn = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (!done[i] && at[i] <= time && pr[i] < mn) {
                    mn = pr[i]; idx = i;
                }
            }
            if (idx ==  -1) { time++; continue;}
            wt[idx] = time - at[idx];
            tat[idx] = wt[idx] + bt[idx];
            time += bt[idx];
            done[idx] = true; completed++;
        }
        for (int i = 0; i < n; i++)
            System.out.println(pid[i] + " WT: " + wt[i] + " TAT: " + tat[i]);
    }
}

