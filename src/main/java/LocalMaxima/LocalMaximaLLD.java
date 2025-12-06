package LocalMaxima;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Point {
    private double x;
    private double y;

    public Point(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}

class Graph {
    private List<Point> pointList;

    public Graph(List<Point> points) {
        pointList = new ArrayList<>();
        for(Point point : points) {
            pointList.add(point);
        }
    }

    public void addPoint(Point point) {
        pointList.add(point);
    }

    public List<Point> getPointList(){
        return pointList;
    }
}

interface findPeakStrategy {
    public List<Point> findPeaks(Graph graph);
}

class localMaximaStrategy implements findPeakStrategy {

    public List<Point> findPeaks(Graph graph) {
        List<Point> result = new ArrayList<>();
        List<Point> pointList = graph.getPointList();
        int size = pointList.size();
        for(int i=1;i<size-1;i++) {
            if(pointList.get(i).getY() > pointList.get(i-1).getY() && pointList.get(i).getY() > pointList.get(i+1).getY()) {
                result.add(pointList.get(i));
            }
        }
        return result;
    }
}

class PeakFinder {
    private findPeakStrategy peakStrategy;

    public PeakFinder(findPeakStrategy peakStrategy){
        this.peakStrategy = peakStrategy;
    }

    public List<Point> findExtremes(Graph graph){
        return peakStrategy.findPeaks(graph);
    }

    public void setPeakStrategy(findPeakStrategy peakStrategy) {
        this.peakStrategy = peakStrategy;
    }
}

public class LocalMaximaLLD {

    public static void main(String[] args) {
        List<Point> points = Arrays.asList(
                new Point(0, 1),
                new Point(1, 3),
                new Point(2, 2),  // Peak at (1,3)
                new Point(3, 4),
                new Point(4, 3),  // Peak at (3,4)
                new Point(5, 5),
                new Point(6, 1)   // Peak at (5,5)
        );


        Graph graph = new Graph(points);

        PeakFinder peakFinder = new PeakFinder(new localMaximaStrategy());
        List<Point> result = peakFinder.findExtremes(graph);
        for(Point it: result){
            System.out.println(it.getX()+ " " + it.getY());
        }
    }
}


