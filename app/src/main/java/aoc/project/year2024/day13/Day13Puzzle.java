package aoc.project.year2024.day13;

import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day13Puzzle {
    public static void main(String[] args) {
        Day13Puzzle puzzle = new Day13Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2024, 13);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 13 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day13Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 13 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day13Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public long doPart1(List<String> lines) {
        // Part 1 code goes here

        int tokenCost = 0;

        while (lines.size() > 0) {
            int buttonAX = 0;
            int buttonAY = 0;

            int buttonBX = 0;
            int buttonBY = 0;

            int xGoal = 0;
            int yGoal = 0;

            String aString = lines.remove(0);
            String bString = lines.remove(0);
            String goalString = lines.remove(0);
            if (lines.size() > 0) { // clear last line
                lines.remove(0);
            }


            buttonAX = Integer.parseInt(aString.substring(aString.indexOf("X+") + 2, aString.lastIndexOf(",")));
            buttonAY = Integer.parseInt(aString.substring(aString.indexOf("Y+") + 2));
            
            buttonBX = Integer.parseInt(bString.substring(bString.indexOf("X+") + 2, bString.lastIndexOf(",")));
            buttonBY = Integer.parseInt(bString.substring(bString.indexOf("Y+") + 2));

            xGoal = Integer.parseInt(goalString.substring(goalString.indexOf("X") + 2, goalString.indexOf(",")));
            yGoal = Integer.parseInt(goalString.substring(goalString.indexOf("Y") + 2));

            int minValue = Integer.MAX_VALUE;

            for (int a = 0; a <= 100; a++) {
                for (int b = 0; b <= 100; b++) {
                    if (buttonAX * a + buttonBX * b == xGoal && buttonAY * a + buttonBY * b == yGoal) {
                        int cost = a * 3 + b;

                        if (cost < minValue) {
                            minValue = cost;
                        }
                    }
                }

            }

            if (minValue == Integer.MAX_VALUE) {
                continue;
            }

            tokenCost += minValue;
        }
        return tokenCost;
    }


    public long doPart2(List<String> lines) {
        // Part 1 code goes here

        long tokenCost = 0;

        while (lines.size() > 0) {
            double buttonAX = 0;
            double buttonAY = 0;

            double buttonBX = 0;
            double buttonBY = 0;

            double xGoal = 0;
            double yGoal = 0;

            String aString = lines.remove(0);
            String bString = lines.remove(0);
            String goalString = lines.remove(0);
            if (lines.size() > 0) { // clear last line
                lines.remove(0);
            }


            buttonAX = Integer.parseInt(aString.substring(aString.indexOf("X+") + 2, aString.lastIndexOf(","))) / 3.0;
            buttonAY = Integer.parseInt(aString.substring(aString.indexOf("Y+") + 2)) / 3.0;
            
            buttonBX = Integer.parseInt(bString.substring(bString.indexOf("X+") + 2, bString.lastIndexOf(",")));
            buttonBY = Integer.parseInt(bString.substring(bString.indexOf("Y+") + 2));

            xGoal = Integer.parseInt(goalString.substring(goalString.indexOf("X") + 2, goalString.indexOf(","))) + 10000000000000L;
            yGoal = Integer.parseInt(goalString.substring(goalString.indexOf("Y") + 2)) + 10000000000000L;

            double bNumerator = buttonAX * yGoal - buttonAY * xGoal;
            double bDenominator = buttonAX * buttonBY - buttonAY * buttonBX;

            double aNumerator = buttonBX * yGoal - buttonBY * xGoal;
            double aDenominator = buttonBX * buttonAY - buttonBY * buttonAX;

            if (bDenominator == 0 || aDenominator == 0) {
                // System.out.println("skip");
                continue;
            }

            double aPresses = (aNumerator / aDenominator);
            double bPresses = (bNumerator / bDenominator);

            if (aPresses < 0 || bPresses < 0) {
                continue;
            }

            // System.out.println();
            // System.out.println(aPresses);
            // System.out.println(bPresses);

            if (Math.abs(Math.round(bPresses) - bPresses) > 0.01 || Math.abs(Math.round(aPresses) - aPresses) > 0.01) {
                continue;
            }

            aPresses = Math.round(aPresses);
            bPresses = Math.round(bPresses);
            
            tokenCost += aPresses + bPresses;
        }
        return tokenCost;
    }

}
