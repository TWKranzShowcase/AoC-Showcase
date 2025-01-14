package aoc.project.year2023.day21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day21PuzzleTest {
    private Day21Puzzle day21Puzzle;

    @BeforeEach
    public void setUp(){
        day21Puzzle = new Day21Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day21Example1.txt");
        assertNotNull(lines);
        assertEquals(11, lines.size());
        assertEquals(42, day21Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day21Example1.txt");
        assertNotNull(lines);
        assertEquals(11, lines.size());
        assertEquals(6536, day21Puzzle.doPart2(lines));  

    }
}
