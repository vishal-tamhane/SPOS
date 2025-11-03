import java.util.*;

class Process {
    int pid, at, bt, ct, tat, wt;
    boolean completed = false;

    Process(int pid, int at, int bt) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
    }
}

public class Sjf_non_preemptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        double twt=0;
        double ttat=0;

        List<Process> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter AT and BT for P" + (i + 1) + ": ");
            list.add(new Process(i + 1, sc.nextInt(), sc.nextInt()));
        }

        int time = 0, completed = 0;
        while (completed < n) {
            Process shortest = null;

            for (Process p : list) {
                if (!p.completed && p.at <= time) {
                    if (shortest == null || p.bt < shortest.bt)
                        shortest = p;
                }
            }

            if (shortest == null) {
                time++;
            } else {
                shortest.ct = time + shortest.bt;
                shortest.tat = shortest.ct - shortest.at;
                shortest.wt = shortest.tat - shortest.bt;
                shortest.completed = true;
                time = shortest.ct;
                completed++;
                twt=twt+shortest.wt;
                ttat=ttat+shortest.tat;
            }
        }

        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        for (Process p : list)
            System.out.println("P" + p.pid + "\t" + p.at + "\t" + p.bt + "\t" + p.ct + "\t" + p.tat + "\t" + p.wt);
        System.out.println("Average waiting time is :"+(twt/n));
        System.out.println("Average turn aroun time is :"+(ttat/n));
    }
}
