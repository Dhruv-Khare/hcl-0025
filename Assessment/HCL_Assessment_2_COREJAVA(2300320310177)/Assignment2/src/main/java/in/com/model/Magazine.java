package in.com.model;

import java.util.Date;

public class Magazine extends LibraryItem{
    private int issueNumber;
    final int lateFee = 1;
    private boolean available;

    public Magazine(String title, Date year, boolean available) {
        super(title, year, available);
    }

    @Override
    public double lateFeee(int dayslate) {
        return lateFee * dayslate;
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
        feedBacks.add(feedback);
    }
}
