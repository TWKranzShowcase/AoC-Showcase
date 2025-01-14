package aoc.project.year2024.day14;

import java.util.ArrayList;
import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;
import org.checkerframework.checker.units.qual.s;

public class Day14Puzzle {
    public static void main(String[] args) {
        Day14Puzzle puzzle = new Day14Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2024, 14);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 14 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day14Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 14 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day14Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public long doPart1(List<String> lines) {
        int[] values = new int[4];

        int ITERATIONS = 100;

        for (String line : lines) {
            int posIndex = line.indexOf("p=") + 2;

            String[] posCoords = line.substring(posIndex, line.indexOf(" ", posIndex)).split(",");

            int velIndex = line.indexOf("v=") + 2;

            String[] velCoords = line.substring(velIndex, line.length()).split(",");

            int finalX = Integer.parseInt(posCoords[0]) + Integer.parseInt(velCoords[0]) * ITERATIONS;
            finalX %= 101;

            if (finalX < 0) {
                finalX = 101 + finalX;
            }

            int finalY = Integer.parseInt(posCoords[1]) + Integer.parseInt(velCoords[1]) * ITERATIONS;
            finalY %= 103;

            if (finalY < 0) {
                finalY = 103 + finalY;
            }

            System.out.println(finalX + "," + finalY);

            if (finalX == 50 || finalY == 51) {
                continue;
            }

            if (finalX > 50) {
                if (finalY > 51) {
                    values[3]++;
                } else {
                    values[2]++;
                }
            } else {
                if (finalY > 51) {
                    values[1]++;
                } else {
                    values[0]++;
                }
            }

        }

        System.out.println();
        System.out.println(values[0]);
        System.out.println(values[1]);
        System.out.println(values[2]);
        System.out.println(values[3]);

        // Part 1 code goes here
        return values[0] * values[1] * values[2] * values[3];
    }

    public class BathroomBot {
        public int x;
        public int y;
        public int xVelocity;
        public int yVelocity;

        public int xLimit;
        public int yLimit;

        public BathroomBot(int xStart, int yStart, int xVelocity, int yVelocity) {
            x = xStart;
            y = yStart;
            this.xVelocity = xVelocity;
            this.yVelocity = yVelocity;
            
            xLimit = 101;
            yLimit = 103;
        }

        public void advance(int seconds) {
            x += xVelocity * seconds;
            y += yVelocity * seconds;

            if (x >= xLimit) {
                x %= xLimit;
            }

            if (y >= yLimit) {
                y %= yLimit;
            }

            if (x < 0) {
                x = xLimit + x;
            }

            if (y < 0) {
                y = yLimit + y;
            }
        }
    }

    public long doPart2(List<String> lines) {
        int ITERATIONS = 100;

        ArrayList<BathroomBot> bots = new ArrayList<>();
        int time = 0;

        for (String line : lines) {
            int posIndex = line.indexOf("p=") + 2;

            String[] posCoords = line.substring(posIndex, line.indexOf(" ", posIndex)).split(",");

            int velIndex = line.indexOf("v=") + 2;

            String[] velCoords = line.substring(velIndex, line.length()).split(",");

            BathroomBot newBot = new BathroomBot(Integer.parseInt(posCoords[0]), Integer.parseInt(posCoords[1]), Integer.parseInt(velCoords[0]), Integer.parseInt(velCoords[1]));
            bots.add(newBot);
        }

        boolean[][] botArray = new boolean[101][103];
        boolean done = false;

        int iteration = 0;

        while (!done) {
            iteration++;
            bots.forEach((a) -> {a.advance(1);});

            botArray = new boolean[101][103];

            for (BathroomBot bot : bots) {
                botArray[bot.x][bot.y] = true;
            }

            boolean showGrid = false;

            int closenessFactor = 0;
            for (int i = 1; i < botArray.length - 1; i++) {
                for (int j = 1; j < botArray[0].length - 1; j++) {
                    if (botArray[i][j] && (botArray[i - 1][j - 1]
                    && botArray[i + 1][j + 1]
                    && botArray[i + 1][j - 1] 
                    && botArray[i - 1][j + 1])) {
                        closenessFactor ++;
                    }
                }
            }

            if (closenessFactor > 10) {
                showGrid = true;
                System.out.println(closenessFactor);
            }

            if (!showGrid) {
                continue;
            }

            System.out.println(iteration);
            for (int i = 0; i < botArray.length; i++) {
                for (int j = 0; j < botArray[0].length; j++) {
                    System.out.print(botArray[i][j] ? "b" : ".");
                }
                System.out.println();
            }
            System.out.println();
        }

        return -1;
    }

}
