package aoc.project.year2024.day08;

import java.util.ArrayList;
import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;
import aoc.project.util.Grid.Coordinates;
import aoc.project.util.Grid.Grid;
import aoc.project.util.Grid.GridUtils;

import org.apache.commons.lang3.time.StopWatch;

public class Day08Puzzle {
    public static void main(String[] args) {
        Day08Puzzle puzzle = new Day08Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2024, 8);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 08 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day08Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 08 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day08Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public long doPart1(List<String> lines) {
        // long total = 0;

        Grid<Character> grid = GridUtils.parseStringInput(lines);
        // GridPointer<Character> pointer = GridUtils.createPointer(grid, 0, 0, Direction.NORTH);

        ArrayList<ArrayList<Coordinates>> uniqueSignals = new ArrayList<>();
        ArrayList<Character> got = new ArrayList<>();

        for (int i = 0; i < grid.getRowSize(); i++) {
            for (int j = 0; j < grid.getRowSize(); j++) {
                Character elementAt = grid.getElement(i, j);
                if (!elementAt.equals('.') && !got.contains(elementAt))  {
                    got.add(elementAt);
                    uniqueSignals.add(grid.getAllInstances(elementAt));
                }
            }
        }

        for (ArrayList<Coordinates> allInstances : uniqueSignals) {

            for (int i = 0; i < allInstances.size(); i++) {
                Coordinates coordFirst = allInstances.get(i);

                for (int j = i + 1; j < allInstances.size(); j++) {
                    Coordinates coordSecond = allInstances.get(j);

                    int rowDiff = coordSecond.row() - coordFirst.row();
                    int colDiff = coordSecond.column() - coordFirst.column();

                    if (grid.isValidCord(coordFirst.row() - rowDiff, coordFirst.column() - colDiff)) {
                        grid.replaceElement(coordFirst.row() - rowDiff, coordFirst.column() - colDiff, '#');
                    }

                    if (grid.isValidCord(coordSecond.row() + rowDiff, coordSecond.column() + colDiff)) {
                        grid.replaceElement(coordSecond.row() + rowDiff, coordSecond.column() + colDiff, '#');
                    }

                }
            }
        }

        grid.printGrid(System.out::print);

        // Part 1 code goes here
        return grid.getAllInstances('#').size();
    }

    public long doPart2(List<String> lines) {
        // long total = 0;

        Grid<Character> grid = GridUtils.parseStringInput(lines);
        // GridPointer<Character> pointer = GridUtils.createPointer(grid, 0, 0, Direction.NORTH);

        ArrayList<ArrayList<Coordinates>> uniqueSignals = new ArrayList<>();
        ArrayList<Character> got = new ArrayList<>();

        for (int i = 0; i < grid.getRowSize(); i++) {
            for (int j = 0; j < grid.getRowSize(); j++) {
                Character elementAt = grid.getElement(i, j);
                if (!elementAt.equals('.') && !got.contains(elementAt))  {
                    got.add(elementAt);
                    uniqueSignals.add(grid.getAllInstances(elementAt));
                }
            }
        }

        for (ArrayList<Coordinates> allInstances : uniqueSignals) {

            for (int i = 0; i < allInstances.size(); i++) {
                Coordinates coordFirst = allInstances.get(i);

                for (int j = i + 1; j < allInstances.size(); j++) {
                    Coordinates coordSecond = allInstances.get(j);

                    int rowDiff = coordSecond.row() - coordFirst.row();
                    int colDiff = coordSecond.column() - coordFirst.column();


                    int harmonicRow = coordFirst.row();
                    int harmonicCol = coordFirst.column();
                    while (grid.isValidCord(harmonicRow, harmonicCol)) {
                        grid.replaceElement(harmonicRow, harmonicCol, '#');
                        harmonicRow -= rowDiff;
                        harmonicCol -= colDiff;
                    }

                    harmonicRow += rowDiff;
                    harmonicCol += colDiff;

                    while (grid.isValidCord(harmonicRow, harmonicCol)) {
                        grid.replaceElement(harmonicRow, harmonicCol, '#');
                        harmonicRow += rowDiff;
                        harmonicCol += colDiff;
                    }
                    

                }
            }
        }

        grid.printGrid(System.out::print);

        // Part 1 code goes here
        return grid.getAllInstances('#').size();
    }

}
