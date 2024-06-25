**Author -** Rahul Kumar Singh ; **Date -** 24 June 2024

## Warehouse Management Application

Backend application for warehouse management which intends to provide basic functionalities like
viewing all the products and articles with quantities available in the inventory, selling products
and updating inventory accordingly.

## Assignment Details

This assignment is to implement a simple warehouse application.This software can loads artcile details from a inventory.json file and hold
articles with id, a name and available stock. The warehouse application, similarly also loads product details from product.json file and hold product details.
It provides the following functionalities:
- Get all the products along with their quantities that is available in current inventory.
- Remove or sell a product by name from the inventory and update it accordingly.
- Get list of all articles from inventory along with their present stock.
- Get a particular article from inventory with its details.

## Technical Features

- Java 17 & Spring Boot 3
- Modular project with controller, service and repository layers.
- REST APIs exposed to serve functionalities.
- H2 In-Memory Database has been used to load and hold articles and products.
- Exception handling done to take care of edge cases and errors.
- Unit test cases to test functionalities
- API documentation using OpenAPI.
- Dockerfile with required steps to containerize this Spring Boot application.

## Prerequisites
- Java 17
- Maven
- Docker installed in local system (if you want to run it by containerizing the application, else it can also run locally using IDE and Maven)
- IDE like IntelliJ IDEA

## Steps to run the application

### Running locally using mvn
1. Clone the repository : git clone https://github.com/Capgemini-Project-Assessment/warehouse-management.git
2. Go inside the project folder (cd warehouse-management) and from the root of warehouse-management project, run below commands.
3. Run the below command to build the application:   
  **.\mvnw clean install**

If the above command does not work, try **mvn clean install**
4. Run the below command to build the application:   
   **.\mvnw spring-boot:run**

If the above command does not work, try **mvn spring-boot:run**
5. The application, if correctly started, should be accessible at localhost on port 8080
6. You can use a tool like Postman to locally run and test the application

### Running locally using docker
1. Clone the repository : git clone https://github.com/Capgemini-Project-Assessment/warehouse-management.git
2. Go inside the project folder (cd warehouse-management) and from the root of warehouse-management project, run below commands.
3. Run the command to build the application: **.\mvnw clean install** or **mvn clean install**
3. **docker build -t warehouse-management .**
4. **docker run -p 8080:8080 warehouse-management**
5. If the docker commands ran successfully, then application should be accessible at http://localhost:8080
6. You can use a tool like Postman to locally run and test the application

<span style='color: red;'>
Note - </span> <span style='color: green;'>Because of some local configurations or docker versions or the underlying operating system (Windows, Mac or Linux) etc., sometimes dockerizing the application throws error which can time some to resolve.
In that scenario, please choose the option of running Spring Boot application locally using mvn/mvnw command as explained in the relevant section or 
run the application using IDE like IntelliJ IDEA and access it on localhost:8080
</span> </span>

### API Testing and Overview using SwaggerUI

http://localhost:8080/swagger-ui.html

#### H2 DB Console

http://localhost:8080/h2-console

- Driver class - org.h2.Driver
- JDBC URL - jdbc:h2:mem:accountdb
- User Name - sa
- Password - password

## API Details

Below are the details regarding exposed REST endpoints and their functionalities:

#### List all the products with their available quantity in the inventory:
```
http://localhost:8080/inventory/v1/products
```

#### Sell a product by its name:

Note - This API to sell a product takes parameters - Product Name (mandatory) and Quantity of the product to sell (optional).
If no quantity is provided, them a default quantity of 1 product is considered for sale.

```
http://localhost:8080/inventory/v1/sell/product/{productName}/{quantity}
```

#### Bonus - Get list of all articles present in the inventory:

```
http://localhost:8080/inventory/v1/articles
```

#### Bonus - Get an article and its details by the article id:

```
http://localhost:8080/inventory/v1/article/{articleId}
```

## Constraints and improvements for next iteration

1. In its current state, the application expects input JSON, both inventory and products, to contain all the fields. It does not put field wise check and validations. This can be further added. Validations can be added which could be applied while loading data from JSON file, which can do field wise validations based on business needs.
2. Functionalities can be further added and improved upon by adding extra end points and their implementations like adding a new article, product etc.
3. Currently, the application loads and stores Inventory and Product data on its startup. However, we could add separate REST end points to load the data from file.
4. API security can be added to secure end-points.
5. CI CD pipeline should be created using Pipeline as Code tool like GitHub Actions, GitLab etc.
6. If the scale of this application and usage grows, then an orchestration tool like Kubernetes can be used to deploy, manage and scale it. It can also be put behind API Gateway and Load Balancer to take care of horizontal scaling, load balancing and other cross-cutting concerns like API rate limiting etc.
7. The application can be hosted on any public cloud providers like GCP, AWS, Azure etc. In order to provision cloud resources, an IaC tool like Terraform can be used.
8. Integration test can be added to test the application end to end.
9. Serialization and deserialization process using Jackson object mapper can be further customised to take care of some of the scenarios in this project.
Eg - The file 'price' is present in Dining Chair but not present for Dining Table in product JSON. So ideally, while showing list of all products with available quantities, price field should only be shown for Dining Chair and not for Dining Table.
In current state, the application returns price = 0.0 even for Dining Table. This can be improved.

