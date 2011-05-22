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
    public float moodIndex;
    public LinkedList<String> words;
    public LinkedList<Integer> wordWeights;
    public LinkedList<BookStats> statistics;

    public Book(String title, String author, int moodIndex)
    {
        words = new LinkedList<String>();
        wordWeights = new LinkedList<Integer>();
        statistics = new LinkedList<BookStats>();
        this.title = title;
        this.author = author;
    }

    public void addStats(int week, int year, int revenue, int ranking)
    {
        statistics.add(new BookStats(week, year, revenue, ranking, this));
    }

    public void addWords()
    {
        
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
