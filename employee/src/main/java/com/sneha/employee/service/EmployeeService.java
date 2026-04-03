package com.sneha.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sneha.employee.dto.EmployeeDTO;
import com.sneha.employee.entity.Employee;
import com.sneha.employee.exception.ResourceNotFoundException;
import com.sneha.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;
    public Employee create(EmployeeDTO dto) {
    	Employee emp=new Employee();
    	emp.setName(dto.getName());
    	emp.setEmail(dto.getEmail());
    	return repo.save(emp);
    }
    public List<Employee> getAll() {
        return repo.findAll();
    }
    public Employee getById(Long id) {
        return repo.findById(id).orElseThrow(()->new
        		ResourceNotFoundException("Employee not found with id"+id));
    }
    public Employee update(Long id,EmployeeDTO dto) {
    	Employee emp=getById(id);
    	emp.setName(dto.getName());
    	emp.setEmail(dto.getEmail());
    	return repo.save(emp);
    }

    public void delete(Long id) {
        // ensure consistent behavior and proper ResourceNotFoundException handling
        Employee employee = getById(id);
        repo.delete(employee);
    }
}