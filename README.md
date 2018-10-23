# uniqueID
To generate an unique ID that meet the following requirements: 1. 4-6 characters in length; 2. all letters uppercase; 3. fisrt letter is a non-vowel letter, number is not allowed 4. remaining characters can be either letter or number

Read Me

How to build the code:

Any IDE can be used to build the code. Java JDK should be installed before install your IDE. I used IntelliJ IDEA (Community Edition) to build my code. You can either “download” the project from GitHub or “clone” the GitHub UniqueID repository link and open it in IntelliJ, simply by select “File” – “New” – “Project from version control”. Then the system may ask you to enter your laptop password for access (Take mine for example, may not be applicable). Input your password, paste the URL and click on “clone”. The code will be built up.


How to run it:

Open this uniqueID project and select “src” – “com.company” – “Main”. Right click on “Main”, select “Run ‘Main’”. Console will show ”Enter a number: ”. Just input the batch number and press enter. Then the console will show the unique ID generated from that batch number. (note: the batch number should be a positive integer that greater than 0 and smaller than 2147483647)


How to test it:

There are two ways. You can choose either way to test the code.
1. Right click “UniqueIDTest” that under com.company package, and select “Run ‘UniqueIDTest’”. I’ve wrote some test cases for testing. The system will automatically test it.
2. Manually test it. Just “Run ‘Main’ “and input the case value you want to test. Only positive integer is allowed. For invalid input type, such as characters, words, and negative numbers, the system will show exception. To exit the exception, just simply “Run ’Main’” again and input a valid number.

Environment:

java version "10.0.2" 2018-07-17
Java(TM) SE Runtime Environment 18.3 (build 10.0.2+13)
Java HotSpot(TM) 64-Bit Server VM 18.3 (build 10.0.2+13, mixed mode)
MacOS
