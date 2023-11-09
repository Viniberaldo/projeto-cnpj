# Project-cnpj 🏬

Project for practicing integration between frontend, backend, and containers.

## Overview

The frontend application, built with JSF, sends an HTTP request to the backend application, which is built using the Spring framework. The backend, in turn, uses the [Brazil API](https://www.brasilapi.com.br/) to retrieve information related to the parameter entered in the frontend. The Brazil API returns the CNPJ and other detailed information about the searched company.

The retrieved information is then displayed on the frontend page. Additionally, the frontend application allows users to manually add supplementary information such as address and telephone when necessary.

After filling in this additional information, users can click the "Save" button. At this point, the frontend makes another HTTP call to the backend, which receives the data and stores it in the database (a PostgreSQL instance running in a container) for future use.

This sequence of actions allows for the study and experimentation of microservices architecture, albeit in a simplified manner.

## Guide to Configuring and Using the Application

This is a step-by-step guide to configuring, deploying, and using the Alpha application. Make sure to follow all instructions for a smooth experience.

### Requirements

- Tomcat version 9.0.82
- Docker
- JDK 17 or higher

### Configuring Tomcat

1. Ensure you have Tomcat 9.0.82 installed on your machine.
2. Configure Tomcat to run on port 8180.
3. Deploy the alfa-front-1.0-SNAPSHOT.war project (located in /alfa-front/alfa-front/target) in Tomcat.

### Configuring the Database with Docker

Use Docker to create a container with PostgreSQL. You can use the following command:

```shell
sudo docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=postgres -v pgdata:/var/lib/postgresql/data --name postgres postgres
```

### Running the Java Application

Ensure you have JDK 17 or higher installed on your machine.

Run the alfa-0.0.1-SNAPSHOT.jar file (located in alfa/target/) with the following command:

```shell
java -jar alfa-0.0.1-SNAPSHOT.jar
```

### Interacting with the Web Application

1. Access the browser using the URL [http://localhost:8081/alfa-front-1.0-SNAPSHOT/](http://localhost:8081/alfa-front-1.0-SNAPSHOT/).
2. Enter a valid CNPJ in the corresponding field and click "Search."
3. After the search, the first four fields in the data form should be filled, and users can complete the address and telephone fields.
4. Click "Save," and a success message will be displayed.

### Checking Data in the Database

To verify if the data has been persisted in the database, follow these steps:

1. Access the PostgreSQL container with the following command:

```shell
sudo docker exec -it postgres psql -U postgres
```

2. After entering the psql, access the 'dbempresas' database with the command:

```sql
\c dbempresas;
```

3. Once connected to the 'dbempresas' database, check the 'company' table with the following command:

```sql
select * from company;
```

## Contributions

Contributions are welcome. Feel free to report issues, open pull requests, and collaborate to enhance this application.

## Future Enhancements

Here are some suggestions for improving the Alpha application in the future:

1. **CNPJ Field Mask**: Add a mask to the CNPJ field to accept only numbers, improving usability.
2. **CNPJ Validation**: Implement validation for the CNPJ field to ensure that the data is valid before sending a request to the corresponding Brazilian API.
3. **Unit and Integration Testing**: Develop comprehensive unit and integration tests for the Spring Boot application's methods to ensure stability and reliability.
4. **Monitoring Tools**: Implement monitoring tools like Prometheus and Grafana to monitor the application's performance and health in real-time.

These are just a few ideas for improving the application in the future. Contributions are welcome to implement these and other enhancements.

## License

This project is licensed under the MIT License.

---
