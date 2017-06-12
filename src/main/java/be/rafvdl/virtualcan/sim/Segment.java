package be.rafvdl.virtualcan.sim;

/**
 * This class is an interface for executable parts of the simulator.
 */
public interface Segment {

    /**
     * Executes certain code.
     */
    void execute();

    /**
     * This method will be called every step until it returns true. The system will then continue its operation.
     *
     * @return True if system can continue
     */
    boolean shouldContinue();

}
