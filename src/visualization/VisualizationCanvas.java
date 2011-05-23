/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visualization;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Riz
 */
public class VisualizationCanvas extends Canvas implements MouseMotionListener, MouseListener, AdjustmentListener {

    // colours
    static Color [] colourSelection= {new Color(247, 61, 12), new Color(221, 38, 11),new Color (190, 16, 10),
new Color(235, 101, 12),new Color (243, 101, 12),new Color (227, 93, 11), new Color
(179, 87, 9), new Color (215, 127, 11), new Color (231, 148, 12), new Color
(186, 136, 9), new Color (228, 194, 11), new Color (191, 142, 10), new Color
(188, 180, 9), new Color (214, 233, 12), new Color (194, 188, 10), new Color
(156, 191, 10), new Color (161, 217, 11), new Color (188, 242, 78), new Color
(112, 230, 11), new Color (114, 191, 10), new Color (107, 253, 13), new Color
(52, 250, 13), new Color (67, 213, 11), new Color (68, 212, 11), new Color
(12, 235, 15), new Color (15, 224, 11), new Color (12, 238, 18), new Color
(9, 183, 25), new Color (12, 243, 74), new Color (10, 202, 39), new Color
(10, 191, 73), new Color (12, 239, 124), new Color (11, 224, 105), new Color
(12, 236, 71), new Color (9, 181, 103), new Color (13, 253, 196), new Color
(12, 237, 224), new Color (10, 205, 176), new Color (9, 190, 154), new Color
(10, 142, 190), new Color (12, 145, 244), new Color (12, 145, 240), new Color
(10, 99, 209), new Color (11, 96, 225), new Color (10, 99, 207), new Color
(10, 53, 209), new Color (12, 40, 239), new Color (11, 51, 215), new Color
(22, 11, 222), new Color (9, 19, 186) , new Color(76, 48, 195), new Color
(142, 12, 240), new Color (111, 70, 24), new Color (175, 10, 95), new Color
(159, 9, 83), new Color (233, 12, 180), new Color (244, 12, 180), new Color
(189, 9, 128), new Color (237, 12, 128), new Color (216, 10, 146), new Color
(255, 13, 69), new Color (184, 9, 87), new Color (186, 7, 72), new Color
(235, 12, 25), new Color (220, 11, 33), new Color (221, 38, 11)
};

    // display state of canvas
    enum State {OVERALL, DETAIL};
    public static State state;

    // basic window dimensions
    int centerY = 400, width = 4224-96 + 128 /*width of all intervals minus half of the last one plus 32 for ranking space*/, height = 2 * centerY;

    // visualization dimensions
    int startY = 64; // baseline of drawing
    int barHeight = 24; // height of a single book's bar
    int intervalWidth = 192; // width of time interval
    int whiteSpaceHeight = 4; // height of white space between bars
    int rankingWidth = 64;

    // book names display
    Book currentBook;
    String bookName;
    Color bookColour;

    // array of lists containing book data over time
    ArrayList<LinkedList<Book.BookStats>> buckets;

    // overlay interface
    public JLabel bookNameLabel;

    public VisualizationCanvas()
    {
        super();
        state = State.OVERALL;
        bookName = "";
        bookColour = null;
        currentBook = null;
        bookNameLabel = null;
        setBackground(Color.WHITE);
        setSize(new Dimension(width, height));
        buckets = new ArrayList<LinkedList<Book.BookStats>>();
        for (int i = 0; i < 22; i++)
            buckets.add(new LinkedList<Book.BookStats>());
        readSalesData();
        
        /*String s = null;
        Process p = Runtime.getRuntime().exec("pwd");
        BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }*/
    }

