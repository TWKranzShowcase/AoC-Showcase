package aoc.project.year2023.day25;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day25Puzzle {
    public static void main(String[] args) {
        Day25Puzzle puzzle = new Day25Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 25);

        puzzle.part1();
        // puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 25 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day25Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 25 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day25Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public void addIfAbsent(ArrayList<String> list, String object) {
        if (!list.contains(object)) {
            list.add(object);
        }
    }

    // returns list of all paths taken or an empty array if no path could be made
    public ArrayList<Edge> dijkastraPath(String startNode, String goal, HashMap<String, ArrayList<String>> lookupMap,
            ArrayList<Edge> toCopyVisited) {
        ArrayList<String> choices = new ArrayList<>();
        ArrayList<String> visited = new ArrayList<>();
        ArrayList<Edge> visitedEdges = new ArrayList<>();
        visitedEdges.addAll(toCopyVisited);
        HashMap<String, Integer> pathDistances = new HashMap<>();
        HashMap<String, ArrayList<Edge>> pathToReachNodeMap = new HashMap<>();

        pathDistances.put(startNode, 0);
        choices.add(startNode);

        String currentNode = startNode;
        ArrayList<Edge> currentPath = new ArrayList<>();
        pathToReachNodeMap.put(currentNode, currentPath);

        while (true) {
            int lowestDistance = Integer.MAX_VALUE;

            // choose node
            for (String choice : choices) {
                int thisDistance = pathDistances.getOrDefault(choice, Integer.MAX_VALUE);
                if (thisDistance < lowestDistance) {
                    lowestDistance = thisDistance;
                    currentNode = choice;
                    currentPath = pathToReachNodeMap.get(currentNode);
                }
            }

            if (lowestDistance == Integer.MAX_VALUE || currentNode.equals(goal)) {
                break;
            }

            if (currentPath.size() > 0) {
                visitedEdges.add(currentPath.get(currentPath.size() - 1));
            }

            choices.remove(currentNode);
            visited.add(currentNode);

            for (String nextChoice : lookupMap.get(currentNode)) {
                Edge moveEdge = new Edge(currentNode, nextChoice);
                if (visited.contains(nextChoice) || visitedEdges.contains(moveEdge)) {
                    continue;
                }

                int otherDistance = pathDistances.getOrDefault(nextChoice, Integer.MAX_VALUE);

                if (lowestDistance < otherDistance) {
                    ArrayList<Edge> newPath = new ArrayList<>();
                    newPath.add(moveEdge);
                    newPath.addAll(currentPath);

                    pathDistances.put(nextChoice, lowestDistance + 1);
                    pathToReachNodeMap.put(nextChoice, newPath);
                    choices.add(nextChoice);
                }
            }
        }

        return pathToReachNodeMap.getOrDefault(goal, new ArrayList<>());
    }

    public boolean isMatchPartOfCut(String start, String end, Edge removeEdge,
            HashMap<String, ArrayList<String>> lookupMap) {

        ArrayList<Edge> removeCut = new ArrayList<>();
        removeCut.add(removeEdge);

        ArrayList<Edge> firstPath = dijkastraPath(start, end, lookupMap, removeCut);
        firstPath.add(removeEdge);

        ArrayList<Edge> secondPath = dijkastraPath(end, start, lookupMap, firstPath);
        if (secondPath.size() == 0) {
            return false;
        }

        secondPath.addAll(firstPath);
        ArrayList<Edge> thirdPath = dijkastraPath(start, end, lookupMap, secondPath);

        if (thirdPath.size() == 0) {
            return true;
        }

        return false;
    }

    public boolean isNodeInOtherCluster(String start, String end, HashMap<String, ArrayList<String>> lookupMap) {
        ArrayList<Edge> firstPath = dijkastraPath(start, end, lookupMap, new ArrayList<>());
        ArrayList<Edge> secondPath = dijkastraPath(end, start, lookupMap, firstPath);
        secondPath.addAll(firstPath);

        ArrayList<Edge> thirdPath = dijkastraPath(start, end, lookupMap, secondPath);
        thirdPath.addAll(secondPath);

        ArrayList<Edge> fourthPath = dijkastraPath(end, start, lookupMap, thirdPath);

        return fourthPath.size() == 0;
    }

    public int sumAreaWithDisconnects(ArrayList<Edge> toDisconnect, HashMap<String, ArrayList<String>> componentList,
            ArrayList<String> toCheck) {
        for (int i = 0; i < 3; i++) {
            String excludeOne = toDisconnect.get(i).firstNode();
            String excludeTwo = toDisconnect.get(i).secondNode();

            componentList.get(excludeOne).remove(excludeTwo);
            componentList.get(excludeTwo).remove(excludeOne);
        }

        ArrayList<String> nextNodes = new ArrayList<>();
        ArrayList<String> containedNodes = new ArrayList<>();
        String node = toCheck.get(0);
        nextNodes.add(node);

        while (nextNodes.size() > 0) {
            ArrayList<String> nextCycle = new ArrayList<>();
            for (String moveNode : nextNodes) {
                containedNodes.add(moveNode);
                for (String possibleNode : componentList.get(moveNode)) {
                    if (!containedNodes.contains(possibleNode) && !nextNodes.contains(possibleNode)
                            && !nextCycle.contains(possibleNode)) {
                        nextCycle.add(possibleNode);
                    }
                }
            }

            nextNodes.clear();
            nextNodes.addAll(nextCycle);
        }

        return (toCheck.size() - containedNodes.size()) * containedNodes.size();
    }

    public long doPart1(List<String> lines) {
        HashMap<String, ArrayList<String>> componentList = new HashMap<>();

        ArrayList<String> toCheck = new ArrayList<>();
        for (String line : lines) {
            String firstCompstartnt = line.substring(0, 3);

            String[] connectedCompstartnts = line.substring(5).split(" ");

            ArrayList<String> stringList = componentList.getOrDefault(firstCompstartnt, new ArrayList<>());
            for (String comp : connectedCompstartnts) {
                ArrayList<String> addArray = componentList.getOrDefault(comp, new ArrayList<>());
                addIfAbsent(addArray, firstCompstartnt);
                componentList.put(comp, addArray);
                addIfAbsent(toCheck, comp);
                addIfAbsent(stringList, comp);
            }
            addIfAbsent(toCheck, firstCompstartnt);
            componentList.put(firstCompstartnt, stringList);
        }

        for (Entry<String, ArrayList<String>> entry : componentList.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        String nodeClusterOne = toCheck.get(0);
        String nodeClusterTwo = "";

        for (int i = 1; i < toCheck.size(); i++) {
            String checkNode = toCheck.get(i);
            if (isNodeInOtherCluster(nodeClusterOne, checkNode, componentList)) {
                nodeClusterTwo = checkNode;
                System.out.println("found other node: " + nodeClusterOne + "-" + checkNode);
                break;
            }
        }

        ArrayList<Edge> firstPath = dijkastraPath(nodeClusterOne, nodeClusterTwo, componentList, new ArrayList<>());

        ArrayList<Edge> secondPath = dijkastraPath(nodeClusterTwo, nodeClusterOne, componentList, firstPath);
        secondPath.addAll(firstPath);

        ArrayList<Edge> thirdPath = dijkastraPath(nodeClusterOne, nodeClusterTwo, componentList, secondPath);
        thirdPath.addAll(secondPath);

        ArrayList<Edge> toDisconnect = new ArrayList<>();

        for (int i = 0; i < thirdPath.size(); i++) {
            Edge removedEdge = thirdPath.get(i);
            boolean valid = isMatchPartOfCut(nodeClusterOne, nodeClusterTwo, removedEdge, componentList);
        
            System.out.println(removedEdge + ": " + valid);
            if (valid) {
                toDisconnect.add(removedEdge);
            }

            // we've found our connections, end early
            if (toDisconnect.size() >= 3) {
                break;
            }
        }

        System.out.println(firstPath);
        System.out.println(secondPath);
        System.out.println(thirdPath);


        System.out.println();
        for (Edge list : toDisconnect) {
            System.out.println(list);
        }

        return sumAreaWithDisconnects(toDisconnect, componentList, toCheck);
    }

    public long doPart2(List<String> lines) {
        // Part 2 code goes here
        return -1;
    }
}

