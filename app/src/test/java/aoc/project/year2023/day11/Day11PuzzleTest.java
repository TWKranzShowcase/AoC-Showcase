package aoc.project.year2023.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day11PuzzleTest {
    private Day11Puzzle day11Puzzle;

    @BeforeEach
    public void setUp(){
        day11Puzzle = new Day11Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day11Example1.txt");
        assertNotNull(lines);
        assertEquals(10, lines.size());
        assertEquals(374, day11Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day11Example1.txt");
        assertNotNull(lines);
        assertEquals(10, lines.size());
        assertEquals(8410, day11Puzzle.doPart2(lines));  
    }

}
