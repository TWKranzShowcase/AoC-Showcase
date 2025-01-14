package aoc.project.year2023.day23;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day23PuzzleTest {
    private Day23Puzzle day23Puzzle;

    @BeforeEach
    public void setUp(){
        day23Puzzle = new Day23Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day23Example1.txt");
        assertNotNull(lines);
        assertEquals(23, lines.size());
        assertEquals(94, day23Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day23Example1.txt");
        assertNotNull(lines);
        assertEquals(23, lines.size());
        assertEquals(154, day23Puzzle.doPart2(lines));  
    }

}
