package in.com.model;

import java.util.List;

public class Member {
    private int id;
    private String name;
    private String email;
    List<LibraryItem> items;

    public Member(int id, String name, String email) {
        this.id = id;
        this.name = name;
        if (!email.endsWith("@")) throw new IllegalArgumentException("Invalid email address");
        this.email = email;
    }
}
