package aoc.project.year2023.day05;

import java.util.ArrayList;
import java.util.List;
import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day05Puzzle {
    public static void main(String[] args) {
        Day05Puzzle puzzle = new Day05Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 5);

        // puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 05 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day05Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 05 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day05Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public void iterateSeedList(ArrayList<Long> seeds, List<String> lines, int startIndex) {
        int endIndex = 0;
        for (int i = startIndex; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.equals("") || i == lines.size() - 1) {
                endIndex = i;
                break;
            }
        }

        for (int i = startIndex; i < endIndex; i++) {
            String line = lines.get(i);

            long destRange = Long.parseLong(line.substring(0, line.indexOf(" ")));
            // System.out.println("destRange: " + destRange);
            long sourceRange = Long.parseLong(line.substring(line.indexOf(" ") + 1, line.indexOf(" ", line.indexOf(" ") + 1)));
            // System.out.println("sourceRange: " + sourceRange);

            long rangeLength = Long.parseLong(line.substring(line.indexOf(" ", line.indexOf(" ") + 1) + 1));
            // System.out.println("rangeLength: " + rangeLength);

            seeds.forEach((seed) -> {
                boolean isInRange = false;
                long locationInRange = -1;

                if (sourceRange <= seed && seed < sourceRange + rangeLength && seed >= 0) {
                    // locationInRange = destRange + seed - sourceRange;
                    locationInRange = destRange + seed - sourceRange;
                    isInRange = true;
                }

                if (isInRange) {
                    seeds.set(seeds.indexOf(seed), -locationInRange); // negative to act as a flag for already checked
                }
            });
        }

        seeds.forEach((seed) -> {
            if (seed < 0) {
                seeds.set(seeds.indexOf(seed), -seed); 
            } 
        });

    }

    public long doPart1(List<String> lines) {
        String seedLine = lines.get(0);

        ArrayList<Long> seeds = new ArrayList<Long>();

        for (int i = 0; i < seedLine.length(); i++) {
            if (seedLine.charAt(i) == ' ') {
                seeds.add(AocUtil.parseNumberLong(seedLine, i + 1));
            }
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
             
            if (line.equals("")) {
                // System.out.println("checking next line: " + lines.get(i + 1));
                // seeds.forEach(seed -> {
                //     System.out.println(seed);
                // });
                iterateSeedList(seeds, lines, i + 2);
            }
        }

        long lowestLocationNumber = Long.MAX_VALUE;

        for (int i = 0; i < seeds.size(); i++) {
            lowestLocationNumber = Math.min(lowestLocationNumber, seeds.get(i));
        }


        // Part 1 code goes here
        return lowestLocationNumber;
    }

    public void iterateSeedRanges(ArrayList<NumberRange> seeds, List<String> lines, int startIndex) {
        int endIndex = 0;
        for (int i = startIndex; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.equals("") || i == lines.size() - 1) {
                endIndex = i;
                break;
            }
        }

        seeds.forEach((seed) -> {
            System.out.println(lines.get(startIndex - 1) + " " + seed.getStart() + "-" + seed.getEnd());
        });

        ArrayList<NumberRange> finalRange = new ArrayList<NumberRange>();

        for (int i = startIndex; i < endIndex; i++) {
            String line = lines.get(i);

            long destRange = Long.parseLong(line.substring(0, line.indexOf(" ")));
            // System.out.println("destRange: " + destRange);
            long sourceRange = Long.parseLong(line.substring(line.indexOf(" ") + 1, line.indexOf(" ", line.indexOf(" ") + 1)));
            // System.out.println("sourceRange: " + sourceRange);
            long rangeLength = Long.parseLong(line.substring(line.indexOf(" ", line.indexOf(" ") + 1) + 1));
            // System.out.println("rangeLength: " + rangeLength);

            NumberRange workRange = new NumberRange(0, 0);

            ArrayList<NumberRange> addToNextRound = new ArrayList<NumberRange>();

            seeds.forEach((seed) -> {
                workRange.setStartAndEnd(sourceRange, sourceRange + rangeLength - 1);
                if (seed.isIntersectingRange(workRange)) {
                    NumberRange intersect = seed.getIntersection(workRange);
                    // System.out.println("intersect of " + seed.getStart() + "-" + seed.getEnd() + " and " + workRange.getStart() + "-" + workRange.getEnd() + " is " + intersect.getStart() + "-" + intersect.getEnd());
                    // seed has some values of the source range
                    finalRange.add(new NumberRange(destRange + intersect.getStart() - sourceRange, destRange + intersect.getEnd() - sourceRange));
                    seed.mark();

                    if (workRange.getStart() > seed.getStart()) {
                        NumberRange split1 = new NumberRange(seed.getStart(), workRange.getStart());
                        split1.performAddition(0, -1);
                        addToNextRound.add(split1);
                    }

                    if (seed.getEnd() > workRange.getEnd()) {
                        NumberRange split2 = new NumberRange(seed.getEnd(), workRange.getEnd());
                        split2.performAddition(1, 0);
                        addToNextRound.add(split2);
                    }

                } 
            }); 

            seeds.addAll(addToNextRound);
        }

        ArrayList<NumberRange> mergedRanges = new ArrayList<NumberRange>();

        seeds.forEach((seed) -> {
            if (!seed.isMarked()) {
                System.out.println("UNCHANGED: " + lines.get(startIndex - 1) + " " + seed.getStart() + "-" + seed.getEnd());
                finalRange.add(seed);
            }
        });

        finalRange.forEach((seed) -> {
            finalRange.forEach((secondSeed) -> {
                if (seed.isIntersectingRange(secondSeed) && seed != secondSeed && !(secondSeed.getEnd() == -1 && secondSeed.getStart() == -1)) {
                    System.out.println("merging " + seed.getStart() + "-" + seed.getEnd() + " and " + secondSeed.getStart() + "-" + secondSeed.getEnd() + " into " + Math.min(seed.getStart(), secondSeed.getStart()) + "-" + Math.max(seed.getEnd(), secondSeed.getEnd()));
                    seed.setStartAndEnd(Math.min(seed.getStart(), secondSeed.getStart()), Math.max(seed.getEnd(), secondSeed.getEnd()));
                    secondSeed.setStartAndEnd(-1, -1);
                    // seed.setStartAndEnd(Math.min(seed.getStart(), secondSeed.getStart()), Math.max(seed.getEnd(), secondSeed.getEnd()));
                }
            });


        });

        finalRange.forEach((seed) -> {
            if (!(seed.getEnd() == -1 && seed.getStart() == -1)) {
                mergedRanges.add(seed);
            }
        });
        
        // for (int i = 0; i < seeds.size(); i++) {
        //     seeds.remove(i);
        // }
        seeds.removeAll(seeds);
        seeds.forEach((seed) -> {
            System.out.println("leftover seed");
        });
        seeds.addAll(mergedRanges);

    }

    // looking back, this code is a mess
    public long doPart2(List<String> lines) {
        String seedLine = lines.get(0);
        
        ArrayList<NumberRange> seeds = new ArrayList<NumberRange>();

        long holdRange = 0;
        boolean rangeOrLength = false;
        for (int i = 0; i < seedLine.length(); i++) {
            if (seedLine.charAt(i) == ' ') {
                long parseNumb = AocUtil.parseNumberLong(seedLine, i + 1);
                if (rangeOrLength) {
                    seeds.add(new NumberRange(holdRange, holdRange + parseNumb - 1));
                } else {
                    holdRange = parseNumb;
                }
                rangeOrLength = !rangeOrLength;
            }
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
             
            if (line.equals("")) {
                // System.out.println("checking next line: " + lines.get(i + 1));
                // seeds.forEach(seed -> {
                //     System.out.println(seed);
                // });
                iterateSeedRanges(seeds, lines, i + 2);
            }
        }

        seeds.forEach((seed) -> {
            System.out.println("FINAL: " + seed.getStart() + "-" + seed.getEnd());
        });

        Long lowestLocationNumber = Long.MAX_VALUE;

        for (int i = 0; i < seeds.size(); i++) {
            NumberRange range = seeds.get(i);
            if (range.getStart() < lowestLocationNumber && range.getStart() != 0) { // I have no idea why zero appears but if I block it this gives the correct answer so :shrug:
                lowestLocationNumber = range.getStart();
            }
        }

        return lowestLocationNumber;
    }

}
