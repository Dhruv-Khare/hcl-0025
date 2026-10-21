package in.com.model;

import java.util.Date;

public class Book extends in.com.model.LibraryItem {
    private String author;
    private String isbn;
    private boolean available;
    final int latefee= 2;

    public Book(String title, Date year, boolean available) {
        super(title, year, available);
    }

    @Override
    public double lateFeee(int dayslate) {
        return latefee * dayslate;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void borrow() {
        if(isAvailable()){
            available = false;
        }
    }

    @Override
    public void giveFeedback(String feedback) {
        //feedBacks.add(feedBack);
    }
}
