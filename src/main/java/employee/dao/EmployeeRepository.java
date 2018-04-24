package employee.dao;

import java.util.List;

import employee.domain.Employee;


public interface EmployeeRepository {

	
	
	Employee findById(long id);
	
	List<Employee> findByLastName( String lastName);
	void persist(Employee emp);
	void delete(Employee emp);
	void updateEmployee(Employee emp);
	
		
	  

	  
	  
}
