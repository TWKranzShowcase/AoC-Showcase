package aoc.project.year2024.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day10PuzzleTest {
    private Day10Puzzle day10Puzzle;

    @BeforeEach
    public void setUp(){
        day10Puzzle = new Day10Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2024/day10Example1.txt");
        assertNotNull(lines);
        assertEquals(8, lines.size());
        assertEquals(36, day10Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2024/day10Example1.txt");
        assertNotNull(lines);
        assertEquals(7, lines.size());
        assertEquals(13, day10Puzzle.doPart2(lines));  
    }

}
