public class Solver {

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

  public static void main(String[] args) {
    printBoard(sodokuBoard);
    if(solveBoard(sodokuBoard)) {
      printBoard(sodokuBoard);
    }
    else {
      System.out.println("No solution");
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
    return true;
  }

  //Solve soduku board using backtracking
  public static boolean solveBoard(int[][] board) {
    int row = -1;
    int col = -1;
    boolean isComplete = true;

    //Check for empty entries
    for(int i=0; i<board.length; i++) {
      for(int j=0; j<board.length; j++) {
        if(board[i][j] == 0) {
          row = i;
          col = j;
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
          return true;
        }
        else {
          board[row][col] = 0;
        }
      }
    }

    return false;
  }

  public static void printBoard(int[][] board) {
    System.out.println("-----------------");
    for(int i=0; i<board.length; i++) {
      for(int j=0; j<board.length; j++) {
        System.out.print(board[i][j] + " ");
      }
      System.out.println();
    }
  }
}
