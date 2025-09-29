import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
* Program Name: SudokuGUI.java
* Purpose: OK DUMMY, PUT SOMETHING DESCRIPTIVE HERE!
* @author Your Name and Student Number goes here
* Date: Sep 26, 2025
*/

@SuppressWarnings("serial")
public class SudokuGUI extends JFrame{
	
	Sudoku sudoku;
	JTextField[][] inputFields;
	private int selectedRow = -1;
	private int selectedCol = -1;
	
	@SuppressWarnings("unused")
	public SudokuGUI() {
		sudoku = new Sudoku();
		inputFields = new JTextField[9][9];
		
		this.setTitle("Sudoku");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 590);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
						
		JPanel inputButtonPanel = new JPanel(new GridLayout(3,3));
		for(int i = 1; i <= 9; i++ ) {
			JButton numberButton = new JButton(""+i);
			numberButton.setFont(new Font("Serif",Font.BOLD,24));
			numberButton.setHorizontalAlignment(JTextField.CENTER);
			numberButton.setPreferredSize(new Dimension(60, 60));
			
			final int value = i;
			numberButton.addActionListener(e -> {
				if(selectedRow >= 0 && selectedCol >= 0 && inputFields[selectedRow][selectedCol].isEditable()) {
		            if(isValidMove(selectedRow, selectedCol, value)) {
		                inputFields[selectedRow][selectedCol].setText(String.valueOf(value));
		                inputFields[selectedRow][selectedCol].setForeground(java.awt.Color.BLUE);
		                checkCompletion();
		            } else {
		                inputFields[selectedRow][selectedCol].setText(String.valueOf(value));
		                inputFields[selectedRow][selectedCol].setForeground(java.awt.Color.RED);
		            }
		        }
			});
			
			inputButtonPanel.add(numberButton);
		}
		
		buttonPanel.add(inputButtonPanel,BorderLayout.CENTER);
		JButton newButton = new JButton();
		newButton.setText("New Game");
		newButton.addActionListener(e -> {
		    sudoku = new Sudoku();
		    selectedRow = -1;
		    selectedCol = -1;
		    fillValues();
		    highlightSelectedCell();
		});
		buttonPanel.add(newButton,BorderLayout.SOUTH);
		
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(9,9));


		
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9;j++) {
				inputFields[i][j] = new JTextField();
				inputFields[i][j].setFont(new Font("Serif",Font.BOLD,24));
				inputFields[i][j].setHorizontalAlignment(JTextField.CENTER);
				inputFields[i][j].setPreferredSize(new Dimension(60, 60));
				
				final int row = i;
				final int col = j;
				inputFields[i][j].addMouseListener(new MouseAdapter() {
				    @Override
				    public void mouseClicked(MouseEvent evt) {
				        if (inputFields[row][col].isEditable()) {
				            selectedRow = row;
				            selectedCol = col;
				            highlightSelectedCell();
				        }
				    }
				});
				
				 inputFields[i][j].addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent evt) {
						 if (inputFields[row][col].isEditable()) 
						 {
		                    String text = inputFields[row][col].getText().trim();
			                    
		                    // Only process if there's text
		                    if (!text.isEmpty()) {
		                    	try {
		                    		int value = Integer.parseInt(text);
		                            // Validate the value (must be 1-9)
		                    			if (value >= 1 && value <= 9) {
		                    				 inputFields[row][col].setText("");
		                    				 
			                                if (isValidMove(row, col, value)) {
			                                	 inputFields[row][col].setText(String.valueOf(value));
			                                    inputFields[row][col].setForeground(Color.BLUE);
			                                    checkCompletion();
			                                } else {
			                                	 inputFields[row][col].setText(String.valueOf(value));
			                                    inputFields[row][col].setForeground(Color.RED);
			                                }
			                            } else {
			                                // Invalid range
			                                inputFields[row][col].setText("");
			                            }
			                        } catch (NumberFormatException e) {
			                            // Not a number
			                            inputFields[row][col].setText("");
			                        }
			                    }
		                }
					}
					 
				});
				gamePanel.add(inputFields[i][j]);
			}
		}
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());
		mainPanel.add(gamePanel);
		mainPanel.add(buttonPanel);
		
		this.setLayout(new BorderLayout());
		this.add(mainPanel,BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		fillValues();
	}
	
	private boolean isValidMove(int row, int col, int num) {
		 int[][] currentBoard = getCurrentBoardState();
		    
		    // Check row
		 for (int c = 0; c < 9; c++) {
	            if (currentBoard[row][c] == num) return false;
	        }
		    
		    // Check column
		 for (int r = 0; r < 9; r++) {
	            if (currentBoard[r][col] == num) return false;
     }
		    
		    // Check 3x3 box
		    int boxRow = (row / 3) * 3;
	        int boxCol = (col / 3) * 3;
	        for (int r = 0; r < 3; r++) {
	            for (int c = 0; c < 3; c++) {
	                if (currentBoard[boxRow + r][boxCol + c] == num) return false;
	            }
	        }
		
		return true;
	}
	
	private int[][] getCurrentBoardState() {
	    int[][] currentBoard = new int[9][9];
	    for(int i = 0; i < 9; i++) {
	        for(int j = 0; j < 9; j++) {
	            String cellValue = inputFields[i][j].getText().trim();
	            if(!cellValue.isEmpty()) {
	                currentBoard[i][j] = Integer.parseInt(cellValue);
	            } else {
	                currentBoard[i][j] = 0;
	            }
	        }
	    }
	    return currentBoard;
	}
	
	private void checkCompletion() {
		 int[][] currentBoard = getCurrentBoardState();
		 
		for(int i = 0; i < 9; i++) {
	        for(int j = 0; j < 9; j++) {
	            if(currentBoard[i][j] == 0) {
	                return; // Board not complete
	            }
	        }
	    }
	
	   // if(valid) {
	        javax.swing.JOptionPane.showMessageDialog(this, "Congratulations! You solved the puzzle!");
	    //}
		
	}
	
	private void highlightSelectedCell() {
		for(int i = 0; i < 9; i++) {
	        for(int j = 0; j < 9; j++) {
	        	if(inputFields[i][j].isEditable())
	        		inputFields[i][j].setBackground(Color.WHITE);
	        }
	    }
		
		if(selectedRow >= 0 && selectedCol >= 0) {
			inputFields[selectedRow][selectedCol].setBackground(Color.CYAN);
		}
	}
	
	private void fillValues() {
		int [][] board = sudoku.getBoard();
		
		for(int i = 0; i < 9;i++) {
			for(int j = 0; j < 9; j++) {
				
				if(board[i][j] == 0) {
					inputFields[i][j].setText("");
					inputFields[i][j].setEditable(true);
				}
				else {
					inputFields[i][j].setText(board[i][j] + "");
					inputFields[i][j].setEditable(false);
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
        new SudokuGUI();
	}
}
//End of class