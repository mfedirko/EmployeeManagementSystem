package employee.service;

import java.util.List;

import employee.dao.EmployeeRepository;
import employee.dao.EmployeeRepositoryImpl;
import employee.domain.Employee;

public class EmployeeService {
	private static EmployeeRepository empRepo;
	
	public EmployeeService() {
		empRepo=new EmployeeRepositoryImpl();
	}
	
	public void createEmployee(Employee emp) {
		
		empRepo.persist(emp);
	
	}
	
	public void update(Employee emp) {
		try {
		empRepo.updateEmployee(emp);
		System.out.println(emp);
	}
	catch(Exception e) {
		System.out.println("Employee not found with id: "+emp.getId());
	}
	
	}
	
	public Employee findById(long id) {
		
		Employee e = empRepo.findById(id);
		
		return e;
	}
	
	public List<Employee> findByLastName(String lastName) {
		
		List<Employee> e=empRepo.findByLastName(lastName);
		
		return e;
	}
	public void deleteEmployeeById(long id) {
		try {
		empRepo.delete(findById(id));
		}
		catch(Exception e) {
			System.out.println("Employee not found with id: "+id);
		}
		
	}
	
	
	
}
