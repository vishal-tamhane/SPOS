import java.util.*;

class Process {
    int processId;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;
    boolean isCompleted;

    public Process(int processId, int arrivalTime, int burstTime) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.isCompleted = false;
    }
}

public class SJFPreemptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        List<Process> processes = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time and burst time for process " + (i + 1) + ": ");
            int at = sc.nextInt();
            int bt = sc.nextInt();
            processes.add(new Process(i + 1, at, bt));
        }

        int currentTime = 0;
        int completed = 0;
        double totalTAT = 0;
        double totalWT = 0;

        while (completed < n) {
            Process shortest = null;

            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && !p.isCompleted && p.remainingTime > 0) {
                    if (shortest == null || p.remainingTime < shortest.remainingTime) {
                        shortest = p;
                    }
                }
            }

            if (shortest != null) {
                shortest.remainingTime--;
                currentTime++;

                if (shortest.remainingTime == 0) {
                    shortest.isCompleted = true;
                    shortest.completionTime = currentTime;
                    shortest.turnaroundTime = shortest.completionTime - shortest.arrivalTime;
                    shortest.waitingTime = shortest.turnaroundTime - shortest.burstTime;

                    totalTAT += shortest.turnaroundTime;
                    totalWT += shortest.waitingTime;
                    completed++;
                }
            } else {
                currentTime++;
            }
        }

        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");
        for (Process p : processes) {
            System.out.println("P" + p.processId + "\t" +
                    p.arrivalTime + "\t" +
                    p.burstTime + "\t" +
                    p.completionTime + "\t" +
                    p.turnaroundTime + "\t" +
                    p.waitingTime);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f\n", totalTAT / n);
        System.out.printf("Average Waiting Time: %.2f\n", totalWT / n);
    }
}
