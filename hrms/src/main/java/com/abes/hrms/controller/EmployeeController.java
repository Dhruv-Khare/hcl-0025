package com.abes.hrms.controller;

import com.abes.hrms.model.DemoEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class EmployeeController {
    @Autowired
    private DemoEmployee demoEmployee;
    
    
    @GetMapping("/")
    public String Welcome() {
        return "<h2 style='color:red'>Welcome to the HRMS!</h2>";
    }
    
    @GetMapping("/add")
    public int sum() {
        System.out.println("Hello Dhruv it hits /add api");
        return demoEmployee.add(50, 100);
    }
    
    @GetMapping("/sqrt")
    public double squareRoot() {
        return demoEmployee.squareRoot();
    }
    
    @GetMapping("/empdata")
    public String empData() {
        return demoEmployee.empData();
    }
    
    @GetMapping("/gtn")
    public int greatenNumber(){
        return demoEmployee.greaterNumber(400, 600);
    }
    
}