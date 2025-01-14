package aoc.project.year2024.day10;

import java.util.ArrayList;
import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;
import aoc.project.util.Grid.Coordinates;
import aoc.project.util.Grid.Direction;
import aoc.project.util.Grid.Grid;
import aoc.project.util.Grid.GridPointer;
import aoc.project.util.Grid.GridUtils;

import org.apache.commons.lang3.time.StopWatch;

public class Day10Puzzle {
    public static void main(String[] args) {
        Day10Puzzle puzzle = new Day10Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2024, 10);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 10 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day10Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 10 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day10Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public long doPart1(List<String> lines) {
        Grid<Character> grid = GridUtils.parseStringInput(lines);
        GridPointer<Character> pointer = GridUtils.createPointer(grid, 0, 0, Direction.NORTH);

        ArrayList<Coordinates> checkCords = grid.getAllInstances('0');
        long sum = 0;

        for (Coordinates cord : checkCords) {
            ArrayList<Coordinates> currentCoords = new ArrayList<>();
            currentCoords.add(cord);

            ArrayList<Coordinates> counted = new ArrayList<>();

            while (currentCoords.size() > 0) {
                ArrayList<Coordinates> nextIteration = new ArrayList<>();
                for (Coordinates operation : currentCoords) {
                    int score = grid.getElement(operation) - 48;
                    pointer.setPointerPosition(operation.row(), operation.column());
                    for (Coordinates nextCord : pointer.getNearby(true)) {
                        int element = grid.getElement(nextCord) - 48;

                        if (element == score + 1) {
                            if (element == 9 && !counted.contains(nextCord)) {
                                sum++;
                                counted.add(nextCord);
                                continue;
                            } else {
                                nextIteration.add(nextCord);
                            }
                        }
                    }
                }
                currentCoords = nextIteration;
            }
        }

        // Part 1 code goes here
        return sum;
    }

    public long doPart2(List<String> lines) {
        Grid<Character> grid = GridUtils.parseStringInput(lines);
        GridPointer<Character> pointer = GridUtils.createPointer(grid, 0, 0, Direction.NORTH);

        ArrayList<Coordinates> checkCords = grid.getAllInstances('0');
        long sum = 0;

        for (Coordinates cord : checkCords) {
            ArrayList<Coordinates> currentCoords = new ArrayList<>();
            currentCoords.add(cord);

            // ArrayList<Coordinates> counted = new ArrayList<>();

            while (currentCoords.size() > 0) {
                ArrayList<Coordinates> nextIteration = new ArrayList<>();
                for (Coordinates operation : currentCoords) {
                    int score = grid.getElement(operation) - 48;
                    pointer.setPointerPosition(operation.row(), operation.column());

                    ArrayList<Coordinates> possiblePositions = pointer.getNearby(true);
                    for (Coordinates nextCord : possiblePositions) {
                        int element = grid.getElement(nextCord) - 48;

                        if (element == score + 1) {
                            if (element == 9) {
                                sum++;
                                continue;
                            } else {
                                nextIteration.add(nextCord);
                            }
                        }
                    }

                }
                currentCoords = nextIteration;
            }
        }

        // Part 1 code goes here
        return sum;
    }

}
