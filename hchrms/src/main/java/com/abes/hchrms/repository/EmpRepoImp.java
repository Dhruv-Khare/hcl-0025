package com.abes.hchrms.repository;

import com.abes.hchrms.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmpRepoImp implements EmpRepo {
    
    List<Employee> employees = new ArrayList<>();
    
    @Override
    public String addEmp(Employee employee) {
        employees.add(employee);
        return "Employee added successfully!";
    }

    @Override
    public List<Employee>  getEmp() {
        System.out.println("Retrieving all employees...");
        return employees.isEmpty() ? null : employees;
    }

    @Override
    public Employee getEmpByID(long emp_id) {
        return employees.stream()
                .filter(employee -> employee.getEmp_id() == emp_id)
                .findFirst()
                .orElse(null);
                
        // for (Employee employee : employees) {
        //     if (employee.getEmp_id() == emp_id) {
        //         return employee;
        //     }
        // }
        // return null; 
    }

    @Override
    public String deleteEmpById(long emp_id) {
        return "Employee deleted successfully!";
    }

    @Override
    public String updateEmp(long emp_id, Employee updatedEmployee) {
        return "Employee updated successfully!";
    }
}