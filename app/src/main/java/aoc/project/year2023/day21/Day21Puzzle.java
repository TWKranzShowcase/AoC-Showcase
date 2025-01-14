package aoc.project.year2023.day21;

import java.util.ArrayList;
import java.util.LinkedList;
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

public class Day21Puzzle {
    public static void main(String[] args) {
        Day21Puzzle puzzle = new Day21Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 21);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 21 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day21Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 21 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day21Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public boolean canMoveIntoTile(Grid<Character> grid, Coordinates coords) {
        if (grid.getElement(coords).equals('.') || grid.getElement(coords).equals('S')) {
            return true;
        }
        return false;
    }

    public void updateTileCheck(GridPointer<Character> pointer, LinkedList<Coordinates> newCoords, Grid<Character> grid,
            Direction direction) {
        pointer.setDirection(direction);
        pointer.moveAccordingToDirection(1);

        Coordinates coords = pointer.getCoordinates();
        if (grid.isValidCord(coords.row(), coords.column()) && canMoveIntoTile(grid, coords)
                && !newCoords.contains(coords)) {
            grid.replaceElement(coords.row(), coords.column(), 'O');
            newCoords.add(coords);
        }
        pointer.returnToBenchmark();
    }

    public long doPart1(List<String> lines) {
        Grid<Character> grid = GridUtils.parseStringInput(lines);

        Coordinates startPos = grid.findInstance('S');
        GridPointer<Character> pointer = GridUtils.createPointer(grid, startPos.row(), startPos.column(),
                Direction.NORTH);

        LinkedList<Coordinates> currentValidTiles = new LinkedList<>();

        currentValidTiles.add(startPos);

        int STEP_AMOUNT = 64;
        for (int i = 0; i < STEP_AMOUNT; i++) {
            // grid.printGrid(System.out::print);
            LinkedList<Coordinates> newCoords = new LinkedList<>();

            for (Coordinates coord : currentValidTiles) {
                grid.replaceElement(coord.row(), coord.column(), '.');
            }

            for (Coordinates coord : currentValidTiles) {
                pointer.setPointerPosition(coord.row(), coord.column());
                pointer.setBenchmark();

                updateTileCheck(pointer, newCoords, grid, Direction.EAST);
                updateTileCheck(pointer, newCoords, grid, Direction.WEST);
                updateTileCheck(pointer, newCoords, grid, Direction.SOUTH);
                updateTileCheck(pointer, newCoords, grid, Direction.NORTH);
            }

            currentValidTiles = newCoords;
        }
        // Part 1 code goes here
        return currentValidTiles.size();
    }

    public void updateTileCheckInfinite(GridPointer<Character> pointer, LinkedList<Coordinates> newCoords,
            Grid<Character> grid,
            Direction direction) {
        pointer.setDirection(direction);
        pointer.moveAccordingToDirection(1);

        Coordinates coords = pointer.getCoordinates();
        if (grid.isValidCord(coords.row(), coords.column()) && canMoveIntoTile(grid, coords)) {
            grid.replaceElement(coords.row(), coords.column(), 'O');
            newCoords.add(coords);
        }
        pointer.returnToBenchmark();
    }

    enum EquationState {
        START_VALUE,
        FIRST_PATTERN,
        SECOND_PATTERN,
        THIRD_PATTERN,
    }

    record PatternKey(int value, int slope) {
    }

