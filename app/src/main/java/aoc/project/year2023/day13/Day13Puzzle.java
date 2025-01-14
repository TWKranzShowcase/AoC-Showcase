package aoc.project.year2023.day13;

import java.util.ArrayList;
import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day13Puzzle {
    public static void main(String[] args) {
        Day13Puzzle puzzle = new Day13Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 13);

        // puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 13 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day13Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 13 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day13Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    enum TileType {
        ASH,
        ROCKS,
    }

    public TileType[][] cutOutMirrorArangement(List<String> lines) {
        List<String> sub = lines.subList(0, lines.contains("") ? lines.indexOf("") : lines.size());
        int width = sub.get(0).length();
        int length = sub.size();

        TileType[][] tileArray = new TileType[length][width];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                tileArray[i][j] = sub.get(i).charAt(j) == '#' ? TileType.ROCKS : TileType.ASH;
            }
        }
        sub.clear();
        if (lines.size() > 0) {
            lines.remove(0);
        }

        return tileArray;
    }

    public ArrayList<TileType> getRow(TileType[][] tileList, int index) {
        ArrayList<TileType> tileRow = new ArrayList<>();
        for (int i = 0; i < tileList[0].length; i++) {
            tileRow.add(tileList[index][i]);
        }

        return tileRow;
    }

    public ArrayList<TileType> getColumn(TileType[][] tileList, int index) {
        ArrayList<TileType> tileColumn = new ArrayList<>();
        for (int i = 0; i < tileList.length; i++) {
            tileColumn.add(tileList[i][index]);
        }

        return tileColumn;
    }

    public void printArray(TileType[][] arangement) {
        for (int i = 0; i < arangement.length; i++) {
            for (int j = 0; j < arangement[0].length; j++) {
                if (arangement[i][j] == TileType.ASH) {
                    System.out.print(".");
                } else {
                    System.out.print("#");
                }
            }   
            System.out.println();
        }
    }

    public int getReflectionValue(TileType[][] arangement, int ignoreParam) {
        // rows
        int reflectPoint = -1;
        for (int row = 0; row < arangement.length; row++) {
            int extend = 0;
            
            while (reflectPoint == -1 && row - extend - 1 >= 0 && row + extend < arangement.length){
                if (!getRow(arangement, row - extend - 1).equals(getRow(arangement, row + extend))) {
                    break;
                } else if ((row - extend - 1 == 0 || row + extend == arangement.length - 1) && ignoreParam / 100 != row) {
                    reflectPoint = row;
                }
                extend++;
            }


        }

        if (reflectPoint != -1) {
            return reflectPoint * 100;
        }

        // columns
        for (int column = 0; column < arangement[0].length; column++) {
            int extend = 0;
            
            while (reflectPoint == -1 && column - extend - 1 >= 0 && column + extend < arangement[0].length){
                if (!getColumn(arangement, column - extend - 1).equals(getColumn(arangement, column + extend))) {
                    break;
                } else if ((column - extend - 1 == 0 || column + extend == arangement[0].length - 1) && ignoreParam != column) {
                    reflectPoint = column;
                }
                extend++;
            }

            if (reflectPoint != -1) {
                return reflectPoint;
            }
        }

        // System.err.println("no pattern found");
        
        return -1;
    }

    public long doPart1(List<String> lines) {
        ArrayList<TileType[][]> mirrorLists = new ArrayList<>();
        long tally = 0;

        while (lines.size() > 0) {
            mirrorLists.add(cutOutMirrorArangement(lines));
        }

        for (TileType[][] arangement : mirrorLists) {
            tally += getReflectionValue(arangement, -1);
        }

        return tally;
    }

    public long doPart2(List<String> lines) {
        ArrayList<TileType[][]> mirrorLists = new ArrayList<>();
        long tally = 0;

        while (lines.size() > 0) {
            mirrorLists.add(cutOutMirrorArangement(lines));
        }

        for (TileType[][] arangement : mirrorLists) {
            boolean validSmudge = false;
            // System.out.println();
            // printArray(arangement);
            // System.out.println(getReflectionValue(arangement));
            // System.out.println("old");
            int oldReflectionValue = getReflectionValue(arangement, -1);

            for (int i = 0; i < arangement.length; i++) {
                for (int j = 0; j < arangement[0].length && !validSmudge; j++) {
                    arangement[i][j] = arangement[i][j] == TileType.ROCKS ? TileType.ASH : TileType.ROCKS;
                    int newReflectionValue = getReflectionValue(arangement, oldReflectionValue);
                    if (newReflectionValue != oldReflectionValue && newReflectionValue > -1) {
                        validSmudge = true;
                        // System.out.println();
                        // printArray(arangement);
                        // System.out.println(newReflectionValue);
                        tally += newReflectionValue;
                    }

                    arangement[i][j] = arangement[i][j] == TileType.ROCKS ? TileType.ASH : TileType.ROCKS;
                }
            }
            if (!validSmudge) {
                // System.out.println();
                // printArray(arangement);
                // System.out.println("NO SOLUTION WITH SMUDGE");
                // System.out.println(oldReflectionValue);
                tally += oldReflectionValue;
            }
        }

        return tally;
    }

}
