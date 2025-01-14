package aoc.project.year2023.day19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;
import aoc.project.year2023.day05.NumberRange;

import org.apache.commons.lang3.time.StopWatch;

public class Day19Puzzle {
    private HashMap<String, ArrayList<WorkflowInstruction>> workflowMap;

    public static void main(String[] args) {
        Day19Puzzle puzzle = new Day19Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 19);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 19 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day19Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 19 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day19Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    enum ProcessType {
        GREATER_THAN,
        LESS_THAN
    }

    class WorkflowInstruction {
        private ProcessType functionType;
        private int threshold;
        private char processCharacter;
        protected String destination;
        private ArrayList<WorkflowInstruction> instructionParent;

        public WorkflowInstruction(ProcessType functionType, int threshold, char processCharacter,
                ArrayList<WorkflowInstruction> instructionParent, String destination) {
            this.functionType = functionType;
            this.threshold = threshold;
            this.processCharacter = processCharacter;
            this.instructionParent = instructionParent;
            this.destination = destination;
        }

        public boolean doesPartMeetConditions(HashMap<Character, Integer> part) {
            int partValue = part.get(processCharacter);

            if (functionType == ProcessType.GREATER_THAN && partValue > threshold) {
                return true;
            } else if (functionType == ProcessType.LESS_THAN && partValue < threshold) {
                return true;
            }
            return false;
        }

        public String runPart(HashMap<Character, Integer> part) {
            if (doesPartMeetConditions(part)) {
                if (destination.equals("A") || destination.equals("R")) {
                    return destination;
                }
                return workflowMap.get(destination).get(0).runPart(part);
            } else {
                int ourIndex = instructionParent.indexOf(this);
                return instructionParent.get(ourIndex + 1).runPart(part);
            }
        }

        public HashMap<Character, NumberRange> copyRangeWithModification(HashMap<Character, NumberRange> model,
                NumberRange swap, Character swapCharacter) {
            HashMap<Character, NumberRange> newPart = new HashMap<>();

            for (Entry<Character, NumberRange> entry : model.entrySet()) {
                newPart.put(entry.getKey(), entry.getValue());
            }

            newPart.put(swapCharacter, swap);
            return newPart;
        }

        public long sendPartToDestination(HashMap<Character, NumberRange> part, String destination) {
            System.out.println("destination: " +  destination);
            if (destination.equals("A")) {
                long tally = 1;

                for (Entry<Character, NumberRange> entry : part.entrySet()) {
                    if (!entry.getValue().isValidRange()) {
                        return 0;
                    }
                    System.out.println(entry.getValue().getLength() + 1);
                    tally *= entry.getValue().getLength() + 1;
                }

                return tally;
            } else if (destination.equals("R")) {
                return 0;
            }

            return workflowMap.get(destination).get(0).runPartRange(part);
        }

        public long continueInstructionRange(HashMap<Character, NumberRange> part) {
            int ourIndex = instructionParent.indexOf(this);
            return instructionParent.get(ourIndex + 1).runPartRange(part);
        }

        // ranges
        public long runPartRange(HashMap<Character, NumberRange> part) {
            NumberRange partValue = part.get(processCharacter);
            if (functionType == ProcessType.GREATER_THAN) {
                if (partValue.getStart() > threshold) {
                    // System.out.println("send greater");
                    return sendPartToDestination(part, destination);
                } else if (partValue.getEnd() <= threshold) {
                    // System.out.println("cont greater");
                    return continueInstructionRange(part);
                } else {
                    NumberRange splitPassCondition = new NumberRange(threshold + 1, partValue.getEnd());
                    NumberRange splitFailCondition = new NumberRange(partValue.getStart(), threshold);

                    HashMap<Character, NumberRange> firstPartGroup = copyRangeWithModification(part, splitPassCondition,
                            processCharacter);
                    HashMap<Character, NumberRange> secondPartGroup = copyRangeWithModification(part,
                            splitFailCondition, processCharacter);

                    return sendPartToDestination(firstPartGroup, destination)
                            + continueInstructionRange(secondPartGroup);
                }
            } else {
                if (partValue.getEnd() < threshold) {
                    // System.out.println("send lower");
                    return sendPartToDestination(part, destination);
                } else if (partValue.getStart() >= threshold) {
                    // System.out.println("continue lower");
                    return continueInstructionRange(part);
                } else {
                    NumberRange splitPassCondition = new NumberRange(partValue.getStart(), threshold - 1);
                    NumberRange splitFailCondition = new NumberRange(threshold, partValue.getEnd());

                    HashMap<Character, NumberRange> firstPartGroup = copyRangeWithModification(part, splitPassCondition,
                            processCharacter);
                    HashMap<Character, NumberRange> secondPartGroup = copyRangeWithModification(part,
                            splitFailCondition, processCharacter);

                    return sendPartToDestination(firstPartGroup, destination)
                            + continueInstructionRange(secondPartGroup);
                }
            }

        }
    }

