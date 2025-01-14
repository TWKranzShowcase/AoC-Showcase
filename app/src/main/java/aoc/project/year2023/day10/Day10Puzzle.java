package aoc.project.year2023.day10;

import java.util.ArrayList;
import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day10Puzzle {
    public static void main(String[] args) {
        Day10Puzzle puzzle = new Day10Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 10);

        // puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 10 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day10Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 10 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day10Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public long doPart1(List<String> lines) {
        AocPipe[][] grid = new AocPipe[lines.size()][lines.get(0).length()];
        ArrayList<AocPipe> allPipes = new ArrayList<>();

        AocPipe startPipe = new AocPipe(0, 0, 'S', grid); // set a default

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char pipeType = line.charAt(j);
                AocPipe newPipe = new AocPipe(i, j, pipeType, grid);
                allPipes.add(newPipe);
                grid[i][j] = newPipe;
                if (pipeType == 'S') {
                    startPipe = newPipe;
                }
            }
        }

        for (int i = 0; i < allPipes.size(); i++) {
            AocPipe pipe = allPipes.get(i);
            pipe.makeConnections();
        }

        ArrayList<AocPipe> connectedToStart = startPipe.getConnectedPipes();
        ArrayList<AocPipe> lastIterationPipes = new ArrayList<>();
        lastIterationPipes.add(startPipe);
        lastIterationPipes.add(startPipe);

        int steps = 1;
        boolean reachedPipe = false;

        while (!reachedPipe) {
            ArrayList<AocPipe> newConnected = new ArrayList<>();
            for (int i = 0; i < connectedToStart.size(); i++) {
                AocPipe pipe = connectedToStart.get(i);
                AocPipe lastPipe = lastIterationPipes.get(i);

                ArrayList<AocPipe> choices = pipe.getConnectedPipes();
                if (choices.size() < 2) {
                    continue;
                }
                choices.remove(lastPipe);
                newConnected.add(choices.get(0));
            }
            lastIterationPipes.clear();
            lastIterationPipes.addAll(connectedToStart);
            connectedToStart.clear();
            connectedToStart.addAll(newConnected);
            steps++;

            if (connectedToStart.get(0) == connectedToStart.get(1)) {
                reachedPipe = true;
            }
        }

        // Part 1 code goes here
        return steps;
    }

    public void spreadOutside(AocPipe grid[][], char spreadCharacter) {
        boolean noChanges = false;
        while (noChanges == false) {
            noChanges = true;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    AocPipe pipeAt = grid[i][j];
                    if (pipeAt.getShape() == spreadCharacter) {
                        ArrayList<AocPipe> pipes = pipeAt.getNearbyPipes();
                        for (int k = 0; k < pipes.size(); k++) {
                            AocPipe otherPipe = pipes.get(k);
                            if (otherPipe.getShape() != 'P' && otherPipe.getShape() != spreadCharacter) {
                                otherPipe.setShape(spreadCharacter);
                                noChanges = false;
                            }
                        }

                    }
                }
            }
        }
    }

    public void debug(AocPipe[][] grid, ArrayList<AocPipe> loopPipes, ArrayList<AocPipe> badPipes) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                AocPipe pipeAt = grid[i][j];
                if (loopPipes.contains(pipeAt)) {

                } else if (badPipes.contains(pipeAt)) {
                    pipeAt.setShape('O'); // outside the loop
                }
            }
        }
        printGrid(grid);
        System.out.println();
    }

    public void printGrid(AocPipe[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j].getShape());
            }
            System.out.println();
        }
    }

    public long doPart2(List<String> lines) {
        AocPipe[][] grid = new AocPipe[lines.size()][lines.get(0).length()];
        ArrayList<AocPipe> allPipes = new ArrayList<>();

        AocPipe startPipe = new AocPipe(0, 0, 'S', grid); // set a default

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char pipeType = line.charAt(j);
                AocPipe newPipe = new AocPipe(i, j, pipeType, grid);
                allPipes.add(newPipe);
                grid[i][j] = newPipe;
                if (pipeType == 'S') {
                    startPipe = newPipe;
                }
            }
        }

        for (int i = 0; i < allPipes.size(); i++) {
            AocPipe pipe = allPipes.get(i);
            pipe.makeConnections();
        }

        ArrayList<AocPipe> connectedToStart = startPipe.getConnectedPipes();
        ArrayList<AocPipe> lastIterationPipes = new ArrayList<>();
        lastIterationPipes.add(startPipe);
        lastIterationPipes.add(startPipe);

        boolean reachedPipe = false;

        AocPipe finalPipe = startPipe; // placeholder value
        while (!reachedPipe) {
            ArrayList<AocPipe> newConnected = new ArrayList<>();
            for (int i = 0; i < connectedToStart.size(); i++) {
                AocPipe pipe = connectedToStart.get(i);
                AocPipe lastPipe = lastIterationPipes.get(i);

                ArrayList<AocPipe> choices = pipe.getConnectedPipes();
                if (choices.size() < 2) {
                    continue;
                }
                choices.remove(lastPipe);
                newConnected.add(choices.get(0));
            }
            lastIterationPipes.clear();
            lastIterationPipes.addAll(connectedToStart);
            connectedToStart.clear();
            connectedToStart.addAll(newConnected);

            if (connectedToStart.get(0) == connectedToStart.get(1)) {
                reachedPipe = true;
                finalPipe = connectedToStart.get(0);
            }
        }

        boolean START_SIDE = true;

        ArrayList<AocPipe> loopPipes = new ArrayList<>();
        ArrayList<AocPipe> badPipes = new ArrayList<>();
        loopPipes.add(finalPipe);
        // badPipes.addAll(finalPipe.getAdjacentPipes(START_SIDE));
        AocPipe lastPipe = finalPipe;
        AocPipe currentPipe = finalPipe.getConnectedPipes().get(1);
        AocPipe secondStart = finalPipe.getConnectedPipes().get(0);
        reachedPipe = false;
        boolean side = START_SIDE;

        // char lastAngle = ' ';

        while (true) {
            // debug(grid, loopPipes, badPipes);
            // check if this is an angle
            if (currentPipe.swapSideValid(lastPipe.getShape())) {
                System.out.println("swap");
                side = !side;
            }
            System.out.println(currentPipe.getShape() + ": " + (side ? "outside" : "inside"));
            badPipes.addAll(currentPipe.getAdjacentPipes(side));

            loopPipes.add(currentPipe);
            ArrayList<AocPipe> choices = currentPipe.getConnectedPipes();
            choices.remove(lastPipe);

            // new pipe
            lastPipe = currentPipe;
            currentPipe = choices.get(0);


            // System.out.println(currentPipe.getShape());
            // System.out.println("outside: " + side);
            // System.out.println(currentShape + " to " + currentPipe.getShape());

            // if (isAngle) {
            // lastAngle = oldAngle;
            // }

            // this is for testing, remove later

            // for (int i = 0; i < grid.length; i++) {
            // for (int j = 0; j < grid[0].length; j++) {
            // AocPipe pipeAt = grid[i][j];
            // if (loopPipes.contains(pipeAt)) {

            // } else if (badPipes.contains(pipeAt)) {
            // pipeAt.setShape('O'); // outside the loop
            // }
            // }
            // }
            // printGrid(grid);
            // System.out.println();

            if (currentPipe == startPipe) {
                if (reachedPipe) {
                    break;
                } else {
                    System.out.println("new");
                    loopPipes.add(currentPipe);
                    side = START_SIDE;
                    reachedPipe = true;
                    currentPipe = secondStart;
                    lastPipe = finalPipe;
                }
            }
        }

        int area = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                AocPipe pipeAt = grid[i][j];
                if (loopPipes.contains(pipeAt)) {
                    pipeAt.setShape('P'); // part of the loop
                } else if (pipeAt.isNearBorder() || badPipes.contains(pipeAt)) {
                    pipeAt.setShape('O'); // outside the loop
                }
            }
        }

        printGrid(grid);
        System.out.println();
        spreadOutside(grid, 'O');
        spreadOutside(grid, 'I');
        printGrid(grid);

        // for (int i = 0; i < grid.length; i++) {
        // boolean activation = false;
        // int activationPoint = 0;
        // for (int j = 0; j < grid[0].length; j++) {
        // AocPipe pipeAt = grid[i][j];
        // if (loopPipes.contains(pipeAt)) {
        // if (activation) {
        // for (int set = activationPoint + 1; set < j; set++) {
        // grid[i][set].setShape('o');
        // }
        // activation = false;
        // } else {
        // activationPoint = j;
        // activation = true;
        // }
        // }
        // // } else {
        // // System.out.print("-");
        // // }
        // }
        // // System.out.println();
        // }

        // for (int i = 0; i < grid[0].length; i++) {
        // boolean activation = false;
        // int activationPoint = 0;
        // for (int j = 0; j < grid.length; j++) {
        // AocPipe pipeAt = grid[j][i];
        // if (loopPipes.contains(pipeAt)) {
        // if (activation) {
        // for (int set = activationPoint + 1; set < j; set++) {
        // if (grid[set][i].getShape() == 'o') {
        // grid[set][i].setShape('e');
        // }
        // }
        // activation = false;
        // } else {
        // activationPoint = j;
        // activation = true;
        // }
        // }

        // // } else {
        // // System.out.print("-");
        // // }
        // }
        // // System.out.println();
        // }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                AocPipe pipeAt = grid[i][j];
                if (pipeAt.getShape() != 'P' && pipeAt.getShape() != 'O') {
                    area++;
                }
            }
        }

        return area;
    }
}
