# Pay My buddy
A money exchange app

### Prerequisites

What things you need to install the software and how to install them

- Java 1.8
- Maven 3.6.2
- Mysql 8.0.17

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install MySql:

https://dev.mysql.com/downloads/mysql/

After downloading the mysql 8 installer and installing it, you will be asked to configure the password for the default `root` account.

This code uses the default root account to connect and the password can be set as `rootroot`.

If you add another user/credentials make sure to change the same in the code base.

### Running App

Post installation of MySQL, Java and Maven, you will have the choice to let spring boot create the tables for you or do it yourself.

If you want Springboot to handle it for you first add :

spring.datasource.username=root
spring.datasource.password=rootroot

to the application.properties file, then launch the application with your IDE

If you want to create the tables yourself please connect to your MySQL instance, and run the sql commands present in the 'schema.sql' file first under the `resources` folder in the code base, then `Data.sql` file .

A third option would be to run mvn clean package -DskipTests=true in your ide or in a command window after navigating to the project folder. 

This will generate a jar file. Please copy this jar file to a folder of your choice.

Then add a "config" folder in this folder. Then add an application.properties file in it. Write 

spring.datasource.username=root
spring.datasource.password=rootroot

in this file. Then navigate on your terminal navigate to the folder with the jar file and run 

java -jar "NAME OF THE JAR".jar

### Diagramme de classe

![image](https://github.com/mafrax2/SpringSecurityAuth/blob/master/SpringSecurityAuth/src/main/resources/images/P6_03_classdiagramm.jpg)

### Modèle physique

![image](https://github.com/mafrax2/SpringSecurityAuth/blob/master/SpringSecurityAuth/src/main/resources/images/P6_04_UML_modelephi.jpg)

