package aoc.project.year2024.day09;

import java.util.ArrayList;
import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day09Puzzle {
    public static void main(String[] args) {
        Day09Puzzle puzzle = new Day09Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2024, 9);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 09 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day09Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 09 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day09Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public long doPart1(List<String> lines) {
        long total = 0;

        for (String line : lines) {
            long id = 0;
            boolean disk = true;

            ArrayList<String> parsed = new ArrayList<>();

            for (int i = 0; i < line.length(); i++) {
                int valAt = Integer.parseInt(line.substring(i, i + 1));

                if (disk) {
                    for (int j = 0; j < valAt; j++) {
                        parsed.add(Long.toString(id));
                    }
                    id++;
                    disk = false;
                } else {
                    for (int j = 0; j < valAt; j++) {
                        parsed.add(".");
                    }
                    disk = true;
                }
            }

            boolean madeChanges = true;
            while (madeChanges) {
                madeChanges = false;

                String moveTarget = "";
                int checkIndex = -1;

                for (int i = parsed.size() - 1; i >= 0; i--) {
                    String check = parsed.get(i);
                    
                    if (!check.equals(".") && moveTarget.equals("")) {
                        checkIndex = i;
                        moveTarget = check;
                        break;
                    }
                }

                for (int i = 0; i < checkIndex; i++) {
                    String check = parsed.get(i);

                    if (check.equals(".") && !moveTarget.equals("")) {
                        parsed.set(i, moveTarget);
                        parsed.set(checkIndex, ".");
                        madeChanges = true;
                        break;
                    }
                } 
            }

            for (int i = 0; i < parsed.size(); i++) {
                String el = parsed.get(i);
                // System.out.print(el);
                if (el.equals(".")) {
                    break;
                }
                total += Long.parseLong(parsed.get(i)) * i;
            }
        }
        // Part 1 code goes here
        return total;
    }
    
    public long doPart2(List<String> lines) {
        long total = 0;

        for (String line : lines) {
            long id = 0;
            boolean disk = true;


            ArrayList<String> toHandle = new ArrayList<>();
            ArrayList<String> parsed = new ArrayList<>();

            for (int i = 0; i < line.length(); i++) {
                int valAt = Integer.parseInt(line.substring(i, i + 1));

                if (disk) {
                    for (int j = 0; j < valAt; j++) {
                        parsed.add(Long.toString(id));
                    }
                    toHandle.add(Long.toString(id));
                    id++;
                    disk = false;
                } else {
                    for (int j = 0; j < valAt; j++) {
                        parsed.add(".");
                    }
                    disk = true;
                }
            }

            ArrayList<String> handled = new ArrayList<>();

            // int tally = 0;
            for (int i = toHandle.size() - 1; i >= 0 ; i--) {
                // tally++;

                String moveTarget = toHandle.get(i);
                int checkIndex = -1;
                int checkSize = 1;

                for (int j = parsed.size() - 1; j >= 0; j--) {
                    String check = parsed.get(j);
                    
                    if (check.equals(moveTarget)) {
                        checkIndex = j;
                        moveTarget = check;
                        handled.add(check);

                        for (int a = j - 1; a >= 0; a--) {
                            String check2 = parsed.get(a);
                            if (!check2.equals(check)) {
                                break;
                            }
                            checkSize++;
                        }
                        break;
                    }
                }
                
                for (int j = 0; j < checkIndex; j++) {
                    String check = parsed.get(j);
                    int spacesToUse = 1;

                    for (int a = j + 1; a < parsed.size(); a++) {
                        String check2 = parsed.get(a);
                        if (!check2.equals(".")) {
                            break;
                        }
                        spacesToUse++;
                    }

                    if (check.equals(".") && !moveTarget.equals("") && spacesToUse >= checkSize) {
                        for (int a = j; a < j + checkSize; a++) {
                            parsed.set(a, moveTarget);
                        }

                        for (int a = 0; a < checkSize; a++) {
                            parsed.set(checkIndex - checkSize + a + 1, ".");

                        }

                        break;
                    }
                } 
            }

            for (int i = 0; i < parsed.size(); i++) {
                String el = parsed.get(i);
                System.out.print(el);
                if (el.equals(".")) {
                    continue;
                }
                total += Long.parseLong(parsed.get(i)) * i;
            }
        }
        // Part 1 code goes here
        return total;
    }

}
