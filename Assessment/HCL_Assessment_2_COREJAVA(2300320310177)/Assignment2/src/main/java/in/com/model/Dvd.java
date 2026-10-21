package in.com.model;

public class Dvd extends LibraryItem{
    int durationMinutes;
    final int latefee= 5;
    private boolean available;
    public Dvd(int durationMinutes) {
        super();
        this.durationMinutes = durationMinutes;
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
        if (available) {
            available = false;
        }
    }

    @Override
    public void giveFeedback(String feedback) {
        feedBacks.add(feedback);
    }
}
