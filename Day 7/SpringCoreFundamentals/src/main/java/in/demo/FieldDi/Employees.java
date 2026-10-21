package in.demo.FieldDi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Employees {
    @Autowired
    private Address address;
    
    public String displayAddress(){
           return address.getAddress();
    }
}