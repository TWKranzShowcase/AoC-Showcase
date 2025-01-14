package aoc.project.year2024.day01;

import java.util.ArrayList;
import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day01Puzzle {
    public static void main(String[] args) {
        Day01Puzzle puzzle = new Day01Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2024, 1);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 01 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day01Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 01 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day01Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public long doPart1(List<String> lines) {
        // Part 1 code goes here

        ArrayList<Integer> firstValues = new ArrayList<>();
        ArrayList<Integer> secondValues = new ArrayList<>();

        for (String line : lines) {
            int firstNumber = Integer.parseInt(line.substring(0, line.indexOf(" ")));
            int secondNumber = Integer.parseInt(line.substring(line.lastIndexOf(" ") + 1));

            firstValues.add(firstNumber);
            secondValues.add(secondNumber);
        }

        firstValues.sort(null);
        secondValues.sort(null);

        int tally = 0;
        for (int i = 0; i < firstValues.size(); i++) {
            tally += Math.abs(firstValues.get(i) - secondValues.get(i));
        }


        return tally;
    }

    public long doPart2(List<String> lines) {
        ArrayList<Integer> firstValues = new ArrayList<>();
        ArrayList<Integer> secondValues = new ArrayList<>();

        for (String line : lines) {
            int firstNumber = Integer.parseInt(line.substring(0, line.indexOf(" ")));
            int secondNumber = Integer.parseInt(line.substring(line.lastIndexOf(" ") + 1));

            firstValues.add(firstNumber);
            secondValues.add(secondNumber);
        }

        firstValues.sort(null);
        secondValues.sort(null);

        int tally = 0;
        for (int i = 0; i < firstValues.size(); i++) {
            int leftValue = firstValues.get(i);
            int copiesInRight = 0;

            for (int value : secondValues) {
                if (value == leftValue) {
                    copiesInRight++;
                }
            }
            tally += copiesInRight * leftValue;
        }


        return tally;
    }

}
