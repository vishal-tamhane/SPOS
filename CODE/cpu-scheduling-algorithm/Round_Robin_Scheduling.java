
import java.util.*;

public class Round_Robin_Scheduling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        int[] at = new int[n], bt = new int[n], rt = new int[n], wt = new int[n], tat = new int[n];
        for(int i=0;i<n;i++) {
            System.out.print("Arrival time for P" + (i+1) + ": ");
            at[i] = sc.nextInt();
            System.out.print("Burst time for P" + (i+1) + ": ");
            bt[i] = sc.nextInt();
            rt[i] = bt[i];
        }
        System.out.print("Enter time quantum: ");
        int tq = sc.nextInt();

        int time = 0, completed = 0;
        boolean[] done = new boolean[n];
        while (completed < n) {
            boolean flag = false;
            for (int i = 0; i < n; i++) {
                if (rt[i] > 0 && at[i] <= time) {
                    flag = true;
                    int exec = Math.min(rt[i], tq);
                    rt[i] -= exec; time += exec;
                    if (rt[i] == 0) {
                        completed++;
                        tat[i] = time - at[i];
                        wt[i] = tat[i] - bt[i];
                        done[i] = true;
                    }
                }
            }
            if (!flag) time++;
        }
        for (int i = 0; i < n; i++)
            System.out.println("P" + (i+1) + " WT: " + wt[i] + " TAT: " + tat[i]);
    }
}

