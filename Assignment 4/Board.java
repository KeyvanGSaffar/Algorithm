import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public final class Board {
  public final int[][] inBlocks;
  private final int n;
  private int r0, c0;

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
    return new neighborsIterable();
  }

  public class neighborsIterable implements Iterable<Board> {

    @Override
    public Iterator<Board> iterator() {
      // TODO Auto-generated method stub
      return new neighborIterator();
    }

    public class neighborIterator implements Iterator<Board> {
      int[] urdl = { 0, 0, 0, 0 };
      boolean start = true;

      @Override
      public boolean hasNext() {
        // TODO Auto-generated method stub
        if (start == true) {
          if (r0 == 0)
            urdl[0] = 1;
          if (r0 == n - 1)
            urdl[2] = 1;
          if (c0 == 0)
            urdl[3] = 1;
          if (c0 == n - 1)
            urdl[1] = 1;
          start = false;
        }

        for (int i = 0; i < 4; i++)
          if (urdl[i] == 0)
            return true;
        return false;
      }

      @Override
      public Board next() {
        // TODO Auto-generated method stub
        int[][] nextBlocks;
        int i;
        for (i = 0; i < 4; i++)
          if (urdl[i] == 0)
            break;
        if (i == 4)
          throw new NoSuchElementException();
        else {
          nextBlocks = new int[n][n];
          for (int k = 0; k < n; k++) {
            for (int j = 0; j < n; j++) {
              nextBlocks[k][j] = inBlocks[k][j];
            }
          }
        }

        if (i == 0) {
          nextBlocks[r0][c0] = nextBlocks[r0 - 1][c0];
          nextBlocks[r0 - 1][c0] = 0;
          return new Board(nextBlocks);
        } else if (i == 1) {
          nextBlocks[r0][c0] = nextBlocks[r0][c0 + 1];
          nextBlocks[r0][c0 + 1] = 0;
          return new Board(nextBlocks);
        } else if (i == 2) {
          nextBlocks[r0][c0] = nextBlocks[r0 + 1][c0];
          nextBlocks[r0 + 1][c0] = 0;
          return new Board(nextBlocks);
        } else {
          nextBlocks[r0][c0] = nextBlocks[r0][c0 - 1];
          nextBlocks[r0][c0 - 1] = 0;
          return new Board(nextBlocks);
        }
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