package aoc.project.year2023.day09;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day09PuzzleTest {
    private Day09Puzzle day09Puzzle;

    @BeforeEach
    public void setUp(){
        day09Puzzle = new Day09Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day09Example1.txt");
        assertNotNull(lines);
        assertEquals(3, lines.size());
        assertEquals(114, day09Puzzle.doPart1(lines));
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day09Example1.txt");
        assertNotNull(lines);
        assertEquals(3, lines.size());
        assertEquals(2, day09Puzzle.doPart2(lines));  
    }

}
