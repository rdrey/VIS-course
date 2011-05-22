/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visualization;

import java.awt.*;
import java.io.*;
import java.util.*;

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
        buckets = new ArrayList<LinkedList<Book.BookStats>>();
        for (int i = 0; i < 22; i++)
            buckets.add(new LinkedList<Book.BookStats>());
        readSalesData();
    }

    public void readSalesData()
    {
        for (int i = 0 ; i < 22; i++)
        {
            try
            {
                BufferedReader input = new BufferedReader (new FileReader("build\\classes\\visualization\\Week " + (i+1) + ".txt"));
                int ranking = 1;
                String line = input.readLine();
                input.readLine();
                while (line!=null)
                {
                    // parse text file
                    int currentIndex = line.indexOf(", by");
                    String name = line.substring(line.indexOf(' ')+1, currentIndex);
                    line = line.substring(currentIndex+4);
                    currentIndex = line.indexOf(". (");
                    String author = line.substring (0, currentIndex);
                    line = line.substring(currentIndex+3);
                    currentIndex = line.indexOf(", $");
                    String publisher = line.substring(0, currentIndex);
                    line = line.substring (currentIndex+3);
                    currentIndex = line.indexOf (".)");
                    float price = Float.parseFloat(line.substring(0, currentIndex));
                    String description = line.substring(currentIndex+3);

                    // see if book already exists
                    Book owner = null;
                    if (i > 0)
                    {
                        ListIterator<Book.BookStats> it = buckets.get(i-1).listIterator();
                        while (it.hasNext())
                        {
                            Book.BookStats current = it.next();
                            if (current.owner.title.equals(name))
                            {
                                owner = current.owner;
                                break;
                            }
                        }
                    }
                    if (owner == null)
                        owner = new Book(name, author, publisher, description, price, 0);
                    Book.BookStats bookStats = owner.addStats(i+1, 2011, 0, ranking);
                    buckets.get(i).add(bookStats);
                    ranking++;
                    line = input.readLine();
                    input.readLine();
                    System.out.println (owner);
                }
            }
            catch (IOException e)
            {
                System.out.println(e);
                break;
            }
        }
    }

    public void paint(Graphics g)
    {
        
    }
}
