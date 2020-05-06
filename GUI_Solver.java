import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSeparator;

import java.lang.Math;

public class GUI_Solver extends JFrame implements ActionListener {
	JLabel infoLabel;
  static JTextField boxes[][] = new JTextField[9][9];

  static int[][] sodokuBoard = {
    {0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0}
  };

	public static void main(String[]args) {
		GUI_Solver obj1 = new GUI_Solver();
		obj1.setVisible(true);
	}

	public GUI_Solver() {
		super("Sudoku Solver");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
    setSize(new Dimension(500, 550));

		JPanel north = new JPanel();
    north.setSize(new Dimension(500, 500));
		north.setLayout(new GridLayout(9,9));
    final Dimension textPrefSize = new Dimension(500 / 9, 500 / 9);
		for(int i=0; i<sodokuBoard.length; i++) {
      for(int j=0; j<sodokuBoard.length; j++) {
        boxes[i][j] = new JTextField(""+sodokuBoard[i][j], SwingConstants.CENTER) {
          @Override
          public Dimension getPreferredSize() {
            return textPrefSize;
          }
        };
        boxes[i][j].setHorizontalAlignment(JTextField.CENTER);
        north.add(boxes[i][j]);
      }
    }

		add(north, BorderLayout.NORTH);

		JPanel south = new JPanel();
		south.setLayout(new GridLayout(1,3));

    //Info Label
    infoLabel = new JLabel("Sudoku Solver");

    //Clear Button
		JButton clearButton = new JButton("Blank");
		south.add(clearButton);
		clearButton.addActionListener(this);
    //Solve Button
    JButton solveButton = new JButton("Solve");
		south.add(solveButton);
		solveButton.addActionListener(this);
    //Exit Button
		JButton exitButton = new JButton("Random");
		south.add(exitButton);
		exitButton.addActionListener(this);

		add(south, BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String word = e.getActionCommand();
		if(word.equals("Random")) {
			createRandomBoard(sodokuBoard);
		}
		else if(word.equals("Blank")) {
			resetBoard(sodokuBoard);
		}
    else if(word.equals("Solve")) {
      getBoard(sodokuBoard);
      if(solveBoard(sodokuBoard)) {
        updateBoard(sodokuBoard);
      }
      else {
        resetBoard(sodokuBoard);
      }
    }
    else if(word.equals("Check")) {
      checkUserEntries(sodokuBoard);
    }
	}

  //Recieve row, col of an entry; check if its valid within soduku rules
  //O(1)
  public static boolean isValidEntry(int[][] board, int row, int col, int entry) {
    //Check row: O(1)
    for(int i=0; i<board.length; i++) {
      if(board[row][i] == entry) {
        //System.out.println("ROW CHECK for entry: row = " + row + ", col = " + i + ", entry = " + board[row][col]);
        return false;
      }
    }
    //Check column: O(1)
    for(int i=0; i<board.length; i++) {
      if(board[i][col] == entry) {
        //System.out.println("COLUMN CHECK for entry: row = " + i + ", col = " + col + ", entry = " + board[row][col]);
        return false;
      }
    }
    //Check nonent
    int startRow = row - (row % 3);
    int startCol = col - (col % 3);
    for(int i=startRow; i<startRow+3; i++) {
      for(int j=startCol; j<startCol+3; j++) {
        if(board[i][j] == entry) {
          return false;
        }
      }
    }
		boxes[row][col].setBackground(Color.GREEN);
    return true;
  }

  //Solve soduku board using backtracking
  public static boolean solveBoard(int[][] board) {
    int row = 0;
    int col = 0;
    boolean isComplete = true;
    //Check for empty entries
    for(int i=0; i<board.length; i++) {
      for(int j=0; j<board.length; j++) {
        if(board[i][j] == 0) {
          row = i;
          col = j;
          boxes[row][col].setBackground(Color.RED);
          isComplete = false;
          break;
        }
      }
      if(!isComplete) {
        break;
      }
    }

    //If all entries are filled
    if(isComplete) {
      return true;
    }

    //Backtracking
    for(int num=1; num<=board.length; num++) {
      if(isValidEntry(board, row, col, num)) {
        board[row][col] = num;
        //printBoard(board);
        if(solveBoard(board)) {
          boxes[row][col].setEditable(false);
          return true;
        }
        else {
          board[row][col] = 0;
        }
      }
      else {
        boxes[row][col].setBackground(Color.RED);
      }
    }

    return false;
  }

  //Reset board to all 0s
  public static void resetBoard(int[][] board) {
    for(int i=0; i<board.length; i++) {
      for(int j=0; j<board.length; j++) {
        board[i][j] = 0;
        updateBoard(board);
        boxes[i][j].setBackground(Color.WHITE);
        boxes[i][j].setEditable(true);
      }
    }
  }

  //Create random board
  public static void createRandomBoard(int[][] board) {
    resetBoard(board);
    int amountOfElements = (int)(Math.random() * 22) + 12;
    int randCol = 0;
    int randRow = 0;
    int randEntry = 1;
    for(int i=0; i<amountOfElements; i++) {
      randRow = (int)(Math.random() * 9);
      randCol = (int)(Math.random() * 9);
      randEntry = (int)(Math.random() * 9) + 1;
      if(isValidEntry(board, randRow, randCol, randEntry)) {
        board[randRow][randCol] = randEntry;
        boxes[randRow][randCol].setBackground(Color.GREEN);
        boxes[randRow][randCol].setEditable(false);
      }
    }
    updateBoard(board);
  }

  //Update GUI Board
  public static void updateBoard(int[][] board) {
    for(int i=0; i<board.length; i++) {
      for(int j=0; j<board.length; j++) {
        boxes[i][j].setText(""+board[i][j]);
      }
    }
  }

  //Update array
  public static void getBoard(int[][] board) {
    for(int i=0; i<board.length; i++) {
      for(int j=0; j<board.length; j++) {
        board[i][j] = Integer.parseInt(boxes[i][j].getText());
      }
    }
  }

  public static void checkUserEntries(int[][] board) {
		//Check user entries based on final answer
  }
}
