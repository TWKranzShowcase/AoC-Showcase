package aoc.project.year2023.day03;

import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day03Puzzle {
    public static void main(String[] args) {
        Day03Puzzle puzzle = new Day03Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 3);

        // puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 03 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day03Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 03 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day03Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public int addUpSchematicsAroundAStar(int[][] numberArray, int cordOne, int cordTwo) {
        int totalSchematicValue = 0;
        for (int row = 0; row < 3; row++) {
            int checkRow = cordOne + row - 1;
            if (checkRow < 0 || checkRow >= numberArray.length) {
                continue;
            }

            for (int col = 0; col < 3; col++) {
                int checkCol = cordTwo + col - 1;
                if (checkCol < 0 || checkCol >= numberArray[0].length) {
                    continue;
                }

                if (numberArray[checkRow][checkCol] >= 0) {
                    totalSchematicValue += fetchNumFromTableUsingCords(numberArray, checkRow, checkCol, true);
                }
            }
        }
        return totalSchematicValue;
    }

    public int getGearRatioOfAStar(int[][] numberArray, int cordOne, int cordTwo) {
        int totalSchematicValue = 1;
        int totalGears = 0;

        for (int row = 0; row < 3; row++) {
            int checkRow = cordOne + row - 1;
            if (checkRow < 0 || checkRow >= numberArray.length) {
                continue;
            }

            for (int col = 0; col < 3; col++) {
                int checkCol = cordTwo + col - 1;
                if (checkCol < 0 || checkCol >= numberArray[0].length) {
                    continue;
                }

                if (numberArray[checkRow][checkCol] >= 0) {
                    totalGears++;
                    totalSchematicValue *= fetchNumFromTableUsingCords(numberArray, checkRow, checkCol, true);
                } 
            }
        }

        if (totalGears == 2) {
            return totalSchematicValue;
        } else {
            return 0;
        }
    }

    public int fetchNumFromTableUsingCords(int[][] numberArray, int cordOne, int cordTwo, boolean erase) {
        int startOfNumber = cordTwo;
        while ((startOfNumber != 0) && numberArray[cordOne][startOfNumber - 1] >= 0) {
            startOfNumber--;
        }

        int endOfNumber = cordTwo;
        while ((endOfNumber != numberArray[0].length - 1) && numberArray[cordOne][endOfNumber + 1] >= 0) {
            endOfNumber++;
        }

        int totalNumber = 0;
        for (int i = startOfNumber; i <= endOfNumber; i++) {
            totalNumber = totalNumber * 10 + numberArray[cordOne][i];
            if (erase) {
                numberArray[cordOne][i] = -1; // prevent checking this again
            }
        }

        return totalNumber;
    }

    public long doPart1(List<String> lines) {
        int[][] numberArray = new int[lines.size()][lines.get(0).length()];

        int sum = 0;

        for (int i = 0; i < lines.size(); i++) {
            String chosenLine = lines.get(i);
            for (int innerCharacter = 0; innerCharacter < lines.get(i).length(); innerCharacter++) {
                char characterAtPoint = chosenLine.charAt(innerCharacter);

                int setCharacter = -1;
                if (Character.isDigit(characterAtPoint)) {
                    setCharacter = characterAtPoint - 48;
                } else if (characterAtPoint != '.') {
                    setCharacter = -2;
                }

                numberArray[i][innerCharacter] = setCharacter;
            }
        }

        for (int outer = 0; outer < numberArray.length; outer++) {
            for (int inner = 0; inner < numberArray[0].length; inner++) {
                int chosenDigit = numberArray[outer][inner];

                if (chosenDigit == -2) {
                    int addSum = addUpSchematicsAroundAStar(numberArray, outer, inner);
                    // System.out.println(addSum);
                    sum += addSum;
                } 
            }
        }

        return sum;
    }

    public long doPart2(List<String> lines) {
        int[][] numberArray = new int[lines.size()][lines.get(0).length()];

        int sum = 0;

        for (int i = 0; i < lines.size(); i++) {
            String chosenLine = lines.get(i);
            for (int innerCharacter = 0; innerCharacter < lines.get(i).length(); innerCharacter++) {
                char characterAtPoint = chosenLine.charAt(innerCharacter);

                int setCharacter = -1;
                if (Character.isDigit(characterAtPoint)) {
                    setCharacter = characterAtPoint - 48;
                } else if (characterAtPoint != '.') {
                    setCharacter = -2;
                }

                numberArray[i][innerCharacter] = setCharacter;
            }
        }

        for (int outer = 0; outer < numberArray.length; outer++) {
            for (int inner = 0; inner < numberArray[0].length; inner++) {
                int chosenDigit = numberArray[outer][inner];

                if (chosenDigit == -2) {
                    int addSum = getGearRatioOfAStar(numberArray, outer, inner);
                    sum += addSum;
                } 
            }
        }

        return sum;
    }

}
