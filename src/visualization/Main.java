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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame mainFrame = new JFrame();
        mainFrame.setSize(new Dimension(1440, 600));
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ScrollPane scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
        VisualizationCanvas canvas = new VisualizationCanvas();
        scrollPane.add(canvas);
        mainFrame.getContentPane().add(scrollPane);
    }

}
