package aoc.project.year2024.day07;

import java.util.ArrayList;
import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day07Puzzle {
    private boolean checkSolved;
    public static void main(String[] args) {
        Day07Puzzle puzzle = new Day07Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2024, 7);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 07 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day07Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 07 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day07Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public long recurse(long result, ArrayList<Long> values, int index, long hold) {
        if (values.size() == index) {
            if (result == hold && !checkSolved) {
                checkSolved = true;
                return result;
            } else {
                return 0;
            }
        }
        
        long secondElement = values.get(index);

        long concatHold = Long.parseLong("" + hold + secondElement);
        // System.out.println(concatHold);

        hold = recurse(result, values, index + 1, hold * secondElement) + recurse(result, values, index + 1, hold + secondElement) + recurse(result, values, index + 1, concatHold);
        return hold;
    }

    public long doPart1(List<String> lines) {
        long total = 0;

        for (String line : lines) {
            long desiredResult = Long.parseLong(line.substring(0, line.indexOf(":")));
            
            String[] toParse = line.substring(line.indexOf(":") + 2).split(" ");

            ArrayList<Long> values = new ArrayList<>();
            for (String parse : toParse) {
                values.add(Long.parseLong(parse));
            }

            long val = recurse(desiredResult, values, 1, values.get(0));
            System.out.println(val);
            total += val;
            checkSolved = false;
        }
        // Part 1 code goes here
        return total;
    }

    public long doPart2(List<String> lines) {
        // Part 2 code goes here
        return -1;
    }

}
