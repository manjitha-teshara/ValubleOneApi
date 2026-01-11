# Valuable One API

I developed a Spring Boot application to manage customers and home loans, ensuring that every loan is linked to a valid customer. The system supports creating customers, creating and searching home loans, updating loan status, and releasing loans via an external bank API. I implemented caching for customer retrieval to reduce database load and improve performance. Resilience4j retry and circuit breaker mechanisms handle external API failures gracefully. Logging and exception handling are added across controllers and services for audit, debugging, and reliability.

---

## **Steps to Run the Application**

### **1. Clone the Repository**
```bash
git clone https://github.com/manjitha-teshara/ValubleOneApi.git
cd ValubleOneApi
```
### **2. Build the Project**
```bash
mvn clean install
```
### **3. Run the Application**
```bash
mvn spring-boot:run
```
### **4. Access H2 Database Console (for Testing)**
```bash
URL: http://localhost:8080/h2-console


Username: sa

Password: (leave empty)
```

### **5. Test APIs**

Use Postman or curl to test the endpoints:
```bash
Create Customer: POST /v1/api/customers

Create Home Loan: POST /v1/api/home-loans

Search Home Loans: GET /v1/api/home-loans?reference=<loanRef>

Release Loan: POST /v1/api/home-loans/release-loan
```
### **6. Swagger API Documentation**
If OpenAPI is integrated:
```bash
URL: http://localhost:8080/swagger-ui.html
```
<img width="2559" height="1361" alt="image" src="https://github.com/user-attachments/assets/cb13dd39-5776-437a-910a-b0e4afa8f014" />
