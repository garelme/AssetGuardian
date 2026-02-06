# 🛡️ AssetGuardian: Enterprise Asset Management System

**AssetGuardian** is a backend solution developed to manage their physical and digital assets, personnel assignment processes, and asset demands from end to end. The system is built on industry-standard **JWT Auth** and a comprehensive **Role-Based Access Control (RBAC)** architecture.

---

## 🚀 Technology Stack

<p align="left">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.5.6-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" />
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" />
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" />
</p>

* **Core:** Java 21 & Spring Boot 3.5.6
* **Data Layer:** Spring Data JPA & PostgreSQL
* **Security:** Spring Security & JSON Web Token (JWT)
* **Documentation:** SpringDoc OpenAPI (Swagger UI)
* **Containerization:** Docker & Docker Compose
* **Libraries:** Lombok, ModelMapper, JavaFaker

---

## ✨ Core Modules and Features

### 🔑 Security and Authentication (/api/v1/auth)
* Secure user registration and JWT-based stateless session management.
* Upon a successful login, the system generates a secure token that allows the user to perform authorized actions safely.

### 📦 Inventory and Asset Control (/api/v1/assets)
* Full lifecycle management of assets (Add, Update, Delete).
* **Batch Delete** support for high-volume data management.

### 📝 Demand and Allocation Mechanism (/api/v1/demands & /api/v1/allocation)
* Digital demand creation process for assets needed by personnel.
* Manager approval flow for demands and assigning (**Allocation**) assets to approved demands.
* Return tracking for assigned assets and monitoring of active inventory status.

### 📜 Assignment Records and Tracking (/api/v1/assignments)
* **Record Viewing:** Central listing of all assignment history and current assignments on the system.
* **Filtering:** Ability for managers and admins to search through history records by asset name.
* **Authorized Access:** Only users with **ADMIN** and **MANAGER** roles can access sensitive assignment data.

### ⚙️ User and System Settings (/api/v1/settings)
* User profile management and secure password update mechanism.
* **Profile Management:** Profile photo upload infrastructure for users with **Multipart File** support.
* **Admin Panel:** Dynamic updating of user roles via the system.

---

## 📦 Start the System

To run the project in any environment, simply follow these steps:

1. **Package the Project:**
   ```bash
   ./mvnw clean package
2. **Run with Docker:**
   ```bash
   docker-compose up --build
This will automatically start both the PostgreSQL database and the Spring Boot application.   

## 📖 API Usage
Once the system is running, you can interact with all API endpoints using the Swagger interface:

🔗 Swagger UI: http://localhost:8080/swagger-ui/index.html

---

## 🛠️ Technical Architecture Notes
* Data Security: All API endpoints are protected at the method level using @PreAuthorize annotations.
* Data Validation: Consistency is ensured at the entry layer using @Validated, @Positive, and @NotEmpty annotations.
* Performance: The DTO (Data Transfer Object) pattern is used throughout the project to optimize data transfer and prevent unnecessary information exposure.

## 📂 Project Structure

```text
AssetGuardian/
├── src/
│   ├── main/
│   │   ├── java/com/akif/assetguardian/
│   │   │   ├── config/        # Security and Bean configurations
│   │   │   ├── controller/    # REST API Endpoints
│   │   │   ├── service/       # Business logic implementation
│   │   │   ├── repository/    # Database access layer (Spring Data JPA)
│   │   │   ├── model/         # Database entities
│   │   │   ├── DTO/           # Data Transfer Objects
│   │   │   ├── exception/     # Global error handling and custom exceptions
│   │   │   ├── security/      # JWT and Security filters
│   │   │   ├── utils/         # Helper classes (JWT tools, etc.)
│   │   │   └── enums/         # Status and Role definitions
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/        # Profile image storage
├── docker-compose.yml         # Multi-container orchestration
├── Dockerfile                 # Docker image setup
└── pom.xml                    # Maven dependencies
