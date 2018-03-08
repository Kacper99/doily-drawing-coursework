import javax.swing.*;
import java.awt.*;

/**
 * This class creates the control panel with all buttons and
 */
public class ControlPanel extends JPanel {

    /**
     * This constructor will create all the components for the control panel and add all listeners to those objects.
     * Most of the listener objects just call a function from the drawing area instead of
     *
     * @param da Reference to the center drawing area
     * @param gallery Reference to the gallery
     */
    public ControlPanel(DoilyDrawingArea da, Gallery gallery) {
        this.setLayout(new GridLayout(8,1));

        //Clear display button
        JButton clearDisplayButton = new JButton("Clear display");
        clearDisplayButton.addActionListener(e -> da.clearDisplay());

        //Pen size slider and text
        JPanel penSizeHolder = new JPanel(new FlowLayout());
        JLabel penSizeText = new JLabel("Size: 5");

        JSlider penSizeSlider = new JSlider(1,20);
        penSizeSlider.addChangeListener(e -> { //When the user moves the slider, I also change the text next to it to display the new value
            penSizeText.setText("Size: " + penSizeSlider.getValue());
            da.setBrushSize(penSizeSlider.getValue());
        });

        penSizeHolder.add(penSizeText);
        penSizeHolder.add(penSizeSlider);

        //Sector selector
        JPanel sectorSelectorHolder = new JPanel(new FlowLayout());
        JLabel sectorNumberText = new JLabel("Sectors: 32");
        JSlider sectorSlider = new JSlider(1,64);

        sectorSlider.addChangeListener(e -> {
            sectorNumberText.setText("Sectors: " + sectorSlider.getValue());
            da.setSectors(sectorSlider.getValue());
        });

        sectorSelectorHolder.add(sectorNumberText);
        sectorSelectorHolder.add(sectorSlider);

        //Sector lines, reflection, and eraser toggles;
        JPanel toggles = new JPanel(new FlowLayout());
        JCheckBox sectorLinesCB = new JCheckBox("Show sector lines", true);
        JCheckBox reflectDrawnPointsCB = new JCheckBox("Reflect drawn points", true);
        JCheckBox eraserCB = new JCheckBox("Eraser");

        sectorLinesCB.addChangeListener(e -> da.setShowSectorLines(sectorLinesCB.isSelected()));
        reflectDrawnPointsCB.addChangeListener(e -> da.setReflectDrawnPoints(reflectDrawnPointsCB.isSelected()));
        toggles.add(sectorLinesCB);
        toggles.add(reflectDrawnPointsCB);
        toggles.add(eraserCB);

        //Undo and redo buttons
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");

        undoButton.addActionListener(e -> da.undo());
        redoButton.addActionListener(e -> da.redo());

        //Save image
        JButton saveImageButton = new JButton("Save image");
        saveImageButton.addActionListener(e -> gallery.addImage(da.getImage())); //Get the image from the drawing area and pass it to the gallery

        //Colour chooser
        JButton colourChooserButton = new JButton("Choose colour");

        colourChooserButton.addActionListener(e -> {
            Color newColour = JColorChooser.showDialog(this, "Choose Colour", da.getPenColour()); //Opens the colour chooser dialog and whatever colour is selected is the new brush colour
            da.setPenColour(newColour);
        });

        this.add(penSizeHolder);
        this.add(sectorSelectorHolder);
        this.add(toggles);
        this.add(clearDisplayButton);
        this.add(undoButton);
        this.add(redoButton);
        this.add(saveImageButton);
        this.add(colourChooserButton);
    }
}