    class SendInstruction extends WorkflowInstruction {
        public SendInstruction(ArrayList<WorkflowInstruction> instructionParent, String destination) {
            super(null, 0, 'a', instructionParent, destination);
        }

        @Override
        public boolean doesPartMeetConditions(HashMap<Character, Integer> part) {
            return true;
        }

        @Override
        public long runPartRange(HashMap<Character, NumberRange> part) {
            return sendPartToDestination(part, destination);
        }
    }

    public HashMap<String, ArrayList<WorkflowInstruction>> generateWorkflowMap(List<String> lines) {
        HashMap<String, ArrayList<WorkflowInstruction>> workflowMap = new HashMap<>();
        int cutoffWorkflowIndex = lines.indexOf("");

        for (int i = 0; i < cutoffWorkflowIndex; i++) {
            String workflow = lines.get(i);

            int startIndex = workflow.indexOf('{') + 1;
            int endIndex = workflow.indexOf('}');

            String workflowName = workflow.substring(0, workflow.indexOf('{'));
            ArrayList<WorkflowInstruction> instructionList = new ArrayList<>();

            String cutString = workflow.substring(startIndex, endIndex);
            String[] instructionStrings = cutString.split(",");

            for (String string : instructionStrings) {
                if (string.contains(">") || string.contains("<")) {
                    int indexOfSign = string.contains(">") ? string.indexOf(">") : string.indexOf("<");
                    ProcessType functionType = string.contains(">") ? ProcessType.GREATER_THAN : ProcessType.LESS_THAN;
                    char processCharacter = string.charAt(0);
                    int threshold = Integer.parseInt(string.substring(indexOfSign + 1, string.indexOf(":")));
                    String destination = string.substring(string.indexOf(":") + 1);

                    instructionList.add(new WorkflowInstruction(functionType, threshold, processCharacter,
                            instructionList, destination));
                } else {
                    instructionList.add(new SendInstruction(instructionList, string));
                }
            }

            workflowMap.put(workflowName, instructionList);
        }

        return workflowMap;
    }

    public ArrayList<HashMap<Character, Integer>> generateParts(List<String> lines) {
        ArrayList<HashMap<Character, Integer>> partList = new ArrayList<>();

        for (int i = lines.indexOf("") + 1; i < lines.size(); i++) {
            HashMap<Character, Integer> newPart = new HashMap<>();
            String baseString = lines.get(i);
            String[] splitPart = baseString.substring(1, baseString.length() - 1).split(",");

            for (String attribute : splitPart) {
                char key = attribute.charAt(0);
                int value = Integer.parseInt(attribute.substring(2, attribute.length()));
                newPart.put(key, value);
            }
            partList.add(newPart);
        }

        return partList;

    }

    public boolean checkPart(HashMap<Character, Integer> part) {
        // while (!destination.equals("A") && !destination.equals("R")) {

        // }

        return workflowMap.get("in").get(0).runPart(part).equals("A");
    }

    public long doPart1(List<String> lines) {
        workflowMap = generateWorkflowMap(lines);
        long sum = 0;

        ArrayList<HashMap<Character, Integer>> partList = generateParts(lines);
        for (HashMap<Character, Integer> part : partList) {
            if (checkPart(part)) {
                long miniSum = 0;
                for (Entry<Character, Integer> entry : part.entrySet()) {
                    miniSum += entry.getValue();
                }
                sum += miniSum;
            }

        }

        return sum;
    }

    public long doPart2(List<String> lines) {
        workflowMap = generateWorkflowMap(lines);

        HashMap<Character, NumberRange> basePartRange = new HashMap<>();
        basePartRange.put('x', new NumberRange(1, 4000));
        basePartRange.put('m', new NumberRange(1, 4000));
        basePartRange.put('a', new NumberRange(1, 4000));
        basePartRange.put('s', new NumberRange(1, 4000));

        return workflowMap.get("in").get(0).runPartRange(basePartRange);
    }
}
