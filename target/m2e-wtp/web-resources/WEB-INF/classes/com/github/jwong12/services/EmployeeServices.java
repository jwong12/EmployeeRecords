package com.github.jwong12.services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.jwong12.data.Employee;
import com.github.jwong12.database.Database;
import com.github.jwong12.database.EmployeeDao;
import com.github.jwong12.exceptions.ApplicationException;

/**
 * A Service class for the controller (MainServlet)
 */
public class EmployeeServices {
	private Database database;
	private EmployeeDao employeeDao;
	
	/**
	 * Constructor
	 * @throws ApplicationException
	 */
	public EmployeeServices() throws ApplicationException {
		if(database == null) {
			database = Database.getTheInstance();
		}
		employeeDao = new EmployeeDao(database);
	}
	
	/**
	 * Add Employees to the DB.
	 * @param id
	 * @param first
	 * @param last
	 * @param date
	 * @throws SQLException
	 * @throws ApplicationException
	 */
	public void addEmployee(String id, String first, String last, String date) throws SQLException, ApplicationException { // date YYYY/MM/DD
		if(id.length() == 9 && id.charAt(0) == 'A' && id.charAt(1) == '0') {
			for(int i = 2; i < id.length(); i++) {
				if(!Character.isDigit(id.charAt(i))) {
					throw new ApplicationException("Invalid employee ID");
				}
			}
			
			Pattern pattern = Pattern.compile("^\\p{Alpha}+$");
			Matcher matcher = pattern.matcher(first);
			
			if(!matcher.matches()) {
				throw new ApplicationException("Invalid employee first name");
			}
			
			matcher = pattern.matcher(last);
			
			if(!matcher.matches()) {
				throw new ApplicationException("Invalid employee last name");
			}
			
		} else {
			throw new ApplicationException("Invalid employee ID");
		}
		
		if(date == null || date.trim().length() == 0) {
			date = "1900/01/01";
		} 
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");
		LocalDate localDate = LocalDate.parse(date, formatter);	
		Employee employee = new Employee(id, first, last, localDate);
		employeeDao.add(employee);
	}
	
	/**
	 * Search for employees by their ID.
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws ApplicationException
	 */
	public String findEmployees(String id) throws SQLException, ApplicationException {
		List<String> employeeIDs = new ArrayList<String>();
		employeeIDs = employeeDao.getEmployeeIDs();
		
		if (employeeIDs.contains(id)) {
			Employee employee = employeeDao.getEmployee(id);
			return "Found " + employee.getFirstName() + " " + employee.getLastName();
		}
		
		return null;
	}
	
	/**
	 * Remove Employees by their ID.
	 * @param id
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	public void removeEmployee(String id) throws ApplicationException, SQLException {
		if(id.length() == 9 && id.charAt(0) == 'A' && id.charAt(1) == '0') {
			for(int i = 2; i < id.length(); i++) {
				if(!Character.isDigit(id.charAt(i))) {
					throw new ApplicationException("Invalid employee ID");
				}
			}
		} else {
			throw new ApplicationException("Invalid employee ID");
		}
		
		employeeDao.delete(id);
	}
	
	/**
	 * Gets a list of employees from the DB.
	 * @return
	 * @throws SQLException
	 * @throws ApplicationException
	 */
	public List<Employee> getEmployeeList() throws SQLException, ApplicationException {
		List<Employee> employeeList = new ArrayList<Employee>();
		List<String> employeeIDs = new ArrayList<String>();
		employeeIDs = employeeDao.getEmployeeIDs();
		
		for (String id : employeeIDs) {
			Employee employee = employeeDao.getEmployee(id);
			employeeList.add(employee);
		}
		
		return employeeList;
	}
	
}

