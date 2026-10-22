package com.abes.hchrms;

import com.abes.hchrms.entity.Employee;
import com.abes.hchrms.repository.EmpRepoImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpServiceImp {
    @Autowired
    private EmpRepoImp empRepo;
    
    public String addEmp(){
        Employee employee = new Employee(1, "John Doe", "Software Engineer", "Java", "john.doe@example.com");
        return empRepo.addEmp(employee);
    }
    
    public String addEmpBulk(List<Employee> employees){
        return empRepo.addEmpBulk(employees);
    }
    
    public List<Employee> getEmp(){
        return empRepo.getEmp();
    }
    
    public Employee getEmpByID(long emp_id){
        return empRepo.getEmpByID(emp_id);
    }
    
}