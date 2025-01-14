package aoc.project.year2023.day01;

import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day01Puzzle {
    public static void main(String[] args) {
        Day01Puzzle puzzle = new Day01Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 1);

        //puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 01 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day01Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 01 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day01Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    

    public long doPart1(List<String> lines) {
        int total = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            int firstNumber = -1; // this cant occur naturally so its a good placeholder
            int lastNumber = -1;

            for (int characterLoop = 0; characterLoop < line.length(); characterLoop++) {
                char characterAtPoint = line.charAt(characterLoop);
                if (Character.isDigit(characterAtPoint)) {
                    int newDigit = (int) characterAtPoint - 48;

                    if (firstNumber == -1) {
                        firstNumber = newDigit;
                    }

                    lastNumber = newDigit;
                }
            } 

            System.out.println(firstNumber + " " + lastNumber);

            total += firstNumber * 10 + lastNumber;
        }

        return total;
    }

    public long doPart2(List<String> lines) {
        int total = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            int firstNumber = -1; // this cant occur naturally so its a good placeholder
            int lastNumber = -1;

            for (int characterLoop = 0; characterLoop < line.length(); characterLoop++) {
                char characterAtPoint = line.charAt(characterLoop);
                int overrideNumber = -1;

                String substringThree = line.substring(characterLoop, Math.min(characterLoop + 3, line.length()));
                switch (substringThree) {
                    case "one":
                        overrideNumber = 1;
                        break;
                        
                    case "two":
                        overrideNumber = 2;
                        break;
                        
                    case "six":
                        overrideNumber = 6;
                        break;
                }

                
                String substringFour = line.substring(characterLoop, Math.min(characterLoop + 4, line.length()));
                switch (substringFour) {
                    case "four":
                        overrideNumber = 4;
                        break;

                    case "five":
                        overrideNumber = 5;
                        break;
                        
                    case "nine":
                        overrideNumber = 9;
                        break;
                }

                String substringFive = line.substring(characterLoop, Math.min(characterLoop + 5, line.length()));
                switch (substringFive) {
                    case "seven":
                        overrideNumber = 7;
                        break;

                    case "three":
                        overrideNumber = 3;
                        break;
                        
                    case "eight":
                        overrideNumber = 8;
                        break;
                }

                if (Character.isDigit(characterAtPoint) || overrideNumber != -1) {
                    int newDigit = overrideNumber != -1 ? overrideNumber : (int) characterAtPoint - 48;

                    if (firstNumber == -1) {
                        firstNumber = newDigit;
                    }
                    
                    lastNumber = newDigit;
                }
            } 

            System.out.println(firstNumber + " " + lastNumber);

            total += firstNumber * 10 + lastNumber;
        }

        return total;
    }

}
