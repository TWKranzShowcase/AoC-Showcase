package aoc.project.year2023.day05;


public class NumberRange {
    private long startOfRange;
    private long endOfRange;
    private boolean marked = false;

    public NumberRange(long startOfRange, long endOfRange) {
        if (startOfRange > endOfRange) {
            this.startOfRange = endOfRange;
            this.endOfRange = startOfRange;
        } else {
            this.startOfRange = startOfRange;
            this.endOfRange = endOfRange;
        }
    }

    public void performAddition(long start, long end) {
        startOfRange += start;
        endOfRange += end;
    }

    public long getStart() {
        return startOfRange;
    }

    public long getEnd() {
        return endOfRange;
    }

    public void mark() {
        marked = true;
    }

    public void demark() {
        marked = true;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setStartAndEnd(long newStart, long newEnd) {
        startOfRange = newStart;
        endOfRange = newEnd;
    }

    public boolean isValidRange() {
        return startOfRange > 0 && endOfRange > 0 && endOfRange >= startOfRange;
    }

    public boolean isLargerThanRange(NumberRange otherRange) {
        return endOfRange >= otherRange.getEnd();
    }

    public boolean isIntersectingRange(NumberRange secondRange) {
        // return startOfRange <= secondRange.getEnd() && secondRange.getStart() <= endOfRange || endOfRange <= secondRange.getStart() && secondRange.getEnd() <= startOfRange;
        return Math.min(endOfRange, secondRange.getEnd()) >= Math.max(startOfRange, secondRange.getStart());
    }

    public NumberRange mergeRanges(NumberRange secondRange) {
        return new NumberRange(Math.min(startOfRange, secondRange.getStart()), Math.max(endOfRange, secondRange.getEnd()));
    }

    public NumberRange getIntersection(NumberRange secondRange) {
        return new NumberRange(Math.max(startOfRange, secondRange.getStart()), Math.min(endOfRange, secondRange.getEnd()));
    }

    public long getLength() {
        return endOfRange - startOfRange;
    }
}
