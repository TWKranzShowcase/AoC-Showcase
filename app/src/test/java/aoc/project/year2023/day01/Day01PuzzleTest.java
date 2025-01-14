package aoc.project.year2023.day01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day01PuzzleTest {
    private Day01Puzzle day01Puzzle;

    @BeforeEach
    public void setUp(){
        day01Puzzle = new Day01Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day01Example1.txt");
        assertNotNull(lines);
        System.out.println(lines.size());
        assertEquals(4, lines.size());
        assertEquals(142, day01Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day01Example1.txt");
        assertNotNull(lines);
        assertEquals(7, lines.size());
        assertEquals(281, day01Puzzle.doPart2(lines));  
    }

}
