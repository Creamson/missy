package pl.edu.agh.missy.results;

import thiagodnf.jacof.aco.ACO;
import thiagodnf.jacof.aco.daemonactions.AbstractDaemonActions;

public class SaveResultDaemon extends AbstractDaemonActions {

    private final BSFResultSaver bsfResultSaver;

    public SaveResultDaemon(BSFResultSaver bsfResultSaver, ACO aco){
        super(aco);
        this.bsfResultSaver = bsfResultSaver;
    }

    @Override
    public void doAction() {
        bsfResultSaver.recordCheckpoint(aco.getProblem().evaluate(aco.getCurrentBest().getSolution()), "aco");
    }

    @Override
    public String toString() {
        return null;
    }
}