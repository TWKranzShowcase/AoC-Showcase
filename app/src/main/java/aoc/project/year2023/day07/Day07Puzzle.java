package aoc.project.year2023.day07;

// import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day07Puzzle {

    private Integer holdPower = 0;
    private boolean doJokers = true;
    private int jokerCount = 0;

    public static void main(String[] args) {
        Day07Puzzle puzzle = new Day07Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 7);

        // puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 07 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day07Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 07 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day07Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public int characterValue(char toCheck) {
        int valueToAdd = 0;
        switch (toCheck) {
            case 'A':
                valueToAdd = 14;
                break;

            case 'K':
                valueToAdd = 13;
                break;

            case 'Q':
                valueToAdd = 12;
                break;

            case 'J':
                if (doJokers) {
                    return 1;
                } else {
                    valueToAdd = 11;
                }
                break;

            case 'T':
                valueToAdd = 10;
                break;

            default:
                valueToAdd = toCheck - 48;
                break;
        }
        return valueToAdd;
    }

    public int greaterCharacters(String card, String card2) {
        for (int i = 0; i < 5; i++) {
            int powerOne = characterValue(card.charAt(i));
            int powerTwo = characterValue(card2.charAt(i));

            if (powerOne > powerTwo) {
                return 1;
            } else if (powerOne < powerTwo) {
                return -1;
            }
        }

        return 0;
    }

    public int getCardsPower(String card, boolean countJokers) {
        holdPower = 0; // using this as a placeholder
        Hashtable<Character, Integer> table = new Hashtable<>();

        for (int i = 0; i < 5; i++) {
            char charAt = card.charAt(i);

            if (table.containsKey(charAt)) {
                table.put(charAt, table.get(charAt) + 1);
            } else {
                table.put(charAt, 1);
            }
        }

        if (doJokers && countJokers) {
            jokerCount = 0;
            if (table.containsKey('J') && table.get('J') != 5) {
                jokerCount = table.get('J');
                table.remove('J');
            }
            table.forEach((character, instances) -> {
                instances += jokerCount;
                switch (instances) {
                    case 5:
                        holdPower = 7;
                        break;

                    case 4:
                        if (holdPower < 6) {
                            holdPower = 6;
                        }
                        break;

                    case 3:
                        if (table.size() == 2 && holdPower < 5) {
                            holdPower = 5;
                        } else if (holdPower < 4) {
                            holdPower = 4;
                        }

                        break;

                    case 2:
                        if (table.size() == 3 && holdPower < 3) {
                            holdPower = 3;
                        } else if (holdPower < 2) {
                            holdPower = 2;
                        }
                        break;

                    default:
                        if (holdPower < 1) {
                            holdPower = 1;
                        }
                        break;
                }
            });
            // if (jokerCount > 0) {
            //     holdPower -= 1;
            // }
        } else {
            table.forEach((character, instances) -> {
                switch (instances) {
                    case 5:
                        holdPower = 7;
                        break;

                    case 4:
                        holdPower = 6;
                        break;

                    case 3:
                        if (table.size() == 2) {
                            holdPower = 5;
                        } else {
                            holdPower = 4;
                        }
                        break;

                    case 2:
                        if (table.size() == 3) {
                            holdPower = 3;
                        } else if (holdPower < 2) {
                            holdPower = 2;
                        }
                        break;

                    default:
                        if (holdPower < 1) {
                            holdPower = 1;
                        }
                        break;
                }
            });
        }

        // System.out.println(holdPower + " card: " + card);
        // System.out.println(buildValue + " card: " + card);

        return holdPower;
    }

    public long doPart1(List<String> lines) {
        // Part 1 code goes here
        // List<Integer> cards = new ArrayList<>();

        lines.sort((card, card2) -> {
            int powerDifference = getCardsPower(card, false) - getCardsPower(card2, false);
            if (powerDifference == 0) {
                return greaterCharacters(card, card2);
            } else {
                return powerDifference;
            }

        });

        lines.forEach((card) -> System.out.println(getCardsPower(card, false) + ": " + card));

        int winnings = 0;
        for (int i = 0; i < lines.size(); i++) {
            // System.out.println(AocUtil.parseNumberInt(lines.get(i), lines.get(i).length()
            // - 1));
            winnings += (i + 1) * AocUtil.parseNumberInt(lines.get(i), lines.get(i).length() - 1);
        }

        return winnings;
    }

    public long doPart2(List<String> lines) {
        // Part 1 code goes here
        // List<Integer> cards = new ArrayList<>();

        lines.sort((card, card2) -> {
            int powerDifference = getCardsPower(card, true) - getCardsPower(card2, true);

            if (powerDifference == 0) {
                int result = greaterCharacters(card, card2);
                if (result == 0) {
                    return powerDifference = getCardsPower(card, false) - getCardsPower(card2, false);
                }
                return result;
            } else {
                return powerDifference;
            }

        });

        lines.forEach((card) -> System.out.println(getCardsPower(card, true) + "-" + getCardsPower(card, false) + ": " + card));


        int winnings = 0;
        for (int i = 0; i < lines.size(); i++) {
            // System.out.println(AocUtil.parseNumberInt(lines.get(i), lines.get(i).length()
            // - 1));
            winnings += (i + 1) * AocUtil.parseNumberInt(lines.get(i), lines.get(i).length() - 1);
        }

        return winnings;
    }

}
