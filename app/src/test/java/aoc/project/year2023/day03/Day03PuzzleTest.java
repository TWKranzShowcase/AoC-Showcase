package aoc.project.year2023.day03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day03PuzzleTest {
    private Day03Puzzle day03Puzzle;

    @BeforeEach
    public void setUp(){
        day03Puzzle = new Day03Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day03Example1.txt");
        assertNotNull(lines);
        assertEquals(10, lines.size());
        assertEquals(4361, day03Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day03Example1.txt");
        assertNotNull(lines);
        assertEquals(0, lines.size());
        assertEquals(-1, day03Puzzle.doPart2(lines));  
    }

}
