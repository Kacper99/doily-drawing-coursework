import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Gallery extends JPanel {

    private ArrayList<JPanel> images = new ArrayList<>();
    JPanel imagesPanel = new JPanel(new GridLayout(2,6));

    public Gallery() {
        super();

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //Adding a delete button
        JButton deleteImageButton = new JButton("Delete image");
        deleteImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(imagesPanel);
        this.add(deleteImageButton);
    }

    public void addImage(BufferedImage image) {
        Image resizedImage = image.getScaledInstance(220,220, Image.SCALE_SMOOTH);
        image.getGraphics().drawImage(resizedImage,0,0, null);
        JPanel imagePanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(resizedImage,0,0,null);
            }
        };

        images.add(imagePanel);
        imagesPanel.add(imagePanel);
    }
}
