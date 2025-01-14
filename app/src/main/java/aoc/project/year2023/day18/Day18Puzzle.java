package aoc.project.year2023.day18;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day18Puzzle {
    public static void main(String[] args) {
        Day18Puzzle puzzle = new Day18Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 18);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 18 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day18Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 18 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day18Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST,
        EMPTY
    }

    record DigInstruction(Direction direction, int distance, String code) {

    }

    public void printGraph(boolean[][] graph) {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                if (graph[i][j]) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    public void diagonalDig(DigPit[][] graph, DigPit[][] changeGraph, int row, int column) {
        // changeGraph[row][column] = new DigPit(true, true);

        int stepRow = row;
        int stepColumn = column;

        boolean ableToChange = false;

        while (stepRow < graph.length && stepColumn < graph[0].length) {
            if (graph[stepRow][stepColumn].dug() && !graph[stepRow][stepColumn].isCorner()) {
                ableToChange = !ableToChange;
            }
            if (ableToChange || graph[stepRow][stepColumn].dug()) {
                changeGraph[stepRow][stepColumn] = new DigPit(true, false);
            }
            stepRow++;
            stepColumn++;
        }
    }

    public void printGraphToFile(DigPit[][] graph, DigPit[][] orginGraph) {
        try {
            File myFile = new File("graph.txt");
            if (myFile.exists()) {
                myFile.delete();
            }
            myFile.createNewFile();
            FileWriter writer = new FileWriter(myFile, true);
            for (int i = 0; i < graph.length; i++) {
                for (int j = 0; j < graph[0].length; j++) {
                    if (orginGraph[i][j].isCorner()) {
                        writer.write('C');
                    } else if (orginGraph[i][j].dug()) {
                        writer.write('W');
                    } else {
                        if (graph[i][j] == null) {
                            writer.write('.');
                            continue;
                        }
                        writer.write(graph[i][j].dug() ? '#' : '.');
                    }
                }
                writer.write('\n');
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    
    record DigPit(boolean dug, boolean isCorner) {

    }

    public long doPart1(List<String> lines) {
        int depth = 0;
        int highestDepth = 0;
        int highestNegativeDepth = 0;
        int width = 0;
        int highestWidth = 0;
        int highestNegativeWidth = 0;

        int currentRow = 0;
        int currentColumn = 0;
        ArrayList<DigInstruction> instructions = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String stringDirection = lines.get(i);
            char charAt = stringDirection.charAt(0);
            int possibleDepth = AocUtil.parseNumberInt(stringDirection, 2);
            Direction putDirection = Direction.NORTH;

            if (charAt == 'D') {
                depth += possibleDepth; 
                putDirection = Direction.SOUTH;
            } else if (charAt == 'U') {
                depth -= possibleDepth;
                putDirection = Direction.NORTH;
            } else if (charAt == 'R') {
                width += possibleDepth;
                putDirection = Direction.EAST;
            } else if (charAt == 'L') {
                width -= possibleDepth;
                putDirection = Direction.WEST;
            }

            if (depth > 0) {
                if (highestDepth < depth) {
                    highestDepth = depth;
                }
            } else {
                if (highestNegativeDepth > depth) {
                    highestNegativeDepth = depth;
                    currentRow = -depth;
                }
            }


            if (width > 0) {
                if (highestWidth < width) {
                    highestWidth = width;
                }
            }  else {
                if (highestNegativeWidth > width) {
                    highestNegativeWidth = width;
                    currentColumn = -width;
                }
            }


            String code = stringDirection.substring(stringDirection.indexOf("("));
            instructions.add(new DigInstruction(putDirection, possibleDepth, code));
        }

        DigPit[][] array = new DigPit[highestDepth - highestNegativeDepth + 1][highestWidth - highestNegativeWidth + 1];
        Direction lastDirection = Direction.EMPTY;

        int startRow = currentRow;
        int startCol = currentColumn;

        for (DigInstruction instruction : instructions) {
            Direction moveDirection = instruction.direction();
            int moveAmount = instruction.distance();
            int end = 0;

            // System.out.println();
            // System.out.println(moveDirection  + ": " + moveAmount);
            // System.out.println(currentRow  + ", " + currentColumn);

            if ((
                (lastDirection == Direction.NORTH && moveDirection == Direction.EAST) ||
                (lastDirection == Direction.EAST && moveDirection == Direction.NORTH) ||
                (lastDirection == Direction.SOUTH && moveDirection == Direction.WEST) ||
                (lastDirection == Direction.WEST && moveDirection == Direction.SOUTH)
                )) {
                    array[currentRow][currentColumn] = new DigPit(true, false);
            } else {
                array[currentRow][currentColumn] = new DigPit(true, true);
            }

            lastDirection = moveDirection;

            switch (moveDirection) {
                case EAST:
                    end = currentColumn + moveAmount;
                    for (int i = currentColumn + 1; i <= end; i++) {
                        array[currentRow][i] = new DigPit(true, false);
                        currentColumn = i;
                    }
                    break;
                case WEST:
                    end = currentColumn - moveAmount;
                    for (int i = currentColumn - 1; i >= end; i--) {
                        array[currentRow][i] = new DigPit(true, false);
                        currentColumn = i;
                    }
                    break;
                case NORTH:
                    end = currentRow - moveAmount;
                    for (int i = currentRow - 1; i >= end; i--) {
                        array[i][currentColumn] = new DigPit(true, false);
                        currentRow = i;
                    }
                    break;
                case SOUTH:
                    end = currentRow + moveAmount;
                    for (int i = currentRow + 1; i <= end; i++) {
                        array[i][currentColumn] = new DigPit(true, false);
                        currentRow = i;
                    }
                    break;

                default:
                    break;

            }
        }

        array[startRow][startCol] = new DigPit(true, true);

        // printGraph(array);
        // System.out.println();

        DigPit[][] validTiles = new DigPit[array.length][array[0].length];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] == null) {
                    array[i][j] = new DigPit(false, false);
                }
            }
        }

        for (int i = 0; i < validTiles.length; i++) {
            diagonalDig(array, validTiles, i, 0);
        }

        for (int i = 0; i < validTiles[0].length; i++) {
            diagonalDig(array, validTiles, 0, i);
        }

        int count = 0;
        for (int i = 0; i < validTiles.length; i++) {
            for (int j = 0; j < validTiles[0].length; j++) {
                if (validTiles[i][j] != null && validTiles[i][j].dug()) {
                    count++;
                }
            }
        }


        printGraphToFile(validTiles, array);

        // Part 1 code goes here
        return count;
    }

    public long decodeHexadecimal(String toDecode) {
        long tally = 0;

        for (int i = toDecode.length() - 1; i >= 0; i--) {
            char letter = toDecode.charAt(i);

            int value = 0;
            if (letter < 58) {
                value = letter - 48;
            } else {
                value = letter - 87;
            }

            tally += value * Math.pow(16, toDecode.length() - i - 1);
        }

        return tally;
    }

    record Line(Point first, Point second) {

    }

    class Point{
        private long x;
        private long y;

        private ArrayList<Line> connectedLines;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
            connectedLines = new ArrayList<>();
        }

        public long getX() {
            return x;
        }

        public long getY() {
            return y;
        }

        public void setX(long x) {
            this.x = x;
        }

        public void setY(long y) {
            this.y = y;
        }

        public ArrayList<Line> getConnectedLines() {
            return connectedLines;
        }
    }

    public void sortPoints(ArrayList<Point> pointList) {
        pointList.sort((point1, point2) -> {
            int comp = -1 * (int) (point1.getY() - point2.getY());
            if (comp == 0) {
                comp = 1 * (int) (point1.getX() - point2.getX());
            }
            return comp;
        });
    }

    public Point pointAtCoords(ArrayList<Point> points, Point toCheck) {
        // System.out.println(toCheck.getX() + ", " + toCheck.getY());
        for (Point point : points) {
            if (point.getX() == toCheck.getX() && point.getY() == toCheck.getY()) {
                return point;
            }
        }
        return null;
    }

    public Line connectPoints(Point pointOne, Point pointTwo) {
        Line newLine = new Line(pointOne, pointTwo);
        pointOne.getConnectedLines().add(newLine);
        pointTwo.getConnectedLines().add(newLine);
        return newLine;
    }

    public Line connectPoints(Point pointOne, Point pointTwo, ArrayList<Line> addArray) {
        Line newLine = connectPoints(pointOne, pointTwo);
        addArray.add(newLine);
        return newLine;
    }

    public boolean horizontalLineTest(Line lineToTest, long coordinate) {
        long yFirst = lineToTest.first().getY();
        long ySecond = lineToTest.second().getY();
        
        long shorterCoordinate = Math.min(yFirst, ySecond);
        long fartherCoordnate = Math.max(yFirst, ySecond);

        return coordinate > shorterCoordinate && coordinate < fartherCoordnate;
    }

    public boolean verticalLineTest(Line lineToTest, long coordinate) {
        long xFirst = lineToTest.first().getX();
        long xSecond = lineToTest.second().getX();
        
        long shorterCoordinate = Math.min(xFirst, xSecond);
        long fartherCoordnate = Math.max(xFirst, xSecond);

        return coordinate >= shorterCoordinate && coordinate <= fartherCoordnate;
    }

    public void removeLineFromPoints(Line line) {
        line.first().getConnectedLines().remove(line);
        line.second().getConnectedLines().remove(line);
    }


    public long doPart2(List<String> lines) {
        long xPos = 0;
        long yPos = 0;

        Point firstPoint = new Point(0, 0); // placeholder
        Point lastPoint = new Point(0, 0);
        boolean firstCycle = true;
        
        ArrayList<Point> pointList = new ArrayList<>();

        ArrayList<Line> horizontalLineList = new ArrayList<>();
        ArrayList<Line> verticalLineList = new ArrayList<>();

        ArrayList<Long> xCords = new ArrayList<>(); 
        ArrayList<Long> yCords = new ArrayList<>(); 

        long perimeter = 0;
        for (int i = 0; i < lines.size(); i++) {
            String stringDirection = lines.get(i);
            String code = stringDirection.substring(stringDirection.indexOf("(") + 2, stringDirection.length() - 1);

            char charAt = code.charAt(code.length() - 1);
            long magnitude = decodeHexadecimal(code.substring(0, code.length() - 1));
            perimeter += magnitude;

            ArrayList<Line> listToAddLine = horizontalLineList;

            if (charAt == '1') {
                yPos -= magnitude; 
                listToAddLine = verticalLineList;

            } else if (charAt == '3') {
                yPos += magnitude; 
                listToAddLine = verticalLineList;

            } else if (charAt == '0') {
                xPos += magnitude; 

            } else if (charAt == '2') {
                xPos -= magnitude; 

            }

            // make sure we have coords of all points for the cut step
            if (!xCords.contains(xPos)) {
                xCords.add(xPos);
            }
            if (!yCords.contains(yPos)) {
                yCords.add(yPos);
            }     

            if (i == lines.size()) {
                connectPoints(firstPoint, lastPoint, listToAddLine);
                break;
            }

            Point newPoint = new Point(xPos, yPos);
            pointList.add(newPoint);

            // connect lines
            if (firstCycle) {
                firstPoint = newPoint;
                firstCycle = false;
            } else {
                connectPoints(lastPoint, newPoint, listToAddLine);
            }

            lastPoint = newPoint;
        }

        
        System.out.println("points before corner additions: ");
        pointList.forEach((p) -> {
            System.out.println(p.getX() + ", " + p.getY());
        });
        System.out.println();

        // establish new corners
        for (long coordinateToCheck : yCords) {
            ArrayList<Line> addVerticalLines = new ArrayList<>();

            ArrayList<Line> removeLines = new ArrayList<>();
            for (Line line : verticalLineList) {
                if (horizontalLineTest(line, coordinateToCheck)) {
                    removeLines.add(line);
                    Point newPoint = new Point(line.first().getX(), coordinateToCheck);
                    pointList.add(newPoint);

                    connectPoints(line.first(), newPoint, addVerticalLines);
                    connectPoints(line.second(), newPoint, addVerticalLines);

                    removeLineFromPoints(line);
                    System.out.println("added: " + newPoint.getX() + ", " + newPoint.getY());
                }
            }

            verticalLineList.addAll(addVerticalLines);
            verticalLineList.removeAll(removeLines);
        }
        System.out.println();

        System.out.println("points before sort: ");
        pointList.forEach((p) -> {
            System.out.println(p.getX() + ", " + p.getY());
        });
        System.out.println();

        pointList.sort((a, b) -> {
            int match = -(int) (a.getY() - b.getY());
            if (match == 0) {
                return (int) (a.getX() - b.getX());
            }
            return match;
        });

        System.out.println("points after sort: ");
        pointList.forEach((p) -> {
            System.out.println(p.getX() + ", " + p.getY());
        });
        System.out.println();

        boolean done = false;
        long area = 0;

        for (long position : xCords) {
            System.out.println(position);
        }
        System.out.println();

        while (!done) {
            if (pointList.size() <= 4) {
                break;
            }
            Point coordOne = pointList.get(0); 
            Point coordTwo = pointList.get(1);

            long belowPosition = Long.MIN_VALUE;
            long cordPairY = coordOne.getY();
            boolean foundBelow = false;

            for (long position : yCords) {
                if (position > belowPosition && position < cordPairY) {
                    belowPosition = position;
                    foundBelow = true;
                }
            }

            // System.out.println("below position: " + belowPosition);

            if (!foundBelow) {
                done = true;
                break;
            }

            Point belowPointOne = pointAtCoords(pointList, new Point(coordOne.getX(), belowPosition));
            Point belowPointTwo = pointAtCoords(pointList, new Point(coordTwo.getX(), belowPosition));
            // System.out.println(coordOne.getX() + ", " + coordOne.getY());
            // System.out.println(coordTwo.getX() + ", " + coordOne.getY());
            
            // System.out.println(coordOne.getX() + ", " + belowPosition);
            // System.out.println(coordTwo.getX() + ", " + belowPosition);

            if (belowPointOne == null || belowPointTwo == null) {
                if (belowPointTwo == null) {
                    pointList.remove(coordTwo);
                }

                if (belowPointOne == null) {
                    pointList.remove(coordOne);
                }
                continue;
            }
            
            System.out.println(Math.abs(coordTwo.getX() - coordOne.getX()) + " * " + Math.abs(coordOne.getY() - belowPosition));
            area += Math.abs((coordOne.getY() - belowPosition)) * Math.abs((coordTwo.getX() - coordOne.getX()));

            pointList.remove(coordOne);
            pointList.remove(coordTwo);
            // pointList.remove(belowPointOne);
            // pointList.remove(belowPointTwo);
        }

        // adjustments, figured out tweaking this is needed to give the correct answer
        perimeter /= 2;
        perimeter++;

        System.out.println("area: " + area);
        System.out.println("perimeter: " + perimeter);

        System.out.println("difference from test input: " + (952408144115L - area));
        System.out.println("difference from test input with perimeter: " + (952408144115L - area - perimeter));
    
        return area + perimeter;
    }

}
