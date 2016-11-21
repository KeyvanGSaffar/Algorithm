import java.util.Arrays;

public class FastCollinearPoints {

  private LineSegment[] arrayLineSegment;
  private int n;
  private int size;

  public FastCollinearPoints(Point[] points) { // finds all line segments
                                               // containing 4 or more points
    if (points == null)
      throw new NullPointerException();
    for (Point p : points) {
      if (p == null)
        throw new IllegalArgumentException();

    }

    for (int i = 0; i < points.length; i++) {
      for (int j = i + 1; j < points.length; j++) {
        if (points[i] == points[j]) {
          throw new IllegalArgumentException();
        }
      }
    }

    n = points.length;
    arrayLineSegment = new LineSegment[n];
    double[] slopeArray = new double[n];
    double[] slopeArrayCopy = new double[n];
    int[][] firstLast = new int[n][2];

    //////////////////////////////////////////////////////////////////////////////////////////
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        slopeArray[j] = points[j].slopeTo(points[i]);
        slopeArrayCopy[j] = slopeArray[j];
      }
      Arrays.sort(slopeArray);
      int cnt = 1;
      double temp = slopeArray[0];
      for (int k = 1; k < n; k++) {
        if (slopeArray[k] ==  Double.NEGATIVE_INFINITY)
          continue;
        if (slopeArray[k] == temp) {
         cnt++;
        }
        else {
          
          int[] tempArray = new int[cnt];
          for(int l = 0; l< n; l++){
            if(slopeArrayCopy[l] = temp)
              
          }
          
          
          
          temp = slopeArray[k];
          cnt = 1;
          
         
        }
      }
    }

  }

  public int numberOfSegments() { // the number of line segments

  }

  public LineSegment[] segments() { // the line segments

  }
}
