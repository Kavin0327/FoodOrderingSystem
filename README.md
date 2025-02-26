# Online Food Ordering System
Java-based web application to manage an online food ordering system. This project enables users to place orders, manage restaurants, and perform CRUD operations using Java, JDBC, Servlets, Struts2, and a Tomcat server.

## Features
- User Authentication (Customer, Manager, Admin)
- View Restaurants and Food Items
- Add Food Items to Cart
- Place Orders
- Manage Restaurants (Admin)
- Manage Food Items (Manager)

## Prerequisites
* Java 8 or latest version
* Apache Tomcat Server
* Any SQL Database
* JDBC Driver for your SQL Database
* Struts2 Framework

## Installation
### Clone the repository
Change your path location and type:
```bash
git clone https://github.com/Kavin0327/OnlineFoodOrdering_JavaProject.git
```

### Configuration of database connection
Modify the database properties in `db-config.properties`:
```
jdbc.url=jdbc:mysql://localhost:port_no/db_name
jdbc.username=your_username
jdbc.password=your_password
```

### Deployment
1. Place the project folder in the `webapps` directory of Apache Tomcat.
2. Start the Tomcat server.
3. Access the application at:
```
http://localhost:8080/OnlineFoodOrderingSystem
```

