package com.abes.hrms.repository;

import org.springframework.stereotype.Component;

@Component
public class EmpRepoImplementation implements EmpRepo {
    @Override
    public double squareRoot(double d) {
        return Math.sqrt(d);
    }

    @Override
    public String empData() {
        return "Employee Data";
    }
    
    @Override
    public int add(int a ,int b){
        return a+b;
    }
    
    @Override
    public int greaterNumber(int a, int b){
        return Math.max(a, b);
    }
}