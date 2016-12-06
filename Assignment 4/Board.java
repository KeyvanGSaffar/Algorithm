import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class Board {
  private final int[][] inBlocks;
  private int r0, c0;
  private Board[] neighborsList;
  // private int neighborsCount = 4;

  public Board(int[][] blocks) { // construct a board from an n-by-n array of
                                 // blocks
                                 // (where blocks[i][j] = block in row i, column
                                 // j)
    inBlocks = new int[blocks.length][blocks.length];

    for (int i = 0; i < inBlocks.length; i++) {
      for (int j = 0; j < inBlocks.length; j++) {
        inBlocks[i][j] = blocks[i][j];
        if (blocks[i][j] == 0) {
          r0 = i;
          c0 = j;
        }
      }
    }

  }

  public int dimension() { // board dimension n
    return inBlocks.length;
  }

  public int hamming() { // number of blocks out of place
    int ham = 0;
    for (int i = 0; i < inBlocks.length; i++) {
      for (int j = 0; j < inBlocks.length; j++) {
        if ((i * inBlocks.length + j != inBlocks.length * inBlocks.length - 1)
            && (inBlocks[i][j] != i * inBlocks.length + (j + 1)))
          ham++;
      }
    }
    return ham;
  }

  public int manhattan() { // sum of Manhattan distances between blocks and goal
    int manh = 0;
    for (int i = 0; i < inBlocks.length; i++) {
      for (int j = 0; j < inBlocks.length; j++) {
        if ((inBlocks[i][j] != 0) && (inBlocks[i][j] != i * inBlocks.length + (j + 1))) {
          // StdOut.println("i: " + i);
          // StdOut.println("i: " + j);
          int rGoal = (inBlocks[i][j] - 1) / inBlocks.length;
          int cGoal = (inBlocks[i][j] - 1) % inBlocks.length;

          // StdOut.println("rGoal: " + rGoal);
          // StdOut.println("cGoal: " + cGoal);

          manh += Math.abs(i - rGoal) + Math.abs(j - cGoal);
        }
      }
    }
    return manh;
  }

  public boolean isGoal() { // is this board the goal board?
    for (int i = 0; i < inBlocks.length; i++) {
      for (int j = 0; j < inBlocks.length; j++) {
        if ((i * inBlocks.length + j != inBlocks.length * inBlocks.length - 1)
            && (inBlocks[i][j] != i * inBlocks.length + (j + 1)))
          return false;
      }
    }
    if (inBlocks[inBlocks.length - 1][inBlocks.length - 1] != 0)
      return false;
    return true;
  }

  public Board twin() { // a board that is obtained by exchanging any pair of
                        // blocks
    int r1;
    int c1;
    int r2;
    int c2;
    while (true) {
      r1 = StdRandom.uniform(inBlocks.length);
      c1 = StdRandom.uniform(inBlocks.length);

      if (inBlocks[r1][c1] != 0)
        break;
    }
    while (true) {
      r2 = StdRandom.uniform(inBlocks.length);
      c2 = StdRandom.uniform(inBlocks.length);
      if (inBlocks[r2][c2] != 0)
        if (r2 != r1 || c2 != c1)
          break;
    }
    int[][] twin = new int[inBlocks.length][inBlocks.length];
    for (int i = 0; i < inBlocks.length; i++)
      for (int j = 0; j < inBlocks.length; j++)
        twin[i][j] = inBlocks[i][j];

    twin[r1][c1] = inBlocks[r2][c2];
    twin[r2][c2] = inBlocks[r1][c1];
    return new Board(twin);

  }

  public boolean equals(Object y) { // does this board equal y?
    if (y == null)
      return false;
    else {
      if (y.getClass() != this.getClass())
        return false;
      else {
        // y = (Board) y;
        if (this.dimension() != ((Board) y).dimension())
          return false;
        for (int i = 0; i < inBlocks.length; i++)
          for (int j = 0; j < inBlocks.length; j++)
            if (this.inBlocks[i][j] != ((Board) y).inBlocks[i][j])
              return false;
        return true;
      }
    }
  }

  private void neighborArray() {
    int neighborsCount = 4;
    // StdOut.println("r0 is: " + r0);
    // StdOut.println("C0 is: " + c0);
    if (r0 == 0 || r0 == inBlocks.length - 1)
      neighborsCount--;
    if (c0 == 0 || c0 == inBlocks.length - 1)
      neighborsCount--;

    // StdOut.println("neighborsCount: " + neighborsCount);

    neighborsList = new Board[neighborsCount];
    int cnt = 0;
    int[][] neighborBlocks;

    if (r0 > 0) {
      neighborBlocks = new int[inBlocks.length][inBlocks.length];
      for (int k = 0; k < inBlocks.length; k++)
        for (int j = 0; j < inBlocks.length; j++)
          neighborBlocks[k][j] = inBlocks[k][j];
      neighborBlocks[r0][c0] = neighborBlocks[r0 - 1][c0];
      neighborBlocks[r0 - 1][c0] = 0;
      neighborsList[cnt++] = new Board(neighborBlocks);
    }
    if (r0 < inBlocks.length - 1) {
      neighborBlocks = new int[inBlocks.length][inBlocks.length];
      for (int k = 0; k < inBlocks.length; k++)
        for (int j = 0; j < inBlocks.length; j++)
          neighborBlocks[k][j] = inBlocks[k][j];
      neighborBlocks[r0][c0] = neighborBlocks[r0 + 1][c0];
      neighborBlocks[r0 + 1][c0] = 0;
      neighborsList[cnt++] = new Board(neighborBlocks);
    }
    if (c0 > 0) {
      neighborBlocks = new int[inBlocks.length][inBlocks.length];
      for (int k = 0; k < inBlocks.length; k++)
        for (int j = 0; j < inBlocks.length; j++)
          neighborBlocks[k][j] = inBlocks[k][j];
      neighborBlocks[r0][c0] = neighborBlocks[r0][c0 - 1];
      neighborBlocks[r0][c0 - 1] = 0;
      neighborsList[cnt++] = new Board(neighborBlocks);
    }
    if (c0 < inBlocks.length - 1) {
      neighborBlocks = new int[inBlocks.length][inBlocks.length];
      for (int k = 0; k < inBlocks.length; k++)
        for (int j = 0; j < inBlocks.length; j++)
          neighborBlocks[k][j] = inBlocks[k][j];
      neighborBlocks[r0][c0] = neighborBlocks[r0][c0 + 1];
      neighborBlocks[r0][c0 + 1] = 0;
      neighborsList[cnt++] = new Board(neighborBlocks);
    }
  }

  public Iterable<Board> neighbors() { // all neighboring boards
    this.neighborArray();
    return new BoardList();
  }

  private class BoardList implements Iterable<Board> {

    @Override
    public Iterator<Board> iterator() {
      // TODO Auto-generated method stub
      return new BoardListIterator();
    }

    public class BoardListIterator implements Iterator<Board> {
      private int itrCount = 0;

      // Board[] nextBoard = neighborsList;
      @Override
      public boolean hasNext() {
        // TODO Auto-generated method stub
        return itrCount < neighborsList.length;
      }

      @Override
      public Board next() {
        // TODO Auto-generated method stub
        if (neighborsList.length - itrCount == 0) {
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
    StringBuilder output = new StringBuilder();
    output.append(this.dimension());
    output.append("\n");
    for (int i = 0; i < inBlocks.length; i++) {
      for (int j = 0; j < inBlocks.length; j++) {
        output.append(inBlocks[i][j]);
        output.append(" ");
      }
      output.append("\n");
    }
    output.append("\n");
    return output.toString();
  }

  public static void main(String[] args) { // unit tests (not graded)
    // int[][] blocks = { { 1, 4, 0 }, { 2, 3, 8 }, { 4, 6, 7 } };
    // Board test = new Board(blocks);
    // StdOut.print(test.toString());
    // for (Board t : test.neighbors())
    // StdOut.print(t.toString());
    // StdOut.print(test.twin());
    // In in = new In(args[0]);
    // int n = in.readInt();
    // int[][] blocks = new int[n][n];
    // for (int i = 0; i < n; i++)
    // for (int j = 0; j < n; j++)
    // blocks[i][j] = in.readInt();
    // Board initial = new Board(blocks);
    // for (Board t : initial.neighbors())
    // StdOut.print(t);
  }
}