import javax.swing.*;
import java.awt.*;
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
        repaint();
    }

    public DoilyDrawingArea() {
        super();
        this.setBackground(Color.BLACK);

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                addPoint(e);
                redoStack.clear();
                System.out.println("mouse pressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lines.add(line);
                line = new Line(DoilyDrawingArea.this);
                System.out.println("Mouse released");
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        this.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                addPoint(e);
                System.out.println("Mouse dragged");
            }

            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    public void clearDisplay() {
        this.lines.clear();
        repaint();
        //TODO: This whole thing
    }

    public void undo() {
        if (!lines.isEmpty()) {
            redoStack.push(lines.get(lines.size() - 1));
            lines.remove(lines.size() - 1);
            repaint();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty())
            lines.add(redoStack.pop());
        repaint();
    }

    public BufferedImage getImage() {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        this.paint(image.getGraphics());
        return image;
    }

    public void addPoint(MouseEvent e) {
        if (sectors % 2 == 0) {
            int tempY = e.getY() - getHeight() / 2;
            int tempX = e.getX() - getWidth() / 2;
            line.addPoint(new Point(-tempX, -tempY));
        } else {
            int tempY = e.getY() - getHeight() / 2;
            int tempX = e.getX() - getWidth() / 2;
            line.addPoint(new Point(tempX, tempY));
        }
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.translate(this.getWidth() / 2, this.getHeight() / 2); //Tell g2d to draw all points from the center of the panel
        if (showSectorLines && sectors > 1) {
            for (int i = 0; i < sectors; i++) {
                g2d.setStroke(new BasicStroke(1));
                g2d.setColor(Color.GREEN);
                g2d.drawLine(0, 0, 0, -400);
                g2d.rotate(Math.toRadians((double) 360 / sectors));
            }
        }

        for (int i = 0; i < lines.size(); i++) {
            lines.get(i).drawLine(g2d);
        }

        line.drawLine(g2d);
    }
}
