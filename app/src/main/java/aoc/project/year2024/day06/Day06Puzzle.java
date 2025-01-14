package aoc.project.year2024.day06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day06Puzzle {
    public static void main(String[] args) {
        Day06Puzzle puzzle = new Day06Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2024, 6);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 06 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day06Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 06 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day06Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST,
    }

    record CoordRecord(int x, int y) {

    }

    public boolean updateDirectionMap(HashMap<CoordRecord, ArrayList<Direction>> traveledLocations, int x, int y, Direction traveled) {
        CoordRecord newRecord = new CoordRecord(x, y);

        if (traveledLocations.containsKey(newRecord) && traveledLocations.get(newRecord).contains(traveled)) {
            return true;
        } else if (traveledLocations.containsKey(newRecord) && !traveledLocations.get(newRecord).contains(traveled)) {
            traveledLocations.get(newRecord).add(traveled);
        } else {
            traveledLocations.put(newRecord, new ArrayList<>());
            traveledLocations.get(newRecord).add(traveled);
        }
        return false;
    }

    public boolean runMap(char[][] grid, int xCoord, int yCoord) {
        Direction moveDir = Direction.NORTH;
        HashMap<CoordRecord, ArrayList<Direction>> traveledLocations = new HashMap<>();
        boolean done = false;

        while (xCoord >= 0 && yCoord >= 0 && xCoord < grid.length && yCoord < grid[0].length) {
            if (grid[xCoord][yCoord] == '#') {
                switch (moveDir) { // undo
                    case EAST:
                        moveDir = Direction.SOUTH;
                        yCoord--;
                        break;
                    case NORTH:
                        moveDir = Direction.EAST;
                        xCoord++;
                        break;
                    case SOUTH:
                        moveDir = Direction.WEST;
                        xCoord--;
                        break;
                    case WEST:
                        moveDir = Direction.NORTH;
                        yCoord++;
                        break;
                    default:
                        break;
    
                }
            }
            grid[xCoord][yCoord] = 'x';
            done = updateDirectionMap(traveledLocations, xCoord, yCoord, moveDir);
            if (done) {
                break;
            }

            switch (moveDir) {
                case EAST:
                    yCoord++;
                    break;
                case NORTH:
                    xCoord--;
                    break;
                case SOUTH:
                    xCoord++;
                    break;
                case WEST:
                    yCoord--;
                    break;
                default:
                    break;
            }
        }

        return done;
    }

    public long doPart1(List<String> lines) {
        char[][] grid = new char[lines.size()][lines.get(0).length()];

        int xCoord = 0;
        int yCoord = 0;

        for (int i = 0; i < lines.size(); i++) {
            String pick = lines.get(i);
            for (int j = 0; j < pick.length(); j++) {
                grid[i][j] = pick.charAt(j);
                System.out.println(pick.charAt(j));

                if (grid[i][j] == '^') {
                    xCoord = i;
                    yCoord = j;
                }
            }
        }

        Direction moveDir = Direction.NORTH;
        while (xCoord >= 0 && yCoord >= 0 && xCoord < grid.length && yCoord < grid[0].length) {
            if (grid[xCoord][yCoord] == '#') {
                switch (moveDir) { // undo
                    case EAST:
                        moveDir = Direction.SOUTH;
                        yCoord--;
                        break;
                    case NORTH:
                        moveDir = Direction.EAST;
                        xCoord++;
                        break;
                    case SOUTH:
                        moveDir = Direction.WEST;
                        xCoord--;
                        break;
                    case WEST:
                        moveDir = Direction.NORTH;
                        yCoord++;
                        break;
                    default:
                        break;
    
                }
            } else {

            }
            grid[xCoord][yCoord] = 'x';

            switch (moveDir) {
                case EAST:
                    yCoord++;
                    break;
                case NORTH:
                    xCoord--;
                    break;
                case SOUTH:
                    xCoord++;
                    break;
                case WEST:
                    yCoord--;
                    break;
                default:
                    break;
            }

            System.out.println(xCoord);
            System.out.println(yCoord);
        }

        int tal = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j]);
                if (grid[i][j] == 'x') {
                    tal++;
                }
            }
            System.out.println();
        }
        // Part 1 code goes here
        return tal;
    }

    public long doPart2(List<String> lines) {
        char[][] grid = new char[lines.size()][lines.get(0).length()];

        int xCoord = 0;
        int yCoord = 0;

        int startXCoord = 0;
        int startYCoord = 0;

        for (int i = 0; i < lines.size(); i++) {
            String pick = lines.get(i);
            for (int j = 0; j < pick.length(); j++) {
                grid[i][j] = pick.charAt(j);
                // System.out.println(pick.charAt(j));

                if (grid[i][j] == '^') {
                    xCoord = i;
                    yCoord = j;

                    startXCoord = i;
                    startYCoord = j;
                }
            }
        }


        ArrayList<CoordRecord> possiblePlaces = new ArrayList<>();
        Direction moveDir = Direction.NORTH;
        while (xCoord >= 0 && yCoord >= 0 && xCoord < grid.length && yCoord < grid[0].length) {
            if (grid[xCoord][yCoord] == '#') {
                switch (moveDir) { // undo
                    case EAST:
                        moveDir = Direction.SOUTH;
                        yCoord--;
                        break;
                    case NORTH:
                        moveDir = Direction.EAST;
                        xCoord++;
                        break;
                    case SOUTH:
                        moveDir = Direction.WEST;
                        xCoord--;
                        break;
                    case WEST:
                        moveDir = Direction.NORTH;
                        yCoord++;
                        break;
                    default:
                        break;
    
                }
            }

            boolean alreadyInList = false;
            for (CoordRecord record : possiblePlaces) {
                if (record.x()  == xCoord && record.y() == yCoord) {
                    alreadyInList = true;
                }
            }
            if (!alreadyInList) {
                possiblePlaces.add(new CoordRecord(xCoord, yCoord));
            }

            grid[xCoord][yCoord] = 'x';

            switch (moveDir) {
                case EAST:
                    yCoord++;
                    break;
                case NORTH:
                    xCoord--;
                    break;
                case SOUTH:
                    xCoord++;
                    break;
                case WEST:
                    yCoord--;
                    break;
                default:
                    break;
            }
        }

        // int tal = 0;
        // for (int i = 0; i < grid.length; i++) {
        //     for (int j = 0; j < grid[0].length; j++) {
        //         System.out.print(grid[i][j]);
        //         if (grid[i][j] == 'x') {
        //             tal++;
        //         }
        //     }
        //     System.out.println();
        // }

        int tally = 0;

        for (CoordRecord place : possiblePlaces) {
            grid[place.x()][place.y()] = '#';
            if (runMap(grid, startXCoord, startYCoord)) {
                tally += 1;
            }
            grid[place.x()][place.y()] = '.';
        }
        // Part 1 code goes here
        return tally;
    }

}
