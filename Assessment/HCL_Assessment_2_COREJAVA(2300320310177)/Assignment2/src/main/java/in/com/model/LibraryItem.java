package in.com.model;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class LibraryItem implements Borrowable{
    private static int id;
    private String Title;
    private Date year;
    private boolean available;
    List<String> feedBacks;
    public LibraryItem(String title, Date year, boolean available) {
        this.Title = title;
        //if (year.before() && year.after(01 / 01 / 1900)) throw new IllegalArgumentException();
        this.year = year;
        this.available = available;
        this.feedBacks = new ArrayList<>();
    }

    public LibraryItem() {

    }

    public abstract double lateFeee(int dayslate);

    public abstract void giveFeedback(String feedback);
}
