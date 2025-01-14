package aoc.project.year2023.day18;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day18PuzzleTest {
    private Day18Puzzle day18Puzzle;

    @BeforeEach
    public void setUp(){
        day18Puzzle = new Day18Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day18Example1.txt");
        assertNotNull(lines);
        assertEquals(14, lines.size());
        assertEquals(62, day18Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day18Example1.txt");
        assertNotNull(lines);
        assertEquals(14, lines.size());
        assertEquals(952408144115L, day18Puzzle.doPart2(lines));  
    }

}
