# Company Portal API

A comprehensive Spring Boot REST API application for managing company operations including employees, departments, projects, and tasks.

## Project Overview

The **Company Portal** is an enterprise resource management system designed to streamline company operations. It provides a robust backend API for managing organizational hierarchy, employee information, departmental structures, project assignments, and task tracking with role-based access control.

## Purpose

This application serves as a central hub for:
- **Employee Management**: Create, update, and manage employee profiles with roles and permissions
- **Department Organization**: Organize employees into departments with proper hierarchy
- **Project Management**: Track and manage company projects with team assignments
- **Task Management**: Create, assign, and track tasks within projects with priorities and status tracking
- **User Authentication**: Secure JWT-based authentication and authorization system
- **Role-Based Access Control**: Different permission levels for Admin, Manager, and Employee roles

## Key Features Implemented

✅ **User Authentication & Authorization**
- JWT (JSON Web Token) based authentication
- Custom user details service with Spring Security
- Role-based access control (Admin, Manager, Employee)
- Secure password handling

✅ **Department Management**
- Create and manage departments
- Admin-only department management endpoints
- Department listing for employees

✅ **Employee Management**
- Employee profile management (Create, Read, Update, Delete)
- Employee role assignment
- Admin and Manager access controls
- Department association

✅ **Project Management**
- Project creation and assignment
- Manager project oversight
- Project team member tracking
- Project listing by department

✅ **Task Management**
- Task creation and assignment within projects
- Task priority levels (High, Medium, Low)
- Task status tracking (To Do, In Progress, Completed)
- Task assignment to employees

✅ **Security & Configuration**
- CORS configuration for cross-origin requests
- JWT filter for request authentication
- Spring Security integration
- Input validation with validation annotations

## Technology Stack

| Component | Version |
|-----------|---------|
| **Java** | 17 |
| **Spring Boot** | 3.5.8 |
| **Spring Security** | Latest |
| **Spring Data JPA** | Latest |
| **MySQL** | 8.0+ |
| **JWT (JJWT)** | 0.11.5 |
| **Lombok** | Latest |
| **Maven** | 3.6+ |

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK) 17** or higher
  - Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/#java17)
  
