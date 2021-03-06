package sudokuSolver.util;

import java.util.ArrayList;

public class Sudoku {

    private Sudoku() {}

    /**
     * Array of given numbers. #1 is the Y coordinate, #2 is the X coordinate and #3 is the actual number
     *
     * @param args array of given numbers
     * @return solved matrix
     */
    public static int[][] solveSudoku(ArrayList<String> args) {
        int[][] matrix = parseProblem(args);
        writeMatrix(matrix);
        if (solve(0, 0, matrix)) {   // solves in place

            writeMatrix(matrix);
            return matrix;

        } else {

            System.out.println("NONE");
            return matrix;
        }
    }

    private static boolean solve(int i, int j, int[][] cells) {
        if (i == 9) {
            i = 0;
            if (++j == 9)
                return true;
        }
        if (cells[i][j] != 0)  // skip filled cells
            return solve(i + 1, j, cells);

        for (int val = 1; val <= 9; ++val) {
            if (legal(i, j, val, cells)) {
                cells[i][j] = val;
                if (solve(i + 1, j, cells))
                    return true;
            }
        }
        cells[i][j] = 0; // reset on backtrack
        return false;
    }

    private static boolean legal(int i, int j, int val, int[][] cells) {
        for (int k = 0; k < 9; ++k)  // row
            if (val == cells[k][j])
                return false;

        for (int k = 0; k < 9; ++k) // col
            if (val == cells[i][k])
                return false;

        int boxRowOffset = (i / 3) * 3;
        int boxColOffset = (j / 3) * 3;
        for (int k = 0; k < 3; ++k) // box
            for (int m = 0; m < 3; ++m)
                if (val == cells[boxRowOffset + k][boxColOffset + m])
                    return false;

        return true; // no violations, so it's legal
    }

    private static int[][] parseProblem(ArrayList<String> args) {
        int[][] problem = new int[9][9]; // default 0 values
        for (String arg : args) {
            int i = Integer.parseInt(arg.substring(0, 1));
            int j = Integer.parseInt(arg.substring(1, 2));
            int val = Integer.parseInt(arg.substring(2, 3));
            problem[i][j] = val;
        }
        return problem;
    }

    private static void writeMatrix(int[][] solution) {
        for (int i = 0; i < 9; ++i) {
            if (i % 3 == 0)
                System.out.println(" -----------------------");
            for (int j = 0; j < 9; ++j) {
                if (j % 3 == 0) System.out.print("| ");
                System.out.print(solution[i][j] == 0
                        ? " "
                        : Integer.toString(solution[i][j]));

                System.out.print(' ');
            }
            System.out.println("|");
        }
        System.out.println(" -----------------------");
    }
}