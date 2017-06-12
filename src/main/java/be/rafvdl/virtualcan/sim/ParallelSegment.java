package be.rafvdl.virtualcan.sim;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A parallel implementation of {@link MultiSegment}.
 */
public class ParallelSegment implements MultiSegment {

    private Set<Segment> segmentSet;

    /**
     * Creates an instance using an array of segments.
     *
     * @param segments The segments
     */
    public ParallelSegment(Segment... segments) {
        this(Arrays.asList(segments));
    }

    /**
     * Creates an instance using a collection of segments.
     *
     * @param segments The segments
     */
    public ParallelSegment(Collection<Segment> segments) {
        segmentSet = new HashSet<>();
        segmentSet.addAll(segments);
    }

    @Override
    public void execute() {
        segmentSet.forEach(Segment::execute);
    }

    @Override
    public boolean shouldContinue() {
        return segmentSet.stream().allMatch(Segment::shouldContinue);
    }

    @Override
    public void addSegment(Segment segment) {
        segmentSet.add(segment);
    }

}
