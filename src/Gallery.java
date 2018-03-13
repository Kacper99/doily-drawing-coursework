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

    /**
     * The constructor initialises the gallery with a border layout which will hold the grid layout for the images and the delete button
     */
    public Gallery() {
        super();
        this.setLayout(new BorderLayout());

        //Adding a delete button
        JButton deleteImageButton = new JButton("Delete image");
        deleteImageButton.addActionListener(e -> deleteSelectedImage() );

        this.add(imagesPanel, BorderLayout.CENTER); //Specifying the images panel to be the center of the border layout
        this.add(deleteImageButton, BorderLayout.SOUTH); //Specifying the delete button to be at the bottom of the border layout
    }

    /**
     * Adds the users image into the gallery if there is room
     * @param image BufferedImage from the drawing area
     */
    public void addImage(BufferedImage image) {

        //Checking if there is enough room in the gallery to add another picture
        if (imagesList.size() > 11) {
            JOptionPane.showMessageDialog(this, "Gallery full, please delete an image", "Gallery full", JOptionPane.ERROR_MESSAGE);
        } else {
            Image resizedImage = image.getScaledInstance(250,250, Image.SCALE_SMOOTH); //This is where the resized image will be stored
            image.getGraphics().drawImage(resizedImage,0,0, null); //Drawing the buffered image onto the resized buffered image.

            JPanel imagePanel = new JPanel() {
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g); //Creating a JPanel with the image painted onto it.
                    g.drawImage(resizedImage,0,0,null);
                }
            };

            imagePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mouseClicked(e);
                    selectedImage = (JPanel) e.getComponent(); //Whenever the JPanel is clicked, store that in a variable.
                    redrawGallery(); //Redraw the gallery so we can highlight the selected image
                }
            });
            imagesList.add(imagePanel); //Add the image to thr arraylist
            redrawGallery(); //Redraw the UI with the new image
        }
    }

    /**
     * Deletes the image which the user has selected
     */
    private void deleteSelectedImage() {
        Iterator<JPanel> it = imagesList.iterator();

        //Iterating through every image to find the one that the user has selected
        while (it.hasNext()) {
            JPanel panel = it.next();
            if (selectedImage == panel) {
                it.remove(); //Remove the image and update the gallery
                redrawGallery();
            }
        }

    }

    /**
     * If an image is added or removed, redraw the gallery
     */
    private void redrawGallery() {
        imagesPanel.removeAll(); //First remove all images from the gridpanel
        for (JPanel panel: imagesList) {
            if (selectedImage == panel) { //Look for the selected image to add a blue border to it
                panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            } else {
                panel.setBorder(null); //If the panel is not the selected image then remove the border from it (in case it was selected before)
            }
            imagesPanel.add(panel); //Iterate through every JPanel and add it to the grid layout
        }
        imagesPanel.updateUI();

    }
}
