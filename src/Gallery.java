import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Gallery extends JPanel {

    private JPanel imagesPanel = new JPanel(new GridLayout(3,4));

    public Gallery() {
        super();
        this.setLayout(new BorderLayout());

        //Adding a delete button
        JButton deleteImageButton = new JButton("Delete image");
        deleteImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(imagesPanel, BorderLayout.CENTER);
        this.add(deleteImageButton, BorderLayout.SOUTH);
    }

    public void addImage(BufferedImage image) {
        Image resizedImage = image.getScaledInstance(250,250, Image.SCALE_SMOOTH);
        image.getGraphics().drawImage(resizedImage,0,0, null);
        JPanel imagePanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(resizedImage,0,0,null);
            }
        };

        imagesPanel.add(imagePanel);
    }
}
