package aoc.project.year2024.day12;

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

public class Day12Puzzle {
    public static void main(String[] args) {
        Day12Puzzle puzzle = new Day12Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2024, 12);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 12 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day12Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 12 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day12Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    record Reigon(long area, long perimeter) {
    }

    public void recurse(Grid<Character> grid, GridPointer<Character> pointer, long[] returnValues,
            ArrayList<Coordinates> removeCoords) {
        if (pointer.getItemAtPointer().equals('#')) {
            return;
        }
        Character elementAt = pointer.replaceElementAtPointer('#');
        removeCoords.add(pointer.getCoordinates());

        returnValues[0]++;
        returnValues[1] += pointer.getNearbyOutOfBounds(true);
        for (Coordinates nearby : pointer.getNearby(true)) {
            pointer.setPointerPosition(nearby);
            Character nearbyOriginal = grid.getElement(nearby);
            if (nearbyOriginal.equals(elementAt)) {
                recurse(grid, pointer, returnValues, removeCoords);
                continue;
            } else if (nearbyOriginal.equals('#')) {
                continue;
            } else {
                returnValues[1]++;
            }
        }
    }

    public long doPart1(List<String> lines) {
        long sum = 0;

        Grid<Character> grid = GridUtils.parseStringInput(lines);
        GridPointer<Character> pointer = GridUtils.createPointer(grid, 0, 0, null);

        for (int i = 0; i < grid.getRowSize(); i++) {
            for (int j = 0; j < grid.getColumnSize(); j++) {
                pointer.setPointerPosition(i, j);

                if (pointer.getItemAtPointer().equals('.')) {
                    continue;
                }
                long[] returnValues = new long[2];
                ArrayList<Coordinates> removeCoords = new ArrayList<>();
                recurse(grid, pointer, returnValues, removeCoords);

                for (Coordinates remove : removeCoords) {
                    grid.replaceElement(remove.row(), remove.column(), '.');
                }
                sum += returnValues[0] * returnValues[1];
                System.out.println(sum);
            }
        }

        return sum;
    }

    public ArrayList<Direction> getBorders(Grid<Character> grid, GridPointer<Character> pointer, Character elementAt) {
        ArrayList<Direction> list = new ArrayList<>();
        Coordinates ogPos = pointer.getCoordinates();

        ArrayList<Coordinates> nearbyCoords = pointer.getNearbyWithOutOfBands(true);
        for (int i = 0; i < nearbyCoords.size(); i++) {
            Coordinates nearby = nearbyCoords.get(i);
            if (grid.isValidCord(nearby.row(), nearby.column())) {
                pointer.setPointerPosition(nearby);
                Character nearbyOriginal = grid.getElement(nearby);
                if (nearbyOriginal.equals(elementAt)) {
                    continue;
                } else if (nearbyOriginal.equals('#')) {
                    continue;
                }
            }

            if (i == 0) {
                list.add(Direction.NORTH);
            } else if (i == 1) {
                list.add(Direction.EAST);
            } else if (i == 2) {
                list.add(Direction.SOUTH);
            } else if (i == 3) {
                list.add(Direction.WEST);
            }
        }

        pointer.setPointerPosition(ogPos);

        return list;
    }

    public void recursePart2(Grid<Character> grid, GridPointer<Character> pointer, long[] returnValues,
            ArrayList<Coordinates> removeCoords) {
        if (pointer.getItemAtPointer().equals('#')) {
            return;
        }
        Character elementAt = pointer.replaceElementAtPointer('#');
        removeCoords.add(pointer.getCoordinates());

        Coordinates saveCoordinates = pointer.getCoordinates();

        returnValues[0]++;

        ArrayList<Direction> borders = getBorders(grid, pointer, elementAt);
        // System.out.println(borders);

        ArrayList<Coordinates> nearbyCoords = pointer.getNearby(true);
        for (int i = 0; i < nearbyCoords.size(); i++) {
            Coordinates nearby = nearbyCoords.get(i);

            pointer.setPointerPosition(nearby);
            Character nearbyOriginal = grid.getElement(nearby);
            if (nearbyOriginal.equals(elementAt) || nearbyOriginal.equals('#')) {
                recursePart2(grid, pointer, returnValues, removeCoords);
                continue;
            }
        }

        pointer.setPointerPosition(saveCoordinates);
        ArrayList<Direction> toRemove = new ArrayList<>();
        for (Direction cornerDirection : borders) {
            Direction oldDirection = cornerDirection;

            switch (cornerDirection) {
                case EAST:
                    cornerDirection = Direction.SOUTH;
                    break;
                case NORTH:
                    cornerDirection = Direction.EAST;
                    break;
                case SOUTH:
                    cornerDirection = Direction.WEST;
                    break;
                case WEST:
                    cornerDirection = Direction.NORTH;
                    break;
                default:
                    break;

            }

            pointer.setDirection(cornerDirection);
            pointer.moveAccordingToDirection(1);
            if (grid.isValidCord(pointer.getCoordinates().row(), pointer.getCoordinates().column())
                    && (pointer.getItemAtPointer().equals(elementAt) || pointer.getItemAtPointer().equals('#'))
                    && getBorders(grid, pointer, elementAt).contains(oldDirection)) {
                toRemove.add(oldDirection);
            }

            pointer.setPointerPosition(saveCoordinates);
        }
        borders.removeAll(toRemove);

        System.out.println();
        returnValues[1] += borders.size();
    }

    public long doPart2(List<String> lines) {
        long sum = 0;

        Grid<Character> grid = GridUtils.parseStringInput(lines);
        GridPointer<Character> pointer = GridUtils.createPointer(grid, 0, 0, null);

        for (int i = 0; i < grid.getRowSize(); i++) {
            for (int j = 0; j < grid.getColumnSize(); j++) {
                pointer.setPointerPosition(i, j);

                if (pointer.getItemAtPointer().equals('.')) {
                    continue;
                }
                long[] returnValues = new long[2];
                ArrayList<Coordinates> removeCoords = new ArrayList<>();

                recursePart2(grid, pointer, returnValues, removeCoords);

                for (Coordinates remove : removeCoords) {
                    grid.replaceElement(remove.row(), remove.column(), '.');
                }

                System.out.println(returnValues[1]);
                System.out.println();
                sum += returnValues[0] * (returnValues[1]);
            }
        }

        return sum;
    }

}
