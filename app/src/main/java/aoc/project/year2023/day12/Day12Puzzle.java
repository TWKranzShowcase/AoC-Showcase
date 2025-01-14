package aoc.project.year2023.day12;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day12Puzzle {
    private Hashtable<ArrayAndCombinations, Long> solvedSolutions;

    public static void main(String[] args) {
        Day12Puzzle puzzle = new Day12Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 12);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 12 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day12Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 12 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day12Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    enum SpringType {
        FUNCTIONAL,
        BROKEN,
        UNKNOWN,
    }

    public ArrayList<Integer> getSolutionSet(List<SpringType> springArray) {
        ArrayList<Integer> newCombinations = new ArrayList<>();
        int currentRowSize = 0;
        for (int i = 0; i < springArray.size(); i++) {
            SpringType springAt = springArray.get(i);
            if (springAt == SpringType.BROKEN) {
                currentRowSize++;
                if (i == springArray.size() - 1) {
                    newCombinations.add(currentRowSize);
                }
            } else if (springAt == SpringType.FUNCTIONAL && currentRowSize > 0) {
                newCombinations.add(currentRowSize);
                currentRowSize = 0;
            }
        }
        return newCombinations;
    }

    public long markSet(ArrayAndCombinations pickRecord, long sum) {
        solvedSolutions.put(pickRecord, sum);
        return sum;
    }

    public long recurse(List<SpringType> springArray, List<Integer> validCombinations) {
        // create a record of the current set of springs and the valid combinations
        ArrayAndCombinations set = new ArrayAndCombinations(new ArrayList<>(springArray), validCombinations);

        // check if we did this calculation already, if so return that calculation
        if (solvedSolutions.containsKey(set)) {
            return solvedSolutions.get(set);
        }

        long sum = 0;

        // check if there are any unknown springs in this list
        if (!springArray.contains(SpringType.UNKNOWN)) {
            // get the solutions of the remaining set of springs
            ArrayList<Integer> newCombinations = getSolutionSet(springArray);

            // if the solutions arent the same size then this arangement isnt a solution
            if (validCombinations.size() != newCombinations.size()) {
                return markSet(set, 0);
            }

            // check if same solutions, mark as valid combination if true
            if (validCombinations.equals(newCombinations)) {
                return markSet(set, 1);
            } else {
                return markSet(set, 0);
            }
        }
        // create new arrays to send, we dont want to modify our starting ones
        ArrayList<SpringType> sendArray = new ArrayList<>(springArray);
        ArrayList<Integer> sendSolutions = new ArrayList<>(validCombinations);

        // get location of unknown spring
        int springLocation = springArray.indexOf(SpringType.UNKNOWN);

        // get the last functional spring, this is the cutoff for springs we can safely
        // remove
        int lastDot = springArray.subList(0, springLocation).lastIndexOf(SpringType.FUNCTIONAL);

        // check if there is a dot, don't do anything if we dont have one
        if (lastDot >= 0) {
            // remove safe to remove springs from the area we will be sending to the next call of recurse
            sendArray.subList(0, lastDot).clear();

            // get new location for the spring to modify
            springLocation = sendArray.indexOf(SpringType.UNKNOWN);

            // grab the solutions to this removed area
            List<Integer> discardSolutions = getSolutionSet(springArray.subList(0, lastDot));

            // check to ensure that solutions to the removed area arent greater than the
            // actual solutions left
            if (discardSolutions.size() > validCombinations.size()) {
                return markSet(set, 0);
            }

            // check to ensure that all of the solutions from the discarded section are
            // present in the new solutions
            for (int i = 0; i < discardSolutions.size(); i++) {
                // the array solutions differ, this must be an invalid arangement for the
                // springs
                if (discardSolutions.get(i) != validCombinations.get(i)) {
                    return markSet(set, 0);
                }
            }

            // remove already fulfilled solutions from the valid solutions
            sendSolutions.subList(0, discardSolutions.size()).clear();
        }

        // recurse by testing a broken spring
        sendArray.set(springLocation, SpringType.BROKEN);
        sum += recurse(sendArray, sendSolutions);

        // recurse by testing a functional spring
        sendArray.set(springLocation, SpringType.FUNCTIONAL);
        sum += recurse(sendArray, sendSolutions);

        // return result of both recursions
        return markSet(set, sum);
    }

    public long doPart1(List<String> lines) {
        solvedSolutions = new Hashtable<>();
        // Part 1 code goes here
        int sum = 0;

        for (String line : lines) {
            int combinationListIndex = line.indexOf(" ");

            ArrayList<SpringType> springArray = new ArrayList<>();

            for (int i = 0; i < combinationListIndex; i++) {
                char charAt = line.charAt(i);
                switch (charAt) {
                    case '.':
                        springArray.add(SpringType.FUNCTIONAL);
                        break;

                    case '#':
                        springArray.add(SpringType.BROKEN);
                        break;

                    case '?':
                        springArray.add(SpringType.UNKNOWN);
                        break;
                }
            }

            ArrayList<Integer> combinations = new ArrayList<>();
            for (int i = combinationListIndex + 1; i < line.length(); i++) {
                char lastChar = line.charAt(i - 1);
                if (lastChar == ' ' || lastChar == ',') {
                    combinations.add(AocUtil.parseNumberInt(line, i));
                }
            }

            sum += recurse(springArray, combinations);
            solvedSolutions.clear();
        }

        return sum;
    }

    // full disclosure, I needed to look at a solution in order to understand how to write this section. 
    public long doPart2(List<String> lines) {
        solvedSolutions = new Hashtable<>();
        long sum = 0;

        for (String line : lines) {
            int combinationListIndex = line.indexOf(" ");

            ArrayList<SpringType> springArray = new ArrayList<>();
            for (int i = 0; i < combinationListIndex; i++) {
                char charAt = line.charAt(i);
                switch (charAt) {
                    case '.':
                        springArray.add(SpringType.FUNCTIONAL);
                        break;

                    case '#':
                        springArray.add(SpringType.BROKEN);
                        break;

                    case '?':
                        springArray.add(SpringType.UNKNOWN);
                        break;
                }
            }

            ArrayList<Integer> combinations = new ArrayList<>();
            for (int i = combinationListIndex + 1; i < line.length(); i++) {
                char lastChar = line.charAt(i - 1);
                if (lastChar == ' ' || lastChar == ',') {
                    combinations.add(AocUtil.parseNumberInt(line, i));
                }
            }

            ArrayList<SpringType> fullSpringArray = new ArrayList<>();
            fullSpringArray.addAll(springArray);
            for (int i = 0; i < 4; i++) {
                fullSpringArray.add(SpringType.UNKNOWN);
                fullSpringArray.addAll(springArray);
            }

            ArrayList<Integer> fullCombinations = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                fullCombinations.addAll(combinations);
            }

            sum += recurse(fullSpringArray, fullCombinations);
            solvedSolutions.clear();
        }

        return sum;
    }

}
