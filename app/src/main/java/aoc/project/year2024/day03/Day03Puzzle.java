package aoc.project.year2024.day03;

import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day03Puzzle {
    public static void main(String[] args) {
        Day03Puzzle puzzle = new Day03Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2024, 3);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 03 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day03Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines, false);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 03 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day03Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public long doPart1(List<String> lines, boolean part2) {
        long sum = 0;

        boolean enabled = true;
        for (String line : lines) {
            for (int i = 0; i < line.length() - 6; i++) {
                if (line.substring(i, i + 4).equals("do()") && part2) {
                    enabled = true;
                } else if (line.substring(i, i + 7).equals("don't()") && part2) {
                    enabled = false;
                } else if (line.substring(i, i + 4).equals("mul(") && enabled) {

                    boolean valid = false;
                    boolean didFirstNumber = false;
                    String firstNumber = "";
                    String secondNumber = "";

                    for (int j = i + 4; j < line.length(); j++) {
                        char charAt = line.charAt(j);

                        if (charAt == ',') {
                            if (firstNumber.equals("")) {
                                valid = false;
                                break;
                            }
                            didFirstNumber = true;
                        } else if (charAt == ')') {
                            if (!firstNumber.equals("") && !secondNumber.equals("")) {
                                valid = true;
                                break;
                            }
                            valid = false;
                            break;
                        } else if (Character.isDigit(charAt)) {
                            if (!didFirstNumber) {
                                firstNumber += charAt;
                            } else {
                                secondNumber += charAt;
                            }
                        } else {
                            valid = false;
                            break;
                        }
                    }

                    // process

                    if (!valid) {
                        continue;
                    }

                    sum += Integer.parseInt(firstNumber) * Integer.parseInt(secondNumber);
                }
            }
        }

        return sum;
    }

    public long doPart2(List<String> lines) {
        // Part 2 code goes here
        return doPart1(lines, true);
    }

}
