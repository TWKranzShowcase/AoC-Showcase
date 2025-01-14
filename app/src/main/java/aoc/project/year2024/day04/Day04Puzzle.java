package aoc.project.year2024.day04;

import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day04Puzzle {
    public static void main(String[] args) {
        Day04Puzzle puzzle = new Day04Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2024, 4);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 04 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day04Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 04 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day04Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public boolean inBounds(char[][] grid, int row, int col) {
        return row >= 0 && col >= 0 && row < grid.length && col < grid[0].length;
    }

    public int xmasMove(char[][] grid, int row, int col, int dirHorizontal, int dirVertical) {
        String build = "";

        for (int i = 0; i < 4; i++) {
            build += grid[row][col];

            row += dirHorizontal;
            col += dirVertical;

            if (!inBounds(grid, row, col) && i != 3) {
                return 0;
            }
        }
        return build.equals("XMAS") ? 1 : 0;
    }

    public int triggerSearchX(char[][] grid, int row, int col) {
        int sum = 0;

        sum += xmasMove(grid, row, col, 1, 0);
        sum += xmasMove(grid, row, col, -1, 0);

        sum += xmasMove(grid, row, col, 0, 1);
        sum += xmasMove(grid, row, col, 0, -1);

        sum += xmasMove(grid, row, col, 1, -1);
        sum += xmasMove(grid, row, col, -1, 1);
        sum += xmasMove(grid, row, col, 1, 1);
        sum += xmasMove(grid, row, col, -1, -1);


        return sum;
    }

    public long doPart1(List<String> lines) {
        long count = 0;

        char[][] grid = new char[lines.size()][lines.get(0).length()];

        for (int i = 0; i < lines.size(); i++) {
            String parseString = lines.get(i);
            for (int j = 0; j < parseString.length(); j++) {
                grid[i][j] = parseString.charAt(j);
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'X') {
                    int add = triggerSearchX(grid, i, j);

                    if (add > 0) {
                        // System.out.print(add);
                    } else {
                        // System.out.print("L");
                    }
                    count += add;
                } else {
                    System.out.print(".");
                }
            }
            // System.out.println();
        }
        return count;
    }

    public int masMove(char[][] grid, int row, int col, int dirHorizontal, int dirVertical) {
        String build = "";

        row -= dirHorizontal;
        col -= dirVertical;

        for (int i = 0; i < 3; i++) {
            if (!inBounds(grid, row, col)) {
                return 0;
            }

            build += grid[row][col];

            row += dirHorizontal;
            col += dirVertical;


        }

        // System.out.println();
        // System.out.println();
        // System.out.println(build);
        // System.out.println();
        return build.equals("MAS") || build.equals("SAM")  ? 1 : 0;
    }

    public int triggerSearchMas(char[][] grid, int row, int col) {
        int sum = 0;

        if (masMove(grid, row, col, 1, -1) > 0 && masMove(grid, row, col, 1, 1) > 0) {
            sum += 1;
        }

        
        return sum;
    }

    public long doPart2(List<String> lines) {
        long count = 0;

        char[][] grid = new char[lines.size()][lines.get(0).length()];

        for (int i = 0; i < lines.size(); i++) {
            String parseString = lines.get(i);
            for (int j = 0; j < parseString.length(); j++) {
                grid[i][j] = parseString.charAt(j);
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'A') {
                    int add = triggerSearchMas(grid, i, j);

                    if (add > 0) {
                        System.out.print(add);
                    } else {
                        System.out.print("L");
                    }
                    count += add;
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        return count;
    }

}
