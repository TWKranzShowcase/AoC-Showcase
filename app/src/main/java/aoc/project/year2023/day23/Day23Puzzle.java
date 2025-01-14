package aoc.project.year2023.day23;

import java.util.ArrayList;
import java.util.List;
import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;
import aoc.project.util.Grid.Coordinates;
import aoc.project.util.Grid.Direction;
import aoc.project.util.Grid.Grid;
import aoc.project.util.Grid.GridPointer;
import aoc.project.util.Grid.GridUtils;

import org.apache.commons.lang3.time.StopWatch;

public class Day23Puzzle {
    public static void main(String[] args) {
        Day23Puzzle puzzle = new Day23Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 23);

        // puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 23 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day23Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        int result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 23 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day23Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        int result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    public int recurse(Grid<Character> grid, GridPointer<Character> pointer, Coordinates coord, int distance,
            ArrayList<Coordinates> traveled, Coordinates end) {
        if (coord.equals(end)) {
            return distance;
        }

        pointer.setPointerPosition(coord);
        char charAt = pointer.getItemAtPointer();

        if (charAt == '#' || traveled.contains(coord)) {
            return -1;
        }

        traveled.add(coord);
        ArrayList<Coordinates> coords = new ArrayList<>();

        if (charAt == '>') {
            pointer.setDirection(Direction.EAST);
            pointer.moveAccordingToDirection(1);

            coords.add(pointer.getCoordinates());
        } else if (charAt == '<') {
            pointer.setDirection(Direction.WEST);
            pointer.moveAccordingToDirection(1);

            coords.add(pointer.getCoordinates());
        } else if (charAt == 'v') {
            pointer.setDirection(Direction.SOUTH);
            pointer.moveAccordingToDirection(1);

            coords.add(pointer.getCoordinates());
        } else if (charAt == '^') {
            pointer.setDirection(Direction.NORTH);
            pointer.moveAccordingToDirection(1);

            coords.add(pointer.getCoordinates());
        } else {
            coords.addAll(pointer.getNearby(true));
        }

        int max = Integer.MIN_VALUE;

        while (coords.size() > 0) {
            Coordinates possibleMove = coords.remove(0);
            if (!grid.isValidCord(possibleMove.row(), possibleMove.column())) {
                continue;
            }
            max = Math.max(max, recurse(grid, pointer, possibleMove, distance + 1, traveled, end));
        }

        traveled.remove(coord);
        return max;
    }

    public int doPart1(List<String> lines) {
        Grid<Character> grid = GridUtils.parseStringInput(lines);
        Coordinates startPos = grid.findInstance('.');
        GridPointer<Character> pointer = new GridPointer<>(grid, 0, 0, null);

        ArrayList<Coordinates> traveledTiles = new ArrayList<>();

        return recurse(grid, pointer, startPos, 0, traveledTiles,
                new Coordinates(grid.getRowSize() - 1, grid.getColumnSize() - 2));
    }

    public void recursePart2(Grid<Character> grid, GridPointer<Character> pointer, Coordinates endPos, Coordinates startCoord,
            Coordinates coord, int distance,
            ArrayList<Coordinates> traveled, ArrayList<ChristmasNode> parentNode, boolean pastFirst) {

        pointer.setPointerPosition(coord);
        char charAt = pointer.getItemAtPointer();

        if (coord.equals(endPos)) {
            // pointer.replaceElementAtPointer('X');
            if (distance == 0) {
                return;
            }
            parentNode.add(new ChristmasNode(distance, startCoord, endPos));
            return;
        }

        if (charAt == '#' || charAt == 'X' ||  traveled.contains(coord)) {
            return;
        }

        if (charAt == '>' || charAt == '<' || charAt == 'v' || charAt == '^') {
            pointer.replaceElementAtPointer('X');
            if (!pastFirst) {
                pastFirst = true;
            } else {
                if (charAt == '>' || charAt == '<') {
                    pointer.setDirection(Direction.EAST);
                    pointer.moveAccordingToDirection(1);
                } else if (charAt == 'v' || charAt == '^') {
                    pointer.setDirection(Direction.SOUTH);
                    pointer.moveAccordingToDirection(1);
                }

                if (traveled.contains(pointer.getCoordinates())) {
                    pointer.moveAccordingToDirection(-2);
                }
                parentNode.add(new ChristmasNode(distance + 1, startCoord, pointer.getCoordinates()));
                return;
            }

        }

        traveled.add(coord);
        ArrayList<Coordinates> coords = new ArrayList<>();

        coords.addAll(pointer.getNearby(true));

        while (coords.size() > 0) {
            Coordinates possibleMove = coords.remove(0);
            recursePart2(grid, pointer, endPos, startCoord, possibleMove, distance + 1, traveled, parentNode, pastFirst);
        }

        traveled.remove(coord);
    }

    public record Crossroad(ArrayList<ChristmasNode> canMove, Coordinates position) {}

    public class ChristmasNode {
        private int distance;
        private Coordinates startPos;
        private Coordinates endPos;

        public ChristmasNode(int distance, Coordinates startPos, Coordinates endPos) {
            this.distance = distance;
            this.startPos = startPos;
            this.endPos = endPos;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public Coordinates getStartPos() {
            return startPos;
        }

        public void setStartPos(Coordinates startPos) {
            this.startPos = startPos;
        }

        public Coordinates getEndPos() {
            return endPos;
        }

        public void setEndPos(Coordinates endPos) {
            this.endPos = endPos;
        }

        public String toString() {
            return "" + distance + "(" + startPos + "-" + endPos + ")";
        } 
    }

    public int getLongestPath(Grid<Character> grid, ChristmasNode startingNode, int distance, Coordinates endPos, ArrayList<Crossroad> crossroadList, ArrayList<Coordinates> nodesTaken) {
        // if (startingNode.getDistance() == 0) {
        //     return -1;
        // }
       
        Coordinates newPosition = startingNode.getEndPos();
        // System.out.println(distance);

        if (nodesTaken.contains(newPosition)) {
            return -1;
        } else {
            nodesTaken.add(newPosition);
        }

        distance += startingNode.getDistance();

        if (newPosition.equals(endPos)) {
            // if (distance >= 6178) {
            //     System.out.println(nodesTaken);
            // }
            nodesTaken.remove(newPosition);
            return distance;
        }

        ArrayList<ChristmasNode> choices = new ArrayList<>();
        for (Crossroad possibleRoad : crossroadList) {
            // System.out.println(possibleRoad);
            if (possibleRoad.position().equals(newPosition)) {
                choices.addAll(possibleRoad.canMove());
            }
        }

        int highestValue = Integer.MIN_VALUE;

        for (ChristmasNode choice : choices) {
            // System.out.println(choices.get(0));

            int p = getLongestPath(grid, choice, distance, endPos, crossroadList, nodesTaken);

            highestValue = Math.max(p, highestValue);
        }
        // System.out.println(highestValue);
        nodesTaken.remove(newPosition);
        
        return highestValue;
    }

    public int doPart2(List<String> lines) {
        Grid<Character> grid = GridUtils.parseStringInput(lines);
        Coordinates startPos = grid.findInstance('.');
        Coordinates endPos = new Coordinates(grid.getRowSize() - 1, grid.getColumnSize() - 2);
        GridPointer<Character> pointer = new GridPointer<>(grid, 0, 0, null);

        ArrayList<ChristmasNode> nodeList = new ArrayList<>();

        recursePart2(grid, pointer, endPos, startPos, startPos, 0, new ArrayList<>(), nodeList, true);
        boolean done = false;

        ChristmasNode startingNode = nodeList.get(0);
        System.out.println(startingNode);
        System.out.println();
        while (!done) {
            done = true;
            ArrayList<Coordinates> nextIterations = new ArrayList<>();

            for (ChristmasNode node : nodeList) {
                if (!nextIterations.contains(node.getEndPos())) {
                    nextIterations.add(node.getEndPos());
                }
            }

            for (Coordinates startingPosition : nextIterations) {
                pointer.setPointerPosition(startingPosition);
                if (grid.getElement(startingPosition).equals('S')) {
                    continue;
                }
                recursePart2(grid, pointer, endPos, startingPosition, startingPosition, 0, new ArrayList<>(), nodeList, false);
                pointer.setPointerPosition(startingPosition);
                pointer.replaceElementAtPointer('S');
                done = false;
            }
        }

        grid.printGrid(System.out::print);

        ArrayList<ChristmasNode> extraNodes = new ArrayList<>();
        for (ChristmasNode node : nodeList) {
            System.out.println(node);
            extraNodes.add(new ChristmasNode(node.getDistance(), node.getEndPos(), node.getStartPos()));
        }
        nodeList.addAll(extraNodes);

        nodeList.forEach(System.out::println);

        // ArrayList<Coordinates> allCrossroadsCharacters = grid.getAllInstances('S');
        ArrayList<Coordinates> allCrossroadsCharacters = new ArrayList<>();
        ArrayList<Crossroad> crossroadList = new ArrayList<>();

        for (ChristmasNode node : nodeList) {
            if (!allCrossroadsCharacters.contains(node.getEndPos())) {
                allCrossroadsCharacters.add(node.getEndPos());
            }
        }

        for (Coordinates crossroad : allCrossroadsCharacters) {
            ArrayList<ChristmasNode> paths = new ArrayList<>();
            
            for (ChristmasNode node : nodeList) {
                if (node.getStartPos().equals(crossroad)) {
                    paths.add(node);
                }
            }

            crossroadList.add(new Crossroad(paths, crossroad));
        }

        System.out.println(grid.getAllInstances('>').size());
        System.out.println(grid.getAllInstances('<').size());
        System.out.println(grid.getAllInstances('v').size());
        System.out.println(grid.getAllInstances('^').size());

        ArrayList<Coordinates> takenCoords = new ArrayList<>();
        takenCoords.add(startPos);
        return getLongestPath(grid, startingNode, 0, endPos, crossroadList, takenCoords);
    }

}
