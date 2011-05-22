/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visualization;

import java.util.LinkedList;
import java.awt.geom.*;
/**
 *
 * @author Riz
 */

public class Book
{
    public String title;
    public String author;
    public String description;
    public String publisher;
    public float price;
    public float moodIndex;
    public LinkedList<String> words;
    public LinkedList<Integer> wordWeights;
    public LinkedList<BookStats> statistics;

    public Book(String title, String author, String publisher, String description, float price, int moodIndex)
    {
        words = new LinkedList<String>();
        wordWeights = new LinkedList<Integer>();
        statistics = new LinkedList<BookStats>();
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.publisher = publisher;
    }

    public BookStats addStats(int week, int year, int revenue, int ranking)
    {
        BookStats bookStats = new BookStats(week, year, revenue, ranking, this);
        statistics.add(bookStats);
        return bookStats;
    }

    public void addWords()
    {
        
    }

    public String toString()
    {
        return title + ": " + author + ", " + publisher + ", $" + price + ", " + description + ", " + statistics.size() + "\n";
    }

    public class BookStats
    {
        public int week;
        public int year;
        public int revenue;
        public int ranking;
        public Book owner;

        public BookStats(int week, int year, int revenue, int ranking, Book owner)
        {
            this.week = week;
            this.year = year;
            this.revenue = revenue;
            this.ranking = ranking;
            this.owner = owner;
        }
    }
}
