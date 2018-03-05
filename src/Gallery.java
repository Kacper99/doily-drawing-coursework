import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class Gallery extends JPanel {

    private JPanel imagesPanel = new JPanel(new GridLayout(3,4));
    private ArrayList<JPanel> imagesList = new ArrayList<>();
    private JPanel selectedImage;

    public Gallery() {
        super();
        this.setLayout(new BorderLayout());

        //Adding a delete button
        JButton deleteImageButton = new JButton("Delete image");
        deleteImageButton.addActionListener(e -> deleteSelectedImage() );

        this.add(imagesPanel, BorderLayout.CENTER);
        this.add(deleteImageButton, BorderLayout.SOUTH);
    }

    public void addImage(BufferedImage image) {

        if (imagesList.size() > 11) {
            JOptionPane.showMessageDialog(this, "Gallery full, please delete an image", "Gallery full", JOptionPane.ERROR_MESSAGE);
        } else {
            Image resizedImage = image.getScaledInstance(250,250, Image.SCALE_SMOOTH);
            image.getGraphics().drawImage(resizedImage,0,0, null);

            JPanel imagePanel = new JPanel() {
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(resizedImage,0,0,null);
                }
            };

            imagePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mouseClicked(e);
                    selectedImage = (JPanel) e.getComponent();
                    System.out.println(selectedImage);
                }
            });
            imagesList.add(imagePanel);
            redrawGallery();
        }
    }

    private void deleteSelectedImage() {
        System.out.println("Delete button pressed");
        Iterator<JPanel> it = imagesList.iterator();

        while (it.hasNext()) {
            JPanel panel = it.next();
            System.out.println("In it loop");
            if (selectedImage == panel) {
                System.out.println("Found image");
                it.remove();
                redrawGallery();
            }
        }

    }

    private void redrawGallery() {
        imagesPanel.removeAll();
        for (JPanel panel: imagesList) {
            imagesPanel.add(panel);
        }
        imagesPanel.updateUI();

    }
}