// failed attempts

// public void recurse(String currentNode, String endNode, ArrayList<String>
// traveled,
// HashMap<String, ArrayList<String>> componentList, HashMap<String, Integer>
// instanceList) {
// if (traveled.contains(currentNode)) {
// return;
// }
// traveled.add(currentNode);
// if (currentNode.equals(endNode)) {
// for (int i = 0; i < traveled.size() - 1; i++) {
// String adjustNode = traveled.get(i);
// String adjustNodeMove = traveled.get(i + 1);
// int num = instanceList.getOrDefault(adjustNode + "-" + adjustNodeMove, 0) +
// 1;
// instanceList.put(adjustNode + "-" + adjustNodeMove, num);
// }
// traveled.remove(currentNode);
// return;
// }

// ArrayList<String> possibleChoices = componentList.get(currentNode);

// if (possibleChoices.size() == 0) {
// traveled.remove(currentNode);
// return;
// }

// for (String choice : possibleChoices) {
// recurse(choice, endNode, traveled, componentList, instanceList);
// }

// traveled.remove(currentNode);
// }

// public int finalCheck(String currentNode, HashMap<String, ArrayList<String>>
// lookupMap,
// ArrayList<String> currentBlob, ArrayList<String> blobChoices) {
// boolean valid = true;

