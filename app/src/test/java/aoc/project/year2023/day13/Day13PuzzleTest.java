package aoc.project.year2023.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day13PuzzleTest {
    private Day13Puzzle day13Puzzle;

    @BeforeEach
    public void setUp(){
        day13Puzzle = new Day13Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day13Example1.txt");
        assertNotNull(lines);
        assertEquals(15, lines.size());
        assertEquals(405, day13Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day13Example1.txt");
        assertNotNull(lines);
        assertEquals(15, lines.size());
        assertEquals(
            400, day13Puzzle.doPart2(lines));  
    }

}
