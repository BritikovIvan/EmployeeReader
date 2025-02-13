# Employee Reader
## Description
Employee Reader is a simple console application that reads data from a text file.
The application processes possible errors in the data, sorts employees and outputs
information either to the console or to a file, as well as displays statistics on 
departments. Incorrect data is excluded from the final table and displayed at the end 
with the heading "Некорректные данные".

## Features
The first parameter of application that will be received through command line 
arguments must be the path to source data file.

Also application supports the following list of optional parameters that are passed 
through command line arguments:
+ --sort or -s (--sort=name or -s=salary) - sorting parameter. If it is not specified,
then the data is output as indicated in the source data.
+ --order (--order=ask or --order=desc) - sorting order. If sorting is not specified,
it is not used. If order set incorrectly, the default value "asc" is used.
+ --output or -o (--output=console or -o=file) - output destination. Defines where
to output the result of data processing. If destination set incorrectly, the default
value "console" is used.
+ --path (--path=<path to the output file>) - output file path. It must be specified
strictly after the output destination parameter and defines the path to the output file.
## Technologies
+ Java 17
+ Maven 3.9.9
## Build and Run Instructions
1. Clone the repository:
    ```
    git clone https://github.com/BritikovIvan/EmployeeReader.git
    ```
2. Navigate to the project directory:
    ```
   cd <path to EmployeeReader app>
   ```
3. Build the project using Maven:
    ```
   mvn clean package
   ```
4. Run the application
    ```
   java -jar <path to jar file> <path to sourse file> <parameters>
   ```