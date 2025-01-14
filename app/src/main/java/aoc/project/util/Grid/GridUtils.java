package aoc.project.util.Grid;

import java.util.ArrayList;
import java.util.List;

import aoc.project.year2023.day21.InfiniteGrid;

public class GridUtils {

    public static Grid<Character> parseStringInput(List<String> lines) {
        Grid<Character> newGrid =  new Grid<>(lines.size(), lines.get(0).length());

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(0).length(); j++) {
                newGrid.replaceElement(i, j, lines.get(i).charAt(j));
            }
        }

        return newGrid;
    }

    public static InfiniteGrid<Character> parseStringInputInfiniteGrid(List<String> lines) {
        InfiniteGrid<Character> newGrid =  new InfiniteGrid<>(lines.size(), lines.get(0).length());
        ArrayList<ArrayList<Character>> internal = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            internal.add(new ArrayList<>());
            for (int j = 0; j < lines.get(0).length(); j++) {
                internal.get(i).add(lines.get(i).charAt(j));
            }
        }

        newGrid.setInternalGrid(internal);

        return newGrid;
    }

    public static <T> GridPointer<T> createPointer(Grid<T> grid, int row, int col, Direction dir) {
        return new GridPointer<T>(grid, row, col, dir);
    }
}
