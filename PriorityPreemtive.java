package com.mycompany.osproject;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Salma Hesham
 */



//import java.util.PriorityQueue;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class PriorityPreemtive{
    
    private List<Process> completedProcesses = new ArrayList<>();
    private PriorityQueue<Process> readyQueue;
    private List<String> ganttChart = new ArrayList<>();
    private Process currentProcess= null;
    
    public PriorityPreemtive(){
        //Priority first, then Arrival Time as a tie-breaker
        readyQueue= new PriorityQueue<>((p1,p2)->{
            if(p1.getPriority()!=p2.getPriority()){
                   return Integer.compare(p1.getPriority(), p2.getPriority());
            }
            return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());

                });
    
}
    
    
    public Process getNextProcess(ArrayList<Process> allProcesses, int currentTime){
        
            // 1. Move arrived processes from the list to the ready queue
            while(!allProcesses.isEmpty() && allProcesses.get(0).getArrivalTime()<=currentTime){
                readyQueue.add(allProcesses.remove(0));
                
            }
            
            // 2. Preemtion Logic
            if(!readyQueue.isEmpty()){
                if(currentProcess==null){
                    currentProcess=readyQueue.poll();
                }
                else if(currentProcess.getPriority() > readyQueue.peek().getPriority()){
                    readyQueue.add(currentProcess); 
                    currentProcess = readyQueue.poll();
                }
            }
            // 3. Execution & Logging
            if(currentProcess!=null){
            currentProcess.runOneUnit(currentTime);
            
            ganttChart.add(currentProcess.getName());
            if (currentProcess.isFinished()) {
                completedProcesses.add(currentProcess); 
                Process finished=currentProcess;
                currentProcess = null; // CPU becomes free for the next second
                return finished;
            }
            }
            else ganttChart.add("Idle");
            
        return currentProcess;
        
    }
    
    public void calculateAverages() {
        if(completedProcesses.isEmpty()){
            return;
        }
    double totalWait = 0;
    double totalTurnaround = 0;
    
    for (Process p : completedProcesses) {
        totalWait += p.getWaitingTime();
        totalTurnaround += p.getTurnaroundTime();
    }
    
    double avgWait = totalWait / completedProcesses.size();
    double avgTurnaround = totalTurnaround / completedProcesses.size();
    System.out.println(avgWait);
    System.out.println(avgTurnaround);
    
}
    
    public List<String> getGanttChart() {
        return ganttChart;
    }
    
    

        }

