package aoc.project.year2023.day16;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day16PuzzleTest {
    private Day16Puzzle day16Puzzle;

    @BeforeEach
    public void setUp(){
        day16Puzzle = new Day16Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day16Example1.txt");
        assertNotNull(lines);
        assertEquals(10, lines.size());
        assertEquals(46, day16Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day16Example1.txt");
        assertNotNull(lines);
        assertEquals(10, lines.size());
        assertEquals(51, day16Puzzle.doPart2(lines));  
    }

}
