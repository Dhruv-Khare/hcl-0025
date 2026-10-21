package in.demo.FieldDi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Employees employee = context.getBean(Employees.class);
        System.out.println(employee.displayAddress());
        context.close();
    }
}