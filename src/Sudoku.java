import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
/**
* Program Name: Sudoku.java
* Purpose: OK DUMMY, PUT SOMETHING DESCRIPTIVE HERE!
* @author Your Name and Student Number goes here
* Date: Sep 27, 2025
*/

public class Sudoku {
	
	private int[][] board;
	private boolean[][] fixed;
	int counter;
	Random random = new Random();
	
	public Sudoku() {
		this.board = new int[9][9];
		this.fixed = new boolean[9][9];
		counter = 0;
		generateSudoku();
		removeNum();
	}
	
	private boolean generateSudoku() {
		counter++;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                   
                    ArrayList<Integer> values = new ArrayList<Integer>();
                    
                    for(int i = 1; i <= 9;i++)
                    	values.add(i);
                    Collections.shuffle(values);
                    
                    for (int value : values) {
                        if (isValid( row, col, value)) {
                            board[row][col] = value;
                            
                            if (generateSudoku()) {
                                return true;
                            }
                            
                            // If we get here, this value didn't work
                            board[row][col] = 0;
                        }
                    }
                    return false; // No valid values found
                }
            }
        }
        return true; // All cells filled
    }
	
	private void removeNum() {
		
		int removeNum = 2;
		
		for(int i = removeNum; i > 0;i-- ) {
			int row = random.nextInt(9);
			int col = random.nextInt(9);
			
			board[row][col] = 0;
		}
	}
	private boolean isValid(int row, int col, int num) {
		
		for (int c = 0; c < 9; c++) {
            if (board[row][c] == num) return false;
        }
		
		for (int r = 0; r < 9; r++) {
	            if (board[r][col] == num) return false;
        }
		
		int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[boxRow + r][boxCol + c] == num) return false;
            }
        }
		
		return true;
	}
	
	public int[][] getBoard(){
		return board;
	}
}
//End of class

/*

RULES:

9x9 grid
1-9 numbers only
nine 3x3 blocks : 1-9 numbers
vertical row & horizontal column: 1-9 num
any num either in row column or block can be used only once


generate random numbers from 1-9 for every cell
check if the num is valid for row column and box


for the first row: generate random numbers acc to time seed
for the 

*/