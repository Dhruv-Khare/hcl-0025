package in.demo.ConstructorDI;


public class Employee {

    private Address address;
    public Employee(Address address) {
        this.address = address;
    }

    public void getAddress(Address address) {
        address.getAddress();
    }
}
