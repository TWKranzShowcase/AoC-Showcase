package aoc.project.year2023.day15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day15Puzzle {
    public static void main(String[] args) {
        Day15Puzzle puzzle = new Day15Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 15);

        // puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 15 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day15Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 15 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day15Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public int hashProtocall(String toHash) {
        int currentValue = 0;
        for (int i = 0; i < toHash.length(); i++) {
            char charAt = toHash.charAt(i);

            currentValue += charAt;
            currentValue *= 17;
            currentValue %= 256;
        }
        return currentValue;
    }

    public long doPart1(List<String> lines) {
        long tally = 0;

        String lastString = lines.get(lines.size() - 1);
        lines.remove(lines.size() - 1);
        lines.add(lastString + ",");

        HashMap<String, Integer> protocallValues = new HashMap<>();

        // Part 1 code goes here

        for (String line : lines) {
            int commaIndex = line.indexOf(',');
            while (commaIndex > 0) {
                String cut = line.substring(0, commaIndex);
                int value = hashProtocall(cut);
                protocallValues.put(cut, value);
                tally += value;

                line = line.substring(commaIndex + 1);
                commaIndex = line.indexOf(',');
            }
        }
        return tally;
    }

    public int removeLensFromArray(ArrayList<BoxOperation> lensArray, String labelToRemove) {
        for (int i = 0; i < lensArray.size(); i++) {
            BoxOperation lens = lensArray.get(i);
            if (lens.label().equals(labelToRemove)) {
                int removeIndex = i;
                lensArray.remove(lens);
                return removeIndex;
            }
        }
        return -1;
    }

    public long doPart2(List<String> lines) {
        long tally = 0;

        String lastString = lines.get(lines.size() - 1);
        lines.remove(lines.size() - 1);
        lines.add(lastString + ",");

        ArrayList<BoxOperation> operationList = new ArrayList<>();

        for (String line : lines) {
            int commaIndex = line.indexOf(',');
            while (commaIndex > 0) {
                String cut = line.substring(0, commaIndex);

                String labelCut = "";
                int label = -1;
                int focalLength = -1;
                char operationType = 'f'; // placeholder

                for (int i = 0; i < cut.length(); i++) {
                    char charAtPoint = cut.charAt(i);

                    if (charAtPoint == '=' || charAtPoint == '-') {
                        labelCut = cut.substring(0, i);
                        label = hashProtocall(labelCut);
                        operationType = charAtPoint;
                        if (charAtPoint == '=') {
                            focalLength = cut.charAt(i + 1) - 48;
                        }
                        break;
                    }
                    
                }
                BoxOperation newOperation = new BoxOperation(labelCut, label, operationType, focalLength);
                operationList.add(newOperation);

                line = line.substring(commaIndex + 1);
                commaIndex = line.indexOf(',');

            }
        }

        HashMap<Integer, ArrayList<BoxOperation>>  boxTable = new HashMap<>();
        for (BoxOperation operation : operationList) {
            int boxPosition = operation.box();
            char operationType = operation.operationType();

            if (!boxTable.containsKey(boxPosition)) {
                boxTable.put(boxPosition, new ArrayList<>());
            }

            ArrayList<BoxOperation> lensArray = boxTable.get(boxPosition);

            if (operationType == '-') {
                removeLensFromArray(lensArray, operation.label());
            } else {
                int addIndex = removeLensFromArray(lensArray, operation.label());
                if (addIndex >= 0) {
                    lensArray.add(addIndex, operation);
                } else {
                    lensArray.add(operation);
                }
            }
        }

        for (Entry<Integer, ArrayList<BoxOperation>> entry : boxTable.entrySet()) {
            int mult = entry.getKey() + 1;
            ArrayList<BoxOperation> valuesToSum = entry.getValue();

            for (int i = 0; i < valuesToSum.size(); i++) {

                BoxOperation operation = valuesToSum.get(i);
                System.out.println(operation.label());

                tally += operation.focalLength() * mult * (i + 1);
            }
        }

        return tally;
    }

}
