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
    public String addEmpBulk(List<Employee> employees) {
        // this.employees.addAll(employees);
        List<Employee> empList =List.of(
            new Employee(1, "John Doe", "Software Engineer", "Java", "john.doe@example.com"),
            new Employee(2, "Jane Smith", "Data Scientist", "Python", "jane.smith@example.com"),
            new Employee(3, "Bob Johnson", "DevOps Engineer", "Docker", "bob.johnson@example.com")
        );
        this.employees.addAll(empList);
        return "Employees added successfully!";
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