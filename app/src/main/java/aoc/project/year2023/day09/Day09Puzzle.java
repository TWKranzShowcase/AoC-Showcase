package aoc.project.year2023.day09;

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
        fetchPuzzleInput.fetchPuzzleInput(2023, 9);

        // puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 09 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day09Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 09 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day09Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public boolean listIsFullOfZeros(ArrayList<Long> list) {
        boolean valid = true;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != 0) {
                valid = false;
            }
        }

        if (list.size() == 0) {
            valid = false;
        }
        return valid;
    }

    public long doPart1(List<String> lines) {
        long total = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            ArrayList<ArrayList<Long>> sequence = new ArrayList<>();

            ArrayList<Long> firstList = new ArrayList<>();
            for (int j = 1; j < line.length(); j++) {
                char characterAt = line.charAt(j);
                char characterBefore = line.charAt(j - 1);
                if ((characterBefore == ' ' || characterBefore == '-') && Character.isDigit(characterAt)) {
                    firstList.add(AocUtil.parseNumberLong(line, j));
                }
            }
            sequence.add(firstList);
            
            ArrayList<Long> checkList = firstList;
            while (!listIsFullOfZeros(checkList)) {
                ArrayList<Long> nextLevel = new ArrayList<>();
                for (int j = 0; j < checkList.size() - 1; j++) {
                    nextLevel.add(checkList.get(j + 1) - checkList.get(j));
                }
                checkList = nextLevel;
                sequence.add(nextLevel);
            }

            checkList.add((long) 0);

            for (int j = sequence.size() - 1; j > 0; j--) {
                ArrayList<Long> upperList = sequence.get(j - 1);
                ArrayList<Long> lowerList = sequence.get(j);

                long lowerValue = lowerList.get(lowerList.size() - 1);
                long upperValue = upperList.get(upperList.size() - 1);
                upperList.add(upperValue + lowerValue);
            }

            System.out.println(firstList.get(firstList.size() - 1));
            total += firstList.get(firstList.size() - 1);
        }

        return total;
    }

    public long doPart2(List<String> lines) {
        long total = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            ArrayList<ArrayList<Long>> sequence = new ArrayList<>();

            ArrayList<Long> firstList = new ArrayList<>();
            for (int j = 0; j < line.length(); j++) {
                if (j == 0 && line.charAt(j) != '-') {
                    System.out.println(AocUtil.parseNumberLong(line, j));
                    firstList.add(AocUtil.parseNumberLong(line, j));
                    continue;
                } else if (line.charAt(j) == '-') {
                    j++;
                }
                char characterAt = line.charAt(j);
                char characterBefore = line.charAt(j - 1);
                if ((characterBefore == ' ' || characterBefore == '-') && Character.isDigit(characterAt)) {
                    firstList.add(AocUtil.parseNumberLong(line, j));
                }
            }
            sequence.add(firstList);

            firstList.forEach((thing) -> System.out.println("list: " + thing));
            
            ArrayList<Long> checkList = firstList;
            while (!listIsFullOfZeros(checkList)) {
                ArrayList<Long> nextLevel = new ArrayList<>();
                for (int j = 0; j < checkList.size() - 1; j++) {
                    nextLevel.add(checkList.get(j + 1) - checkList.get(j));
                    // System.out.print(checkList.get(j + 1) - checkList.get(j) + " ");
                }
                // System.out.println();
                checkList = nextLevel;
                sequence.add(nextLevel);
            }

            // sequence.forEach((list) -> {
            //     list.forEach((numb) -> System.out.print(numb + " "));
            //     System.out.println();
            // });

            // checkList.add((long) 0);

            // for (int j = sequence.size() - 1; j > 0; j--) {
            //     ArrayList<Long> upperList = sequence.get(j - 1);
            //     ArrayList<Long> lowerList = sequence.get(j);

            //     long lowerValue = lowerList.get(lowerList.size() - 1);
            //     long upperValue = upperList.get(upperList.size() - 1);
            //     upperList.add(upperValue + lowerValue);
            // }

            // go backwards

            // checkList = sequence.get(sequence.size() - 1);
            checkList.add(0, (long) 0);

            for (int j = sequence.size() - 1; j > 0; j--) {
                ArrayList<Long> upperList = sequence.get(j - 1);
                ArrayList<Long> lowerList = sequence.get(j);

                long lowerValue = lowerList.get(0);
                long upperValue = upperList.get(0);  

                // System.out.println("subtract: " + (upperValue - lowerValue));
                upperList.add(0, upperValue - lowerValue);
            }

            // sequence.forEach((list) -> {
            //     list.forEach((numb) -> System.out.print(numb + " "));
            //     System.out.println();
            // });

            // System.out.println(firstList.get(0));
            total += firstList.get(0);
        }

        return total;
    }

}
