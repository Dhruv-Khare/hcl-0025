package com.abes.hchrms.controller;

import com.abes.hchrms.EmpServiceImp;
import com.abes.hchrms.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmpController {
    
    @Autowired
    private EmpServiceImp empService;
    
    @GetMapping("/")
    public String welcomeMsg(){
        return "Welcome to HRMS Project!";
    }
    
    @GetMapping("/addEmployee")
    public String addNewEmployee(){
        return empService.addEmp();
    }
    
    @GetMapping("/addEmployeeBulk")
    public String addNewEmployeeBulk(){
        return empService.addEmpBulk(null);
    }
        
    @GetMapping("/getEmployee")
    public List<Employee> getEmp(){
        return empService.getEmp();
    }
    
    @GetMapping("/getEmployeeById/{emp_id}")
    public Employee getEmpByID(@PathVariable long emp_id){
        return empService.getEmpByID(emp_id);
    }
}