// int sum = lookupMap.size();

// boolean dstart = false;

// ArrayList<String> allNodes = new ArrayList<>();
// allNodes.add(currentNode);
// while (!dstart) {
// dstart = true;
// ArrayList<String> addLater = new ArrayList<>();
// for (String gridNode : allNodes) {
// ArrayList<String> newChoices = lookupMap.get(gridNode);

// for (String possibleNode : newChoices) {
// if (!currentBlob.contains(possibleNode) && !allNodes.contains(possibleNode))
// {
// addIfAbsent(addLater, possibleNode);
// dstart = false;
// }
// }
// }

// allNodes.addAll(addLater);
// }

// if (sum != allNodes.size() + currentBlob.size()) {
// valid = false;
// // System.out.println();

// System.out.println();
// System.out.println(blobChoices);
// System.out.println(allNodes);
// System.out.println(currentBlob);
// } else {
// // if (allNodes.size() == 6 || allNodes.size() == 9) {
// System.out.println();
// System.out.println(blobChoices);
// System.out.println(allNodes);
// System.out.println(currentBlob);
// // }

// }

// return valid ? (sum - currentBlob.size()) * currentBlob.size() : 0;

// }

// public int recurseBlob(String currentNode, HashMap<String, ArrayList<String>>
// lookupMap,
// ArrayList<String> currentBlob, ArrayList<String> blobChoices) {
// if (currentBlob.contains(currentNode)) {
// return 0;
// }

// boolean dstart = blobChoices.remove(currentNode);
// if (dstart == false) {
// }

// // System.out.println(currentBlob);
// int sum = 0;

// ArrayList<String> newChoices = lookupMap.get(currentNode);

// currentBlob.add(currentNode);
// for (String possibleChoice : newChoices) {
// if (!currentBlob.contains(possibleChoice)) {
// blobChoices.add(possibleChoice);
// }
// }

// ArrayList<String> toRecurse = new ArrayList<>();
// toRecurse.addAll(blobChoices);

// if (blobChoices.size() == 3) {
// int finalCheck = finalCheck(blobChoices.get(0), lookupMap, currentBlob,
// blobChoices);
// if (finalCheck > 0) {
// return finalCheck;
// }
// }

// for (String choice : toRecurse) {

// ArrayList<String> copyList = new ArrayList<>();
// copyList.addAll(blobChoices);
// sum += recurseBlob(choice, lookupMap, currentBlob, copyList);
// }

// currentBlob.remove(currentNode);

// // for (String possibleChoice : newChoices) {
// // blobChoices.remove(possibleChoice);
// // }

// return sum;
// }

    // public void pathLengthRecurse(String currentNode, int currentLength, HashMap<String, ArrayList<String>> lookupMap,
    //         ArrayList<String> takenNodes, HashMap<String, Integer> pathDistances) {
    //     if (takenNodes.contains(currentNode)) {
    //         return;
    //     }

    //     takenNodes.add(currentNode);

    //     if (pathDistances.getOrDefault(currentNode, Integer.MAX_VALUE) > currentLength) {
    //         pathDistances.put(currentNode, currentLength);
    //     }

    //     ArrayList<String> newChoices = lookupMap.get(currentNode);
    //     for (String choice : newChoices) {
    //         pathLengthRecurse(choice, currentLength + 1, lookupMap, takenNodes, pathDistances);
    //     }

    //     takenNodes.remove(currentNode);
    //     return;
    // }