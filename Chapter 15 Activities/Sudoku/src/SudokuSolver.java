import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * TL; DR:
 * 
 * Write a program that solves Sudoku puzzles using sets and operations on sets. The general algorithm should be a traditional 
 * backtracking algorithm. In a backtracking algorithm, a potential solution is explored recursively until it fails. When that
 * happens, the most recent move is undone and an alternative move is explored.
 * 
 */

public class SudokuSolver {
    private final int M = 3;
    private final int N = M * M;
    private int[][] grid;
    private ArrayList<Set<Integer>> rows;
    private ArrayList<Set<Integer>> cols;
    private ArrayList<Set<Integer>> squares;
    private Set<Integer> nums;

    public SudokuSolver(String fileName) {
        // read the puzzle file
        try (Scanner in = new Scanner(new File(fileName))) {

            this.grid = new int[N][N];

            for (int row = 0; row < N; row++) {
                String line = in.next();

                for (int col = 0; col < N; col++) {
                    String strVal = line.substring(col, col + 1);
                    int number;
                    if (strVal.equals("x")) {
                        number = 0;
                    } else {
                        number = Integer.parseInt(strVal);
                    }
                    this.grid[row][col] = number;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open: " + fileName);
        }

        
        // create the list of sets for each row (this.rows)
        ArrayList<Set<Integer>> rows = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            Set<Integer> set = new HashSet<>();
            
            System.out.println(set);
            //Set<Integer> remove = new HashSet<>();
            for (int j = 0; j < N; j++) {
                if (grid[i][j]!=0)
                set.add(grid[i][j]);
                
            }
            //set.removeAll(remove);
            rows.add(set);
        }
        this.rows = rows;
        System.out.println(this.rows.get(1)+"a");


        // create the list of sets for each col (this.cols)
        ArrayList<Set<Integer>> cols = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            Set<Integer> set = new HashSet<>();
            //for (int j = 0; j < N; j++){
                //set.add(j+1);
            
            Set<Integer> remove = new HashSet<>();
            for (int j = 0; j < N; j++) {
                if (grid[j][i]!=0)
                set.add(grid[j][i]);
                
                
            }

            //set.removeAll(remove);
            cols.add(set);
        }
        this.cols = cols;
        System.out.println(this.rows.get(0));

        // create the list of sets for each square (this.squares)
        /* the squares are added to the list row-by-row:
            0 1 2
            3 4 5
            6 7 8
         */
        int minX = 0;
        int minY = 0;
        ArrayList<Set<Integer>> sqrs = new ArrayList<>();
        for (int i = 0; i<N; i++)
        {
            Set<Integer> set = new HashSet<>();
            for (int j=0;j<N;j++)
            {
                minY += 3;
                if (this.grid[i][j]!=0){
                    if ((minX>=i&&i>=minX+3) && (minY >= j&& j>= minY+3)){
                        set.add(grid[i][j]);
                    }
                }
            }
            //set.removeAll(remove);
            sqrs.add(set);
        }
    System.out.println(sqrs.get(0)+"a");
        this.squares = sqrs;


        // create a hash set for [1...9] (this.nums)
        Set<Integer> nums = new HashSet();
        for (int i = 1; i <= 9; i++){
            nums.add(i);
        }
        
        this.nums = nums;
        
        // visually inspect that all the sets are correct
        for (int row = 0; row < N; row++) {
            System.out.println("row " + row + ": " + this.rows.get(row));
        }
        for (int col = 0; col < N; col++) {
            System.out.println("col " + col + ": " + this.cols.get(col));
        }
        for(int i = 0; i<N;i++){
        System.out.println(this.squares.get(i));}
        for (int square = 0; square < N; square++) {
            System.out.println("square " + square + ": " + this.squares.get(square));
        }
        System.out.println(this.nums);
    }

    public boolean solve() {
        // find an empty location, if any
        boolean finished = true;
        int nextRow = -1;
        int nextCol = -1;
        for (int row = 0; row < N && finished; row++) {
            for (int col = 0; col < N && finished; col++) {
                if (this.grid[row][col] == 0) {
                    finished = false;
                    nextRow = row;
                    nextCol = col;
                }
            }
        }

        // the board is complete; we solved it
        if (finished) {
            return true;
        }

        // get all possible numbers for the row and column we are trying to populate
        /*
            Create a new set based on the this.nums and remove all elements in the sets
            corresponding to nextRow, nextCol, and the corresponding square (use the
            removeAll method).

            Properly indexing the squares list of sets is tricky. Verify that your
            algorithm is correct.
         */

        Set<Integer> possibleNums = new HashSet<Integer>();
        possibleNums.addAll(this.nums);
        int nextGrid = (int) Math.floor(nextRow/3 + nextCol/3);
        //possibleNums.removeAll(this.rows.get(nextRow));
        //possibleNums.removeAll(this.cols.get(nextCol));
        possibleNums.removeAll(this.squares.get(nextGrid));
       
        // if there are no possible numberas, we cannot solve the board in its current state
        if (possibleNums.isEmpty()) {
            return false;
        }
        System.out.println(possibleNums);
        // try each possible number
        for (Integer possibleNum : possibleNums) {
            // update the grid and all three corresponding sets with possibleNum
            this.grid[nextRow][nextCol] = possibleNum;
            this.rows.get(nextRow).remove(possibleNum);
            this.cols.get(nextCol).remove(possibleNum);
            this.squares.get(nextGrid).remove(possibleNum);
            

            // recursively solve the board
            if (this.solve()) {
                // the board is solved!
                return true;
            } else {
                System.out.println("elrip_");
            }
        }

        return false;
    }

    public String toString() {
        String str = "";

        for (int[] row : grid) {
            for (int val : row) {
                str += val + "\t";
            }

            str += "\n";
        }

        return str;
    }

    public static void main(String[] args) {
        
        //the file path is different on my laptop 
        //String fileName = "Chapter 15 Activities\\Sudoku\\src\\puzzle1.txt";
        String fileName = "Sudoku\\src\\puzzle1.txt";

        SudokuSolver solver = new SudokuSolver(fileName);
        System.out.println(solver);
        if (solver.solve()) {
            System.out.println("Solved!");
            System.out.println(solver);
        } else {
            System.out.println("Unsolveable...");
        }
    }
}