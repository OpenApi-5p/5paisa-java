5Paisa Open API's: Java


What You Need

A favorite text editor or IDE

JDK 1.8 or later

Maven 3.2+

You can also import the code straight into your IDE:

Spring Tool Suite (STS)

IntelliJ IDEA



How to run this

1. Clone it using Git 


git clone <link>


2. Import the code straight into your IDE:

Spring Tool Suite (STS)

IntelliJ IDEA


3.Install Mavel dependency

                <dependency>
			<groupId>com.squareup.okhttp3</groupId>
			<artifactId>okhttp</artifactId>
			<version>3.4.2</version>
		</dependency>
		<dependency>
			<groupId>com.googlecode.json-simple</groupId>
			<artifactId>json-simple</artifactId>
			<version>1.1</version>
		</dependency>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.12</version>
			<scope>test</scope>
		</dependency>


4. Change "userId", "password" and other parameters in "src/main/java/com/FivePaisa/service/Properties.java" file

5. Run the test file "src/test/java/test2.java' for APIs Testing

6. Run the test file "src/test/java/TestWebSocket.java" for Web Socket testing,Yo can set Thread Time in Properties file to keep live connection for web socket
