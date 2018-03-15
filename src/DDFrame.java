import javax.swing.*;
import java.awt.*;

public class DDFrame extends JFrame {

    public DDFrame(String title) {
        super(title);
        this.setSize(1250, 880); //Set default size of the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //When the close button is pressed the program will end
    }

    /**
     * Initialises the window
     */
    public void init() {
        JTabbedPane tabbedPane = new JTabbedPane(); //Create a tabbed pane so we can have a tab for gallery and drawing area

        //Creating a container which will hold the drawing screen on the left and control panel on the right
        Container container = this.getContentPane();
        JPanel drawingScreen = new JPanel(new BorderLayout());
        drawingScreen.setPreferredSize(new Dimension(800,800));
        DoilyDrawingArea drawingArea = new DoilyDrawingArea();
        drawingScreen.add(drawingArea, BorderLayout.CENTER); //Adding drawing area to the center

        Gallery gallery = new Gallery(); //Creating an instance of gallery to hold images
        ControlPanel controlPanel = new ControlPanel(drawingArea,gallery);
        drawingScreen.add(controlPanel,BorderLayout.EAST);  //Creating an instance of the control panel, putting it to the right of the drawing area

        //Adding tabs
        tabbedPane.addTab("Drawing area", drawingScreen);
        tabbedPane.addTab("Gallery", gallery);

        container.add(tabbedPane);
        this.setVisible(true);
    }
}
