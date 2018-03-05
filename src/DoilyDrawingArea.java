import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

public class DoilyDrawingArea extends JPanel{

    private boolean showSectorLines = true;
    private boolean reflectDrawnPoints = true;
    private int sectors = 16;
    private int brushSize = 3;
    private Color penColour = Color.WHITE;
    private Line line = new Line(this);
    private ArrayList<Line> lines = new ArrayList<>();
    private Stack<Line> redoStack = new Stack<>();

    //Getters and setters
    public void setShowSectorLines(boolean showSectorLines) {
        this.showSectorLines = showSectorLines;
        repaint();
    }

    public void setSectors(int sectors) {
        this.sectors = sectors;
        repaint();
    }

    public Color getPenColour() {
        return penColour;
    }

    public void setPenColour(Color colour) {
        this.penColour = colour;
        line.setBrushColour(colour);
    }

    public int getBrushSize() {
        return brushSize;
    }

    public boolean isReflectDrawnPoints() {
        return reflectDrawnPoints;
    }

    public int getSectors() { return sectors;}

    public void setBrushSize(int brushSize) {
        this.brushSize = brushSize;
        line.setBrushSize(brushSize);
    }

    public void setReflectDrawnPoints(boolean reflectDrawnPoints) {
        this.reflectDrawnPoints = reflectDrawnPoints;
        line.setReflected(reflectDrawnPoints);
        repaint();
    }

    //

    /**
     * Adding all the mouse and mouse motion listeners within the constructor
     */
    public DoilyDrawingArea() {
        super();
        this.setBackground(Color.BLACK);

        this.addMouseListener(new MouseAdapter() { //Using a mouse adapter as not all methods of MouseListener are needed
            //When the mouse is pressed invoke addPoint, also clear the redo stack as those redo's are not needed anymore
            @Override
            public void mousePressed(MouseEvent e) {
                addPoint(e);
                redoStack.clear();
                System.out.println("mouse pressed");
            }

            //When the mouse is released add the line you just drew to the line array list and the delete that line from the line variable and make a new one
            @Override
            public void mouseReleased(MouseEvent e) {
                lines.add(line);
                line = new Line(DoilyDrawingArea.this);
                System.out.println("Mouse released");
            }
        });

        this.addMouseMotionListener(new MouseMotionListener() { //MouseDragged doesn't work in MouseAdapter so need to use in MouseMotionListener
            //Whenever the mouse is dragged, also add that point
            public void mouseDragged(MouseEvent e) {
                addPoint(e);
                System.out.println("Mouse dragged");
            }

            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    /**
     * Clear all lines in the lines array list and then repaint
     */
    public void clearDisplay() {
        this.lines.clear();
        repaint();
    }

    /**
     * Removes the previous line and stores it in a stack of elements which have been undone, then repaints.
     */
    public void undo() {
        if (!lines.isEmpty()) {
            Line previousLine = lines.get(lines.size() - 1); //Gets the latest line that has been drawn
            redoStack.push(previousLine); //Push the line onto a stack in case the user wants to redo the line
            lines.remove(previousLine); //Removes the latest line from the line array list
            repaint();
        }
    }

    /**
     * Redraws the last removed line
     */
    public void redo() {
        if (!redoStack.isEmpty())
            lines.add(redoStack.pop()); //Pop the line off the top of the stack and add it o the lines array list
        repaint();
    }

    /**
     * Turns the JPanel with the doily drawing into a buffered image which can be saved and redrawn later in the gallery
     * @return Doily drawing as a buffered image
     */
    public BufferedImage getImage() {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        this.paint(image.getGraphics());
        return image;
    }

    /**
     * Adds a point to the current line
     * @param e Passing in the mouse event to get the coordinates of the mouse click or drag
     */
    private void addPoint(MouseEvent e) {
        int newX = e.getX() - getWidth() / 2; //Adjusting x and y for 0,0 to be the center of the panel
        int newY = e.getY() - getHeight() / 2;
        if (sectors % 2 == 0) { //Adjust y value depending on if theres an odd or even number of sectors
            line.addPoint(new Point(newX, -newY));

        } else {
            line.addPoint(new Point(newX, newY));

        }
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
    }

    /**
     * Used to paint the sector lines and the users drawing
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(this.getWidth() / 2, this.getHeight() / 2); //Tell g2d to draw all points from the center of the panel

        //Drawing the sectors by drawing a line from the center to the bottom edge and then rotating by 360 divided by the number of sectors to get the angle to rotate by
        if (showSectorLines && sectors > 1) {
            for (int i = 0; i < sectors; i++) {
                g2d.setStroke(new BasicStroke(1));
                g2d.setColor(Color.GREEN);
                g2d.drawLine(0, 0, 0, -400);
                g2d.rotate(Math.toRadians((double) 360 / sectors));
            }
        }

        //Draw all the previous lines that were drawn before
        for (Line line1 : lines) {
            line1.drawLine(g2d);
        }

        line.drawLine(g2d); //Draw the newest line
    }
}
