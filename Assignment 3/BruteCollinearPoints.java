import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

  private LineSegment[] arrayLineSegment;
  private int n;
  private int size;

  public BruteCollinearPoints(Point[] points) { // finds all line segments
                                                // containing 4 points
    if (points == null)
      throw new NullPointerException();
    for (Point p : points) {
      if (p == null)
        throw new IllegalArgumentException();

    }

    for (int i = 0; i < points.length; i++) {
      for (int j = i + 1; j < points.length; j++) {
        if (points[i].compareTo(points[j]) == 0) {
          throw new IllegalArgumentException();
        }
      }
    }

    n = points.length;
    arrayLineSegment = new LineSegment[n];
    double[] slopeArray = new double[n];
    int[] tempArray = new int[4];
    int[][] firstLast = new int[n][2];
    // for (int t = 0; t < n; t++) {
    // StdOut.println(firstLast[t][0]);
    // StdOut.println(firstLast[t][1]);
    // }

    //////////////////////////////////////////////////////////////////////////////////////////
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        slopeArray[j] = points[j].slopeTo(points[i]);
        if (slopeArray[j] == Double.NEGATIVE_INFINITY)
          tempArray[0] = j;
      }

      for (int k = 0; k < n; k++) {
        if (slopeArray[k] == Double.NEGATIVE_INFINITY)
          continue;

        int count = 1;
        tempArray[count++] = k;
        // StdOut.println(count);
        for (int l = 0; l < n; l++) {
          if (k != l && slopeArray[k] == slopeArray[l]) {
            tempArray[count++] = l;
            // StdOut.println(count);
          }
        }
        // StdOut.println(tempArray[0]+" "+tempArray[1]+" "+tempArray[2]+"
        // "+tempArray[3]);
        if (count == 4) {
          // StdOut.println("Entered");
          int sLine = tempArray[0]; // start point of line segment
          int eLine = tempArray[0]; // end point of line segment

          for (int t = 1; t < 4; t++) {
            if (points[tempArray[t]].compareTo(points[eLine]) > 0)
              eLine = tempArray[t];
            if (points[tempArray[t]].compareTo(points[sLine]) < 0)
              sLine = tempArray[t];

          }

          // StdOut.println("sLine" + sLine);
          // StdOut.println("eLine" + eLine);

          int chk = 0;
          int m;
          for (m = 0; firstLast[m][0] != firstLast[m][1]; m++) {
            // for (m = 0; (firstLast[m][0] != 0) && (firstLast[m][1] !=0); m++)
            // {

            // StdOut.println(m);
            if (firstLast[m][0] == sLine && firstLast[m][1] == eLine) {
              chk = 1;
              break;
            }
          }

          if (chk == 0) {
            arrayLineSegment[m] = new LineSegment(points[sLine], points[eLine]);
            firstLast[m][0] = sLine;
            firstLast[m][1] = eLine;
            size = m + 1;
          }

        }
      }
    }
    // for (int t = 0; t < size; t++) {
    // StdOut.println("sLine" + firstLast[t][0]);
    // StdOut.println("eLine" + firstLast[t][1]);
    // }

  }

  public int numberOfSegments() { // the number of line segments

    return size;
  }

  public LineSegment[] segments() { // the line segments
    LineSegment[] arrayToReturn = new LineSegment[numberOfSegments()];

    for (int i = 0; i < numberOfSegments(); i++) {
      arrayToReturn[i] = arrayLineSegment[i];
    }
    return arrayToReturn;
  }

  public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}