#include <bits/stdc++.h>
using namespace std;

struct Process {
    int processId;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;
};

int main() {
    int n;
    cout << "Enter number of processes: ";
    cin >> n;

    vector<Process> processes(n);

    // Input processes
    for (int i = 0; i < n; i++) {
        cout << "Enter arrival time and burst time for process " << i + 1 << ": ";
        cin >> processes[i].arrivalTime >> processes[i].burstTime;
        processes[i].processId = i + 1;
    }

    // Sort by arrival time
    sort(processes.begin(), processes.end(), [](Process a, Process b) {
        return a.arrivalTime < b.arrivalTime;
    });

    int currentTime = 0;
    double totalTAT = 0, totalWT = 0;

    for (int i = 0; i < n; i++) {
        if (currentTime < processes[i].arrivalTime) {
            currentTime = processes[i].arrivalTime;
        }

        processes[i].completionTime = currentTime + processes[i].burstTime;
        processes[i].turnaroundTime = processes[i].completionTime - processes[i].arrivalTime;
        processes[i].waitingTime = processes[i].turnaroundTime - processes[i].burstTime;

        totalTAT += processes[i].turnaroundTime;
        totalWT += processes[i].waitingTime;

        currentTime = processes[i].completionTime;
    }

    // Print process table
    cout << "\nProcess\tAT\tBT\tCT\tTAT\tWT\n";
    for (int i = 0; i < n; i++) {
        cout << "P" << processes[i].processId << "\t"
             << processes[i].arrivalTime << "\t"
             << processes[i].burstTime << "\t"
             << processes[i].completionTime << "\t"
             << processes[i].turnaroundTime << "\t"
             << processes[i].waitingTime << "\n";
    }

    // Print averages
    cout << fixed << setprecision(2);
    cout << "\nAverage Turnaround Time: " << totalTAT / n << "\n";
    cout << "Average Waiting Time: " << totalWT / n << "\n";

    return 0;
}
