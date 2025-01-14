package aoc.project.year2023.day04;

import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day04Puzzle {
    public static void main(String[] args) {
        Day04Puzzle puzzle = new Day04Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 4);

        // puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 04 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day04Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 04 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day04Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public int scoreACard(String line, boolean matchesOnly) {
        int cardValue = 0;

        boolean parsingWins = true;
        
        int[] winningNumbers = new int[10];
        int currentWin = 0;

        for (int characterPos = line.indexOf(':'); characterPos < line.length(); characterPos++) {
            char characterAtPoint = line.charAt(characterPos);
            if (characterAtPoint == '|') {
                parsingWins = false;
            } else if (parsingWins && Character.isDigit(characterAtPoint) && !Character.isDigit(line.charAt(characterPos - 1))) {
                winningNumbers[currentWin] = AocUtil.parseNumberInt(line, characterPos);
                currentWin++;
            } else if (Character.isDigit(characterAtPoint) && !Character.isDigit(line.charAt(characterPos - 1))) {
                int possibleNumb = AocUtil.parseNumberInt(line, characterPos);
                for (int winToCheck = 0; winToCheck < 10; winToCheck++) {
                    if (winningNumbers[winToCheck] == possibleNumb) {
                        if (matchesOnly) {
                            cardValue++;
                        } else {
                            cardValue = cardValue == 0 ? 1 : cardValue * 2;
                        }
                        break;
                    }
                }
            }
        }

        return cardValue;
    }

    public long doPart1(List<String> lines) {
        int totalCardValue = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            totalCardValue += scoreACard(line, false);
        }
        return totalCardValue;
    }

    public long doPart2(List<String> lines) {
        long totalCardValue = 0;

        long[] cardCounts = new long[lines.size()];

        for (int i = 0; i < lines.size(); i++) {
            cardCounts[i] = 1;
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int matches = scoreACard(line, true);

            for (int scoreIteration = 1; scoreIteration <= matches; scoreIteration++) {
                int cardRecieved = scoreIteration + i;
                if (cardRecieved > lines.size()) {
                   break;
                }
                System.out.println("adding " + cardCounts[i] + " cards to card " + (cardRecieved + 1));
                cardCounts[cardRecieved] += cardCounts[i];
            }
        }

        for (int i = 0; i < lines.size(); i++) {
            totalCardValue += cardCounts[i];
        }

        return totalCardValue;
    }

}
