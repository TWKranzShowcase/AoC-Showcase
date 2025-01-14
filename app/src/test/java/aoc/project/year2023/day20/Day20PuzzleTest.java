package aoc.project.year2023.day20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day20PuzzleTest {
    private Day20Puzzle day20Puzzle;

    @BeforeEach
    public void setUp(){
        day20Puzzle = new Day20Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day20Example1.txt");
        assertNotNull(lines);
        assertEquals(5, lines.size());
        assertEquals(11687500, day20Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day20Example1.txt");
        assertNotNull(lines);
        assertEquals(5, lines.size());
        assertEquals(-1, day20Puzzle.doPart2(lines));  
    }

}
