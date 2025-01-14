package aoc.project.year2023.day16;

import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day16Puzzle {
    public static void main(String[] args) {
        Day16Puzzle puzzle = new Day16Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 16);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 16 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day16Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 16 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day16Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }
    
    enum EnergyGridType {
        EMPTY,
        MIRROR_RIGHT,
        MIRROR_LEFT,
        SPLITTER_VERTICAL,
        SPLITTER_HORIZONTAL,
        SPLITTER_VERTICAL_USED,
        SPLITTER_HORIZONTAL_USED,
    }

    enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST,
    }

    public void beam(EnergyGridType[][] grid, boolean[][] energizedGrid, int rowPos, int columnPos, Direction moveDirection) {

        boolean cancel = false;
        
        while (rowPos >= 0 && columnPos >= 0 && rowPos < grid.length && columnPos < grid[0].length && !cancel) {
            energizedGrid[rowPos][columnPos] = true;

            EnergyGridType typeAt = grid[rowPos][columnPos];
            switch (typeAt) {
                case MIRROR_RIGHT:
                    if (moveDirection == Direction.EAST) {
                        moveDirection = Direction.NORTH;
                    } else if (moveDirection == Direction.WEST) {
                        moveDirection = Direction.SOUTH;
                    } else if (moveDirection == Direction.NORTH) {
                        moveDirection = Direction.EAST;
                    } else if (moveDirection == Direction.SOUTH) {
                        moveDirection = Direction.WEST;
                    }
                    break;

                case MIRROR_LEFT:
                    if (moveDirection == Direction.EAST) {
                        moveDirection = Direction.SOUTH;
                    } else if (moveDirection == Direction.WEST) {
                        moveDirection = Direction.NORTH;
                    } else if (moveDirection == Direction.NORTH) {
                        moveDirection = Direction.WEST;
                    } else if (moveDirection == Direction.SOUTH) {
                        moveDirection = Direction.EAST;
                    }
                    break;

                case SPLITTER_HORIZONTAL:
                    if (moveDirection == Direction.EAST || moveDirection == Direction.WEST) {
                        cancel = true;
                        grid[rowPos][columnPos] = EnergyGridType.SPLITTER_HORIZONTAL_USED;
                        beam(grid, energizedGrid, rowPos, columnPos, Direction.NORTH);
                        beam(grid, energizedGrid, rowPos, columnPos, Direction.SOUTH);
                    }
                    break;

                case SPLITTER_VERTICAL:
                    if (moveDirection == Direction.NORTH || moveDirection == Direction.SOUTH) {
                        cancel = true;
                        grid[rowPos][columnPos] = EnergyGridType.SPLITTER_VERTICAL_USED;
                        beam(grid, energizedGrid, rowPos, columnPos, Direction.EAST);
                        beam(grid, energizedGrid, rowPos, columnPos, Direction.WEST);
                    }
                    break;

                case SPLITTER_HORIZONTAL_USED:
                    if (moveDirection == Direction.EAST || moveDirection == Direction.WEST) {
                        cancel = true;
                    }
                    break;

                case SPLITTER_VERTICAL_USED:
                    if (moveDirection == Direction.NORTH || moveDirection == Direction.SOUTH) {
                        cancel = true;
                    }

                    break;

            
                default:
                    break;
            }

            switch (moveDirection) {
                case NORTH:
                    rowPos--;
                    break;

                case EAST:
                    columnPos++;
                    break;

                case SOUTH:
                    rowPos++;
                    break;

                case WEST:
                    columnPos--;
                    break;
            }
        }
    }

    public long doPart1(List<String> lines) {
        EnergyGridType[][] grid = new EnergyGridType[lines.size()][lines.get(0).length()];
        boolean[][] energizedGrid = new boolean[lines.size()][lines.get(0).length()];

        for (int i = 0; i < grid.length; i++) {
            String row = lines.get(i);
            for (int j = 0; j < grid[0].length; j++) {
                char charAt = row.charAt(j);

                EnergyGridType setType = EnergyGridType.EMPTY;

                if (charAt == '\\') {
                    setType = EnergyGridType.MIRROR_LEFT;
                } else if (charAt == '/') {
                    setType = EnergyGridType.MIRROR_RIGHT;
                } else if (charAt == '|') {
                    setType = EnergyGridType.SPLITTER_HORIZONTAL;
                } else if (charAt == '-') {
                    setType = EnergyGridType.SPLITTER_VERTICAL;
                }

                grid[i][j] = setType;
                energizedGrid[i][j] = false;
            }
        }

        beam(grid, energizedGrid, 0, 0, Direction.EAST);

        int tally = 0;
        for (int i = 0; i < energizedGrid.length; i++) {
            for (int j = 0; j < energizedGrid[0].length; j++) {
                if (energizedGrid[i][j]) {
                    tally++;
                }
            }
        }
        
        return tally;
    }

    
    public int runBeamRound(EnergyGridType[][] grid , boolean[][] energizedGrid, int row, int col, Direction startDir) {
        beam(grid, energizedGrid, row, col, startDir);

        int tally = 0;
        for (int i = 0; i < energizedGrid.length; i++) {
            for (int j = 0; j < energizedGrid[0].length; j++) {
                if (energizedGrid[i][j]) {
                    tally++;
                }
            }
        }

        for (int i = 0; i < energizedGrid.length; i++) {
            for (int j = 0; j < energizedGrid[0].length; j++) {
                energizedGrid[i][j] = false;
            }

        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == EnergyGridType.SPLITTER_HORIZONTAL_USED) {
                    grid[i][j] = EnergyGridType.SPLITTER_HORIZONTAL;
                } else if (grid[i][j] == EnergyGridType.SPLITTER_VERTICAL_USED) {
                    grid[i][j] = EnergyGridType.SPLITTER_VERTICAL;
                }
            }
        }
        return tally;
    }

    public long doPart2(List<String> lines) {

        EnergyGridType[][] grid = new EnergyGridType[lines.size()][lines.get(0).length()];
        boolean[][] energizedGrid = new boolean[lines.size()][lines.get(0).length()];

        for (int i = 0; i < grid.length; i++) {
            String row = lines.get(i);
            for (int j = 0; j < grid[0].length; j++) {
                char charAt = row.charAt(j);

                EnergyGridType setType = EnergyGridType.EMPTY;

                if (charAt == '\\') {
                    setType = EnergyGridType.MIRROR_LEFT;
                } else if (charAt == '/') {
                    setType = EnergyGridType.MIRROR_RIGHT;
                } else if (charAt == '|') {
                    setType = EnergyGridType.SPLITTER_HORIZONTAL;
                } else if (charAt == '-') {
                    setType = EnergyGridType.SPLITTER_VERTICAL;
                }

                grid[i][j] = setType;
                energizedGrid[i][j] = false;
            }
        }

        int highest = 0;
        for (int i = 0; i < grid[0].length; i++) {
            highest = Math.max(highest, runBeamRound(grid, energizedGrid, 0, i, Direction.SOUTH));
        }

        for (int i = 0; i < grid.length; i++) {
            highest = Math.max(highest, runBeamRound(grid, energizedGrid, i, 0, Direction.EAST));
        }

        for (int i = 0; i < grid[0].length; i++) {
            highest = Math.max(highest, runBeamRound(grid, energizedGrid, grid.length - 1, i, Direction.NORTH));
        }   

        for (int i = 0; i < grid.length; i++) {
            highest = Math.max(highest, runBeamRound(grid, energizedGrid, i, grid[0].length - 1, Direction.WEST));
        }



        
        return highest;
    }

}
