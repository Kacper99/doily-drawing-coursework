import javax.swing.*;
import java.awt.*;

public class DDFrame extends JFrame {

    public DDFrame(String title) {
        super(title);
        this.setSize(1250, 880);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void init() {

        //Create a tabbed pane so we can have a tab for gallery and drawing area
        JTabbedPane tabbedPane = new JTabbedPane();
        //Creating a container which will hold the drawing screen on the left and control panel on the right
        Container container = this.getContentPane();
        JPanel drawingScreen = new JPanel(new BorderLayout());

        //Adding the drawing area
        DoilyDrawingArea drawingArea = new DoilyDrawingArea();
        drawingScreen.add(drawingArea, BorderLayout.CENTER);

        //Creating a gallery
        Gallery gallery = new Gallery();

        //Adding the control panel
        ControlPanel controlPanel = new ControlPanel(drawingArea,gallery);
        drawingScreen.add(controlPanel,BorderLayout.EAST);

        tabbedPane.addTab("Drawing area", drawingScreen);
        tabbedPane.addTab("Gallery", gallery); //TODO: Create a variable for gallery

        container.add(tabbedPane);
        this.setVisible(true);
    }
}