- **Apache Maven 3.6.0** or higher
  - Download from [Apache Maven](https://maven.apache.org/download.cgi)
  - Or use `mvnw.cmd` (included in the project)

- **MySQL Server 8.0** or higher
  - Download from [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)

- **Git** (optional, for version control)
  - Download from [Git](https://git-scm.com/downloads)

## Installation & Setup

### 1. Clone or Extract the Project
```bash
cd E:\Projects\company-portal\company-portal
```

### 2. Create the Database
```sql
-- Connect to MySQL
mysql -u root -p

-- Create the database
CREATE DATABASE company_portal;
USE company_portal;
```

Alternatively, the application can auto-create tables using `spring.jpa.hibernate.ddl-auto=update` in `application.properties`.

### 3. Configure Database Connection
Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/company_portal
spring.datasource.username=root
spring.datasource.password=root
```

Update these credentials to match your MySQL installation.

### 4. Build the Project
Using Maven wrapper (Windows):
```bash
mvnw.cmd clean install
```

Or using Maven directly:
```bash
mvn clean install
```

## Running the Application

### Using Maven
```bash
mvnw.cmd spring-boot:run
```

Or:
```bash
mvn spring-boot:run
```

### Using Java JAR
```bash
java -jar target/company-portal-0.0.1-SNAPSHOT.jar
```

### Using IDE (IntelliJ IDEA)
1. Open the project in IntelliJ IDEA
2. Navigate to `CompanyPortalApplication.java`
3. Click the Run button (green play icon) or press `Shift + F10`

The application will start on **http://localhost:8080**

## API Endpoints Overview

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and receive JWT token

### Users
- `GET /api/users` - Get current user profile
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user profile

### Employees
- `GET /api/employees` - List all employees (paginated)
- `GET /api/employees/{id}` - Get employee details
- `POST /api/admin/employees` - Create new employee (Admin only)
- `PUT /api/admin/employees/{id}` - Update employee (Admin only)
- `DELETE /api/admin/employees/{id}` - Delete employee (Admin only)

### Departments
- `GET /api/departments` - List all departments
- `GET /api/departments/{id}` - Get department details
- `POST /api/admin/departments` - Create department (Admin only)
- `PUT /api/admin/departments/{id}` - Update department (Admin only)
- `DELETE /api/admin/departments/{id}` - Delete department (Admin only)

### Projects
- `GET /api/projects` - List all projects
- `GET /api/projects/{id}` - Get project details
- `POST /api/projects` - Create new project
- `PUT /api/projects/{id}` - Update project
- `GET /api/manager/projects` - List manager's projects (Manager only)

### Tasks
- `GET /api/tasks` - List all tasks (paginated)
- `GET /api/tasks/{id}` - Get task details
- `POST /api/tasks` - Create new task
- `PUT /api/tasks/{id}` - Update task
- `DELETE /api/tasks/{id}` - Delete task

### Health Check
- `GET /api/ping` - Check API health status

## Project Structure

```
company-portal/
├── src/
│   ├── main/
│   │   ├── java/com/company/company_portal/
│   │   │   ├── config/              # Configuration classes (JWT, Security, CORS)
│   │   │   ├── controller/          # REST API endpoints
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── entity/              # JPA entities (Department, Employee, etc.)
│   │   │   ├── exception/           # Custom exceptions
│   │   │   ├── repository/          # Spring Data JPA repositories
│   │   │   ├── service/             # Business logic layer
│   │   │   └── CompanyPortalApplication.java  # Main application class
│   │   └── resources/
│   │       ├── application.properties  # Application configuration
│   │       ├── static/              # Static web resources
│   │       └── templates/           # HTML templates
│   └── test/
│       └── java/com/company/company_portal/  # Unit tests
├── pom.xml                          # Maven dependencies and build configuration
├── mvnw.cmd & mvnw                 # Maven wrapper scripts
└── README.md                        # This file
```

## Database Schema

The application manages the following entities:

- **Users**: Application users with authentication credentials and roles
- **Employees**: Employee profiles linked to users and departments
- **Departments**: Organizational departments
- **Projects**: Company projects with team assignments
- **Tasks**: Project tasks with priority and status tracking
- **Roles**: User roles (ADMIN, MANAGER, EMPLOYEE)

## Configuration Details

### JWT Configuration
```properties
jwt.secret=ThisIsASuperSecureJwtSecretKeyForHS256Algorithm12345
jwt.expiration=86400000  # 24 hours in milliseconds
```

### Server Configuration
```properties
server.port=8080
```

### Database Configuration
```properties
spring.jpa.hibernate.ddl-auto=update      # Auto-create/update tables
spring.jpa.show-sql=true                  # Log SQL queries
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

## Security Features

✅ **JWT Authentication**: Token-based stateless authentication  
✅ **Role-Based Access Control**: Route protection based on user roles  
✅ **Input Validation**: Request validation with JSR-380 annotations  
✅ **CORS Configuration**: Controlled cross-origin resource sharing  
✅ **Password Security**: Secure password handling with Spring Security  

## Important Notes

**⚠️ Note on Package Name**: The original package name `com.company.company-portal` is invalid in Java. This project uses `com.company.company_portal` instead (underscores are used in package names).

**⚠️ Security**: 
- The JWT secret key and database credentials in `application.properties` are for development only
- For production, use environment variables or external configuration management
- Change default credentials immediately

## Troubleshooting

### Database Connection Issues
- Ensure MySQL is running: `mysql -u root -p`
- Verify credentials in `application.properties`
- Check that `company_portal` database exists

### Build Failures
- Clear Maven cache: `mvn clean install`
- Ensure Java 17 is installed: `java -version`
- Update Maven: `mvn -version`

### Port Already in Use
- Change port in `application.properties`: `server.port=8081`
- Or kill the process using port 8080

## Reference Documentation

- [Official Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/)
- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/reference/)
- [JWT (JJWT) Documentation](https://github.com/jwtk/jjwt)
- [Apache Maven Documentation](https://maven.apache.org/guides/index.html)
- [MySQL Documentation](https://dev.mysql.com/doc/)

## Development Guides

- [Building a RESTful Web Service with Spring](https://spring.io/guides/gs/rest-service/)
- [Securing a Web Application with Spring](https://spring.io/guides/gs/securing-web/)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Accessing Data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

## Future Enhancements

- [ ] Add more comprehensive error handling and logging
- [ ] Implement pagination and filtering for all list endpoints
- [ ] Add email notifications for task assignments
- [ ] Implement audit logging for data changes
- [ ] Add dashboard and analytics features
- [ ] Integrate with frontend application
- [ ] Add API documentation with Swagger/OpenAPI

## Support & Contact

For issues, questions, or contributions, please reach out to the development team.

---

**Version**: 0.0.1-SNAPSHOT  
**Last Updated**: June 2026  
**License**: Open Source
