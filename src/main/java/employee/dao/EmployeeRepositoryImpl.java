 package employee.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Parameter;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import employee.domain.Employee;


public class EmployeeRepositoryImpl implements EmployeeRepository {
	
	public EmployeeRepositoryImpl() {}
	
	//should always execute
	@Override
	public void finalize() {
		closeCurrentSession();
		
		try {
		super.finalize();
		}
		catch(Throwable t) {}
	}
	
	//Session connection details
	private final Session currentSession=getSessionFactory().openSession();
	private Transaction currentTransaction;
	
	public Session openCurrentSession() {
		//currentSession = this.currentSession;
		return currentSession;
	}

	public Session openCurrentSessionwithTransaction() {
//		currentSession = getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		currentTransaction.commit();
		return currentSession;
		
	}
	
	public void closeCurrentSession() {
		currentSession.close();
	}
	
	
	private static SessionFactory getSessionFactory() {
			StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
				 .configure( "hibernate.cfg.xml" )
				 .build();
				 
				 Metadata metadata = new MetadataSources( standardRegistry )
				 .addAnnotatedClass(Employee.class ) 
				 // You can add more entity classes here like above
				 .addResource( "hibernate.cfg.xml" )
				 .getMetadataBuilder()
				 .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE )
				 .build();
				 
				 SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
				 return sessionFactory;
	}

	public Session getCurrentSession() {
		return currentSession;
	}


	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}
	
	
	
	
	
	//Overriding methods	
	public Employee findById(long id) {
			this.openCurrentSession();
			Employee employee = (Employee) getCurrentSession().get(Employee.class, id);
			//this.closeCurrentSession();
			return employee;
	}

	public List<Employee> findByLastName( String lastName) {
		this.openCurrentSession();
		List<Employee> employee = (List<Employee>) getCurrentSession().createQuery("from Employee e where e.lastName=:lastName").setString("lastName", lastName).list();
		//this.closeCurrentSession();
		return employee;
	}

		
	public void updateEmployee(Employee emp) {
		this.openCurrentSessionwithTransaction();
		getCurrentSession().update(emp);
		//this.closeCurrentSessionwithTransaction();
	}
	public void delete(Employee emp) {
		this.openCurrentSessionwithTransaction();
		getCurrentSession().delete(emp);
		//this.closeCurrentSessionwithTransaction();
	}

	public void persist(Employee emp) {
		this.openCurrentSessionwithTransaction();
		getCurrentSession().save(emp);
		//this.closeCurrentSessionwithTransaction();
	}

	
}
