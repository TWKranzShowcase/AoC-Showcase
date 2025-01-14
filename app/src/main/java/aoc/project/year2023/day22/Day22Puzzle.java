package aoc.project.year2023.day22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import aoc.project.Constants;
import aoc.project.util.AocUtil;
import aoc.project.util.FetchPuzzleInput;

import org.apache.commons.lang3.time.StopWatch;

public class Day22Puzzle {
    public static void main(String[] args) {
        Day22Puzzle puzzle = new Day22Puzzle();
        FetchPuzzleInput fetchPuzzleInput = new FetchPuzzleInput(Constants.PATH_TO_PROJECT);
        fetchPuzzleInput.fetchPuzzleInput(2023, 22);

        puzzle.part1();
        puzzle.part2();
    }

    public void part1() {
        System.out.println("Executing Day 22 Part 1...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day22Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart1(lines);
        stopWatch.stop();

        System.out.println("  Part 1 result: " + result);
        System.out.println("  Part 1 completed in " + stopWatch.getTime() + " ms.");
    }

    public void part2() {
        System.out.println("Executing Day 22 Part 2...");
        List<String> lines = AocUtil
                .readFile("app/src/main/resources/puzzleInputs/year2023/day22Puzzle.txt");

        StopWatch stopWatch = StopWatch.createStarted();
        long result = doPart2(lines);
        stopWatch.stop();

        System.out.println("  Part 2 result: " + result);
        System.out.println("  Part 2 completed in " + stopWatch.getTime() + " ms.");
    }

    record DimensionalCoord(long x, long y, long z) {

    }

    record Block(int label, DimensionalCoord coord1, DimensionalCoord coord2) {

    }

    public boolean checkCoordBounds(long check1, long check2, long other1, long other2) {
        long lowCheck = Math.min(check1, check2);
        long highCheck = Math.max(check1, check2);

        long lowOther = Math.min(other1, other2);
        long highOther = Math.max(other1, other2);

        if (lowCheck >= lowOther && highCheck <= highOther
                || highCheck >= lowOther && lowCheck <= lowOther
                || highCheck >= highOther && lowCheck <= highOther) {
            return false;
        }

        return true;
    }

    public ArrayList<Block> blockIntersectsWithBlocksOnZLevel(HashMap<Long, ArrayList<Block>> map, Block checkBlock,
            long level) {
        ArrayList<Block> blocksOnLevel = map.getOrDefault(level, new ArrayList<>());
        ArrayList<Block> touching = new ArrayList<>();

        for (Block otherBlock : blocksOnLevel) {
            if (otherBlock.equals(checkBlock)) {
                continue;
            }

            if (!checkCoordBounds(checkBlock.coord1().x(), checkBlock.coord2().x(), otherBlock.coord1().x(),
                    otherBlock.coord2().x())
                    && !checkCoordBounds(checkBlock.coord1().y(), checkBlock.coord2().y(), otherBlock.coord1().y(),
                            otherBlock.coord2().y())) {
                touching.add(otherBlock);
            }
        }

        return touching;
    }

    public long doPart1(List<String> lines) {
        ArrayList<Block> blockList = new ArrayList<>();

        int blockCount = 0;

        for (String coordinate : lines) {
            String[] splitCoords = coordinate.split("~");

            String[] split = splitCoords[0].split(",");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            long z = Integer.parseInt(split[2]);
            DimensionalCoord coord1 = new DimensionalCoord(x, y, z);

            split = splitCoords[1].split(",");
            x = Integer.parseInt(split[0]);
            y = Integer.parseInt(split[1]);
            z = Integer.parseInt(split[2]);

            DimensionalCoord coord2 = new DimensionalCoord(x, y, z);

            blockList.add(new Block(blockCount, coord1, coord2));
            blockCount++;

        }

        // int highestX = Integer.MIN_VALUE, highestY = Integer.MIN_VALUE, highestZ =
        // Integer.MIN_VALUE;
        // for (Block block : blockList) {
        // highestX = block.coord1().x() > highestX ? block.coord1().x() : highestX;
        // highestX = block.coord2().x() > highestX ? block.coord2().x() : highestX;

        // highestY = block.coord1().y() > highestY ? block.coord1().y() : highestY;
        // highestY = block.coord2().y() > highestY ? block.coord2().y() : highestY;

        // highestZ = block.coord1().z() > highestZ ? block.coord1().z() : highestZ;
        // highestZ = block.coord2().z() > highestZ ? block.coord2().z() : highestZ;
        // }

        HashMap<Long, ArrayList<Block>> blockZMap = new HashMap<>();
        ArrayList<Block> finalBlocks = new ArrayList<>();

        blockList.sort((a, b) -> (int) (Math.min(a.coord1().z(), a.coord2().z()) - Math.min(b.coord1().z(), b.coord2().z())));

        for (Block block : blockList) {
            long low = Math.min(block.coord1().z(), block.coord2().z());
            long max = Math.max(block.coord1().z(), block.coord2().z());

            while (blockIntersectsWithBlocksOnZLevel(blockZMap, block, low - 1).size() == 0 && low > 1) {
                low--;
                max--;
                DimensionalCoord adjustOne = new DimensionalCoord(block.coord1().x(), block.coord1().y(),
                        block.coord1().z() - 1);
                DimensionalCoord adjustTwo = new DimensionalCoord(block.coord2().x(), block.coord2().y(),
                        block.coord2().z() - 1);
                block = new Block(block.label(), adjustOne, adjustTwo);
            }

            for (long i = low; i <= max; i++) {
                ArrayList<Block> blocksOnLevel = blockZMap.getOrDefault(i, new ArrayList<>());
                blocksOnLevel.add(block);
                blockZMap.put(i, blocksOnLevel);
            }
            finalBlocks.add(block);
        }

        for (Entry<Long, ArrayList<Block>> entry : blockZMap.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            for (Block blockLabel : entry.getValue()) {
                System.out.print(blockLabel + " ");
            }
            System.out.println();
        }

        System.out.println("newblocks");

        long total = 0;

        for (Block block : finalBlocks) {

            // System.out.println(block.coord1().x() + ", " + block.coord1().y() + ", " + block.coord1().z());
            // System.out.println(block.coord2().x() + ", " + block.coord2().y() + ", " + block.coord2().z());
            // System.out.println();

            long max = Math.max(block.coord1().z(), block.coord2().z());

            boolean canDestroy = true;
            for (Block touchBlock : blockIntersectsWithBlocksOnZLevel(blockZMap, block, max + 1)) {
                if (blockIntersectsWithBlocksOnZLevel(blockZMap, touchBlock, max).size() == 1) {
                    canDestroy = false;
                }
            }

            if (canDestroy) {
                total++;
            }
        }

        // Part 1 code goes here
        return total;
    }

    public long causeBlocksToFall(ArrayList<Block> checkList) {
        long total = 0;
        HashMap<Long, ArrayList<Block>> blockZMap = new HashMap<>();

        for (Block block : checkList) {
            long low = Math.min(block.coord1().z(), block.coord2().z());
            long max = Math.max(block.coord1().z(), block.coord2().z());

            boolean moved = false;

            while (blockIntersectsWithBlocksOnZLevel(blockZMap, block, low - 1).size() == 0 && low > 1) {
                low--;
                max--;
                if (!moved) {
                    moved = true;
                    total++;
                }
                DimensionalCoord adjustOne = new DimensionalCoord(block.coord1().x(), block.coord1().y(),
                        block.coord1().z() - 1);
                DimensionalCoord adjustTwo = new DimensionalCoord(block.coord2().x(), block.coord2().y(),
                        block.coord2().z() - 1);
                block = new Block(block.label(), adjustOne, adjustTwo);
            }

            for (long i = low; i <= max; i++) {
                ArrayList<Block> blocksOnLevel = blockZMap.getOrDefault(i, new ArrayList<>());
                blocksOnLevel.add(block);
                blockZMap.put(i, blocksOnLevel);
            }
        }
        
        return total;
    }

    public long doPart2(List<String> lines) {
        ArrayList<Block> blockList = new ArrayList<>();

        int blockCount = 0;

        for (String coordinate : lines) {
            String[] splitCoords = coordinate.split("~");

            String[] split = splitCoords[0].split(",");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            long z = Integer.parseInt(split[2]);
            DimensionalCoord coord1 = new DimensionalCoord(x, y, z);

            split = splitCoords[1].split(",");
            x = Integer.parseInt(split[0]);
            y = Integer.parseInt(split[1]);
            z = Integer.parseInt(split[2]);

            DimensionalCoord coord2 = new DimensionalCoord(x, y, z);

            blockList.add(new Block(blockCount, coord1, coord2));
            blockCount++;

        }

        HashMap<Long, ArrayList<Block>> blockZMap = new HashMap<>();
        ArrayList<Block> finalBlocks = new ArrayList<>();

        blockList.sort((a, b) -> (int) (Math.min(a.coord1().z(), a.coord2().z()) - Math.min(b.coord1().z(), b.coord2().z())));

        for (Block block : blockList) {
            long low = Math.min(block.coord1().z(), block.coord2().z());
            long max = Math.max(block.coord1().z(), block.coord2().z());

            while (blockIntersectsWithBlocksOnZLevel(blockZMap, block, low - 1).size() == 0 && low > 1) {
                low--;
                max--;
                DimensionalCoord adjustOne = new DimensionalCoord(block.coord1().x(), block.coord1().y(),
                        block.coord1().z() - 1);
                DimensionalCoord adjustTwo = new DimensionalCoord(block.coord2().x(), block.coord2().y(),
                        block.coord2().z() - 1);
                block = new Block(block.label(), adjustOne, adjustTwo);
            }

            for (long i = low; i <= max; i++) {
                ArrayList<Block> blocksOnLevel = blockZMap.getOrDefault(i, new ArrayList<>());
                blocksOnLevel.add(block);
                blockZMap.put(i, blocksOnLevel);
            }
            finalBlocks.add(block);
        }

        for (Entry<Long, ArrayList<Block>> entry : blockZMap.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            for (Block blockLabel : entry.getValue()) {
                System.out.print(blockLabel + " ");
            }
            System.out.println();
        }

        System.out.println("newblocks");

        ArrayList<Block> chainReactions = new ArrayList<>();

        for (Block block : finalBlocks) {
            long max = Math.max(block.coord1().z(), block.coord2().z());

            boolean canDestroy = true;
            for (Block touchBlock : blockIntersectsWithBlocksOnZLevel(blockZMap, block, max + 1)) {
                if (blockIntersectsWithBlocksOnZLevel(blockZMap, touchBlock, max).size() == 1) {
                    canDestroy = false;
                }
            }

            if (!canDestroy) {
                chainReactions.add(block);
            }
        }

        long total = 0;
        
        for (Block toCheck : chainReactions) {
            ArrayList<Block> clone = new ArrayList<>(finalBlocks);
            clone.remove(toCheck);
            total += causeBlocksToFall(clone);
        }

        return total;
    }

}
