import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
/**
 * CanvasClassTemplate.java
 * Purpose: generic Class that represent the canvas where you will show the stuff.
 * @author: Jeffrey Pallarés Núñez.
 * @version: 1.0 23/07/19
 */

class MainCanvas extends JPanel {

    private static final long serialVersionUID = 1L;

    /** Object of the class that Needs Visualization (ONV)  */
    public static parallelMandelbrot task;
    public static BufferedImage image_ref;
    public static int xMax;
    public static int yMax;


    public void updateCanvas(){
        this.validate();
        this.repaint();
    }

    /** Constructor of the class that works as a link between the classNV and the GUI */
    public MainCanvas(int x_max, int y_max) {

        this.setOpaque(true);

        task = new parallelMandelbrot();
        task.plug(this);
        xMax = x_max;
        yMax = y_max;

        image_ref = new BufferedImage(xMax, yMax,BufferedImage.TYPE_BYTE_INDEXED);

    }
    public static void setDimensions(int x_max, int y_max){
        xMax = x_max;
        yMax = y_max;
        image_ref = new BufferedImage(xMax, yMax,BufferedImage.TYPE_BYTE_INDEXED);
    }

    /**
     * This method process the information of the ONV and draw it in the image.
     * This method is called in {@link #paintComponent(Graphics)} method.
     * Change to represent what you want, in this example we will draw a chessboard.
     * @return A BufferedImage with the information drew on it.
     */

    private  BufferedImage GenerateImage() {
        image_ref = task.getData();
        return image_ref;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (task.getData() != null)
            g.drawImage(GenerateImage(), 500 - xMax / 2, 500 - yMax / 2, xMax, yMax, null);
    }
}