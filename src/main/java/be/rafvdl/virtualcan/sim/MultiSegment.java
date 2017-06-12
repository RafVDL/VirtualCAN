package be.rafvdl.virtualcan.sim;

/**
 * A segment which consists of multiple other segments. The implementation defines how the segments are handled.
 */
public interface MultiSegment extends Segment {

    /**
     * Adds a segment.
     *
     * @param segment The segment to be added
     */
    void addSegment(Segment segment);

}
