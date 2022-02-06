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
This code uses the default root account to connect and the password can be set as `rootroot`. If you add another user/credentials make sure to change the same in the code base.

### Running App

Post installation of MySQL, Java and Maven, you will have the choice to let spring boot create the tables for you or do it yourself.
For this, please run the sql commands present in the 'schema.sql' file first under the `resources` folder in the code base, then `Data.sql` file .

Finally, you will be ready to import the code into an IDE of your choice and run the App.java to launch the application.

### Testing

(src/main/resources/images/class.png)

