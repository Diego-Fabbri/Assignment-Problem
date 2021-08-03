
package Utility;


public class Data {
  


//In the example there are five workers (numbered 0-4) and four tasks (numbered 0-3). 
public static int numWorkers() {
    int numWorkers = 5;//costs.length
        return numWorkers;
    }
public  static int numTasks() {
       int numTasks =4 ;//costs[0].length
       return numTasks;
    }
public static double[][] Costs(){
      double[][] costs = {
    {90, 80, 75, 70},
    {35, 85, 55, 65},
    {125, 95, 90, 95},
    {45, 110, 95, 115},
    {50, 100, 90, 100},
};
return costs;
}
}
