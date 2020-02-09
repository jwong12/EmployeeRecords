# EmployeesJSP

A JavaServer Pages web application that uses HttpServlets, Java Beans and MySQL to manage employee records through a browser. Achieved high cohesion and loose coupling by applying the principles of Object Oriented design to the Java classes and using the MVC pattern to separate the business, presentation and controller layers. Achieved high level of separation between the database access class and the business class by using the DAO pattern.

## Getting Started

I've used Eclipse, JBoss Tools pluggin and JBoss WildFly to write this project. To see this project on your browser, use the employees.war file and run it with WildFly.

I will not be covering how to run this project in Eclipse because the process is very complicated and the compatibility of software versions is sometimes inconsistent. However, if you already have your environment set up, please feel free to run it in Eclipse .

### Prerequisites

JBoss WildFly (16.0.0 or newer version)

### Run Project on Windows

Download and install JBoss WildFly on your Windows.

Place the employees.war file in the standalone\deployments folder. 

Use the Command Prompt to navigate to your wildfly-16.0.0.Final\bin directory and run this command to start WildFly:
```
standalone.bat
```

Lastly, go to this URL to check out this project:
```
http://localhost:8080/employees/
```

### Run Project on Linux

For Linux users, please follow the instructions on this link:
https://community.i2b2.org/wiki/display/getstarted/2.4.2.3+Run+Wildfly+as+a+Linux+Service

### Author

* James Wong
