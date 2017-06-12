package be.rafvdl.virtualcan.sim;

/**
 * This class represents an event on which the Simulator will wait before continuing.
 */
public abstract class Event implements Segment {

    /**
     * This method will be called every step until it returns true. The system will then continue its operation.
     *
     * @return True if system can continue
     */
    public abstract boolean hasOccurred();

    @Override
    public boolean shouldContinue() {
        return hasOccurred();
    }

}
