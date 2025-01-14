package aoc.project.year2023.day14;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day14Puzzle {
    public static void main(String[] args) {
        Day14Puzzle puzzle = new Day14Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 14);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 14 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day14Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 14 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day14Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public void printArray(char[][] toPrint) {
        for (int i = 0; i < toPrint.length; i++) {
            for (int j = 0; j < toPrint[0].length; j++) {
                char charAt = toPrint[i][j];
                System.out.print(charAt);
            }
            System.out.println();
        }
        System.out.println();
    }

    public char[][] getRockArray(List<String> lines) {
        char[][] newArray = new char[lines.size()][lines.get(0).length()];

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(0).length(); j++) {
                newArray[i][j] = lines.get(i).charAt(j);
            }
        }

        return newArray;
    }

    enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST,
    }

    public void overrideArray(char[][] toOverride, char[][] blueprint) {
        for (int i = 0; i < toOverride.length; i++) {
            for (int j = 0; j < toOverride[0].length; j++) {
                toOverride[i][j] = blueprint[i][j];
            }
        }
    }

    public boolean oldProgressAray(char[][] rockArray, Direction cycleDirection) {
        boolean noMoves = true;

        int northAdjustment = 0;
        int eastAdjustment = 0;
        int southAdjustment = 0;
        int westAdjustment = 0;

        switch (cycleDirection) {
            case NORTH:
                northAdjustment = 1;
                break;

            case EAST:
                eastAdjustment = 1;
                break;

            case SOUTH:
                southAdjustment = 1;
                break;

            case WEST:
                westAdjustment = 1;
                break;
        }

        for (int i = northAdjustment; i < rockArray.length - southAdjustment; i++) {
            for (int j = westAdjustment; j < rockArray[0].length - eastAdjustment; j++) {
                char charAt = rockArray[i][j];
                if (charAt != 'O') {
                    continue;
                }

                char above = rockArray[i - northAdjustment + southAdjustment][j - westAdjustment + eastAdjustment];
                if (above == '.') {
                    rockArray[i][j] = '.';
                    rockArray[i - northAdjustment + southAdjustment][j - westAdjustment + eastAdjustment] = 'O';
                    noMoves = false;
                }
            }
        }

        return noMoves;
    }

    public void writeRowOfOs(char[][] rockArray, boolean vertical, int start, int end, int constant) {
        for (int i = start; i < end; i++) {
            if (vertical) {
                rockArray[i][constant] = 'O';
            } else {
                rockArray[constant][i] = 'O';
            }
        }
    }

    public void moveRocks(char[][] rockArray, Direction cycleDirection) {
        // boolean finished = false;
        // while (!finished) {
        // finished = progressAray(rockArray, cycleDirection);
        // }

        if (cycleDirection == Direction.NORTH) {
            for (int j = 0; j < rockArray[0].length; j++) {
                int rockTally = 0;
                for (int i = rockArray.length - 1; i >= 0; i--) {
                    char charAt = rockArray[i][j];

                    switch (charAt) {
                        case 'O':
                            rockArray[i][j] = '.';
                            rockTally++;

                            break;
                        case '#':
                            if (rockTally > 0) {
                                writeRowOfOs(rockArray, true, i + 1, i + rockTally + 1, j);
                                rockTally = 0;
                            }

                            break;

                        default:
                            break;
                    }
                }

                if (rockTally > 0) {
                    writeRowOfOs(rockArray, true, 0, 0 + rockTally, j);
                }
            }
        } else if (cycleDirection == Direction.SOUTH) {
            for (int j = 0; j < rockArray[0].length; j++) {
                int rockTally = 0;
                for (int i = 0; i < rockArray.length; i++) {
                    char charAt = rockArray[i][j];

                    switch (charAt) {
                        case 'O':
                            rockArray[i][j] = '.';
                            rockTally++;

                            break;
                        case '#':
                            if (rockTally > 0) {
                                writeRowOfOs(rockArray, true, i - rockTally, i, j);
                                rockTally = 0;
                            }

                            break;

                        default:
                            break;
                    }
                }

                if (rockTally > 0) {
                    writeRowOfOs(rockArray, true, rockArray.length - rockTally, rockArray.length, j);
                }
            }
        } else if (cycleDirection == Direction.EAST) {
            for (int i = 0; i < rockArray.length; i++) {
                int rockTally = 0;
                for (int j = 0; j < rockArray[0].length; j++) {
                    char charAt = rockArray[i][j];

                    switch (charAt) {
                        case 'O':
                            rockArray[i][j] = '.';
                            rockTally++;

                            break;
                        case '#':
                            if (rockTally > 0) {
                                writeRowOfOs(rockArray, false, j - rockTally, j, i);
                                rockTally = 0;
                            }

                            break;

                        default:
                            break;
                    }
                }

                if (rockTally > 0) {
                    writeRowOfOs(rockArray, false, rockArray[0].length - rockTally, rockArray[0].length, i);
                }
            }
        } else if (cycleDirection == Direction.WEST) {
            for (int i = 0; i < rockArray.length; i++) {
                int rockTally = 0;
                for (int j = rockArray[0].length - 1; j >= 0; j--) {
                    char charAt = rockArray[i][j];

                    switch (charAt) {
                        case 'O':
                            rockArray[i][j] = '.';
                            rockTally++;

                            break;
                        case '#':
                            if (rockTally > 0) {
                                writeRowOfOs(rockArray, false, j + 1, j + rockTally + 1, i);
                                rockTally = 0;
                            }

                            break;

                        default:
                            break;
                    }
                }

                if (rockTally > 0) {
                    writeRowOfOs(rockArray, false, 0, 0 + rockTally, i);
                }
            }
        }
    }

    public long doPart1(List<String> lines) {
        // Part 1 code goes here
        char[][] rockArray = getRockArray(lines);

        moveRocks(rockArray, Direction.NORTH);

        long tally = 0;

        for (int i = 0; i < rockArray.length; i++) {
            for (int j = 0; j < rockArray[0].length; j++) {
                char charAt = rockArray[i][j];
                // System.out.print(charAt);
                if (charAt == 'O') {
                    tally += rockArray.length - i;
                }
            }
            // System.out.println();
        }

        return tally;
    }

    public long doPart2(List<String> lines) {
        // Part 1 code goes here
        HashMap<String, Long> solutions = new HashMap<>();

        char[][] rockArray = getRockArray(lines);

        long cyclesRemaining = 4000000000L;
        long currentCycle = 0;
        Direction onDirection = Direction.NORTH;
        boolean foundCycle = false;

        // boolean foundCycle = false;

        while (currentCycle < cyclesRemaining) {
            moveRocks(rockArray, onDirection);
            if (!foundCycle) {
                long entryValue = 0;
                String checkString = "";

                for (int i = 0; i < rockArray.length; i++) {
                    for (int j = 0; j < rockArray[0].length; j++) {
                        checkString += rockArray[i][j];
                    }
                }

                // long printTally = 0;
                // for (int i = 0; i < rockArray.length; i++) {
                //     for (int j = 0; j < rockArray[0].length; j++) {
                //         char charAt = rockArray[i][j];
                //         if (charAt == 'O') {
                //             printTally += rockArray.length - i;
                //         }
                //     }
                // }

                // if (printTally == 64) {
                //     System.out.println("currentCycle: " + currentCycle);
                //     printArray(rockArray);
                // }

                for (Entry<String, Long> entry : solutions.entrySet()) {

                    if (entry.getKey().equals(checkString)) {
                        // System.out.println(entry.getValue());

                        foundCycle = true;
                        entryValue = entry.getValue();
                    }
                }

                if (foundCycle) {
                    // System.out.println((currentCycle - entryValue));
                    // System.out.println("currentCycle: " + currentCycle);
                    cyclesRemaining = (cyclesRemaining - entryValue)  % (currentCycle - entryValue);
                    currentCycle = 0;
                } else {
                    solutions.put(checkString, currentCycle);
                }
            }
            
            switch (onDirection) {
                case NORTH:
                    onDirection = Direction.WEST;
                    break;

                case WEST:
                    onDirection = Direction.SOUTH;
                    break;

                case SOUTH:
                    onDirection = Direction.EAST;
                    break;

                case EAST:
                    onDirection = Direction.NORTH;
                    break;
            }
            currentCycle++;

        }

        printArray(rockArray);

        long tally = 0;
        for (int i = 0; i < rockArray.length; i++) {
            for (int j = 0; j < rockArray[0].length; j++) {
                char charAt = rockArray[i][j];
                if (charAt == 'O') {
                    tally += rockArray.length - i;
                }
            }
        }

        return tally;
    }

}
