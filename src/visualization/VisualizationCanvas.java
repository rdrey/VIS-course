/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visualization;

import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.regex.*;

/**
 *
 * @author Riz
 */
public class VisualizationCanvas extends Canvas {

    int centerY = 450, width = 2880, height = 2 * centerY;
    ArrayList<LinkedList<Book.BookStats>> buckets;

    public VisualizationCanvas()
    {
        super();
        setSize(new Dimension(width, height));
        buckets = new ArrayList<LinkedList<Book.BookStats>>(23);
        readSalesData();
    }

    public void readSalesData()
    {
        for (int i = 0 ; i < 23; i++)
        {
            try
            {
                BufferedReader input = new BufferedReader (new FileReader("Week " + (i+1) + ".txt"));
                
            }
            catch (FileNotFoundException e)
            {
                System.out.println(e);
                break;
            }
        }
    }

    @Override
    public void paint(Graphics g)
    {
        
    }
}
