package aoc.project.year2023.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day17PuzzleTest {
    private Day17Puzzle day17Puzzle;

    @BeforeEach
    public void setUp(){
        day17Puzzle = new Day17Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day17Example1.txt");
        assertNotNull(lines);
        assertEquals(13, lines.size());
        assertEquals(102, day17Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day17Example1.txt");
        assertNotNull(lines);
        assertEquals(13, lines.size());
        assertEquals(94, day17Puzzle.doPart2(lines));  
    }

}
