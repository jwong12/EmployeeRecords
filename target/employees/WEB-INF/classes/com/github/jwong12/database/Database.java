package com.github.jwong12.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.jwong12.exceptions.ApplicationException;

/**
 * Database Class
 */
public class Database {
	private static final Logger LOG = LogManager.getLogger(Database.class);

	private static Database theInstance = new Database();
	private final Properties properties;
	private static Connection connection;
	private EmployeeDao employeeDao;

	/**
	 * Default constructor
	 */
	private Database() {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = null;
		properties = new Properties();
		
		try {
			inputStream = classLoader.getResourceAsStream(DbConstants.DB_PROPERTIES_FILENAME);
			properties.load(inputStream);
			employeeDao = new EmployeeDao(this);
			
		} catch (IOException | ApplicationException e) {
			LOG.error(e.getMessage());
			
		} finally {
			if (inputStream != null) {
		        try {
		            inputStream.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
			classLoader = null;
		}
	}

	/**
	 * Get the instance of this Singleton class.
	 * 
	 * @return Database
	 */
	public static Database getTheInstance() {
		return theInstance;
	}

	/**
	 * Connects to the Database.
	 * 
	 * @return a connection object
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		if (connection != null) {
			return connection;
		}

		try {
			connect();

		} catch (ClassNotFoundException e) {
			LOG.error(e.getMessage());
		}

		return connection;
	}

	private void connect() throws SQLException, ClassNotFoundException {
		String url = "jdbc:sqlserver://" + properties.getProperty(DbConstants.DB_URL_KEY) + ";user=" + properties.getProperty(DbConstants.DB_USER_KEY) + ";password=" + properties.getProperty(DbConstants.DB_PASSWORD_KEY);
		connection = DriverManager.getConnection(url);
		LOG.info("Database connected.");
	}

	/**
	 * Disconnect from the Database.
	 */
	public void shutdown() {
		LOG.info("Shutting down Database.");
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the customerDao
	 */
	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}


	/**
	 * Checks if the table exists in the Database.
	 * 
	 * @param tableName
	 *            to be checked
	 * @return true if the table exists in the Database
	 * @throws SQLException
	 */
	public boolean tableExists(String tableName) throws SQLException {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet resultSet = null;
		String rsTableName = null;

		try {
			resultSet = databaseMetaData.getTables(connection.getCatalog(), "%", "%", null);
			while (resultSet.next()) {
				rsTableName = resultSet.getString("TABLE_NAME");
				if (rsTableName.equalsIgnoreCase(tableName)) {
					return true;
				}
			}
		} finally {
			resultSet.close();
		}

		return false;
	}
}
