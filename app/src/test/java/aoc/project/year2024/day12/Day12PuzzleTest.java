package aoc.project.year2024.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day12PuzzleTest {
    private Day12Puzzle day12Puzzle;

    @BeforeEach
    public void setUp(){
        day12Puzzle = new Day12Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2024/day12Example1.txt");
        assertNotNull(lines);
        assertEquals(4, lines.size());
        assertEquals(140, day12Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2024/day12Example1.txt");
        assertNotNull(lines);
        assertEquals(4, lines.size());
        assertEquals(80, day12Puzzle.doPart2(lines));  
    }

}
