package aoc.project.year2023.day24;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day24PuzzleTest {
    private Day24Puzzle day24Puzzle;

    @BeforeEach
    public void setUp(){
        day24Puzzle = new Day24Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day24Example1.txt");
        assertNotNull(lines);
        assertEquals(5, lines.size());
        assertEquals(2, day24Puzzle.doPart1(lines));
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day24Example1.txt");
        assertNotNull(lines);
        assertEquals(0, lines.size());
        assertEquals(-1, day24Puzzle.doPart2(lines));  
    }

}
