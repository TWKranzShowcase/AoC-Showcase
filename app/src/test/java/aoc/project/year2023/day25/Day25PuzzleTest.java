package aoc.project.year2023.day25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day25PuzzleTest {
    private Day25Puzzle day25Puzzle;

    @BeforeEach
    public void setUp(){
        day25Puzzle = new Day25Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day25Example1.txt");
        assertNotNull(lines);
        assertEquals(13, lines.size());
        assertEquals(54, day25Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day25Example1.txt");
        assertNotNull(lines);
        assertEquals(0, lines.size());
        assertEquals(-1, day25Puzzle.doPart2(lines));  
    }

}
