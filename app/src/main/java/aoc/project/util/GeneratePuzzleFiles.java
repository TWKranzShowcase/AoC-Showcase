package aoc.project.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class GeneratePuzzleFiles {
    private final String fileSystemPathToAocProject;
    private final Path templateMainFile;
    private final Path templateStringFile;

    public GeneratePuzzleFiles(String fileSystemPathToAocProject) {
        this.fileSystemPathToAocProject = fileSystemPathToAocProject;
        this.templateMainFile = Path.of(fileSystemPathToAocProject, "app/src/main/resources/templates",
                "TemplateMainDayPuzzle.txt");
        this.templateStringFile = Path.of(fileSystemPathToAocProject, "app/src/main/resources/templates",
                "TemplateTestDayPuzzle.txt");
    }

    public void generateFiles(int year, int day) {
        try {
            if (StringUtils.isEmpty(fileSystemPathToAocProject)) {
                System.err.println("The provided path must not be empty or null.");
                return;
            }

            if (!Files.exists(Path.of(fileSystemPathToAocProject))) {
                System.err.println("The provided path to this project does not exist.  Path provided is: "
                        + fileSystemPathToAocProject);
                return;
            }

            String yearNum = Integer.toString(year);
            String yearString = String.format("%04d", year);
            String dayNum = Integer.toString(day);
            String dayString = String.format("%02d", day);

            String packagePath = String.format("aoc.project.year%s.day%s", yearString, dayString);

            // test and create main package and class
            Path mainClassFilePath = Path.of(fileSystemPathToAocProject, "app/src/main/java", String.format("aoc/project/year%s/day%s", yearString, dayString));
            Path mainClassFile = Path.of(mainClassFilePath.toString(), "Day" + dayString + "Puzzle.java");

            if (!Files.exists(mainClassFile)) {
                System.out.println("Create this file: " + mainClassFile.toString());

                Files.createDirectories(mainClassFilePath);

                List<String> lines = Files.readAllLines(templateMainFile);
                lines = convertJavaText(lines, packagePath, yearNum, yearString, dayNum, dayString);
                Files.write(mainClassFile, lines, StandardOpenOption.CREATE_NEW);
            } else {
                System.out.println("File already Exists.  Skipping... " + mainClassFile.toString());
            }

            // test and create test package and class
            Path testClassFilePath = Path.of(fileSystemPathToAocProject, "app/src/test/java", String.format("aoc/project/year%s/day%s", yearString, dayString));
            Path testClassFile = Path.of(testClassFilePath.toString(), "Day" + dayString + "PuzzleTest.java");

            if (!Files.exists(testClassFile)) {
                System.out.println("Create this file: " + testClassFile.toString());

                Files.createDirectories(testClassFilePath);

                List<String> lines = Files.readAllLines(templateStringFile);
                lines = convertJavaText(lines, packagePath, yearNum, yearString, dayNum, dayString);
                Files.write(testClassFile, lines, StandardOpenOption.CREATE_NEW);
            } else {
                System.out.println("File already Exists.  Skipping... " + testClassFile.toString());
            }

            // test and create main puzzle input empty
            Path mainPuzzleInputPath = Path.of(fileSystemPathToAocProject, "app/src/main/resources", String.format("puzzleInputs/year%s", yearString));
            Path mainPuzzleInputFile = Path.of(mainPuzzleInputPath.toString(), "day" + dayString + "Puzzle.txt");
            if (!Files.exists(mainPuzzleInputFile)) {
                System.out.println("Create this file: " + mainPuzzleInputFile.toString());

                Files.createDirectories(mainPuzzleInputPath);
                Files.createFile(mainPuzzleInputFile);
            } else {
                System.out.println("File already Exists.  Skipping... " + mainPuzzleInputFile.toString());
            }

            // test and create test puzzle input 1 empty
            Path testPuzzleInputPath = Path.of(fileSystemPathToAocProject, "app/src/test/resources", String.format("puzzleInputs/year%s", yearString));
            Path testPuzzleInputFile = Path.of(testPuzzleInputPath.toString(), "day" + dayString + "Example1.txt");
            if (!Files.exists(testPuzzleInputFile)) {
                System.out.println("Create this file: " + testPuzzleInputFile.toString());

                Files.createDirectories(testPuzzleInputPath);
                Files.createFile(testPuzzleInputFile);
            } else {
                System.out.println("File already Exists.  Skipping... " + testPuzzleInputFile.toString());
            }

        } catch (IOException e) {
            // sad face - something to fix
            e.printStackTrace();
        }
    }

    private List<String> convertJavaText(List<String> lines, String projectPath, String yearNum, String yearString,
            String dayNum, String dayString) {
        List<String> retval = new ArrayList<>(lines.size());
        for (String line : lines) {
            line = StringUtils.replace(line, "{{projectPath}}", projectPath);
            line = StringUtils.replace(line, "{{yearNum}}", yearNum);
            line = StringUtils.replace(line, "{{yearString}}", yearString);
            line = StringUtils.replace(line, "{{dayNum}}", dayNum);
            line = StringUtils.replace(line, "{{dayString}}", dayString);
            retval.add(line);
        }
        return retval;
    }
}
