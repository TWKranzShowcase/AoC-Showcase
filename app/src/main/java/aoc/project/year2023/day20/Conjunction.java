package aoc.project.year2023.day20;

import java.util.Map.Entry;

public class Conjunction extends RadioModule {
    /**
     *
     */

    public Conjunction(Day20Puzzle day20Puzzle, String[] sendTo, String label) {
        super(sendTo, label, day20Puzzle);
    }

    @Override
    public void reset() {
        for (Entry<RadioModule, Boolean> entry : inputs.entrySet()) {
            inputs.put(entry.getKey(), false);
        }
    }

    @Override
    public void recieveSignal(boolean high, RadioModule from) {
        inputs.put(from, high);

        boolean allHigh = true;
        for (Entry<RadioModule, Boolean> entry : inputs.entrySet()) {
            // System.out.println(label + ": " + entry.getKey().getLabel() + "-" +
            // entry.getValue());
            if (!entry.getValue()) {
                allHigh = false;
                break;
            }
        }
        // System.out.println();

        this.day20Puzzle.queSignal(new Signal(this, sendTo, !allHigh));
    }
}