package aoc.project.year2023.day15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day15PuzzleTest {
    private Day15Puzzle day15Puzzle;

    @BeforeEach
    public void setUp(){
        day15Puzzle = new Day15Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day15Example1.txt");
        assertNotNull(lines);
        assertEquals(1, lines.size());
        assertEquals(1320, day15Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day15Example1.txt");
        assertNotNull(lines);
        assertEquals(1, lines.size());
        assertEquals(145, day15Puzzle.doPart2(lines));  
    }

}
