package aoc.project.year2023.day17;

import java.util.ArrayList;
import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day17Puzzle {
    public static void main(String[] args) {
        Day17Puzzle puzzle = new Day17Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 17);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 17 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day17Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 17 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day17Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    class GraphNode {
        private int row;
        private int column;
        private int floor;

        private int weight;
        private int distance;
        private boolean checked;

        private GraphNode fromNode;

        public GraphNode(int floor, int row, int column, int weight) {
            this.row = row;
            this.column = column;
            this.weight = weight;
            this.floor = floor;

            this.distance = Integer.MAX_VALUE;
            this.checked = false;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public GraphNode getFromNode() {
            return fromNode;
        }

        public void setFromNode(GraphNode fromNode) {
            this.fromNode = fromNode;
        }
    }

    record NodeChoices(ArrayList<GraphNode> choices) {

    }

    public GraphNode getInvalidNode() {
        GraphNode invalidNode = new GraphNode(-1, -999, -999, Integer.MAX_VALUE);
        invalidNode.setChecked(true);
        return invalidNode;
    }

    public void updateNodeDirectionAndDistance(GraphNode[][][] graph, GraphNode neighbor, GraphNode chosenNode) {
        if (neighbor.isChecked() || graph[neighbor.getFloor()][neighbor.getRow()][neighbor.getColumn()].isChecked()) {
            return;
        }
        int newDistance = neighbor.getWeight() + chosenNode.getDistance();
        neighbor = graph[neighbor.getFloor()][neighbor.getRow()][neighbor.getColumn()];

        if (newDistance < neighbor.getDistance()) {
            neighbor.setDistance(newDistance);
            neighbor.setFromNode(chosenNode);
        }
    }

    public GraphNode getNodeAtPosition(GraphNode[][][] graph, int floor, int row, int column, boolean ignoreCheck) {
        if (row >= 0 && column >= 0 && row < graph[0].length && column < graph[0][0].length) {
            GraphNode pickNode = graph[floor][row][column];
            return pickNode.isChecked() && !ignoreCheck ? getInvalidNode() : pickNode;
        }
        return getInvalidNode();
    }

    public GraphNode getNodeRowAtPosition(GraphNode[][][] graph, GraphNode startNode, int floor, int row, int column) {
        if (!(row >= 0 && column >= 0 && row < graph[0].length && column < graph[0][0].length)) {
            return getInvalidNode();
        }

        int weightTotal = 0;
        if (startNode.getRow() != row) {
            int adjustStart = 1;
            int adjustEnd = 0;
            if (startNode.getRow() > row) {
                adjustStart = 0;
                adjustEnd = 1;
            }

            int start = Math.min(startNode.getRow(), row);
            int end = Math.max(startNode.getRow(), row);

            for (int i = start + adjustStart; i <= end - adjustEnd; i++) {
                GraphNode addNode = getNodeAtPosition(graph, floor, i, column, true);
                if (addNode.getFloor() == -1) {
                    return getInvalidNode();
                }
                weightTotal += addNode.getWeight();
            }
        } else if (startNode.getColumn() != column) {
            int adjustStart = 1;
            int adjustEnd = 0;
            if (startNode.getColumn() > column) {
                adjustStart = 0;
                adjustEnd = 1;
            }
            int start = Math.min(startNode.getColumn(), column);
            int end = Math.max(startNode.getColumn(), column);

            for (int i = start + adjustStart; i <= end - adjustEnd; i++) {
                GraphNode addNode = getNodeAtPosition(graph, floor, row, i, true);
                if (addNode.getFloor() == -1) {
                    return getInvalidNode();
                }
                weightTotal += addNode.getWeight();
            }
        } else {
            return getNodeAtPosition(graph, floor, row, column, false);
        }

        return new GraphNode(floor, row, column, weightTotal);
    }

    public ArrayList<GraphNode> getSurroundingNodes(GraphNode[][][] graph, GraphNode node) {
        ArrayList<GraphNode> nodeChoices = new ArrayList<>();

        int floor = node.getFloor();

        // System.out.println("options: " + node.getFloor() + ", " + node.getRow() + ",
        // " + node.getColumn());

        if (floor == 1) {
            for (int i = 1; i <= 3; i++) {
                nodeChoices.add(getNodeRowAtPosition(graph, node, 0, node.getRow() + i, node.getColumn()));
                nodeChoices.add(getNodeRowAtPosition(graph, node, 0, node.getRow() - i, node.getColumn()));
            }
        } else {
            for (int i = 1; i <= 3; i++) {
                nodeChoices.add(getNodeRowAtPosition(graph, node, 1, node.getRow(), node.getColumn() + i));
                nodeChoices.add(getNodeRowAtPosition(graph, node, 1, node.getRow(), node.getColumn() - i));
            }
        }

        // nodeChoices.forEach((a) -> System.out.println( "floor " + a.getFloor() + ": "
        // + a.getRow() + ", " + a.getColumn()));

        return nodeChoices;
    }

    public ArrayList<GraphNode> getSurroundingNodesPart2(GraphNode[][][] graph, GraphNode node) {
        ArrayList<GraphNode> nodeChoices = new ArrayList<>();

        int floor = node.getFloor();

        // System.out.println("options: " + node.getFloor() + ", " + node.getRow() + ",
        // " + node.getColumn());

        if (floor == 1) {
            for (int i = 4; i <= 10; i++) {
                nodeChoices.add(getNodeRowAtPosition(graph, node, 0, node.getRow() + i, node.getColumn()));
                nodeChoices.add(getNodeRowAtPosition(graph, node, 0, node.getRow() - i, node.getColumn()));
            }
        } else {
            for (int i = 4; i <= 10; i++) {
                nodeChoices.add(getNodeRowAtPosition(graph, node, 1, node.getRow(), node.getColumn() + i));
                nodeChoices.add(getNodeRowAtPosition(graph, node, 1, node.getRow(), node.getColumn() - i));
            }
        }

        // nodeChoices.forEach((a) -> System.out.println( "floor " + a.getFloor() + ": "
        // + a.getRow() + ", " + a.getColumn()));

        return nodeChoices;
    }

    public void printGraph(GraphNode[][][] graph) {
        for (int g = 0; g < graph.length; g++) {
            System.out.println();
            for (int i = 0; i < graph[0].length; i++) {
                System.out.println();

                for (int j = 0; j < graph[0][0].length; j++) {
                    GraphNode nodeAt = graph[g][i][j];

                    if (nodeAt.isChecked()) {
                        System.out.print("#" + " ");
                    } else if (nodeAt.getDistance() < Integer.MAX_VALUE) {
                        System.out.print(nodeAt.getDistance() + " ");
                    } else {
                        System.out.print("." + " ");
                    }
                }

            }

        }
        System.out.println();
    }

    public void printGraphPath(GraphNode[][][] graph, ArrayList<GraphNode> pathHighlight) {
        for (int i = 0; i < graph[0].length; i++) {
            System.out.println();

            for (int j = 0; j < graph[0][0].length; j++) {
                GraphNode nodeAtOne = graph[0][i][j];
                GraphNode nodeAtTwo = graph[1][i][j];

                if (pathHighlight.contains(nodeAtOne)) {

                    System.out.print("#");

                    // int count = pathHighlight.indexOf(nodeAtOne);

                    // System.out.print(count + " ");
                    // System.out.print(nodeAtOne.getDistance() + " ");
                } else if (pathHighlight.contains(nodeAtTwo)) {

                    System.out.print("+");

                    // int count = pathHighlight.indexOf(nodeAtTwo);
                    // System.out.print(count + " ");
                    // System.out.print(nodeAtTwo.getDistance() + " ");
                } else {
                    // System.out.print("." + " ");
                    System.out.print(".");
                    // System.out.print(nodeAtOne.getWeight());

                }
            }
        }

        System.out.println();
    }


    // one of my favorite days, I figured out you can just split this into a graph with 2 z levels with different movement choices avaliable 
    public long doPart1(List<String> lines) {
        // Part 1 code goes here

        GraphNode[][][] graph = new GraphNode[2][lines.size()][lines.get(0).length()];
        ArrayList<GraphNode> setOfAllNodes = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(0).length(); j++) {
                GraphNode updateNode = new GraphNode(0, i, j, lines.get(i).charAt(j) - 48);
                graph[0][i][j] = updateNode;
                setOfAllNodes.add(updateNode);

                updateNode = new GraphNode(1, i, j, lines.get(i).charAt(j) - 48);
                graph[1][i][j] = updateNode;
                setOfAllNodes.add(updateNode);
            }
        }

        GraphNode startNode = graph[0][0][0];
        GraphNode chosenNode = startNode;
        chosenNode.setDistance(0);

        GraphNode startNode2 = graph[1][0][0];
        startNode2.setDistance(0);

        GraphNode endNodeBottom = graph[0][graph[0].length - 1][graph[0][0].length - 1];
        GraphNode endNodeTop = graph[1][graph[0].length - 1][graph[0][0].length - 1];

        boolean oneEndReached = false;

        while (true) {
            int lowestDistance = Integer.MAX_VALUE;

            boolean outOfValues = true;
            for (GraphNode node : setOfAllNodes) {
                if (lowestDistance > node.getDistance() && !node.isChecked()) {
                    lowestDistance = node.getDistance();
                    chosenNode = node;
                    outOfValues = false;
                }
            }

            if (chosenNode == endNodeBottom || chosenNode == endNodeTop || outOfValues) {
                if (oneEndReached) {
                    if (outOfValues) {
                        System.err.println("no values left: ending early");
                    } else {
                        System.out.println("found end");
                    }
                    break;
                } else {
                    oneEndReached = true;
                }
            }

            // System.out.println(chosenNode.getFloor() + ", " + chosenNode.getRow() + ", "
            // + chosenNode.getColumn());
            // if (fixedStartingValues) {
            ArrayList<GraphNode> choices = getSurroundingNodes(graph, chosenNode);
            // } else {
            // fixedStartingValues = true;
            // choices = getSurroundingNodesStart(graph, chosenNode);
            // }

            for (GraphNode choice : choices) {
                updateNodeDirectionAndDistance(graph, choice, chosenNode);
            }

            chosenNode.setChecked(true);
            setOfAllNodes.remove(chosenNode);

            // printGraph(graph);
        }

        ArrayList<GraphNode> pathNodes = new ArrayList<>();
        pathNodes.add(startNode);
        GraphNode pathfindNode = endNodeBottom;

        while (pathfindNode != startNode && pathfindNode != startNode2) {
            pathNodes.add(pathfindNode);
            pathfindNode = pathfindNode.getFromNode();
        }
        printGraphPath(graph, pathNodes);
        System.out.println(endNodeBottom.getDistance());

        pathNodes = new ArrayList<>();
        pathNodes.add(startNode);
        pathfindNode = endNodeTop;
        while (pathfindNode != startNode && pathfindNode != startNode2) {
            pathNodes.add(pathfindNode);
            pathfindNode = pathfindNode.getFromNode();
        }
        printGraphPath(graph, pathNodes);
        System.out.println(endNodeTop.getDistance());

        return Math.min(endNodeBottom.getDistance(), endNodeTop.getDistance());
    }

    public long doPart2(List<String> lines) {
        // Part 1 code goes here

        GraphNode[][][] graph = new GraphNode[2][lines.size()][lines.get(0).length()];
        ArrayList<GraphNode> setOfAllNodes = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(0).length(); j++) {
                GraphNode updateNode = new GraphNode(0, i, j, lines.get(i).charAt(j) - 48);
                graph[0][i][j] = updateNode;
                setOfAllNodes.add(updateNode);

                updateNode = new GraphNode(1, i, j, lines.get(i).charAt(j) - 48);
                graph[1][i][j] = updateNode;
                setOfAllNodes.add(updateNode);
            }
        }

        GraphNode startNode = graph[0][0][0];
        GraphNode chosenNode = startNode;
        chosenNode.setDistance(0);

        GraphNode startNode2 = graph[1][0][0];
        startNode2.setDistance(0);

        GraphNode endNodeBottom = graph[0][graph[0].length - 1][graph[0][0].length - 1];
        GraphNode endNodeTop = graph[1][graph[0].length - 1][graph[0][0].length - 1];

        boolean oneEndReached = false;

        while (true) {
            int lowestDistance = Integer.MAX_VALUE;

            boolean outOfValues = true;
            for (GraphNode node : setOfAllNodes) {
                if (lowestDistance > node.getDistance() && !node.isChecked()) {
                    lowestDistance = node.getDistance();
                    chosenNode = node;
                    outOfValues = false;
                }
            }

            if (chosenNode == endNodeBottom || chosenNode == endNodeTop || outOfValues) {
                if (oneEndReached) {
                    if (outOfValues) {
                        System.err.println("no values left: ending early");
                    } else {
                        System.out.println("found end");
                    }
                    break;
                } else {
                    oneEndReached = true;
                }
            }

            // System.out.println(chosenNode.getFloor() + ", " + chosenNode.getRow() + ", "
            // + chosenNode.getColumn());
            // if (fixedStartingValues) {
            ArrayList<GraphNode> choices = getSurroundingNodesPart2(graph, chosenNode);
            // } else {
            // fixedStartingValues = true;
            // choices = getSurroundingNodesStart(graph, chosenNode);
            // }

            for (GraphNode choice : choices) {
                updateNodeDirectionAndDistance(graph, choice, chosenNode);
            }

            chosenNode.setChecked(true);
            setOfAllNodes.remove(chosenNode);

            // printGraph(graph);
        }

        ArrayList<GraphNode> pathNodes = new ArrayList<>();
        pathNodes.add(startNode);
        GraphNode pathfindNode = endNodeBottom;

        while (pathfindNode != startNode && pathfindNode != startNode2) {
            pathNodes.add(pathfindNode);
            pathfindNode = pathfindNode.getFromNode();
        }
        printGraphPath(graph, pathNodes);
        System.out.println(endNodeBottom.getDistance());

        pathNodes = new ArrayList<>();
        pathNodes.add(startNode);
        pathfindNode = endNodeTop;
        while (pathfindNode != startNode && pathfindNode != startNode2) {
            pathNodes.add(pathfindNode);
            pathfindNode = pathfindNode.getFromNode();
        }
        printGraphPath(graph, pathNodes);
        System.out.println(endNodeTop.getDistance());

        return Math.min(endNodeBottom.getDistance(), endNodeTop.getDistance());
    }

}