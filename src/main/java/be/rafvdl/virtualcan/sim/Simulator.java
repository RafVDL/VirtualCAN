package be.rafvdl.virtualcan.sim;

import java.util.function.BooleanSupplier;

/**
 * This class contains the simulator and a builder.
 */
public class Simulator {

    /**
     * This class builds a Simulator object from the ground up.
     */
    public static final class Builder {

        private MultiSegment segment;

        private Builder(MultiSegment segment) {
            this.segment = segment;
        }

        /**
         * Adds a segment to the system.
         *
         * @param segment The segment
         * @return The builder
         */
        public Builder addSegment(Segment segment) {
            this.segment.addSegment(segment);
            return this;
        }

        /**
         * Adds a wait segment to the system.
         *
         * @param event The event
         * @return The builder
         */
        public Builder when(Event event) {
            return addSegment(event);
        }

        /**
         * Adds a wait segment to the system. Until the supplier returns true, the system will be blocked.
         *
         * @param supplier The supplier
         * @return The builder
         */
        public Builder ifTrue(BooleanSupplier supplier) {
            return when(new Event() {
                private boolean occurred = false;

                @Override
                public boolean hasOccurred() {
                    return occurred;
                }

                @Override
                public void execute() {
                    occurred = supplier.getAsBoolean();
                }
            });
        }

        /**
         * Adds a wait segment to the system that blocks the system for a given amount of steps.
         *
         * @param steps The amount of steps
         * @return The builder
         */
        public Builder delay(int steps) {
            return when(new Event() {
                private int count = 0;

                @Override
                public boolean hasOccurred() {
                    return count == steps;
                }

                @Override
                public void execute() {
                    count++;
                }
            });
        }

        /**
         * Performs an action
         *
         * @param action The action
         * @return The builder
         */
        public Builder doAction(Action action) {
            return addSegment(action);
        }

        /**
         * A workaround method to add support for lambda expressions.
         *
         * @param action The action
         * @return The builder
         */
        public Builder doAction(BooleanSupplier action) {
            return doAction(new Action() {
                @Override
                public boolean perform() {
                    return action.getAsBoolean();
                }
            });
        }

        /**
         * Adds segments that will be executed in parallel;
         *
         * @param segments The segments
         * @return The builder
         */
        public Builder parallel(Segment... segments) {
            return addSegment(new ParallelSegment(segments));
        }

        /**
         * Returns the system's main segment, which contains all added segments.
         *
         * @return The system's main segment
         */
        public Segment segment() {
            return segment;
        }

        /**
         * Builds the Simulator object.
         *
         * @return The Simulator
         */
        public Simulator build() {
            return new Simulator(segment);
        }

    }

    private final Segment segment;

    private Simulator(Segment segment) {
        this.segment = segment;
    }

    /**
     * Returns a sequential builder.
     *
     * @return A sequential builder
     */
    public static Builder builder() {
        return new Builder(new SequentialSegment());
    }

    /**
     * Returns a parallel builder
     *
     * @return A parallel builder
     */
    public static Builder parallelBuilder() {
        return new Builder(new ParallelSegment());
    }

    /**
     * Calling this method will execute the current segment.
     */
    public void step() {
        segment.execute();
    }

}
