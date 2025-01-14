package aoc.project.year2024.day02;

import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day02Puzzle {
    public static void main(String[] args) {
        Day02Puzzle puzzle = new Day02Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2024, 2);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 02 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day02Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 02 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day02Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    enum TimelineType {
        UNDECIDED,
        INCREASING,
        DECREASING
    }

    public long doPart1(List<String> lines) {
        // Part 1 code goes here

        int safeCount = 0;
        for (String report : lines) {
            String[] splitReport = report.split(" ");

            boolean isSafe = true;
            TimelineType sequenceType = TimelineType.UNDECIDED;

            int lastNumber = -1; // arbitary constant
            for (String splitNumber : splitReport) {
                int number = Integer.parseInt(splitNumber);

                if (lastNumber != -1) {
                    if (lastNumber == number || Math.abs(lastNumber - number) > 3) {
                        isSafe = false;
                        break;
                    }

                    if (lastNumber > number) {
                        if (sequenceType == TimelineType.INCREASING) {
                            isSafe = false;
                            break;
                        }
                        sequenceType = TimelineType.DECREASING;
                    } else {
                        if (sequenceType == TimelineType.DECREASING) {
                            isSafe = false;
                            break;
                        }
                        sequenceType = TimelineType.INCREASING;
                    }
                }

                lastNumber = number;
            }

            if (isSafe) {
                safeCount++;
            }
        }
        return safeCount;
    }

    public long doPart2(List<String> lines) {
        int safeCount = 0;
        for (String report : lines) {
            String[] splitReport = report.split(" ");

            boolean isSafe = false;

            for (int i = 0; i < splitReport.length; i++) {
                boolean safePath = true;
                int lastNumber = -1; // arbitrary constant
                TimelineType sequenceType = TimelineType.UNDECIDED;

                for (int j = 0; j < splitReport.length; j++) {
                    if (i == j) {
                        continue;
                    }
                    int number = Integer.parseInt(splitReport[j]);

                    if (lastNumber != -1) {
                        if (lastNumber == number || Math.abs(lastNumber - number) > 3) {
                            safePath = false;
                            break;
                        }

                        if (lastNumber > number) {
                            if (sequenceType == TimelineType.INCREASING) {
                                safePath = false;
                                break;
                            }
                            sequenceType = TimelineType.DECREASING;
                        } else {
                            if (sequenceType == TimelineType.DECREASING) {
                                safePath = false;
                                break;
                            }
                            sequenceType = TimelineType.INCREASING;
                        }
                    }

                    lastNumber = number;
                }
                System.out.println(safePath);
                if (safePath) {
                    isSafe = true;
                }
            }
            System.out.println();

            if (isSafe) {
                safeCount++;
            }
        }
        return safeCount;
    }

}
