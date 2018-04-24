package endpoint;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import employee.domain.Employee;
import employee.service.EmployeeService;
import reporting.ReportFormatter;
import reporting.ReportFormatter.Delim;

public class EmployeeManagementEndpoint {
	static Scanner scanner= new Scanner(System.in);
	static EmployeeService empService = new EmployeeService();
	
	
	public EmployeeManagementEndpoint() {
		mainLoop();
	}
	

	private void mainLoop() {
		int choice=0;
		do {
			showMenu();
			choice=scanner.nextInt();
			if (choice >4 || choice <1) {
				System.out.println("Invalid choice "+choice);
				showMenu();
				
			}
			else {
				
				switch(choice) {
				case(1):
					updateEmployee();
					break;
					
				case(2):
					break;
				case(3):
					
						findEmployee();
					
					break;
				
				}
			}
			System.out.println();
			
		}
		while (choice != 4); 
		System.out.println("Quitting...");
		
	}
	
	private void showMenu() {
		System.out.println("Please enter an option below");
		System.out.println("(1) Update employee, (2) Create new Employee, (3) Find Employee, (4) Quit");
	}
	
	
	private void findEmployee()  {
		System.out.println("Please enter delimiter for report ("+Arrays.asList(Delim.values()).toString()+")");
		String d=scanner.next().toUpperCase();
		Delim del=Delim.COMMA;
		try{
			del=Delim.valueOf(d);
		}
		catch(IllegalArgumentException e) {
			System.out.println("Invalid choice: "+d);
			System.out.println("Defaulted to COMMA separated");
		}
		System.out.println("Please enter Employee id or last Name");
		scanner.nextLine();
		String p=scanner.nextLine();
		List<Employee> e;
		if (p.matches("[\\s]*[0-9][0-9]*[\\s]*[\n]*")) {
				e=Arrays.asList(empService.findById(Long.parseLong(p)));
		}
		else {
			e=empService.findByLastName(p);
		}
			
			
			if (!e.isEmpty()) {
				try {
					ReportFormatter<Employee> rf;
					String output=(rf=new ReportFormatter<Employee>(del)).formatAsDelimitedFile(e);
					System.out.println(output);
					System.out.println("Export output? (y or n)");
					if (scanner.next().toLowerCase().charAt(0)=='y') {
						System.out.println("Please enter filename for export");
						String filename=scanner.next();
						rf.export(filename,output);
						System.out.println("Exported results to file: "+new File(filename).getAbsolutePath());	
						}
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return;
			}
		
		System.out.println("Employee not found: "+p);
	}
	
	private void updateEmployee() {
		System.out.println("Please enter Employee id");
		long id=scanner.nextLong();
		Employee e=empService.findById(id);
		if(e== null) {
			System.out.println("Employee not found with id "+id);
		}
		else {
			String lastName=e.getLastName();
			String firstName=e.getFirstName();
			int age=e.getAge();
			String title=e.getTitle();
			int salary=e.getSalary();
			
			System.out.println(e);
			System.out.println("Last name (current: "+e.getLastName()+") or press enter to keep existing");
			scanner.nextLine();
			lastName=scanner.nextLine();
			System.out.println("First name (current: "+e.getFirstName()+") or press enter to keep existing");
			scanner.nextLine();
			firstName=scanner.nextLine();
			System.out.println("Age (current: "+e.getAge()+") or press -1 to keep existing");
			age=scanner.nextInt();
			
			System.out.println("Title (current: "+e.getTitle()+") or press enter to keep existing");
			scanner.nextLine();
			title=scanner.nextLine();
			
			System.out.println("Salary (current: "+e.getSalary()+") or press -1 to keep existing");
			salary=scanner.nextInt();
			
			if (!lastName.isEmpty())
				e.setLastName(lastName);
		
			if (!firstName.isEmpty())
				e.setFirstName(firstName);
			if (age > 0)
				e.setAge(age);
			if (!title.isEmpty())
				e.setTitle(title);
			
			if (salary > 0)
				e.setSalary(salary);
			empService.update(e);
		}
		
	}
	
}
