import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    DoilyDrawingArea da;

    public ControlPanel(DoilyDrawingArea da, Gallery gallery) {

        this.da = da; //Set the drawing area
        this.setLayout(new GridLayout(6,1));

        //Clear display button
        JButton clearDisplayButton = new JButton("Clear display");

        clearDisplayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        clearDisplayButton.addActionListener(e -> da.clearDisplay());

        this.add(clearDisplayButton);

        //Pen size slider and text
        JPanel penSizeHolder = new JPanel(new FlowLayout());
        JLabel penSizeText = new JLabel("Size: 3");

        JSlider penSizeSlider = new JSlider(1,10); //TODO: Set a min and max for the pen size
        penSizeSlider.addChangeListener(e -> {
            penSizeText.setText("Size: " + penSizeSlider.getValue());
            da.setBrushSize(penSizeSlider.getValue());
        });

        penSizeHolder.add(penSizeText);
        penSizeHolder.add(penSizeSlider);
        this.add(penSizeHolder);

        //Sector selector
        JPanel sectorSelectorHolder = new JPanel(new FlowLayout());
        JLabel sectorNumberText = new JLabel("Sectors: 16");
        JSlider sectorSlider = new JSlider(1,64); //TODO: Set an actual maximum

        sectorSlider.addChangeListener(e -> {
                da.setSectors(sectorSlider.getValue());
                sectorNumberText.setText("Sectors: " + sectorSlider.getValue());
        });

        sectorSelectorHolder.add(sectorNumberText);
        sectorSelectorHolder.add(sectorSlider);
        this.add(sectorSelectorHolder);

        //Sector lines, reflection, and eraser toggles;
        JPanel toggles = new JPanel(new FlowLayout());
        JCheckBox sectorLinesCB = new JCheckBox("Show sector lines", true);
        JCheckBox reflectDrawnPointsCB = new JCheckBox("Reflect drawn points", true);
        JCheckBox eraserCB = new JCheckBox("Eraser");


        sectorLinesCB.addChangeListener(e -> da.setShowSectorLines(sectorLinesCB.isSelected()));
        reflectDrawnPointsCB.addChangeListener(e -> da.setReflectDrawnPoints(reflectDrawnPointsCB.isSelected()));

        //TODO: Add eraser
        toggles.add(sectorLinesCB);
        toggles.add(reflectDrawnPointsCB);
        toggles.add(eraserCB);
        this.add(toggles);

        //Undo and redo buttons
        JPanel buttonsHolder = new JPanel(new GridLayout(2,2));
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");

        undoButton.addActionListener(e -> da.undo());
        redoButton.addActionListener(e -> da.redo());

        buttonsHolder.add(undoButton);
        buttonsHolder.add(redoButton);

        //Save image
        JButton saveImageButton = new JButton("Save image");
        saveImageButton.addActionListener(e -> gallery.addImage(da.getImage()));
        buttonsHolder.add(saveImageButton);

        //Colour chooser
        JButton colourChooserButton = new JButton("Choose colour");
        colourChooserButton.addActionListener(e -> {
            Color newColour = JColorChooser.showDialog(this, "Choose Colour", null);
            da.setPenColour(newColour);
        });
        buttonsHolder.add(colourChooserButton);

        //TODO: Add a colour chooser (JColorChooser)

        this.add(buttonsHolder);
    }
}
