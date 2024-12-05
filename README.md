Create a table in database:
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);


In application.properties:
spring.datasource.url=jdbc:postgresql://localhost:5432/{your database name}
spring.datasource.username={your username}
spring.datasource.password={your password}

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true


Postman:
