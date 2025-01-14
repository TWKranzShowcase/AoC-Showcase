package aoc.project.year2023.day20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class RadioModule {
    protected String[] sendTo;
    protected String label;
    protected final Day20Puzzle day20Puzzle;
    protected HashMap<RadioModule, Boolean> inputs;

    public RadioModule(String[] sendTo, String label, Day20Puzzle day20Puzzle) {
        this.sendTo = sendTo;
        this.label = label;
        this.day20Puzzle = day20Puzzle;
        inputs = new HashMap<>();
    }

    public void recieveSignal(boolean high, RadioModule from) {

    }

    public String[] getSendTo() {
        return sendTo;
    }

    public String getLabel() {
        return label;
    }

    public void reset() {

    }

    public void fetchInputs() {
        for (Entry<String, RadioModule> entry : this.day20Puzzle.radioMap.entrySet()) {
            RadioModule module = entry.getValue();
            if (this.day20Puzzle.inList(module.getSendTo(), label)) {
                inputs.put(module, false);
            }
        }
    }

    public ArrayList<RadioModule> getInputs() {
        ArrayList<RadioModule> returnInputs = new ArrayList<>();
        for (Entry<RadioModule, Boolean> entry : inputs.entrySet()) {
            returnInputs.add(entry.getKey());
        }
        return returnInputs;
    }
}