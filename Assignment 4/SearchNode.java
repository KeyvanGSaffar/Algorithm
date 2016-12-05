import java.util.Comparator;

public class SearchNode {
  public final Board board; 
  public final int moves;
  public final SearchNode searchN;
  public static final Comparator<SearchNode> HamPriority = new ByHam();
  public static final Comparator<SearchNode> ManhPriority = new ByManh();
  public SearchNode(Board brd, int mv, SearchNode sN){
    board = brd;
    moves = mv;
    searchN = sN;
  }
  
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


}
