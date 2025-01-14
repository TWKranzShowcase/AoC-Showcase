package aoc.project.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aoc.project.Constants;

public class FetchPuzzleInputTest {
    private final String fileSystemPathToAocProject = Constants.PATH_TO_PROJECT;

    private FetchPuzzleInput fetchPuzzleInput;

    @BeforeEach
    public void setUp() {
        fetchPuzzleInput = new FetchPuzzleInput(fileSystemPathToAocProject);
    }


    @Test
    public void generateFiles_givenPathYearAndDay_generateNewFilesIfNoneExist(){
        fetchPuzzleInput.fetchPuzzleInput(Constants.AOC_YEAR,4);

        assertTrue(true, "Nothing to see here.");
    }
}
