package com.sneha.employee.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sneha.employee.entity.Employee;
public interface EmployeeRepository  extends JpaRepository<Employee,Long >{

}
