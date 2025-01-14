package aoc.project.util.Grid;

import java.util.ArrayList;

public class GridPointer<T extends Object> {
    private int pointerRow;
    private int pointerColumn;
    private Direction direction;

    private int benchmarkPointerRow;
    private int benchmarkPointerColumn;
    private Direction benchmarkDirection;

    private Grid<T> pointGrid;

    public GridPointer(Grid<T> pointGrid, int row, int col, Direction direction) {
        this.pointGrid = pointGrid;
        pointerRow = row;
        pointerColumn = col;
        this.direction = direction;

        setBenchmark();
    }

    public Coordinates getCoordinates() {
        return new Coordinates(pointerRow, pointerColumn);
    }

    public void setDirection(Direction set) {
        direction = set;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setPointerPosition(int row, int col) {
        pointerRow = row;
        pointerColumn = col;
    }

    
    public void setPointerPosition(Coordinates coords) {
        setPointerPosition(coords.row(), coords.column());
    }

    public T getItemAtPointer() {
        return pointGrid.getElement(pointerRow, pointerColumn);
    }

    public T replaceElementAtPointer(T replace) {
        return pointGrid.replaceElement(pointerRow, pointerColumn, replace);
    }

    public void shiftPointerPosition(int rowMove, int colMove) {
        pointerRow += rowMove;
        pointerColumn += colMove;
    }

    public void setBenchmark() {
        benchmarkPointerRow = pointerRow;
        benchmarkPointerColumn = pointerColumn;
        benchmarkDirection = direction;
    }

    public void returnToBenchmark() {
        pointerRow = benchmarkPointerRow;
        pointerColumn = benchmarkPointerColumn;
        direction = benchmarkDirection;
    }
    

    public T shiftAndGetElement(int rowMove, int colMove) {
        shiftPointerPosition(rowMove, colMove);
        return getItemAtPointer();
    }

    public void moveUp(int magnitude) {
        shiftPointerPosition(-magnitude, 0);
    }

    public void moveDown(int magnitude) {
        shiftPointerPosition(magnitude, 0);
    }

    public void moveRight(int magnitude) {
        shiftPointerPosition(0, magnitude);
    }

    public void moveLeft(int magnitude) {
        shiftPointerPosition(0, -magnitude);
    }

    public ArrayList<Coordinates> getNearby(boolean cardinal) {
        ArrayList<Coordinates> nearby = new ArrayList<>();
        setBenchmark();

        moveUp(1);
        nearby.add(getCoordinates());
        returnToBenchmark();

        moveRight(1);
        nearby.add(getCoordinates());
        returnToBenchmark();

        moveDown(1);
        nearby.add(getCoordinates());
        returnToBenchmark();

        moveLeft(1);
        nearby.add(getCoordinates());
        returnToBenchmark();

        if (!cardinal) {
            moveDown(1);
            moveRight(1);
            nearby.add(getCoordinates());
            returnToBenchmark();
    
            moveUp(1);
            moveRight(1);
            nearby.add(getCoordinates());
            returnToBenchmark();
    
            moveDown(1);
            moveLeft(1);
            nearby.add(getCoordinates());
            returnToBenchmark();
    
            moveUp(1);
            moveLeft(1);
            nearby.add(getCoordinates());
            returnToBenchmark();
        }

        for (int i = 0; i < nearby.size(); i++) {
            Coordinates checkCord = nearby.get(i);
            if (!pointGrid.isValidCord(checkCord.row(), checkCord.column())) {
                nearby.remove(i);
                i--;
            }
        }

        return nearby;
    }

    public ArrayList<Coordinates> getNearbyWithOutOfBands(boolean cardinal) {
        ArrayList<Coordinates> nearby = new ArrayList<>();
        setBenchmark();

        moveUp(1);
        nearby.add(getCoordinates());
        returnToBenchmark();

        moveRight(1);
        nearby.add(getCoordinates());
        returnToBenchmark();

        moveDown(1);
        nearby.add(getCoordinates());
        returnToBenchmark();

        moveLeft(1);
        nearby.add(getCoordinates());
        returnToBenchmark();

        if (!cardinal) {
            moveDown(1);
            moveRight(1);
            nearby.add(getCoordinates());
            returnToBenchmark();
    
            moveUp(1);
            moveRight(1);
            nearby.add(getCoordinates());
            returnToBenchmark();
    
            moveDown(1);
            moveLeft(1);
            nearby.add(getCoordinates());
            returnToBenchmark();
    
            moveUp(1);
            moveLeft(1);
            nearby.add(getCoordinates());
            returnToBenchmark();
        }

        return nearby;
    }

    public int getNearbyOutOfBounds(boolean cardinal) {
        int exact = cardinal ? 4 : 8;
        exact -= getNearby(cardinal).size();

        return exact;
    }

    public void moveAccordingToDirection(int magnitude) {
        switch (direction) {
            case EAST:
                moveRight(magnitude);
                break;
            case NORTH:
                moveUp(magnitude);
                break;
            case NORTHEAST:
                moveRight(magnitude);
                moveUp(magnitude);
                break;
            case NORTHWEST:
                moveLeft(magnitude);
                moveUp(magnitude);
                break;
            case SOUTH:
                moveDown(magnitude);
                break;
            case SOUTHEAST:
                moveRight(magnitude);
                moveDown(magnitude);
                break;
            case SOUTHWEST:
                moveLeft(magnitude);
                moveDown(magnitude);
                break;
            case WEST:
                moveLeft(magnitude);
                break;
            default:
                break;
        }
    }
}