    public long doPart2(List<String> lines) {
        InfiniteGrid<Character> grid = GridUtils.parseStringInputInfiniteGrid(lines);

        Coordinates startPos = grid.findInstance('S');
        GridPointer<Character> pointer = GridUtils.createPointer(grid, startPos.row(), startPos.column(),
                Direction.NORTH);

        LinkedList<Coordinates> currentValidTiles = new LinkedList<>();

        currentValidTiles.add(startPos);

        int STEP_AMOUNT = 26501365;
        int lastElement = 0;

        EquationState state = EquationState.START_VALUE;
        ArrayList<Long> firstPatternList = new ArrayList<>();
        ArrayList<Long> secondPatternList = new ArrayList<>();
        ArrayList<Long> thirdPatternList = new ArrayList<>();
        int zeroTerm = 0;
        int zeroIndex = 0;

        for (int i = 0; i < STEP_AMOUNT; i++) {
            LinkedList<Coordinates> newCoords = new LinkedList<>();

            for (Coordinates coord : currentValidTiles) {
                grid.replaceElement(coord.row(), coord.column(), '.');
            }

            for (Coordinates coord : currentValidTiles) {
                pointer.setPointerPosition(coord.row(), coord.column());
                pointer.setBenchmark();

                updateTileCheckInfinite(pointer, newCoords, grid, Direction.EAST);
                updateTileCheckInfinite(pointer, newCoords, grid, Direction.WEST);
                updateTileCheckInfinite(pointer, newCoords, grid, Direction.SOUTH);
                updateTileCheckInfinite(pointer, newCoords, grid, Direction.NORTH);
            }

            currentValidTiles = newCoords;
            int newSlope = currentValidTiles.size() - lastElement;

            if ((i + 1) % lines.size() == 0 && i >= lines.size() * 2) {
                // System.out.println();

                if (state == EquationState.START_VALUE) {
                    state = EquationState.FIRST_PATTERN;
                } else if (state == EquationState.FIRST_PATTERN) {
                    state = EquationState.SECOND_PATTERN;
                } else if (state == EquationState.SECOND_PATTERN) {
                    state = EquationState.THIRD_PATTERN;
                    zeroTerm = lastElement;
                    zeroIndex = i - 1;
                } else if (state == EquationState.THIRD_PATTERN) {
                    // if (patternsLeft > 0) {
                    //     patternsLeft--;
                    // } else {
                        break;
                    // }
                }

            }

            if (state == EquationState.FIRST_PATTERN) {
                firstPatternList.add((long) newSlope);
            } else if (state == EquationState.SECOND_PATTERN) {
                secondPatternList.add((long) newSlope);
            } else if (state == EquationState.THIRD_PATTERN) {
                thirdPatternList.add((long) newSlope);
            }
            System.out.println(i);
            // System.out.println(i + 1 + ": " + currentValidTiles.size() + "-" + newSlope);
            // System.out.println(newSlope < lastSlope ? "-d" : "-i");
            // System.out.println(i + 1 + ": " + currentValidTiles.size());

            // lastSlope = newSlope;
            lastElement = currentValidTiles.size();
            // grid.printGrid(System.out::print);

        }

        System.out.println();
        System.out.println("zeroindex-" + zeroIndex);
        System.out.println("zero-" + zeroTerm);

        // int[] checkVals = {51, 52, 62, 72, 73, 74, 83, 84, 90, 95, 96, 97, 98, 99,
        // 100, 500, 1000, 5000};

        // for (int val : checkVals) {
        long sum = 0;
        // STEP_AMOUNT = val;
        // long accelTotal = 0;
        for (int i = 0; i < firstPatternList.size(); i++) {
            long divisions = (STEP_AMOUNT - zeroIndex + (lines.size() - i) - 2) / (lines.size());
            long acceleration = thirdPatternList.get(i) - secondPatternList.get(i);

            if (divisions <= 0) {
                continue;
            }

            long accelTerm = 0;
            for (long j = divisions; j > 0; j--) {
                accelTerm += acceleration * j;
            }
            long addTerm = (thirdPatternList.get(i) - acceleration) * divisions;

            // accelTotal += accelTerm;
            sum += addTerm + accelTerm;
        }

        sum += zeroTerm;
        // System.out.println("diff: " + (sum - 6536));
        System.out.println(sum);
        // }

        // grid.printGrid(System.out::print);
        return sum;
    }

}
