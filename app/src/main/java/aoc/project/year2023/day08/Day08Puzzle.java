package aoc.project.year2023.day08;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day08Puzzle {

    private int totalSteps = 0;
    private long lcm = 1;

    public static void main(String[] args) {
        Day08Puzzle puzzle = new Day08Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 8);

        // puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 08 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day08Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 08 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day08Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public String followMazeWithInstructions(ArrayList<Boolean> steps, Hashtable<String, String> maze,
            String currentStep) {
        for (int i = 0; i < steps.size(); i++) {
            System.out.println(currentStep);
            totalSteps++;
            boolean stepDirection = steps.get(i);

            String choices = maze.get(currentStep);

            String choiceLeft = choices.substring(0, 3);
            String choiceRight = choices.substring(3, 6);

            if (stepDirection) {
                currentStep = choiceLeft;
            } else {
                currentStep = choiceRight;
            }

            if (currentStep.equals("ZZZ")) {
                return currentStep;
            }
        }
        return currentStep;
    }

    public long getStepsForAMazeToGetZ(ArrayList<Boolean> stepList, Hashtable<String, String> maze,
            String currentStep) {

        long totalSteps = 0;
        int currentStepDirection = 0;

        while (currentStep.charAt(2) != 'Z') {
            boolean stepDirection = stepList.get(currentStepDirection);
            currentStepDirection++;
            if (currentStepDirection == stepList.size()) {
                currentStepDirection = 0;
            }

            String choices = maze.get(currentStep);

            String choiceLeft = choices.substring(0, 3);
            String choiceRight = choices.substring(3, 6);

            if (stepDirection) {
                currentStep = choiceLeft;
            } else {
                currentStep = choiceRight;
            }
            totalSteps++;
        }

        return totalSteps;
    }

    public long doPart1(List<String> lines) {
        // Part 1 code goes here
        ArrayList<Boolean> steps = new ArrayList<>();
        String stepString = lines.get(0);
        for (int i = 0; i < stepString.length(); i++) {
            char step = stepString.charAt(i);
            if (step == 'L') {
                steps.add(true);
            } else {
                steps.add(false);
            }
        }

        Hashtable<String, String> maze = new Hashtable<>();

        for (int i = 2; i < lines.size(); i++) {
            String key = lines.get(i);

            String start = key.substring(0, 3);

            int startOfPathOne = key.indexOf("(");
            String paths = key.substring(startOfPathOne + 1, startOfPathOne + 4);
            int startOfPathTwo = key.indexOf(",");
            paths += key.substring(startOfPathTwo + 2, startOfPathTwo + 5);

            maze.put(start, paths);
        }

        String startStep = "AAA";

        while (!startStep.equals("ZZZ")) {
            startStep = followMazeWithInstructions(steps, maze, startStep);
        }

        return totalSteps;
    }

    public long doPart2(List<String> lines) {
        ArrayList<Boolean> steps = new ArrayList<>();
        String stepString = lines.get(0);
        for (int i = 0; i < stepString.length(); i++) {
            char step = stepString.charAt(i);
            if (step == 'L') {
                steps.add(true);
            } else {
                steps.add(false);
            }
        }

        ArrayList<String> currentSteps = new ArrayList<>();

        Hashtable<String, String> maze = new Hashtable<>();
        for (int i = 2; i < lines.size(); i++) {
            String key = lines.get(i);

            String start = key.substring(0, 3);

            if (start.charAt(2) == 'A') {
                currentSteps.add(start);
            }

            int startOfPathOne = key.indexOf("(");
            String paths = key.substring(startOfPathOne + 1, startOfPathOne + 4);
            int startOfPathTwo = key.indexOf(",");
            paths += key.substring(startOfPathTwo + 2, startOfPathTwo + 5);

            maze.put(start, paths);
        }

        ArrayList<Long> stepsPerPath = new ArrayList<>();

        for (int i = 0; i < currentSteps.size(); i++) {
            stepsPerPath.add(getStepsForAMazeToGetZ(steps, maze, currentSteps.get(i)));
        }

        // get largest number
        long largest = Long.MIN_VALUE;

        for (int i = 0; i < stepsPerPath.size(); i++) {
            long stepLength = stepsPerPath.get(i);
            if (stepLength > largest) {
                largest = stepLength;
            }
        }

        // get the factors

        @SuppressWarnings({ "unchecked", "rawtypes" })
        Hashtable<Long, Long> commonFactors = new Hashtable();
        for (int i = 0; i < stepsPerPath.size(); i++) {
            long stepLength = stepsPerPath.get(i);
            for (long possibleFactor = 2; possibleFactor <= stepLength; possibleFactor++) {
                if (stepLength % possibleFactor == 0) {
                    long instances = 0;
                    while (stepLength % possibleFactor == 0) {
                        instances++;
                        stepLength /= possibleFactor;
                    }

                    if (commonFactors.containsKey(possibleFactor) && commonFactors.get(possibleFactor) > instances) {
                        commonFactors.put(possibleFactor, instances);
                    } else if (!commonFactors.containsKey(possibleFactor)) {
                        commonFactors.put(possibleFactor, instances);
                    }
                }
            }
        }

        // create a basic lcm
        commonFactors.forEach((factor, power) -> {
            System.out.println("factor: " + factor);
            System.out.println("power: " + power);
            lcm *= Math.pow(factor, power);
        });

        // divide out common factors

        // String startStep = "AAA";
        // while (!startStep.equals("ZZZ")) {
        // startStep = followMazeWithInstructions(steps, maze, startStep);
        // }

        return lcm;
    }

}
