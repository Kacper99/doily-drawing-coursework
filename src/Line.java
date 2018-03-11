import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Line {

    private ArrayList<Point> points;
    private DoilyDrawingArea da;
    private int brushSize; //Storing brush size so when it's resized later it won't affect this line
    private Color brushColour;
    private boolean reflected;
    private boolean isEraser;

    //Setters are used for when values are changed after the new line is created so they apply for the current line, and not for the next one
    public void setBrushSize(int brushSize) {
        this.brushSize = brushSize;
    }

    public void setBrushColour(Color brushColour) {
        this.brushColour = brushColour;
    }

    public void addPoint(Point p) {
        this.points.add(p);
    }

    public void setReflected(boolean reflected) {
        this.reflected = reflected;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    /**
     * Set all the current line settings in the constructor
     * @param da Reference to the drawing area
     */
    public Line(DoilyDrawingArea da) {
        points = new ArrayList<>();
        this.da = da;
        this.brushSize = da.getBrushSize();
        this.brushColour = da.getPenColour();
        this.reflected = da.isReflectDrawnPoints();
        this.isEraser = da.isPenAsEraser();
    }

    public void removePoint(Point p) {
        Iterator<Point> pointsIterator = points.iterator();
        ArrayList<Point> allPoints =  new ArrayList<>();
        for (int i = 1; i < da.getSectors() + 1; i++) {
            Double angle = (360.0/da.getSectors()) * i;
            int newX = (int)(p.getX() * Math.cos(Math.toRadians(angle)) - p.getY() * Math.sin(Math.toRadians(angle)));
            int newY = (int)(p.getY() * Math.cos(Math.toRadians(angle)) + p.getX() * Math.sin(Math.toRadians(angle)));
            if (reflected)
                allPoints.add(new Point(-newX, newY));
            allPoints.add(new Point(newX, newY));

        }

        System.out.println(allPoints.toString());
        while (pointsIterator.hasNext()) {
            Point checkPoint = pointsIterator.next();
            for (Point angledPoint: allPoints) {
                //Calculate distance
                Double distance = Math.sqrt(Math.pow(checkPoint.getX()-angledPoint.getX(),2) + Math.pow(checkPoint.getY()-angledPoint.getY(), 2));
                System.out.println("Distance " + distance);
                System.out.println("Passed Point: " + angledPoint.getX() + "," + angledPoint.getY() + " point to check: " + checkPoint.getX() + "," + checkPoint.getY());
                if (distance <= brushSize /2) {
                    System.out.println("Found point");
                    pointsIterator.remove();
                }
            }
        }
    }
    /**
     * Draw the point or line
     * @param g
     */
    public void drawLine(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(brushColour); //Setting the colour of the line to the one selected by the user
        g2d.setStroke(new BasicStroke(brushSize));

        for (int i = 0; i < da.getSectors(); i++) {
            Iterator<Point> pointIterator = points.iterator();
            Point firstLineEnd;
            //Point secondLineEnd;

            while (pointIterator.hasNext()) {
                firstLineEnd = pointIterator.next();
                g2d.fillOval((int) firstLineEnd.getX()- brushSize/2,(int) firstLineEnd.getY()- brushSize/2, brushSize, brushSize); //Draw a point at the coordinate and set the width to the brush siz
                if (reflected)
                    g2d.fillOval(-(int) firstLineEnd.getX()- brushSize/2,(int) firstLineEnd.getY()- brushSize/2, brushSize, brushSize); //Draw a point at the coordinate and set the width to the brush siz

            }
            /*
            //If there is only one point then we only want to draw a dot, otherwise draw a line
            if (points.size() == 1) {
                firstLineEnd = points.get(0); //Instead of using iterator, use points to get the first coordinate
                g2d.fillOval((int) firstLineEnd.getX() - brushSize/2,(int) firstLineEnd.getY()- brushSize/2, brushSize, brushSize); //Draw a point at the coordinate and set the width to the brush siz
                if (reflected)
                    g2d.fillOval(-(int) firstLineEnd.getX()- brushSize/2, (int) firstLineEnd.getY()- brushSize/2, brushSize , brushSize); //Reflect by drawing at the negative x , as the (0,0) is in the middle of the screen
            } else if (points.size() > 1){
                firstLineEnd = pointIterator.next(); //Set the first line end to the next item in the iterator

                while (pointIterator.hasNext()) { //Loop until we get to the end of the iterator
                    secondLineEnd = pointIterator.next();

                    g2d.fillOval((int) firstLineEnd.getX()- brushSize/2,(int) firstLineEnd.getY()- brushSize/2, brushSize, brushSize); //Draw a point at the coordinate and set the width to the brush siz
                    if (reflected)
                        g2d.fillOval(-(int) firstLineEnd.getX()- brushSize/2,(int) firstLineEnd.getY()- brushSize/2, brushSize, brushSize); //Draw a point at the coordinate and set the width to the brush siz
                    firstLineEnd = secondLineEnd; //Set the next line beginning to the current ending
                }
            }
            */
            g2d.rotate(Math.toRadians(360.0 / da.getSectors())); //Rotate to draw it through all sectors.
        }
    }
}
