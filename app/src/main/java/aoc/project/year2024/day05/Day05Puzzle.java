package aoc.project.year2024.day05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day05Puzzle {
    public static void main(String[] args) {
        Day05Puzzle puzzle = new Day05Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2024, 5);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 05 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day05Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 05 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2024/day05Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public long doPart1(List<String> lines) {
        List<String> pageOrderRules = lines.subList(0, lines.indexOf(""));
        List<String> pagesToProduce = lines.subList(lines.indexOf("") + 1, lines.size());

        HashMap<Integer, ArrayList<Integer>> orderMap = new HashMap<>();

        for (String line : pageOrderRules) {
            String[] split = line.split("\\|");

            int key = Integer.parseInt(split[0]);
            int value = Integer.parseInt(split[1]);

            if (orderMap.containsKey(key)) {
                orderMap.get(key).add(value);
            } else {
                ArrayList<Integer> newArray = new ArrayList<>();
                newArray.add(value);
                orderMap.put(key, newArray);
            }

        }

        long sum = 0;

        for (String page : pagesToProduce) {
            boolean validPage = true;
            String[] numbers = page.split(",");

            ArrayList<Integer> alreadyChosen = new ArrayList<>();

            int middlePage = Integer.parseInt(numbers[numbers.length / 2]);

            for (String toParse : numbers) {
                int check = Integer.parseInt(toParse);
                alreadyChosen.add(check);
            }

            for (Integer intChoose : alreadyChosen) {
                if (!orderMap.containsKey(intChoose)) {
                    continue;
                }
                int checkIndex = alreadyChosen.indexOf(intChoose);

                for (Integer secondChoose : orderMap.get(intChoose)) {
                    int secondIndex = alreadyChosen.indexOf(secondChoose);
                    if (checkIndex > secondIndex && secondIndex != -1) {
                        System.out.println(intChoose + "-" + secondChoose);
                        validPage = false;
                        break;
                    }
                }

                if (!validPage) {
                    break;
                }

            }

            if (validPage) {
                System.out.println(middlePage);
                sum += middlePage;
            }
        }

        return sum;
    }

    // finished in 1:09:46
    public long doPart2(List<String> lines) {
        List<String> pageOrderRules = lines.subList(0, lines.indexOf(""));
        List<String> pagesToProduce = lines.subList(lines.indexOf("") + 1, lines.size());

        HashMap<Integer, ArrayList<Integer>> orderMap = new HashMap<>();

        for (String line : pageOrderRules) {
            String[] split = line.split("\\|");

            int key = Integer.parseInt(split[0]);
            int value = Integer.parseInt(split[1]);

            if (orderMap.containsKey(key)) {
                orderMap.get(key).add(value);
            } else {
                ArrayList<Integer> newArray = new ArrayList<>();
                newArray.add(value);
                orderMap.put(key, newArray);
            }

        }
        long sum = 0;

        for (String page : pagesToProduce) {
            boolean validPage = true;
            String[] numbers = page.split(",");

            ArrayList<Integer> alreadyChosen = new ArrayList<>();


            for (String toParse : numbers) {
                int check = Integer.parseInt(toParse);
                alreadyChosen.add(check);
            }

            for (Integer intChoose : alreadyChosen) {
                if (!orderMap.containsKey(intChoose)) {
                    continue;
                }
                int checkIndex = alreadyChosen.indexOf(intChoose);

                for (Integer secondChoose : orderMap.get(intChoose)) {
                    int secondIndex = alreadyChosen.indexOf(secondChoose);
                    if (checkIndex > secondIndex && secondIndex != -1) {
                        System.out.println(intChoose + "-" + secondChoose);
                        validPage = false;
                        break;
                    }
                }

                if (!validPage) {
                    break;
                }
            }

            alreadyChosen.sort((a, b) -> {
                if (orderMap.containsKey(a) && orderMap.get(a).contains(b)) {
                    return -1;
                } else if (orderMap.containsKey(b) && orderMap.get(b).contains(a)) {
                    return 1;
                }
                return 0;
            });

            int middlePage = alreadyChosen.get(numbers.length / 2);

            if (!validPage) {
                System.out.println(middlePage);
                sum += middlePage;
            }
        }

        return sum;
    }

}
