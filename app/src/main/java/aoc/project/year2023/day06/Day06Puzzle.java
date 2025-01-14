package aoc.project.year2023.day06;

import java.util.ArrayList;
import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day06Puzzle {
    public static void main(String[] args) {
        Day06Puzzle puzzle = new Day06Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 6);

        // puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 06 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day06Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 06 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day06Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public long doPart1(List<String> lines) {
        ArrayList<Integer> times = new ArrayList<>();
        ArrayList<Integer> distances = new ArrayList<>();

        String firstLine = lines.get(0);
        String secondLine = lines.get(1);

        for (int i = 1; i < firstLine.length(); i++) {
            char charAtPlace = firstLine.charAt(i);
            char charBehind = firstLine.charAt(i - 1);
            if (charBehind == ' ' && Character.isDigit(charAtPlace)) {
                times.add(AocUtil.parseNumberInt(firstLine, i));
            }
        }

        for (int i = 1; i < secondLine.length(); i++) {
            char charAtPlace = secondLine.charAt(i);
            char charBehind = secondLine.charAt(i - 1);
            if (charBehind == ' ' && Character.isDigit(charAtPlace)) {
                distances.add(AocUtil.parseNumberInt(secondLine, i));
            }
        }

        int total = 1;
        for (int i = 0; i < times.size(); i++) {
            int time = times.get(i);
            int distance = distances.get(i);

            double start = ((time - Math.sqrt(time * time - 4 * distance)) / 2);
            double end = ((time + Math.sqrt(time * time - 4 * distance)) / 2);

            if (start == Math.ceil(start)) {
                start++;
            }

            if (end == Math.ceil(end)) {
                end--;
            }

            start = Math.ceil(start);
            end = Math.floor(end);

            System.out.println(start);
            System.out.println(end);
            System.out.println(end - start + 1);
            total *= end - start + 1;
        }

        // Part 1 code goes here
        return total;
    }

    public long doPart2(List<String> lines) {
        String firstLine = lines.get(0);
        String secondLine = lines.get(1);

        long time = 0;
        long distance = 0;

        for (int i = 0; i < firstLine.length(); i++) {
            if (Character.isDigit(firstLine.charAt(i))) {
                time = time * 10 + firstLine.charAt(i) - 48;
            }
        }

        for (int i = 0; i < secondLine.length(); i++) {
            if (Character.isDigit(secondLine.charAt(i))) {
                distance = distance * 10 + secondLine.charAt(i) - 48;
            }
        }

        long total = 1;

        double start = ((time - Math.sqrt(time * time - 4 * distance)) / 2);
        double end = ((time + Math.sqrt(time * time - 4 * distance)) / 2);

        if (start == Math.ceil(start)) {
            start++;
        }

        if (end == Math.ceil(end)) {
            end--;
        }

        start = Math.ceil(start);
        end = Math.floor(end);

        System.out.println(start);
        System.out.println(end);
        System.out.println(end - start + 1);
        total *= end - start + 1;

        // Part 1 code goes here
        return total;
    }

}
