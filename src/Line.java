import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Line {

    private ArrayList<Point> points;
    private DoilyDrawingArea da;
    private int brushSize; //Storing brush size so when it's resized later it won't affect this line
    private Color brushColour;

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

    public Line(DoilyDrawingArea da) {
        points = new ArrayList<>();
        this.da = da;
        this.brushSize = da.getBrushSize();
        this.brushColour = da.getPenColour();
    }

    public void drawLine(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(brushColour);

        for (int i = 0; i < da.getSectors(); i++) {
            g2d.setStroke(new BasicStroke(brushSize));
            Iterator<Point> iter = points.iterator();
            Point firstLineEnd;
            Point secondLineEnd;

            if (points.size() == 1) {
                firstLineEnd = points.get(0);
                g2d.drawRect((int) firstLineEnd.getX(),(int) firstLineEnd.getY(), brushSize / 4, brushSize / 4);
                if (da.isReflectDrawnPoints()) {
                    g2d.drawRect(-(int) firstLineEnd.getX(), (int) firstLineEnd.getY(), brushSize / 4, brushSize / 4);
                }
                g2d.rotate(Math.toRadians((double) 360 / da.getSectors()));

            } else if (points.size() > 1){

                firstLineEnd = iter.next();

                while (iter.hasNext()) {

                    secondLineEnd = iter.next();

                    g2d.drawLine((int) firstLineEnd.getX(), (int) firstLineEnd.getY(), (int) secondLineEnd.getX(), (int) secondLineEnd.getY());

                    if (da.isReflectDrawnPoints()) {
                        g2d.drawLine(-(int) firstLineEnd.getX(), (int) firstLineEnd.getY(), -(int) secondLineEnd.getX(), (int) secondLineEnd.getY());
                    }

                    firstLineEnd = secondLineEnd; //Set the new line beginning to the old ending
                }
                g2d.rotate(Math.toRadians((double) 360 / da.getSectors()));
            }
        }
    }
}
