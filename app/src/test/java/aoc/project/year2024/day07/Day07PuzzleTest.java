package aoc.project.year2024.day07;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day07PuzzleTest {
    private Day07Puzzle day07Puzzle;

    @BeforeEach
    public void setUp(){
        day07Puzzle = new Day07Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2024/day07Example1.txt");
        assertNotNull(lines);
        assertEquals(9, lines.size());
        assertEquals(11387, day07Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2024/day07Example1.txt");
        assertNotNull(lines);
        assertEquals(0, lines.size());
        assertEquals(-1, day07Puzzle.doPart2(lines));  
    }

}