    public void readSalesData()
    {
        for (int i = 0 ; i < 22; i++)
        {
            try
            {
                BufferedReader input = new BufferedReader (new FileReader("src/visualization/Week " + (i+1) + ".txt"));
                            
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
                        for (int j = 0; j < i; j++)
                        {
                            ListIterator<Book.BookStats> it = buckets.get(j).listIterator();
                            while (it.hasNext())
                            {
                                Book.BookStats current = it.next();
                                if (current.owner.title.equals(name))
                                {
                                    owner = current.owner;
                                    break;
                                }
                            }
                            if (owner != null)
                                break;
                        }
                    }
                    if (owner == null)
                    {
                        owner = new Book(name, author, publisher, description, price, 0);
                        
                        // calculate colour
                        owner.colour = colourSelection[i*3+(ranking-1)%3];
                    }

                    // add book stats to bucket
                    Book.BookStats bookStats = owner.addStats(i+1, 2011, 0, ranking);
                    buckets.get(i).add(bookStats);
                    ranking++;
                    line = input.readLine();
                    input.readLine();
                    //System.out.println (owner);
                }
            }
            catch (IOException e)
            {
                System.out.println(e);
                break;
            }
        }
    }

    @Override
    public void paint(Graphics g)
    {
        g.setFont(new Font("DejaVu Sans", Font.BOLD, 14));

        // drawing the rank numbers
        for (int i = 0; i < 16; i++)
        {
            int currentY = height-startY-(15 - i) * (barHeight + whiteSpaceHeight);
            String rank = i+1+"";
            if (i <9)
                rank = " " + rank;
            g.setColor(Color.LIGHT_GRAY);
            g.drawOval(16-4, currentY-1, 26, 26);
            g.drawLine(16-3+26, currentY+12, 16-3+26+24, currentY+12);
            g.drawLine(16-3+26, currentY+13, 16-3+26+24, currentY+13);
            g.drawLine(16-3+26, currentY+14, 16-3+26+24, currentY+14);
            g.drawOval(width-40-4, currentY-1, 26, 26);
            g.drawLine(width-40-4, currentY+12, width-40-4-24, currentY+12);
            g.drawLine(width-40-4, currentY+13, width-40-4-24, currentY+13);
            g.drawLine(width-40-4, currentY+14, width-40-4-24, currentY+14);
            g.setColor(Color.BLACK);
            g.drawString(rank,16, currentY + 17);
            g.drawString(rank,width-40, currentY+17);
        }
        g.drawString("Rank", 12, 307);
        g.drawString("Rank", width-52, 307);

        // drawing the segments
        for (int i = 0; i < 22; i++)
        {
            LinkedList<Book.BookStats> currentBucket = buckets.get(i);
            int currentX = rankingWidth+i * intervalWidth;
            /*g.setColor(new Color(224,224,224));
            int beginY = height-startY-15 * (barHeight + whiteSpaceHeight);
            int endY = height-startY + barHeight;
            g.fillRect(currentX-4, beginY-4, intervalWidth/2+8, endY -startY + 8);*/
            Iterator<Book.BookStats> it = currentBucket.listIterator();
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            while (it.hasNext())
            {
                // draw basic rectangles
                Book.BookStats stat = it.next();
                int currentY = height-startY-(16 - stat.ranking) * (barHeight + whiteSpaceHeight);
                if (state == State.OVERALL || stat.owner == currentBook)
                    g.setColor(stat.owner.colour);
                else
                    g.setColor(new Color(224,224,224));
                g.fillRect(currentX, currentY, intervalWidth/2, barHeight);

                               
                // draw connecting polygon
                if (stat.next != null && stat.week+1 == stat.next.week)
                {
                    int nextY = height-startY-(16 - stat.next.ranking) * (barHeight + whiteSpaceHeight);
                    int y [] = {currentY, nextY, nextY+barHeight, currentY+barHeight};
                    int x [] = {currentX + intervalWidth/2, currentX + intervalWidth, currentX + intervalWidth, currentX + intervalWidth/2};
                    g.fillPolygon(x, y, 4);
                }
                if (stat.isFirst && (state == State.OVERALL || currentBook == stat.owner))
                {
                    
                    //g.setColor(Color.black);
                    //g.fillRect(currentX, currentY, (int)(intervalWidth/2 * stat.owner.moodIndex), 3);
                    g.setColor(Color.WHITE);
                    if (stat.owner.title.length() < 10)
                        g.drawString(stat.owner.title, currentX + 4, currentY+16);
                    else
                        g.drawString(stat.owner.title.substring(0,10) + "...", currentX + 4, currentY+16);
                }
            }
            g.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
            g.setColor(Color.BLACK);
            g.drawString("   Week " + (i + 1), currentX, height-20);

            // draw name label
            if (bookColour != null)
            {
                int xOffset = (int)((ScrollPane)(this.getParent())).getScrollPosition().getX() + 720-256;
                g.clearRect(0, height, width, 36);
                g.setColor(Color.GRAY);
                g.fillRect(xOffset+4,height+4,512,32);
                g.setColor(bookColour);
                g.fillRect(xOffset, height, 512, 32);
                g.setColor(Color.WHITE);
                g.drawString(bookName, xOffset+9, height+9+12);
                if (state == state.DETAIL)
                {
                    
                }
            }
            else
            {
                g.clearRect(0, height, width, 36);
            }
        }
    }

    public void mouseDragged(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    
    public void mouseClicked(MouseEvent e)
    {
        if (state == State.OVERALL)
        {
            if (currentBook != null)
            {
                state = State.DETAIL;
                this.paint(this.getGraphics());
            }
        }
        else
        {
            state = State.OVERALL;
            this.paint(this.getGraphics());
        }
    }

    public void mouseMoved(MouseEvent e)
    {
        if (state == State.OVERALL)
        {
            if (e.getX() > rankingWidth && e.getX() < width - rankingWidth && (e.getX()-rankingWidth)%intervalWidth <= intervalWidth/2)
            {
                int bucket = (e.getX()-rankingWidth)/intervalWidth;
                int beginY = height-startY-15 * (barHeight + whiteSpaceHeight);
                int numEntries = buckets.get(bucket).size();
                int endY = height-startY - (16 - numEntries) * (barHeight+whiteSpaceHeight) + barHeight;
                if (e.getY() >= beginY && e.getY() <= endY)
                {
                    int index = (e.getY() - beginY)/(barHeight+whiteSpaceHeight);
                    Book.BookStats stat = buckets.get(bucket).get(index);
                    if (!bookName.equals(stat.owner.title + " by " + stat.owner.author))
                    {
                        currentBook = stat.owner;
                        bookName = stat.owner.title + " by " + stat.owner.author;
                        bookColour = stat.owner.colour;
                        this.paint(this.getGraphics());
                    }
                }
                else if (bookColour != null)
                {
                    bookName = "";
                    bookColour = null;
                    currentBook = null;
                    this.paint(this.getGraphics());
                }
            }
            else if (bookColour != null)
            {
                bookName = "";
                bookColour = null;
                currentBook = null;
                this.paint(this.getGraphics());
            }
        }
    }

    public void adjustmentValueChanged (AdjustmentEvent e)
    {
        this.paint(this.getGraphics());
    }
}
