package aoc.project.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import aoc.project.Constants;

public class AocUtil {

    public static List<String> readFile(String filePathAndName) {
        List<String> lines = null;
        try {
            lines = Files
            		.lines(Paths.get(Constants.PATH_TO_PROJECT, filePathAndName))
            		.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static double parseNumber(String parseFrom, int startingPoint, boolean includeFractions) {
        int newStart = startingPoint;
        while (newStart - 1 >= 0 && Character.isDigit(parseFrom.charAt(newStart - 1)) && !(includeFractions && parseFrom.charAt(newStart - 1) == '.')) {
            newStart--;
        }

        int newEnd = startingPoint;
        while (newEnd + 1 < parseFrom.length() && Character.isDigit(parseFrom.charAt(newEnd + 1)) && !(includeFractions && parseFrom.charAt(newEnd + 1) == '.')) {
            newEnd++;
        }

        return Double.parseDouble(parseFrom.substring(newStart, newEnd + 1));
    }

    public static long parseNumberLong(String parseFrom, int startingPoint) {
        int newStart = startingPoint;
        while (newStart - 1 >= 0 && (Character.isDigit(parseFrom.charAt(newStart - 1)) || parseFrom.charAt(newStart - 1) == '-')) {
            newStart--;
        }

        int newEnd = startingPoint;
        while (newEnd + 1 < parseFrom.length() && Character.isDigit(parseFrom.charAt(newEnd + 1))) {
            newEnd++;
        }

        return Long.parseLong(parseFrom.substring(newStart, newEnd + 1));
    }

    public static int parseNumberInt(String parseFrom, int startingPoint) {
        return (int) parseNumber(parseFrom, startingPoint, false);
    }
    
}
