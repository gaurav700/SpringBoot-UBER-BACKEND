put all these in application.properties file


spring.application.name=UBER-BACKEND-PROJECT

# database configurations
spring.datasource.url=jdbc:postgresql://localhost:5432/<database-name>?useSSL=false
spring.datasource.username=<username>
spring.datasource.password=<password>
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show.sql=true
spring.jpa.properties.hibernate.format_sql=true

### data dummy initialization (use these data.sql file when you want to insert data on build)
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
spring.sql.init.data-locations=classpath:data.sql

#security config
jwt.secretKey = <any-secret-key>;

# mail configurations
spring.mail.username=<email>
spring.mail.password=<app= password>  genearted in the google account
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true


