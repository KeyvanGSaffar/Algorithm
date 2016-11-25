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
        if (points[i].compareTo(points[j]) == 0) {
          throw new IllegalArgumentException();
        }
      }
    }

    n = points.length;
    arrayLineSegment = new LineSegment[n];
    double[] slopeArray = new double[n];
    int[][] firstLast = new int[n][2];
    Point[] pointsC = new Point[n];

    //////////////////////////////////////////////////////////////////////////////////////////
    for (Point p : points)
      StdOut.println(p);
    for (int t = 0; t < n; t++) {
      pointsC[t] = points[t];
    }
    for (int i = 0; i < n; i++) {
      Arrays.sort(points, pointsC[i].slopeOrder());
      
      StdOut.println();
      for (Point p : points)
        StdOut.println(p);
      
      for (int j = 0; j < n; j++) {
        slopeArray[j] = points[j].slopeTo(pointsC[i]);
        // slopeArrayCopy[j] = slopeArray[j];
        StdOut.println(slopeArray[j]);
      }
      // Arrays.sort(slopeArray);
      int cnt = 1;
      double temp = slopeArray[1];
      int sLine = points[0].getIndex(); // start point of line segment
      int eLine = points[0].getIndex(); // end point of line segment

      for (int k = 2; k < n; k++) {
//        if (slopeArray[k] == Double.NEGATIVE_INFINITY)
//          continue;

        
        if (slopeArray[k] == temp) {
          cnt++;
        } else {
          if (cnt >= 3) {
            for (int l = k - cnt; l < k; l++) {
              if (points[l].compareTo(pointsC[eLine]) > 0)
                eLine = points[l].getIndex();
              if (points[l].compareTo(pointsC[sLine]) < 0)
                sLine = points[l].getIndex();
            }

          }

          int chk = 0;
          int m;
          for (m = 0; firstLast[m][0] != firstLast[m][1]; m++) {
            if (firstLast[m][0] == sLine && firstLast[m][1] == eLine) {
              chk = 1;
              break;
            }
          }

          if (chk == 0) {
            arrayLineSegment[m] = new LineSegment(pointsC[sLine], pointsC[eLine]);
            firstLast[m][0] = sLine;
            firstLast[m][1] = eLine;
            size = m + 1;
          }

          //////////////////////////////////////////////////////////////////////////////////////////////////

          temp = slopeArray[k];
          cnt = 1;

        }
      }
    }
//    for (int s=0; s<size: s++){
//      StdOut.println(firstLast[s,0] + ",");
//    }
//  for (Point p : points)
//    StdOut.println(p);
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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}
