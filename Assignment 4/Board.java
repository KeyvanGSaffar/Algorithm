import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public final class Board {
  public final int[][] inBlocks;
  private final int n;
  private int r0, c0;
  private Board[] neighborsList;
  private int neighborsCount = 4;
  public Board(int[][] blocks) { // construct a board from an n-by-n array of
                                 // blocks
                                 // (where blocks[i][j] = block in row i, column
                                 // j)
    n = blocks.length;
    inBlocks = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        inBlocks[i][j] = blocks[i][j];
        if (blocks[i][j] == 0)
          r0 = i;
        c0 = j;
      }
    }
    neighborArray();
  }
  
  private void neighborArray() {
    
    if (r0 == 0 || r0 == n - 1)
      neighborsCount--;
    if (c0 == 0 || c0 == n - 1)
      neighborsCount--;

    neighborsList = new Board[neighborsCount];
    int cnt = 0;
    
    if (r0 > 0) {
      int[][] neighborBlocks = new int[n][n];
      for (int k = 0; k < n; k++) 
        for (int j = 0; j < n; j++) 
          neighborBlocks[k][j] = inBlocks[k][j];
      neighborBlocks[r0][c0] = neighborBlocks[r0 - 1][c0];
      neighborBlocks[r0 - 1][c0] = 0;
      neighborsList[cnt++] = new Board(neighborBlocks);
    }
    if (r0 < n-1) {
      int[][] neighborBlocks = new int[n][n];
      for (int k = 0; k < n; k++) 
        for (int j = 0; j < n; j++) 
          neighborBlocks[k][j] = inBlocks[k][j];
      neighborBlocks[r0][c0] = neighborBlocks[r0 + 1][c0];
      neighborBlocks[r0 + 1][c0] = 0;
      neighborsList[cnt++] = new Board(neighborBlocks);
    }
    if (c0 > 0) {
      int[][] neighborBlocks = new int[n][n];
      for (int k = 0; k < n; k++) 
        for (int j = 0; j < n; j++) 
          neighborBlocks[k][j] = inBlocks[k][j];
      neighborBlocks[r0][c0] = neighborBlocks[r0][c0-1];
      neighborBlocks[r0][c0-1] = 0;
      neighborsList[cnt++] = new Board(neighborBlocks);
    }
    if (c0 < n-1) {
      int[][] neighborBlocks = new int[n][n];
      for (int k = 0; k < n; k++) 
        for (int j = 0; j < n; j++) 
          neighborBlocks[k][j] = inBlocks[k][j];
      neighborBlocks[r0][c0] = neighborBlocks[r0][c0+1];
      neighborBlocks[r0][c0+1] = 0;
      neighborsList[cnt++] = new Board(neighborBlocks);
    }
    
  }

  public int dimension() { // board dimension n
    return n;
  }

  public int hamming() { // number of blocks out of place
    int ham = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if ((i * n + j != 8) && (inBlocks[i][j] != i * n + (j + 1)))
          ham++;
      }
    }
    return ham;
  }

  public int manhattan() { // sum of Manhattan distances between blocks and goal
    int manh = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if ((i * n + j != 8) && (inBlocks[i][j] != i * n + (j + 1))) {
          int rGoal = (inBlocks[i][j] - 1) / n;
          int cGoal = (inBlocks[i][j] - 1) % n;
          manh += Math.abs(i - rGoal) + Math.abs(j - cGoal);
        }
      }
    }
    return manh;
  }

  public boolean isGoal() { // is this board the goal board?
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if ((i * n + j != 8) && (inBlocks[i][j] != i * n + (j + 1)))
          return false;
      }
    }
    return true;
  }

  public Board twin() { // a board that is obtained by exchanging any pair of
                        // blocks
    int r1;
    int c1;
    int r2;
    int c2;
    while (true) {
      r1 = StdRandom.uniform(n);
      c1 = StdRandom.uniform(n);

      if (inBlocks[r1][c1] != 0)
        break;
    }
    while (true) {
      r2 = StdRandom.uniform(n);
      c2 = StdRandom.uniform(n);
      if (inBlocks[r2][c2] != 0)
        if (r2 != r1 || c2 != c1)
          break;
    }
    int[][] twin = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        twin[i][j] = inBlocks[i][j];

    twin[r1][c1] = inBlocks[r2][c2];
    twin[r2][c2] = inBlocks[r1][c1];
    return new Board(twin);

  }

  public boolean equals(Object y) { // does this board equal y?
    if (y.getClass() != Board.class)
      return false;
    else {
      Board y_chk = (Board) y;
      if (this.dimension() != y_chk.dimension())
        return false;
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          if (this.inBlocks[i][j] != y_chk.inBlocks[i][j])
            return false;
      return true;
    }
  }

  public Iterable<Board> neighbors() { // all neighboring boards
    
    return new BoardList();
  }

  public class BoardList implements Iterable<Board> {

    @Override
    public Iterator<Board> iterator() {
      // TODO Auto-generated method stub
      return new BoardListIterator();
    }

    public class BoardListIterator implements Iterator<Board> {
      int itrCount = 0;
//      Board[] nextBoard = neighborsList;
      @Override
      public boolean hasNext() {
        // TODO Auto-generated method stub
        return itrCount < neighborsCount;
      }

      @Override
      public Board next() {
        // TODO Auto-generated method stub
        if (neighborsCount - itrCount == 1) {
          throw new NoSuchElementException();
        }

        return neighborsList[itrCount++];

      }

      @Override
      public void remove() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
      }

    }

  }

  public String toString() { // string representation of this board (in the
                             // output format specified below)
    String output = "";
    output += this.dimension() + "\n";
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        output += inBlocks[i][j] + " ";
      }
      output += "\n";
    }
    output += "\n";
    return output;
  }

  public static void main(String[] args) { // unit tests (not graded)
    String test = "";
    test += "g" + "\n";
    test += "hello";
    StdOut.print(test);

  }
}