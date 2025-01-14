package aoc.project.year2023.day02;

import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day02Puzzle {
    public static void main(String[] args) {
        Day02Puzzle puzzle = new Day02Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 2);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 02 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day02Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 02 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day02Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public long doPart1(List<String> lines) {
        int MAX_RED = 12;
        int MAX_GREEN = 13;
        int MAX_BLUE = 14;

        int total = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int gameId = i + 1;
            boolean validGame = true;

            String lineWithoutStart = line.substring(line.indexOf(":") + 1);
            
            for (int characterIndex = 0; characterIndex < lineWithoutStart.length(); characterIndex++) {
                if (Character.isDigit(lineWithoutStart.charAt(characterIndex))) {
                    String fullDigit = lineWithoutStart.substring(characterIndex, lineWithoutStart.indexOf(" ", characterIndex));
                    int convertedNumber = Integer.parseInt(fullDigit);

                    char color = lineWithoutStart.charAt(lineWithoutStart.indexOf(" ", characterIndex) + 1);

                    switch (color) {
                        case 'r':
                            if (convertedNumber > MAX_RED) {
                                validGame = false;
                            }
                            break;
                        case 'b':
                            if (convertedNumber > MAX_BLUE) {
                                validGame = false;
                            }
                            break;
                        case 'g':
                            if (convertedNumber > MAX_GREEN) {
                                validGame = false;
                            }
                            break;
                    }

                }
            }

            if (validGame) {
             total += gameId;
            }
        }
        return total;
    }

    public long doPart2(List<String> lines) {
        int total = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            int maxRed = 0;
            int maxBlue = 0;
            int maxGreen = 0;

            String lineWithoutStart = line.substring(line.indexOf(":") + 1);
            
            for (int characterIndex = 0; characterIndex < lineWithoutStart.length(); characterIndex++) {
                if (Character.isDigit(lineWithoutStart.charAt(characterIndex))) {
                    String fullDigit = lineWithoutStart.substring(characterIndex, lineWithoutStart.indexOf(" ", characterIndex));
                    int convertedNumber = Integer.parseInt(fullDigit);

                    char color = lineWithoutStart.charAt(lineWithoutStart.indexOf(" ", characterIndex) + 1);

                    switch (color) {
                        case 'r':

                            if (maxRed < convertedNumber) {
                                maxRed = convertedNumber;
                            }
                          
                            break;
                        case 'b':

                            if (maxBlue < convertedNumber) {
                                maxBlue = convertedNumber;
                            }

                            break;
                        case 'g':

                            if (maxGreen < convertedNumber) {
                                maxGreen = convertedNumber;
                            }

                            break;
                    }

                }
            }

             total += maxRed * maxGreen * maxBlue;
        }
        return total;
    }

}
