package be.rafvdl.virtualcan.sim;

/**
 * This class defines an action which can be executed.
 */
public abstract class Action implements Segment {

    private boolean hasExecuted = false;

    /**
     * Executes certain code.
     *
     * @return True if execution has finished
     */
    public abstract boolean perform();

    @Override
    public void execute() {
        hasExecuted = perform();
    }

    @Override
    public boolean shouldContinue() {
        return hasExecuted;
    }

}
