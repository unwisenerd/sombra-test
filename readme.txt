Online Clothing Boutique Project:

Implemented Functions:
	-	user registration with e-mail confirmation, login/logout
	- 	user profile with a possibility to modify user credentials or image
	-	viewing products with images
	- 	filtering and ordering products by different criteria with paging
	-	adding products to the shopping cart
	-	browsing the shopping cart with a possibility to order/delete the product
	-	credit card payment simulation
	-	sending the payment check to user`s e-mail or viewing it on the orders page
	-	administrator section, where you can add/modify/delete entities
	-	adding commodities from a CSV file (administrator only)
	-	editing the user/commodity image (administrator only)
	-	disabling/enabling user accounts
	-	both server and client side validation for most operations
	
Elapsed Time:
	-	database design, entity mapping, database access layer ~4 hours
	-	administrator section ~ 12 hours
	-	authentication, registration, user profile ~ 16 hours
	-	filling the database ~ 4 hours
	- 	product filter, paging ~ 11 hours
	-	shopping cart, orders, payment simulation ~ 11 hours
	-	payment checks ~ 6 hours
	-	reading commodities from csv file ~ 2 hours
	-	validation for all operations ~ 5 hours
	-	html/css writing ~10 hours
	-	deploying the project to a hosting ~6 hours
	Total Elapsed Time: ~87 hours
	
Project Configuration:
	1) To change database connectivity credentials, open [WEB-INF/classes/META-INF/persistence.xml] and modify these lines:
			<property name="hibernate.connection.url" value="jdbc:mysql://localhost/[DATABASE_NAME]?characterEncoding=UTF-8" />
			<property name="hibernate.connection.username" value="[USER NAME]" />
			<property name="hibernate.connection.password" value="[PASSWORD]" />
			
	2) To change JSP files location, open [WEB-INF/servlet-context.xml] and modify these lines:
			<bean
				class="org.springframework.web.servlet.view.InternalResourceViewResolver">
				<property name="prefix">
					<value>/WEB-INF/[PATH TO JSP FILES]</value>
				</property>
				<property name="suffix">
					<value>.jsp</value>
				</property>
			</bean>
			
	3) To enable in-memory authentication, open [WEB-INF/security-context.xml] and uncomment these lines:
			<!-- <security:authentication-provider>
				<security:user-service>
					<security:user name="root" password="root"
					authorities="ROLE_ADMIN" />
				</security:user-service>
			</security:authentication-provider> -->
			
	4) To change the location of XML files, do the following:
			persistence.xml
			Open [WEB-INF/application-context.xml] and modify this line:
			<property name="persistenceXmlLocation" value="classpath:/META-INF/persistence.xml" />
			
			For other files:
			Open [WEB-INF/web.xml] and modify these lines:
			servlet-context.xml
			<init-param>
				<param-name>contextConfigLocation</param-name>
				<param-value>
					/WEB-INF/servlet-context.xml
				</param-value>
			</init-param>
			
			application-context.xml, security-context.xml
			<context-param>
				<param-name>contextConfigLocation</param-name>
				<param-value>
					/WEB-INF/application-context.xml
					/WEB-INF/security-context.xml
				</param-value>
			</context-param>

Design Patterns:
	DAO - creating a layer of interfaces to access database and isolate it from business logic
	DTO - creating objects that do not have any behaviour unlike entity classes and are intended for data transfer
	Proxy - is used by jpa to manage entities
	Factory - is used by jpa to provide entity managers
	Singleton - restricting the instantiation of a class to one object (Spring Beans)
	MVC - dividing a given application into three interconnected parts (M - model, V - view, C - controller)
		model - represented by the object which carries data
		view - represented by the visual part of application
		controller - controls the data flow between model and view

Used Frameworks:
	Java Persistence API - to work with the database
	Spring IoC - for dependency injection and beans management
	Spring MVC - for MVC pattern implementation
	Spring Security - for user authentication
	JSoup - to fill the database
	iText - to create PDF files
	Jackson - converting objects to/from JSON
	Apache Commons - to parse CSV and manage multipart files
	
Other:
	-	commodities are parsed by JSoup Parser from https://leboutique.com, so they are in Russian despite the application being English
	-	the eclipse project includes Maven build configuration to automatically create WAR and run it on embedded Tomcat Server
	-	resources such as commodity images or payment checks are located at Tomcat folder in /resources folder
	-	database dump and example CSV file are included