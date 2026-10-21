package in.demo.FieldDi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Address {
    public String getAddress() {
        return "Address: 123 Main St, City, Country";
    }
}