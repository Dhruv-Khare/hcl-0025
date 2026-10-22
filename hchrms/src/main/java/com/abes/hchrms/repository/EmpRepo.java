package com.abes.hchrms.repository;

import com.abes.hchrms.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpRepo {
    String addEmp(Employee employee);
    List<Employee> getEmp();
    Employee getEmpByID(long emp_id);
    String deleteEmpById(long emp_id);
    String updateEmp(long emp_id, Employee updatedEmployee);
}