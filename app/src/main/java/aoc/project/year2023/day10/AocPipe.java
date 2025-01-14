package aoc.project.year2023.day10;

import java.util.ArrayList;

public class AocPipe {

    // constants

    enum ConnectionType {
        INVALID, // out of bounds
        IS_TILE, // this is the pipe that we are
        CONNECTED, // valid connection
        NOT_CONNECTED, // can't connect
    }

    enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST,
        UNREACHABLE,
    }

    //

    private AocPipe[][] containGrid;
    private int[] coordinates;

    private ConnectionType[][] connectionGrid;
    private boolean madeConnections = false;
    private char shape;

    public AocPipe(int x, int y, char shape, AocPipe[][] grid) {
        coordinates = new int[] { x, y };
        this.shape = shape;
        connectionGrid = new ConnectionType[3][3];
        containGrid = grid;
    }

    public char getShape() {
        return shape;
    }

    public ConnectionType[][] getConnectionGrid() {
        return connectionGrid;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public boolean swapSideValid(char checkShape) { // garbage
        if (
            (checkShape == '7' && shape == 'L') ||
            (checkShape == 'L' && shape == '7' )
        ) {
            return false;
        }

        if (
                // (checkShape == '-' && shape == 'L') ||
                (checkShape == '|' && (shape == '7' || shape == 'L')) ||
                (shape == '|' && (checkShape == '7' || checkShape == 'L')) ||
                (checkShape == 'F' && shape == 'L') ||
                (checkShape == '7' && shape == 'J') ||
                (shape == 'F' && checkShape == 'L') ||
                (shape == '7' && checkShape == 'J') 

                // (checkShape == '7' && shape == 'F') ||
                // (checkShape == 'F' && shape == '7') 

                // (checkShape == 'L' && shape == 'J')
                )  {
            return true;
        }
        return false;
    }

    public boolean swapSideValidAngle(char checkShape) {
        if ((checkShape == '7' && shape == 'L') ||
                (checkShape == 'L' && shape == '7') ||
                (checkShape == 'F' && shape == 'J') ||
                (checkShape == 'J' && shape == 'F')) {
            return true;
        }
        return false;
    }

    public boolean validIntersectionFromDirection(Direction from) {
        switch (shape) {
            case '|':
                return from == Direction.NORTH || from == Direction.SOUTH;

            case '-':
                return from == Direction.EAST || from == Direction.WEST;

            case 'L':
                return from == Direction.NORTH || from == Direction.EAST;

            case 'J':
                return from == Direction.NORTH || from == Direction.WEST;

            case '7':
                return from == Direction.SOUTH || from == Direction.WEST;

            case 'F':
                return from == Direction.SOUTH || from == Direction.EAST;

            case '.':
                return false;

            case 'S': // this will probably break things, yeabh
                return true;

            default:
                return false;
        }
    }

    public ArrayList<AocPipe> getAdjacentPipes(boolean outside) {
        ArrayList<AocPipe> adjacentPipes = new ArrayList<>();

        switch (getShape()) {
            case '|':
                if (outside) {
                    addNearbyPipeToArray(adjacentPipes, 0, -1);
                } else {
                    addNearbyPipeToArray(adjacentPipes, 0, 1);
                }
                break;

            case '-':
                if (outside) {
                    addNearbyPipeToArray(adjacentPipes, -1, 0);
                } else {
                    addNearbyPipeToArray(adjacentPipes, 1, 0);
                }
                break;

            case 'L':
                if (!outside) {
                    addNearbyPipeToArray(adjacentPipes, 0, -1);
                    addNearbyPipeToArray(adjacentPipes, 1, -1);
                    addNearbyPipeToArray(adjacentPipes, 1, 0);
                } else {
                    addNearbyPipeToArray(adjacentPipes, -1, 1);
                }
                break;

            case 'J':
                if (!outside) {
                    addNearbyPipeToArray(adjacentPipes, 0, 1);
                    addNearbyPipeToArray(adjacentPipes, 1, 1);
                    addNearbyPipeToArray(adjacentPipes, 1, 0);
                } else {
                    addNearbyPipeToArray(adjacentPipes, -1, -1);
                }
                break;

            case '7':
                if (outside) {
                    addNearbyPipeToArray(adjacentPipes, -1, 0);
                    addNearbyPipeToArray(adjacentPipes, -1, 1);
                    addNearbyPipeToArray(adjacentPipes, 0, 1);
                } else {
                    addNearbyPipeToArray(adjacentPipes, 1, -1);
                }
                break;

            case 'F':
                if (outside) {
                    addNearbyPipeToArray(adjacentPipes, -1, 0);
                    addNearbyPipeToArray(adjacentPipes, -1, -1);
                    addNearbyPipeToArray(adjacentPipes, 0, -1);
                } else {
                    addNearbyPipeToArray(adjacentPipes, 1, 1);
                }
                break;

            case '.':
                break;

            default:
                break;
        }

        return adjacentPipes;
    }

    public boolean isConnectedToTile(AocPipe otherPipe) {
        int[] otherCoordinates = otherPipe.getCoordinates();

        int thisX = coordinates[0];
        int thisY = coordinates[1];

        int otherX = otherCoordinates[0];
        int otherY = otherCoordinates[1];

        int adjustedX = otherX - thisX;
        int adjustedY = otherY - thisY;

        if (adjustedX > 1 || adjustedY > 1 || adjustedX < -1 || adjustedY < -1) {
            return false;
        }

        // diagonals will break things
        if (Math.abs(adjustedX) == Math.abs(adjustedY)) {
            return false;
        }

        Direction direction = Direction.UNREACHABLE;
        if (adjustedX == -1) {
            direction = Direction.SOUTH;
        } else if (adjustedX == 1) {
            direction = Direction.NORTH;
        }

        if (adjustedY == -1) {
            direction = Direction.EAST;
        } else if (adjustedY == 1) {
            direction = Direction.WEST;
        }

        // System.out.println(otherPipe.getShape() + ": " + direction);
        return otherPipe.validIntersectionFromDirection(direction);
    }

    // public Direction isConnectedToTileDirection(AocPipe otherPipe) {
    // int[] otherCoordinates = otherPipe.getCoordinates();

    // int thisX = coordinates[0];
    // int thisY = coordinates[1];

    // int otherX = otherCoordinates[0];
    // int otherY = otherCoordinates[1];

    // int adjustedX = otherX - thisX;
    // int adjustedY = otherY - thisY;

    // if (adjustedX > 1 || adjustedY > 1 || adjustedX < -1 || adjustedY < -1) {
    // return Direction.UNREACHABLE;
    // }

    // // diagonals will break things
    // if (Math.abs(adjustedX) == Math.abs(adjustedY)) {
    // return Direction.UNREACHABLE;
    // }

    // Direction direction = Direction.UNREACHABLE;
    // if (adjustedX == -1) {
    // direction = Direction.WEST;
    // } else if (adjustedX == 1) {
    // direction = Direction.EAST;
    // }

    // if (adjustedY == -1) {
    // direction = Direction.SOUTH;
    // } else if (adjustedY == 1) {
    // direction = Direction.NORTH;
    // }

    // if (!otherPipe.validIntersectionFromDirection(direction)) {
    // return Direction.UNREACHABLE;
    // }
    // return direction;
    // }

    public void addNearbyPipeToArray(ArrayList<AocPipe> array, int x, int y) {
        int thisX = coordinates[0];
        int thisY = coordinates[1];

        int adjustedX = thisX + x;
        int adjustedY = thisY + y;

        if (adjustedX < 0 || adjustedX >= containGrid.length || adjustedY < 0 || adjustedY >= containGrid[0].length) {
            return;
        } else {
            array.add(getPipeFromLocalCords(x + 1, y + 1));
        }
    }

    public AocPipe getPipeFromLocalCords(int x, int y) {
        int thisX = coordinates[0];
        int thisY = coordinates[1];

        int adjustedX = thisX + x - 1;
        int adjustedY = thisY + y - 1;

        return containGrid[adjustedX][adjustedY];
    }

    public ArrayList<AocPipe> getConnectedPipes() {
        makeConnections();
        ArrayList<AocPipe> connections = new ArrayList<>();

        for (int outer = 0; outer <= 2; outer++) {
            for (int inner = 0; inner <= 2; inner++) {
                // System.err.println(connectionGrid[outer][inner]);
                if (connectionGrid[outer][inner] == ConnectionType.CONNECTED) {
                    connections.add(getPipeFromLocalCords(outer, inner));
                }
            }
        }
        return connections;
    }

    public ArrayList<AocPipe> getNearbyPipes() {
        makeConnections();
        ArrayList<AocPipe> connections = new ArrayList<>();

        for (int outer = 0; outer <= 2; outer++) {
            for (int inner = 0; inner <= 2; inner++) {
                if (connectionGrid[outer][inner] != ConnectionType.INVALID) {
                    connections.add(getPipeFromLocalCords(outer, inner));
                }
            }
        }
        return connections;
    }

    public boolean isNearBorder() {
        for (int outer = 0; outer <= 2; outer++) {
            for (int inner = 0; inner <= 2; inner++) {
                if (connectionGrid[outer][inner] == ConnectionType.INVALID) {
                    return true;
                }
            }
        }
        return false;
    }

    public void makeConnections() {
        if (madeConnections) {
            return;
        }
        for (int outer = 0; outer <= 2; outer++) {
            for (int inner = 0; inner <= 2; inner++) {
                int adjustedX = coordinates[0] + outer - 1;
                int adjustedY = coordinates[1] + inner - 1;
                if (adjustedX < 0 || adjustedY < 0 || adjustedX >= containGrid.length
                        || adjustedY >= containGrid[0].length) {
                    connectionGrid[outer][inner] = ConnectionType.INVALID;
                } else if (inner == 1 && outer == 1) {
                    connectionGrid[outer][inner] = ConnectionType.IS_TILE;
                } else {
                    AocPipe otherPipe = containGrid[adjustedX][adjustedY];

                    if (isConnectedToTile(otherPipe) && otherPipe.isConnectedToTile(this)) {
                        connectionGrid[outer][inner] = ConnectionType.CONNECTED;
                    } else {
                        connectionGrid[outer][inner] = ConnectionType.NOT_CONNECTED;
                    }
                }
            }
        }
        madeConnections = true;
    }

    public void setShape(char c) {
        shape = c;
    }

}
