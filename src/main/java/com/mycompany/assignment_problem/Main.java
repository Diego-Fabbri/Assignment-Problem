
package com.mycompany.assignment_problem;

import Utility.Data;
import ilog.concert.IloException;
import java.io.FileNotFoundException;
import java.io.PrintStream;



public class Main {
    public static void main(String[] args) throws IloException, FileNotFoundException{
          System.setOut(new PrintStream("Assignment_Problem.log"));
       
    int workers =Data.numWorkers();
    int tasks = Data.numTasks();
    double[][] costs= Data.Costs();
      ASSIGN_Model model = new ASSIGN_Model(workers,tasks,costs);
     model.solveModel();
    }
}
