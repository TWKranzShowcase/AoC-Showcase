package aoc.project.year2023.day19;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import aoc.project.util.AocUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day19PuzzleTest {
    private Day19Puzzle day19Puzzle;

    @BeforeEach
    public void setUp(){
        day19Puzzle = new Day19Puzzle();
    }

    @Test
    public void doPart1_givenExample1_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day19Example1.txt");
        assertNotNull(lines);
        assertEquals(17, lines.size());
        assertEquals(19114, day19Puzzle.doPart1(lines)); 
    }

    @Test
    public void doPart2_givenChallenge_assertNotNull(){
        List<String> lines = AocUtil.readFile("app/src/test/resources/puzzleInputs/year2023/day19Example1.txt");
        assertNotNull(lines);
        assertEquals(17, lines.size());
        assertEquals(167409079868000L, day19Puzzle.doPart2(lines));  
    }

}
