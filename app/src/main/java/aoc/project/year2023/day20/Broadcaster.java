package aoc.project.year2023.day20;

public class Broadcaster extends RadioModule {

    public Broadcaster(Day20Puzzle day20Puzzle, String[] sendTo, String label) {
        super(sendTo, label, day20Puzzle);
    }

    @Override
    public void recieveSignal(boolean high, RadioModule from) {
        this.day20Puzzle.queSignal(new Signal(this, sendTo, high));
    }
}