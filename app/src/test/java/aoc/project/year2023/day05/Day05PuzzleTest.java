package aoc.project.year2023.day05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day05PuzzleTest {
    private Day05Puzzle day05Puzzle;

    @BeforeEach
    public void setUp(){
        day05Puzzle = new Day05Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day05Example1.txt");
        assertNotNull(lines);
        assertEquals(33, lines.size());
        assertEquals(35, day05Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day05Example1.txt");
        assertNotNull(lines);
        assertEquals(33, lines.size());
        assertEquals(46, day05Puzzle.doPart2(lines));  
    }

}
