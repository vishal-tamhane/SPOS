import java.util.*;

class Process {
    int processId;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;

    public Process(int processId, int arrivalTime, int burstTime) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class FCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        List<Process> processes = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time and burst time for process " + (i + 1) + ": ");
            int arrival = sc.nextInt();
            int burst = sc.nextInt();
            processes.add(new Process(i + 1, arrival, burst));
        }

        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        double totalTAT = 0;
        double totalWT = 0;

        // Calculate times
        for (Process p : processes) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }

            p.completionTime = currentTime + p.burstTime;
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;

            currentTime = p.completionTime;

            totalTAT += p.turnaroundTime;
            totalWT += p.waitingTime;
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
        double avgTAT = totalTAT / n;
        double avgWT = totalWT / n;

        System.out.printf("\nAverage Turnaround Time: %.2f\n", avgTAT);
        System.out.printf("Average Waiting Time: %.2f\n", avgWT);
    }
}
