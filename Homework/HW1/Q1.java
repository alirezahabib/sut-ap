package SUT.AP.HW1;

import java.util.Scanner;

public class Q1 {
    static boolean[][] alreadyBeenMap = new boolean[100][100];
    static int[] ThomasPosition = {49, 49};

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();

        int[] nextMove = new int[2];
        for (int i = 0; i < n; i++) {
            nextMove[0] = input.nextInt();
            nextMove[1] = input.nextInt();

            if (canMove(nextMove)) moveThomas(nextMove);
        }
        System.out.println((ThomasPosition[0] + 1) + " " + (ThomasPosition[1] + 1));
    }

    private static boolean canMove(int[] nextMove) {
        int i = ThomasPosition[0];
        int j = ThomasPosition[1];

        if (i + nextMove[0] >= 100 || i + nextMove[0] < 0) return false;
        if (j + nextMove[1] >= 100 || j + nextMove[1] < 0) return false;

        if (nextMove[0] >= 0) {
            for (; i < ThomasPosition[0] + nextMove[0] + 1; i++) {
                if (alreadyBeenMap[i][j]) return false;
            }
            i--;
        } else {
            for (; i > ThomasPosition[0] + nextMove[0] - 1; i--) {
                if (alreadyBeenMap[i][j]) return false;
            }
            i++;
        }

        if (nextMove[1] >= 0) {
            for (; j < ThomasPosition[1] + nextMove[1] + 1; j++) {
                if (alreadyBeenMap[i][j]) return false;
            }
        } else {
            for (; j > ThomasPosition[1] + nextMove[1] - 1; j--) {
                if (alreadyBeenMap[i][j]) return false;
            }
        }
        return true;
    }

    private static void moveThomas(int[] nextMove) {
        int i = ThomasPosition[0];
        int j = ThomasPosition[1];

        if (nextMove[0] >= 0) {
            for (; i < ThomasPosition[0] + nextMove[0]; i++) alreadyBeenMap[i][j] = true;
        } else {
            for (; i > ThomasPosition[0] + nextMove[0]; i--) alreadyBeenMap[i][j] = true;
        }

        if (nextMove[1] >= 0) {
            for (; j < ThomasPosition[1] + nextMove[1]; j++) alreadyBeenMap[i][j] = true;
        } else {
            for (; j > ThomasPosition[1] + nextMove[1]; j--) alreadyBeenMap[i][j] = true;
        }

        ThomasPosition[0] += nextMove[0];
        ThomasPosition[1] += nextMove[1];
    }
}

