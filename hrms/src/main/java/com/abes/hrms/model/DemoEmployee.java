package com.abes.hrms.model;

import com.abes.hrms.repository.EmpRepoImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DemoEmployee {
    
    @Autowired
    private EmpRepoImplementation empRepo;
    
    public int add(int a, int b) {
        return empRepo.add(a, b);
    }
    public double squareRoot() {
        return empRepo.squareRoot(190);
    }
    public String empData() {
        return empRepo.empData();
    }
    public int greaterNumber(int a, int b){
        return empRepo.greaterNumber(a, b);
    }
}