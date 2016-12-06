import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
  // private static final Comparator<SearchNode> HAMPRIORITY = new ByHam();
  // private static final Comparator<SearchNode> MANHPRIORITY = new ByManh();

  // private MinPQ<SearchNode> snPQ;
  // private MinPQ<SearchNode> snPQPrim;
  // private SearchNode initSN;
  // private SearchNode initSNPrim;
  private SearchNode solutSN = null;
  private Board[] gameT;

  public Solver(Board initial) { // find a solution to the initial board (using
                                 // the A* algorithm)
    // final Comparator<SearchNode> HAMPRIORITY = new ByHam();
    final Comparator<SearchNode> MANHPRIORITY = new ByManh();

    SearchNode initSN = new SearchNode(initial, 0, null);
    SearchNode initSNPrim = new SearchNode(initial.twin(), 0, null);

    MinPQ<SearchNode> snPQ = new MinPQ<SearchNode>(MANHPRIORITY);
    MinPQ<SearchNode> snPQPrim = new MinPQ<SearchNode>(MANHPRIORITY);
    snPQ.insert(initSN);
    snPQPrim.insert(initSNPrim);
    // int counter =0;
    SearchNode minSN;
    SearchNode minSNPrim;
    while (!snPQ.min().board.isGoal() && !snPQPrim.min().board.isGoal()) {

      minSN = snPQ.delMin();
      minSNPrim = snPQPrim.delMin();
      // StdOut.println("counter: "+counter++);
      // StdOut.println("minSN.searchN: "+ minSN.searchN);
      // if (minSN.searchN!=null)
      // StdOut.println(minSN.searchN.board);
      // StdOut.println("counter: " + counter++);
      for (Board nb : minSN.board.neighbors()) {

        if ((minSN.searchN != null && !nb.equals(minSN.searchN.board)) || minSN.searchN == null)
          snPQ.insert(new SearchNode(nb, minSN.moves + 1, minSN));
        else
          continue;

      }
      for (Board nb : minSNPrim.board.neighbors()) {

        if ((minSNPrim.searchN != null && !nb.equals(minSNPrim.searchN.board)) || minSNPrim.searchN == null)
          snPQPrim.insert(new SearchNode(nb, minSNPrim.moves + 1, minSNPrim));
        else
          continue;

      }
    }
    if (snPQ.min().board.isGoal())
      solutSN = snPQ.delMin();

  }

  public boolean isSolvable() { // is the initial board solvable?
    return solutSN != null;
  }

  public int moves() { // min number of moves to solve initial board; -1 if
    if (this.isSolvable()) // unsolvable
      return solutSN.moves;
    else
      return -1;
  }

  private void solutionArray() {
    SearchNode innerIter = solutSN;
    gameT = new Board[solutSN.moves + 1];
    int index = solutSN.moves;
    while (innerIter != null) {
      gameT[index--] = innerIter.board;
      innerIter = innerIter.searchN;
    }
  }

  public Iterable<Board> solution() { // sequence of boards in a shortest
    if (this.isSolvable()) { // solution; null if unsolvable
      this.solutionArray();
      return new SolutionList();
    } else
      return null;
  }

  private class SolutionList implements Iterable<Board> {
    @Override
    public Iterator<Board> iterator() {
      // TODO Auto-generated method stub
      return new SolutionIterator();
    }

    private class SolutionIterator implements Iterator<Board> {
      private int itr = 0;

      @Override
      public boolean hasNext() {
        // TODO Auto-generated method stub
        return itr < solutSN.moves + 1;
      }

      @Override
      public Board next() {
        // TODO Auto-generated method stub
        if (solutSN.moves - itr == -1)
          throw new NoSuchElementException();

        return gameT[itr++];
      }
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////
  private static class ByHam implements Comparator<SearchNode> {

    @Override
    public int compare(SearchNode o1, SearchNode o2) {
      // TODO Auto-generated method stub
      int w1 = o1.board.hamming() + o1.moves;
      int w2 = o2.board.hamming() + o2.moves;
      return w1 - w2;
    }

  }

  private static class ByManh implements Comparator<SearchNode> {

    @Override
    public int compare(SearchNode o1, SearchNode o2) {
      // TODO Auto-generated method stub
      int w1 = o1.board.manhattan() + o1.moves;
      int w2 = o2.board.manhattan() + o2.moves;
      return w1 - w2;
    }

  }

  ///////////////////////////////////////////////////////////////////////////////////////
  private class SearchNode {
    private final Board board;
    private final int moves;
    private final SearchNode searchN;

    public SearchNode(Board brd, int mv, SearchNode sN) {
      board = brd;
      moves = mv;
      searchN = sN;
    }

  }
  ////////////////////////////////////////////////////////////////////////////////////////

  public static void main(String[] args) { // solve a slider puzzle (given
                                           // below)
    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }

}
