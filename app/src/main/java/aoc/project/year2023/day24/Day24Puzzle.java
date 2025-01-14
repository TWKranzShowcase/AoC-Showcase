package aoc.project.year2023.day24;

import java.util.ArrayList;
import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day24Puzzle {
    public static void main(String[] args) {
        Day24Puzzle puzzle = new Day24Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 24);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 24 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day24Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 24 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day24Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    record Vector3(long x, long y, long z) {
    }

    record Hailstone(Vector3 position, Vector3 velocity) {
    }

    public long doPart1(List<String> lines) {
        ArrayList<Hailstone> stones = new ArrayList<>();

        for (String line : lines) {
            String[] split = line.split(" @ ");

            String[] positions = split[0].split("(,  )|(, )");
            String[] velocities = split[1].split("(,  )|(, )");

            if (velocities[0].startsWith(" ")) {
                velocities[0] = velocities[0].strip();
            }

            stones.add(new Hailstone(
                    new Vector3(Long.parseLong(positions[0]), Long.parseLong(positions[1]),
                            Long.parseLong(positions[2])),
                    new Vector3(Long.parseLong(velocities[0]), Long.parseLong(velocities[1]),
                            Long.parseLong(velocities[2]))));
        }

        long cutoffStart = 200000000000000L;
        long cutoffEnd = 400000000000000L;

        if (lines.size() == 5) {
            cutoffStart = 7;
            cutoffEnd = 25;
            System.out.println("testing");
        }

        int count = 0;

        long sum = 0;
        for (int i = 0; i < stones.size() - 1; i++) {
            Hailstone firstStone = stones.get(i);

            double firstXVelocity = firstStone.velocity().x();
            double firstYVelocity = firstStone.velocity().y();

            double firstSlope = firstYVelocity / firstXVelocity;

            double firstYIntercept = firstStone.position.y() - firstStone.position.x() * firstSlope;

            for (int j = i + 1; j < stones.size(); j++) {
                Hailstone secondStone = stones.get(j);

                double secondXVelocity = secondStone.velocity().x();
                double secondYVelocity = secondStone.velocity().y();
                double secondSlope = secondYVelocity / secondXVelocity;

                double secondYIntercept = secondStone.position.y() - secondStone.position.x() * secondSlope;

                double xValue = (secondYIntercept - firstYIntercept) / (firstSlope - secondSlope);
                double yValue = xValue * firstSlope + firstYIntercept;

                // System.out.println();
                // count++;

                if (secondSlope == firstSlope) {
                    if (firstYIntercept == secondYIntercept) {
                        sum++;
                    }
                    System.out.println(secondSlope);
                    continue;
                }

                double time = (xValue - firstStone.position().x()) / firstXVelocity;
                double time2 = (xValue - secondStone.position().x()) / secondXVelocity;

                if (yValue >= cutoffStart && yValue <= cutoffEnd && xValue >= cutoffStart && xValue <= cutoffEnd
                        && (time > 0 && time2 > 0)) {
                    sum++;
                }
            }
        }
        return sum;
    }

    public double calculateCollisionTime(double startOne, double velocityOne, double startTwo, double velocityTwo) {
        return (startOne - startTwo) / (velocityTwo - velocityOne);
    }

    public double calculateCollisionVelocity(double startOne, double velocityOne, double startTwo, double time) {
        return (startOne - startTwo + velocityOne * time) / time;
    }

    // couldnt figure this one out so I used a python library called z3 to solve the resulting system of equations, commented code is completely nonfunctional
    public long doPart2(List<String> lines) {
        // ArrayList<Hailstone> stones = new ArrayList<>();

        // for (String line : lines) {
        //     String[] split = line.split(" @ ");

        //     String[] positions = split[0].split("(,  )|(, )");
        //     String[] velocities = split[1].split("(,  )|(, )");

        //     if (velocities[0].startsWith(" ")) {
        //         velocities[0] = velocities[0].strip();
        //     }

        //     stones.add(new Hailstone(
        //             new Vector3(Long.parseLong(positions[0]), Long.parseLong(positions[1]),
        //                     Long.parseLong(positions[2])),
        //             new Vector3(Long.parseLong(velocities[0]), Long.parseLong(velocities[1]),
        //                     Long.parseLong(velocities[2]))));
        // }

        // NumberRange xRange = new NumberRange(Integer.MIN_VALUE, Integer.MAX_VALUE);

        // ArrayList<Hailstone> stonesToTest = new ArrayList<>();

        // boolean madeChange = true;
        // int iterations = 0;

        // stones.sort((a, b) -> {
        //     int sort = (int) (a.velocity().x() - b.velocity().x());

        //     if (sort == 0) {
        //         return (int) (a.position().x() / 100000- b.position().x() / 100000);
        //     }
        //     return sort;
        // });
        // NumberRange validVelocities = new NumberRange(Integer.MIN_VALUE, Integer.MAX_VALUE);
        // madeChange = false;
        // int testValue = (int) (xRange.getStart() / 2 + xRange.getEnd() / 2);

        // boolean cont = false;
        // boolean headLeft = false;
        // long lastVelocity = 0;

        // ArrayList<Long> holdList = new ArrayList<>();
        // for (int i = 0; i < stones.size(); i++) {
        //     Hailstone stone = stones.get(i);

        //     double stoneXPosition = stone.position().x();
        //     double stoneXVelocity = stone.velocity().x();

        //     // System.out.println((long) stoneXPosition + ": x" + stoneXVelocity + " y" + stone.velocity().y() + " z" + stone.velocity().z());

        //     if (lastVelocity == stoneXVelocity) {
        //         holdList.add((long) stoneXPosition);
        //     } else {
        //         holdList.sort(null);

        //         if (holdList.size() >= 3) {
        //             System.out.println(lastVelocity);
        //             // System.out.println((double) (holdList.get(1) - holdList.get(0)) / (holdList.get(2) - holdList.get(1)));
        //             double timeRatio = (double) (holdList.get(1) - holdList.get(0)) / (holdList.get(2) - holdList.get(1));
        //             System.out.println(timeRatio);

        //             System.out.println();
        //         }
            
        //         holdList.clear();
        //         lastVelocity = (long) stoneXVelocity;
        //         holdList.add((long) stoneXPosition);
        //     }

        // }

        // // for (int i = 0; i < stones.size() - 1; i++) {
        // // Hailstone firstStone = stones.get(i);

        // // double firstXVelocity = firstStone.velocity().x();
        // // double firstYVelocity = firstStone.velocity().y();
        // // double firstZVelocity = firstStone.velocity().z();

        // // double firstXPosition = firstStone.position().x();
        // // double firstYPosition = firstStone.position().y();
        // // double firstZPosition = firstStone.position().z();
        // // }
        return -1;
    }

}
