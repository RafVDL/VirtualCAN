package be.rafvdl.virtualcan.sim;

import java.util.Arrays;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * A sequential implementation of {@link MultiSegment}.
 */
public class SequentialSegment implements MultiSegment {

    private Queue<Segment> segmentQueue;

    /**
     * Creates an instance using an array of segments.
     *
     * @param segments The segments
     */
    public SequentialSegment(Segment... segments) {
        this(Arrays.asList(segments));
    }

    /**
     * Creates an instance using a collection of segments.
     *
     * @param segments The segments
     */
    public SequentialSegment(Collection<Segment> segments) {
        segmentQueue = new LinkedBlockingDeque<>();
        segmentQueue.addAll(segments);
    }

    @Override
    public void addSegment(Segment segment) {
        segmentQueue.offer(segment);
    }

    @Override
    public void execute() {
        if (segmentQueue.size() == 0)
            return;
        if (segmentQueue.peek().shouldContinue())
            segmentQueue.remove();
        if (segmentQueue.size() == 0)
            return;
        segmentQueue.peek().execute();
    }

    @Override
    public boolean shouldContinue() {
        return segmentQueue.size() == 0;
    }

}
