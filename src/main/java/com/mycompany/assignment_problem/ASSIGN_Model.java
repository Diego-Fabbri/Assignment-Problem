
package com.mycompany.assignment_problem;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVarType;
import ilog.concert.IloObjective;
import ilog.concert.IloObjectiveSense;
import ilog.cplex.IloCplex;
import static ilog.cplex.IloCplex.Status.Feasible;
import static ilog.cplex.IloCplex.Status.Optimal;


public class ASSIGN_Model {

    protected IloCplex model;
    protected IloIntVar[][] x;
    protected int n;
    protected int m;
    protected double[][] costs;

    ASSIGN_Model(int workers, int tasks, double[][] costs) throws IloException {
        this.n = workers;
        this.m = tasks;
        this.costs = costs;
        this.model = new IloCplex();
        this.x = new IloIntVar[workers][tasks];
    }
    //The following code creates binary integer variables for the problem.
    // x[i][j] is an array of 0-1 variables, which will be 1
// if worker i is assigned to task j, 0 otherwise

    protected void addVariables() throws IloException {
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < m; j++) {

                x[i][j] = (IloIntVar) model.numVar(0, 1, IloNumVarType.Int, "x[" + i + "][" + j + "]");
            }
        }

    }

    //The following code creates the objective function for the problem.
    protected void addObjective() throws IloException {
        IloLinearNumExpr objective = model.linearNumExpr();

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; j++) {
                objective.addTerm(x[i][j], costs[i][j]);

            }
        }

        IloObjective Obj = model.addObjective(IloObjectiveSense.Minimize, objective);
    }

//The following code creates the constraints for the problem.
// Each worker is assigned to at most one task.
    protected void addConstraints() throws IloException {
// Each worker is assigned to at most one task.

        for (int i = 0; i < n; i++) {
            IloLinearNumExpr expr_1 = model.linearNumExpr();
            for (int j = 0; j < m; j++) {
                expr_1.addTerm(x[i][j], 1);
            }
            model.addLe(expr_1, 1);
        }

        // Each task is assigned to exactly one worker.
        for (int j = 0; j < m; j++) {
            IloLinearNumExpr expr_2 = model.linearNumExpr();
            for (int i = 0; i < n; i++) {
                expr_2.addTerm(x[i][j], 1);
            }
            model.addEq(expr_2, 1);
        }
    }

    public void solveModel() throws IloException {
        addVariables();
        addObjective();
        addConstraints();
        model.exportModel("Assignment_Problem.lp");

        model.solve();// questo metodo risolve il problema

        if (model.getStatus() == IloCplex.Status.Feasible
                | model.getStatus() == IloCplex.Status.Optimal) {
            
        System.out.println("Solution status = "+ model.getStatus());
        
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("Total Assignment Cost = " + model.getObjValue());
            System.out.println();
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    // Test if x[i][j] is 0 or 1 (with tolerance for floating point
                    // arithmetic).
                    if (model.getValue(x[i][j]) != 0) {
                        System.out.println("Worker " + i + " assigned to task " + j + ".  Cost = " + costs[i][j] + "  ---->  " + x[i][j].getName() + " = " + model.getValue(x[i][j]));
                    }
                }
            }
        } else {
            System.out.println("The problem status is:" + model.getStatus());
            
        }
    }
}
