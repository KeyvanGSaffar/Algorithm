import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

  private MinPQ<SearchNode> snPQ;
  private SearchNode initSN;

  private SearchNode solutSN = null; 

  public Solver(Board initial) { // find a solution to the initial board (using
                                 // the A* algorithm)
    initSN = new SearchNode(initial, 0, null);
    snPQ = new MinPQ<SearchNode>(SearchNode.HamPriority);
    snPQ.insert(initSN);
    while (!snPQ.min().board.isGoal()) {
      SearchNode minSN = snPQ.delMin();
      for (Board nb : minSN.board.neighbors()) {
        snPQ.insert(new SearchNode(nb, minSN.moves + 1, minSN));
      }
    }
    solutSN = snPQ.delMin();
    
  }

  public boolean isSolvable() { // is the initial board solvable?
    Solver chkSolvable =  new Solver(this.initSN.board.twin());
    if(chkSolvable.solutSN != null)
      return true;
    else
      return false;

  }

  public int moves() { // min number of moves to solve initial board; -1 if
    if(this.isSolvable())                   // unsolvable
      return solutSN.moves;
    else
      return -1;
  }

  public Iterable<Board> solution() { // sequence of boards in a shortest
    if(this.isSolvable())                                  // solution; null if unsolvable
      return new solutIter();
    else
      return null;
  }
  
  private class solutIter implements Iterable<Board>{
    @Override
    public Iterator<Board> iterator() {
      // TODO Auto-generated method stub
      return new solIterator();
    }
    
    private class solIterator implements Iterator<Board>{
      private SearchNode innerIter = solutSN;

      @Override
      public boolean hasNext() {
        // TODO Auto-generated method stub
       return innerIter.searchN != null;
      }

      @Override
      public Board next() {
        // TODO Auto-generated method stub
        if (innerIter.searchN == null)
          throw new NoSuchElementException();
        
        Board nxtBoard = innerIter.board;
        innerIter = innerIter.searchN;
        return nxtBoard;
      }
    }
  }

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