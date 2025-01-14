package aoc.project.year2023.day08;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day08PuzzleTest {
    private Day08Puzzle day08Puzzle;

    @BeforeEach
    public void setUp(){
        day08Puzzle = new Day08Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day08Example1.txt");
        assertNotNull(lines);
        assertEquals(9, lines.size());
        assertEquals(2, day08Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day08Example1.txt");
        assertNotNull(lines);
        assertEquals(10, lines.size());
        assertEquals(6, day08Puzzle.doPart2(lines));  
    }

}
