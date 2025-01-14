package aoc.project.year2023.day20;

public class FlipflopModule extends RadioModule {
    /**
     *
     */
    private boolean charged;

    public FlipflopModule(Day20Puzzle day20Puzzle, String[] sendTo, String label) {
        super(sendTo, label, day20Puzzle);
        charged = false;
    }

    @Override
    public void recieveSignal(boolean high, RadioModule from) {
        if (high) {
            return;
        }

        charged = !charged;
        this.day20Puzzle.queSignal(new Signal(this, sendTo, charged));
    }

    @Override
    public void reset() {
        charged = false;
    }
}