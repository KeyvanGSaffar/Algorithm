
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
        if (points[i] == points[j]) {
          throw new IllegalArgumentException();
        }
      }
    }

    
    n = points.length;
    arrayLineSegment = new LineSegment[n];
    double[] slopeArray = new double[n];
    int[] tempArray = new int[4];
    int[][] firstLast = new int[n][2];

    //////////////////////////////////////////////////////////////////////////////////////////
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        slopeArray[j] = points[j].slopeTo(points[i]);
      }
      for (int k = 0; k < n; k++) {
        if (slopeArray[k] == Double.NEGATIVE_INFINITY)
          continue;

        int count = 0;
        tempArray[count++] = k;
        for (int l = k + 1; l < n; l++) {
          if (slopeArray[k] == slopeArray[l]) {
            tempArray[count++] = l;
          }
        }
        if (count == 4) {
          int sLine = -1; // start point of line segment
          int eLine = -1; // end point of line segment

          int comp01 = points[tempArray[0]].compareTo(points[tempArray[1]]);
          int comp12 = points[tempArray[1]].compareTo(points[tempArray[2]]);
          int comp23 = points[tempArray[2]].compareTo(points[tempArray[3]]);
          int comp02 = points[tempArray[0]].compareTo(points[tempArray[2]]);
          int comp03 = points[tempArray[0]].compareTo(points[tempArray[3]]);
          int comp13 = points[tempArray[1]].compareTo(points[tempArray[3]]);
          if (comp01 + comp02 + comp03 == 3)
            eLine = tempArray[0];
          if (comp01 + comp02 + comp03 == -3)
            sLine = tempArray[0];
          if (-comp01 + comp12 + comp13 == 3)
            eLine = tempArray[1];
          if (-comp01 + comp12 + comp13 == -3)
            sLine = tempArray[1];
          if (-comp02 - comp12 + comp23 == 3)
            eLine = tempArray[2];
          if (-comp02 - comp12 + comp23 == -3)
            sLine = tempArray[2];
          if (-comp03 - comp13 - comp23 == 3)
            eLine = tempArray[3];
          if (-comp03 - comp13 - comp23 == -3)
            sLine = tempArray[3];

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
}