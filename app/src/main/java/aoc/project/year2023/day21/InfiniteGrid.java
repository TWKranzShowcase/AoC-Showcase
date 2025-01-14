package aoc.project.year2023.day21;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.function.Consumer;

import aoc.project.util.Grid.Coordinates;
import aoc.project.util.Grid.Grid;

public class InfiniteGrid<T> extends Grid<T> {
    protected HashMap<Coordinates, ArrayList<ArrayList<T>>> gridMap;

    public InfiniteGrid(int rows, int columns) {
        super(rows, columns);

        gridMap = new HashMap<>();
    }

    public void setInternalGrid(ArrayList<ArrayList<T>> setList) {
        internalGrid = setList;
    }

    public int getAdjustedRow(int row) {
        row %= getRowSize();
        if (row < 0) {
            row = getRowSize() + row;
        }
        return row;
    }

    public int getAdjustedColumn(int col) {
        col %= getColumnSize();
        if (col < 0) {
            col = getColumnSize() + col;
        }
        return col;
    }

    public int getInfiniteRow(int row) {
        if (row < 0) {
            row -= getRowSize();
        }
        return row / getRowSize();
    }

    public int getInfiniteCol(int col) {
        if (col < 0) {
            col -= getColumnSize();
        }
        return col / getColumnSize();
    }

    public ArrayList<ArrayList<T>> getGridOrCreate(int row, int col) {
        Coordinates checkCords = new Coordinates(getInfiniteRow(row), getInfiniteCol(col));

        if (gridMap.containsKey(checkCords)) {
            return gridMap.get(checkCords);
        } else {
            // System.out.println(checkCords.row() + "," + checkCords.column());

            ArrayList<ArrayList<T>> copyGrid = new ArrayList<>();
            for (int i = 0; i < internalGrid.size(); i++) {
                ArrayList<T> addCycle = new ArrayList<>();
                copyGrid.add(addCycle);
                for (int j = 0; j < internalGrid.get(0).size(); j++) {
                    addCycle.add(internalGrid.get(i).get(j));
                }
            }

            gridMap.put(checkCords, copyGrid);
            return copyGrid;
        }
    }

    @Override
    public boolean isValidCord(int row, int column) {
        return true;
    }

    @Override
    public T replaceElement(int row, int column, T replaceWith) {
        ArrayList<ArrayList<T>> useGrid = getGridOrCreate(row, column);
        T oldElement = useGrid.get(getAdjustedRow(row)).set(getAdjustedColumn(column), replaceWith);
        return oldElement;
    }

    @Override
    public T getElement(int row, int column) {
        ArrayList<ArrayList<T>> useGrid = getGridOrCreate(row, column);
        return useGrid.get(getAdjustedRow(row)).get(getAdjustedColumn(column));
    }

    @Override
    public void printGrid(Consumer<T> action) {
        for (Entry<Coordinates, ArrayList<ArrayList<T>>> entry : gridMap.entrySet()) {
            System.out.println("(" + entry.getKey().row() + ", " + entry.getKey().column() + ")");

            ArrayList<ArrayList<T>> workMap = entry.getValue();
            for (int i = 0; i < workMap.size(); i++) {
                for (int j = 0; j < workMap.get(0).size(); j++) {
                    action.accept(workMap.get(i).get(j));
                }
                System.out.println();
            }

            System.out.println();
        }
    }
}
