import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

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
    int[][] firstLast = new int[n][2];

    //////////////////////////////////////////////////////////////////////////////////////////
    for (int i = 0; i < n; i++) {
      Arrays.sort(points, points[i].slopeOrder());
      for (int j = 0; j < n; j++) {
        slopeArray[j] = points[j].slopeTo(points[i]);
        // slopeArrayCopy[j] = slopeArray[j];
      }
      // Arrays.sort(slopeArray);
      int cnt = 1;
      double temp = slopeArray[1];
      int sLine = 0; // start point of line segment
      int eLine = 0; // end point of line segment

      for (int k = 2; k < n; k++) {
        if (slopeArray[k] == Double.NEGATIVE_INFINITY)
          continue;
        if (slopeArray[k] == temp) {
          cnt++;
        } else {
          if (cnt >= 4) {
            for (int l = k - cnt; l < k; l++) {
              if (points[l].compareTo(points[eLine]) == +1)
                eLine = l;
              if (points[l].compareTo(points[sLine]) == -1)
                sLine = l;
            }

          }

          int chk = 0;
          int m;
          for (m = 0; firstLast[m][1] != firstLast[m][2]; m++) {
            if (firstLast[m][1] == sLine && firstLast[m][2] == eLine) {
              chk = 1;
              break;
            }
          }

          if (chk == 0) {
            arrayLineSegment[m] = new LineSegment(points[sLine], points[eLine]);
            firstLast[m][1] = sLine;
            firstLast[m][2] = eLine;
            size = m;
          }

          //////////////////////////////////////////////////////////////////////////////////////////////////

          temp = slopeArray[k];
          cnt = 1;

        }
      }
    }

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
