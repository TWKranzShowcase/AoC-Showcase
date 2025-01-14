package aoc.project.year2024.day06;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day06PuzzleTest {
    private Day06Puzzle day06Puzzle;

    @BeforeEach
    public void setUp(){
        day06Puzzle = new Day06Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2024/day06Example1.txt");
        assertNotNull(lines);
        assertEquals(10, lines.size());
        assertEquals(41, day06Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2024/day06Example1.txt");
        assertNotNull(lines);
        assertEquals(10, lines.size());
        assertEquals(6, day06Puzzle.doPart2(lines));  
    }

}
