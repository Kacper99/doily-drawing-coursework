/**
 * @author Kacper Martela
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Stores the data for a single line that the user has drawn
 */
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

    /**
     * This method will attempt to remove the point if it can find it in the points list
     * @param p The point to be removed
     */
    public void removePoint(Point p) {
        Iterator<Point> pointsIterator = points.iterator();
        ArrayList<Point> allPoints =  new ArrayList<>();

        for (int i = 1; i < da.getSectors() + 1; i++) { //Loop through each point and calculate the x and y of the point in that sector
            Double angle = (360.0/da.getSectors()) * i;
            int newX = (int)(p.getX() * Math.cos(Math.toRadians(angle)) - p.getY() * Math.sin(Math.toRadians(angle))); //Calculate the x of the point in that sector
            int newY = (int)(p.getY() * Math.cos(Math.toRadians(angle)) + p.getX() * Math.sin(Math.toRadians(angle))); //Calculate the y of the point in that sector
            if (reflected) //Also do it for reflections
                allPoints.add(new Point(-newX, newY));
            allPoints.add(new Point(newX, newY)); //Add the point to the list of points to check

        }

        while (pointsIterator.hasNext()) { //Loop through all the points in the line
            Point checkPoint = pointsIterator.next();
            for (Point angledPoint: allPoints) { //Compare the point to the mouse click in all sectors
                Double distance = Math.sqrt(Math.pow(checkPoint.getX()-angledPoint.getX(),2) + Math.pow(checkPoint.getY()-angledPoint.getY(), 2)); //Calculate the distance to the point
                if (distance <= brushSize) { //Check if the distance is less than or equal to the brush size and if it is then remove the point
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
            g2d.rotate(Math.toRadians(360.0 / da.getSectors())); //Rotate to draw it through all sectors.
        }
    }
}
