import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("WeakerAccess")
public class Line {

    private ArrayList<Point> points;
    private DoilyDrawingArea da;
    private int brushSize; //Storing brush size so when it's resized later it won't affect this line
    private Color brushColour;
    private boolean reflected;

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
            Point secondLineEnd;

            //If there is only one point then we only want to draw a dot, otherwise draw a line
            if (points.size() == 1) {
                firstLineEnd = points.get(0); //Instead of using iterator, use points to get the first coordinate
                g2d.drawRect((int) firstLineEnd.getX(),(int) firstLineEnd.getY(), brushSize / 4, brushSize / 4); //Draw a point at the coordinate and set the width to the brush siz
                if (reflected)
                    g2d.drawRect(-(int) firstLineEnd.getX(), (int) firstLineEnd.getY(), brushSize / 4, brushSize / 4); //Reflect by drawing at the negative x , as the (0,0) is in the middle of the screen
            } else if (points.size() > 1){
                firstLineEnd = pointIterator.next(); //Set the first line end to the next item in the iterator

                while (pointIterator.hasNext()) { //Loop until we get to the end of the iterator
                    secondLineEnd = pointIterator.next();

                    g2d.drawLine((int) firstLineEnd.getX(), (int) firstLineEnd.getY(), (int) secondLineEnd.getX(), (int) secondLineEnd.getY()); //Draw a line between the first point and the second one
                    if (reflected)
                        g2d.drawLine(-(int) firstLineEnd.getX(), (int) firstLineEnd.getY(), -(int) secondLineEnd.getX(), (int) secondLineEnd.getY()); //Reflect the line
                    firstLineEnd = secondLineEnd; //Set the next line beginning to the current ending
                }
            }
            g2d.rotate(Math.toRadians(360.0 / da.getSectors())); //Rotate to draw it through all sectors.
        }
    }
}
