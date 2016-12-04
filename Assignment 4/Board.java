import edu.princeton.cs.algs4.StdRandom;

public final class Board {
  public final int[][] in_blocks;
  private final int n;

  public Board(int[][] blocks) { // construct a board from an n-by-n array of
                                 // blocks
                                 // (where blocks[i][j] = block in row i, column
                                 // j)
    n = blocks.length;
    in_blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        in_blocks[i][j] = blocks[i][j];

  }

  public int dimension() { // board dimension n
    return n;
  }

  public int hamming() { // number of blocks out of place
    int ham = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if ((i * n + j != 8) && (in_blocks[i][j] != i * n + (j + 1)))
          ham++;
      }
    }
    return ham;
  }

  public int manhattan() { // sum of Manhattan distances between blocks and goal
    int manh = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if ((i * n + j != 8) && (in_blocks[i][j] != i * n + (j + 1))){
          int rGoal = (in_blocks[i][j]-1) / n;
          int cGoal = (in_blocks[i][j]-1) % n;
          manh += Math.abs(i-rGoal)+Math.abs(j-cGoal);
        }
      }
    }
    return manh;
  }

  public boolean isGoal() { // is this board the goal board?
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if ((i * n + j != 8) && (in_blocks[i][j] != i * n + (j + 1)))
          return false;
      }
    }
    return true;
  }

  public Board twin() { // a board that is obtained by exchanging any pair of
                        // blocks
    int r1 = StdRandom.uniform(n);
    int c1 = StdRandom.uniform(n);
    int r2;
    int c2;
    while(true){
      r2 = StdRandom.uniform(n);
      c2 = StdRandom.uniform(n);
      if (r2 != r1 || c2 != c1)
        break;
    }
    int[][] twin = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        twin[i][j] = in_blocks[i][j];
    
   twin[r1][c1] =  in_blocks[r2][c2];
   twin[r2][c2] =  in_blocks[r1][c1];
   return new Board(twin);
    
  }

  public boolean equals(Object y) { // does this board equal y?
    Board y_chk = (Board) y;
    for (int i = 0; i < n; i++) 
      for (int j = 0; j < n; j++)
        if (this.in_blocks[i][j]!= y_chk.in_blocks[i][j])
          return false;
    return true;
  }

  public Iterable<Board> neighbors() { // all neighboring boards

  }

  public String toString() { // string representation of this board (in the
                             // output format specified below)

  }

  public static void main(String[] args) { // unit tests (not graded)

  }
}