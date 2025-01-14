package aoc.project.year2024.day11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
// import java.util.Map;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day11Puzzle {

    private HashMap<StoneRecord, Long> cache;
    public static void main(String[] args) {
        Day11Puzzle puzzle = new Day11Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2024, 11);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 11 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day11Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 11 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day11Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public long doPart1(List<String> lines) {

        long sum = 0;
        int iterations = 25;

        for (String stoneString : lines) {
            String[] stoneSplit = stoneString.split(" ");

            for (String toParse : stoneSplit) {
                ArrayList<Long> stoneList = new ArrayList<>();
                stoneList.add(Long.parseLong(toParse));

                for (int i = 0; i < iterations; i++) {
                    ArrayList<Long> newStones = new ArrayList<>();

                    for (Long stone : stoneList) {
                        if (stone == 0) {
                            newStones.add(1L);
                            continue;
                        }

                        long digits = 0;
                        long testStone = stone;

                        while (testStone > 0) {
                            testStone /= 10;
                            digits++;
                        }

                        if (digits % 2 == 0) {
                            long splitOne = stone / (long) Math.pow(10, digits / 2);
                            long splitTwo = stone % (long) Math.pow(10, digits / 2);

                            newStones.add(splitOne);
                            newStones.add(splitTwo);
                        } else {
                            newStones.add(stone * 2024);
                        }
                    }
                    stoneList = newStones;
                }
                sum += stoneList.size();
            }
        }
        // Part 1 code goes here
        return sum;
    }

    record StoneRecord(long stone, int iteration) {
        
    }

    public long runStone(long stone, int iterationsLeft, long sum) {  
        StoneRecord record = new StoneRecord(stone, iterationsLeft);

        if (cache.containsKey(record)) {
            return cache.get(record);
        }

        if (iterationsLeft == 0) {
            return 1;
        }
        
        if (stone == 0) {
            sum += runStone(1, iterationsLeft - 1, 0);
            return sum;
        }

        long digits = 0;
        long testStone = stone;

        while (testStone > 0) {
            testStone /= 10;
            digits++;
        }

        if (digits % 2 == 0) {
            long splitOne = stone / (long) Math.pow(10, digits / 2);
            long splitTwo = stone % (long) Math.pow(10, digits / 2);

            sum += runStone(splitOne, iterationsLeft - 1, 0);
            sum += runStone(splitTwo, iterationsLeft - 1, 0);
        } else {
            sum += runStone(stone * 2024, iterationsLeft - 1, 0);
        }

        cache.put(record, sum);
        return sum;
    }

    public long doPart2(List<String> lines) {
        long sum = 0;
        int iterations = 75;

        cache = new HashMap<>();

        for (String stoneString : lines) {
            String[] stoneSplit = stoneString.split(" ");

            for (String toParse : stoneSplit) {
                sum += runStone(Long.parseLong(toParse), iterations, 0);
            }
        }
        // Part 1 code goes here
        return sum;
    }

}
