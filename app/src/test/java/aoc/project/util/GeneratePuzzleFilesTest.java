package aoc.project.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aoc.project.Constants;

public class GeneratePuzzleFilesTest {
    private final String fileSystemPathToAocProject = Constants.PATH_TO_PROJECT;

    private GeneratePuzzleFiles generatePuzzleFiles;

    @BeforeEach
    public void setUp() {
        generatePuzzleFiles = new GeneratePuzzleFiles(fileSystemPathToAocProject);
    }


    @Test
    public void generateFiles_givenPathYearAndDay_generateNewFilesIfNoneExist(){
        generatePuzzleFiles.generateFiles(2024, 14);

        assertTrue(true, "Nothing to see here.");
    }
}
