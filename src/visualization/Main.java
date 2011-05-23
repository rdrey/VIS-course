/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visualization;

import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Riz
 */
public class Main{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setSize(new Dimension(800, 600));
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Book Picker");
        
        ScrollPane scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);

        VisualizationCanvas canvas = new VisualizationCanvas();
        canvas.addMouseMotionListener(canvas);
        canvas.addMouseListener(canvas);
        
        scrollPane.add(canvas);
        scrollPane.getHAdjustable().addAdjustmentListener(canvas);
        mainFrame.getContentPane().add(scrollPane);
        
        mainFrame.setVisible(true);
        return;
    }
}
