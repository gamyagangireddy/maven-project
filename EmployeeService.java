package com.spring.springbootstarter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.AttributeNotFoundException;
import javax.management.ServiceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jca.cci.RecordTypeNotSupportedException;
import org.springframework.stereotype.Service;

import com.spring.springbootstarter.entity.EmployeeEntity;
import com.spring.springbootstarter.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository repository;
	
	public List<EmployeeEntity> getAllEmployees() {
		System.out.println("getAllEmployees");
		List<EmployeeEntity> result = (List<EmployeeEntity>) repository.findAll();
		
		if(result.size() > 0) {
			return result;
		}else {
			return new ArrayList<EmployeeEntity>();
		}
	}
	
public EmployeeEntity getEmployeeById(Long id) throws ServiceNotFoundException{
		System.out.println("gerEmployeeById");
		Optional<EmployeeEntity> employee = repository.findById(id);
		
		if(employee.isPresent()) {
			return employee.get();
			
		}else {
			throw new ServiceNotFoundException("no employee record exist");
		}
		
}
	public  EmployeeEntity createOrUpdateEmployee (EmployeeEntity entity)
	{
		System.out.println("createOrUpdateEmployee");
		if(entity.getId() == null) {
			entity = repository.save(entity);
			return entity;
		} else {
			Optional<EmployeeEntity> employee = repository.findById(entity.getId());
			
			if(employee.isPresent())
			{
				EmployeeEntity newEntity = employee.get();
			newEntity.setDesc(entity.getDesc());
			newEntity.setName(entity.getName());
			newEntity = repository.save(newEntity);
			return newEntity;
			}else {
				entity = repository.save(entity);
				
			}
		}
		return entity;
	}
	public void deleteEmployeeById(Long id)throws ServiceNotFoundException{
		System.out.println("deleteEmployeeById");
		Optional<EmployeeEntity> employee = repository.findById(id);
		
		if(employee.isPresent())
		{
			repository.deleteById(id);
		}else {
			throw new ServiceNotFoundException("no  employee record exist");
		}
	}
}
