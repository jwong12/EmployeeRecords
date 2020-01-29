package com.github.jwong12.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.jwong12.exceptions.ApplicationException;

import com.github.jwong12.data.Employee;

/**
 * EmployeeDao Class
 */
public class EmployeeDao extends Dao {
	public static final String TABLE_NAME = DbConstants.EMPLOYEE_TABLE_NAME;
	private static final Logger LOG = LogManager.getLogger(EmployeeDao.class);

	/**
	 * Default constructor
	 * 
	 * @param database as a Database object
	 * @throws ApplicationException
	 */
	public EmployeeDao(Database database) throws ApplicationException {
		super(database, TABLE_NAME);
	}

	/**
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	  public void load() throws ApplicationException { 
		  try {
			  this.database.getConnection();
		  
			  if (!this.database.tableExists(EmployeeDao.TABLE_NAME)) {
				  create(); 
			  }
		  
		  } catch (SQLException e) { 
			  
			  throw new ApplicationException(e); 
			  
		  }
	  }
		 

	/**
	 * Creates a employee table in the Database.
	 */
	@Override
	public void create() throws SQLException {
		LOG.info("Creating table " + TABLE_NAME);
		String sql = String.format("create table %s(" + //
				"%s VARCHAR(9), " + // id
				"%s VARCHAR(250), " + // first
				"%s VARCHAR(250), " + // last
				"%s DATE, " + // DateOfBirth
				"primary key (employeeID) )", //
				TABLE_NAME, //
				EmployeeFields.EMPLOYEE_ID.getName(), //
				EmployeeFields.FIRST_NAME.getName(), //
				EmployeeFields.LAST_NAME.getName(), //
				EmployeeFields.DATE.getName()); //
		super.create(sql);
	}

	/**
	 * Adds a employee to the DB.
	 * 
	 * @param employee
	 * @throws SQLException
	 */
	public void add(Employee employee) throws SQLException {
		LOG.debug(employee);
		String sql = String.format("insert into %s values(" // 1 tableName
				+ "'%s', " // 2 EmployeeID
				+ "'%s', " // 3 FirstName
				+ "'%s', " // 4 LastName
				+ "'%s')", // 5 DateOfBirth
				TABLE_NAME, // 1
				employee.getEmployeeID(), // 2
				employee.getFirstName(), // 3
				employee.getLastName(), // 4
				(employee.getDateOfBirth() != null ? Date.valueOf(employee.getDateOfBirth()) : null)); // 10
		super.add(sql);
		LOG.info("Employee with ID '" + employee.getEmployeeID() + "' was added to " + TABLE_NAME);
	}

	/**
	 * Version #1: Delete a employee by employeeID.
	 * 
	 * @param employeeID
	 * @throws SQLException
	 * @throws ApplicationException 
	 */
	public void delete(String employeeID) throws SQLException, ApplicationException {
		List<String> employeeIDs = new ArrayList<String>();
		employeeIDs = getEmployeeIDs();

		if (employeeIDs.contains(employeeID)) {
			String sql = String.format("DELETE from %s WHERE %s='%s'", TABLE_NAME, EmployeeFields.EMPLOYEE_ID.getName(),
					employeeID);
			super.delete(sql);
			LOG.info("Employee with ID '" + employeeID + "' was removed from " + TABLE_NAME);
		} else {
			LOG.info("There are no matches for this employee ID.");
			throw new ApplicationException( "Employee ID does not exist." );
		}
	}

	/**
	 * Retrieve all the employee IDs from the database
	 * 
	 * @return the list of employee IDs
	 * @throws SQLException
	 */
	public List<String> getEmployeeIDs() throws SQLException {
		List<String> ids = new ArrayList<String>();
		String selectString = String.format("SELECT %s FROM %s", EmployeeFields.EMPLOYEE_ID.getName(), TABLE_NAME);
		LOG.debug(selectString);

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Connection connection = this.database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectString);

			while (resultSet.next()) {
				ids.add(resultSet.getString(EmployeeFields.EMPLOYEE_ID.getName()));
			}

		} finally {
			close(statement);
		}
		LOG.debug(ids.toString());
		return ids;
	}

	/**
	 * Get an employee from the database employee's table.
	 * 
	 * @param employeeID
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public Employee getEmployee(String employeeID) throws ApplicationException {
		String sqlString = String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_NAME,
				EmployeeFields.EMPLOYEE_ID.getName(), employeeID);
		LOG.debug(sqlString);

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Connection connection = this.database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlString);

			int count = 0;
			while (resultSet.next()) {
				count++;
				if (count > 1) {
					throw new ApplicationException(String.format("Expected one result, got %d", count));
				}
				String id = resultSet.getString(EmployeeFields.EMPLOYEE_ID.getName());
				String firstName = resultSet.getString(EmployeeFields.FIRST_NAME.getName());
				String lastName = resultSet.getString(EmployeeFields.LAST_NAME.getName());
				Date date = resultSet.getDate(EmployeeFields.DATE.getName());
				LocalDate dateOfBirth = date.toLocalDate();

				Employee employee = new Employee(id, firstName, lastName, dateOfBirth);

				LOG.debug(employee);
				return employee;
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());

		} finally {
			close(statement);
		}

		return null;
	}

	/**
	 * Fields enum class for the employee table in the Database.
	 */
	public enum EmployeeFields {
		EMPLOYEE_ID("ID", "VARCHAR", 9, 1), //
		FIRST_NAME("firstName", "VARCHAR", 250, 2), //
		LAST_NAME("lastName", "VARCHAR", 250, 3), //
		DATE("dob", "DATE", -1, 4); //

		private final String name;
		private final String type;
		private final int length;
		private final int column;

		EmployeeFields(String name, String type, int length, int column) {
			this.name = name;
			this.type = type;
			this.length = length;
			this.column = column;
		}

		public String getType() {
			return type;
		}

		public String getName() {
			return name;
		}

		public int getLength() {
			return length;
		}

		public int getColumn() {
			return column;
		}
	}
}
