package SUT.AP.HW1;

import java.util.*;

public class Q3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int m = input.nextInt();
        int n = input.nextInt();
        int t = input.nextInt();

        Set<String> mazeSet = new HashSet<>();

        while (mazeSet.size() < t) {
            mazeSet.add((new Maze(n, m)).toString());
        }

        for (String maze : mazeSet) {
            System.out.println(maze);
        }
    }

    private static class Maze {
        private final int width;
        private final int height;
        private final boolean[][] maze;

        public Maze(int x, int y) {
            this.width = 2 * x + 1;
            this.height = 2 * y + 1;
            maze = new boolean[width][height];

            walk(1, 1);
        }

        private void walk(int x, int y) {
            maze[x][y] = true; // Set current position as visited

            int[][] nextMoves = {{0, +1}, {0, -1}, {+1, 0}, {-1, 0}};
            Collections.shuffle(Arrays.asList(nextMoves));

            for (int[] nextMove : nextMoves) {
                if (canWalk(x + 2 * nextMove[0], y + 2 * nextMove[1])) {
                    maze[x + nextMove[0]][y + nextMove[1]] = true; // Remove the wall between the cells
                    walk(x + 2 * nextMove[0], y + 2 * nextMove[1]);
                }
            }
        }

        private boolean canWalk(int x, int y) {
            if (0 > x || x >= width) return false;
            if (0 > y || y >= height) return false;
            return !maze[x][y]; // Check if already visited
        }

        public String toString() {
            char[][] chars = new char[height][width];

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    chars[i][j] = maze[j][i] ? '0' : '1';
                }
            }
            for (int i = 1; i < height - 1; i += 2) {
                for (int j = 1; j < width - 1; j += 2) {
                    chars[i][j] = '*';
                }
            }
            chars[0][1] = 'e';
            chars[height - 1][width - 2] = 'e';

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < height; i++) {
                stringBuilder.append(new String(chars[i])).append('\n');
            }
            return (stringBuilder.toString());
        }
    }
}

