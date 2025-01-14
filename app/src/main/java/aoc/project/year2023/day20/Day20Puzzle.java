package aoc.project.year2023.day20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day20Puzzle {
    HashMap<String, RadioModule> radioMap;
    private ArrayList<Signal> signalQue;
    private int lowSingals;
    private int highSignals;

    public static void main(String[] args) {
        Day20Puzzle puzzle = new Day20Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 20);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 20 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day20Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 20 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day20Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public boolean inList(String[] search, String label) {
        for (String element : search) {
            if (element.equals(label)) {
                return true;
            }
        }

        return false;
    }

    public void queSignal(Signal signal) {
        signalQue.add(signal);
    }

    public void sendSignal(Signal signal) {
        RadioModule sender = signal.sender();
        String[] toSend = signal.toSend();
        boolean high = signal.high();
        // System.out.println(high + "-" + toSend.length);
        for (String signalKey : toSend) {
            // System.out.println(sender.getLabel() + " -(" + high + ")-> " + signalKey);
            if (radioMap.containsKey(signalKey)) {
                radioMap.get(signalKey).recieveSignal(high, sender);
            }
        }

        if (high) {
            highSignals += toSend.length;
        } else {
            lowSingals += toSend.length;
        }
    }

    public long doPart1(List<String> lines) {
        // Part 1 code goes here
        radioMap = new HashMap<>();
        signalQue = new ArrayList<>();
        lowSingals = 0;
        highSignals = 0;

        Broadcaster broadcaster = new Broadcaster(this, null, null); // placeholder values

        for (String module : lines) {
            RadioModule createdModule;

            String[] sendStrings = module.substring(module.indexOf(">") + 2, module.length()).split(", ");
            String label = module.substring(1, module.indexOf(" "));
            char typeChar = module.charAt(0);

            switch (typeChar) {
                case '%':
                    createdModule = new FlipflopModule(this, sendStrings, label);
                    break;

                case '&':
                    createdModule = new Conjunction(this, sendStrings, label);
                    break;

                case 'b':
                    label = "broadcaster";
                    Broadcaster createdCaster = new Broadcaster(this, sendStrings, label);
                    broadcaster = createdCaster;
                    createdModule = broadcaster;
                    break;

                default:
                    System.err.print("Invalid radio module created.");
                    createdModule = new RadioModule(sendStrings, null, this);
                    break;
            }
            radioMap.put(label, createdModule);
        }

        for (Entry<String, RadioModule> entry : radioMap.entrySet()) {
            if (entry.getValue().getClass().hashCode() == Conjunction.class.hashCode()) {
                Conjunction conjunctionModule = (Conjunction) entry.getValue();
                conjunctionModule.fetchInputs();
            }
        }

        String[] broadcastList = { broadcaster.getLabel() };
        RadioModule button = new RadioModule(broadcastList, "button", this);
        radioMap.put("button", button);

        for (int i = 0; i < 1000; i++) {
            queSignal(new Signal(button, broadcastList, false));
            // System.out.println("high: " + highSignals);
            // System.out.println("low: " + lowSingals);
            // System.out.println();

            // highSignals = 0;
            // lowSingals = 0;

            while (signalQue.size() > 0) {
                sendSignal(signalQue.remove(0));
            }
        }

        // System.out.println("high: " + highSignals);
        // System.out.println("low: " + lowSingals);
        return highSignals * lowSingals;
    }

    public void resetRadios() {
        signalQue = new ArrayList<>();
        for (Entry<String, RadioModule> entry : radioMap.entrySet()) {
            RadioModule module = entry.getValue();
            module.reset();
        }
    }

    public long bruteForceActivationTime(RadioModule radio, Broadcaster cast, boolean highOrLow, boolean reset) {
        if (radio.getClass().equals(Broadcaster.class)) {
            return 1;
        }
        long count = 0;

        boolean done = false;

        while (!done) {
            if (count > 10000) {
                count = 0;
                break;
            }
            count++;
            queSignal(new Signal(cast, cast.getSendTo(), false));

            while (signalQue.size() > 0) {
                Signal toSend = signalQue.remove(0);
                sendSignal(toSend);
                if (toSend.sender().equals(radio) && toSend.high() == highOrLow) {
                    done = true;
                    break;
                }
            }
        }
        if (reset) {
            resetRadios();
        }
        // System.out.println(count);

        return count;
    }

    public long doPart2(List<String> lines) {
        // Part 1 code goes here
        radioMap = new HashMap<>();
        signalQue = new ArrayList<>();
        lowSingals = 0;
        highSignals = 0;

        Broadcaster broadcaster = new Broadcaster(this, null, null); // placeholder values

        for (String module : lines) {
            RadioModule createdModule;

            String[] sendStrings = module.substring(module.indexOf(">") + 2, module.length()).split(", ");
            String label = module.substring(1, module.indexOf(" "));
            char typeChar = module.charAt(0);

            switch (typeChar) {
                case '%':
                    createdModule = new FlipflopModule(this, sendStrings, label);
                    break;

                case '&':
                    createdModule = new Conjunction(this, sendStrings, label);
                    break;

                case 'b':
                    label = "broadcaster";
                    Broadcaster createdCaster = new Broadcaster(this, sendStrings, label);
                    broadcaster = createdCaster;
                    createdModule = broadcaster;
                    break;

                default:
                    System.err.print("Invalid radio module created.");
                    createdModule = new RadioModule(sendStrings, null, this);
                    break;
            }

            radioMap.put(label, createdModule);
        }

        String[] broadcastList = { broadcaster.getLabel() };
        RadioModule button = new RadioModule(broadcastList, "button", this);
        radioMap.put("button", button);

        RadioModule finalConjuction = new Conjunction(this, broadcastList, null); // placeholder
        for (Entry<String, RadioModule> entry : radioMap.entrySet()) {
            RadioModule conjunctionModule = entry.getValue();
            conjunctionModule.fetchInputs();

            if (inList(conjunctionModule.getSendTo(), "rx")) {
                finalConjuction = conjunctionModule;
                System.out.println("final: " + finalConjuction.getLabel());
            }
        }
        System.out.println();

        // for (Entry<String, RadioModule> entry : radioMap.entrySet()) {
        //     RadioModule conjModule = entry.getValue();
        //     System.out.println(conjModule.getLabel() + "-low: " + bruteForceActivationTime(conjModule, broadcaster, false));
        //     System.out.println(conjModule.getLabel() + "-high: " + bruteForceActivationTime(conjModule, broadcaster, true));
        // }

        ArrayList<ArrayList<Long>> slopes = new ArrayList<>();

        long sum = 1;
        for (RadioModule mod : finalConjuction.getInputs()) {
            System.out.print(mod.getLabel() + "-high: ");
            int prev = 0;
            for (int i = 0; i < 5; i++) {
                prev += bruteForceActivationTime(mod, broadcaster, true, i == 4);
                System.out.print(prev + ", ");
            }
            System.out.println();

            ArrayList<Long> thisSlopes = new ArrayList<>();
            slopes.add(thisSlopes);

            System.out.print(mod.getLabel() + "-highdiff: ");
            for (int i = 0; i < 5; i++) {
                prev = (int) bruteForceActivationTime(mod, broadcaster, true, i == 4);
                System.out.print(prev + ", ");
            }
            System.out.println();
            System.out.println();

            resetRadios();
            sum *= bruteForceActivationTime(mod, broadcaster, true, true);
        }

        return sum;
    }

}
