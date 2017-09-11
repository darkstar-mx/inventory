# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* Quick summary
* Version
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How do I get set up? ###

* Summary of set up
	* Install grails 2.4.5, it's suggested to install it via GVM.
	* Install Oracle JDK 7 version.
	* In eclipse install the plugin for Grails, don't forget to point eclipse to your Grails installation.
	* In eclipse install the plugin for Groovy 2.4.3.
	* Install Oracle Express XE.
* Configuration
	* Go to oracle and create a schema to the database.
	* Open file DataSource.groovy and add the Oracle JDBC information.
	
* Dependencies
	* Oracle XE
	* Vaadin 7.5.2
	* Oracle JDK 7
	* Grails (Hibernate, Spring, Groovy)
	* Spring security
	
* Database configuration
	* Open file DataSource.groovy, ensure upcoming configurations:
		* dataSource section:
			* dialect = "org.hibernate.dialect.Oracle10gDialect"
		* hibernate section:
			* driverClassName = 'oracle.jdbc.OracleDriver'
			* cache.region.factory_class = 'org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory' // Hibernate 4
			* jdbc.use_get_generated_keys = true
* How to run app
	* Press CTRL+ALT+SHIFT+g and type: run-app
* How to run tests
	* Press CTRL+ALT+SHIFT+g and type (in the dialg-box) "test-app"
* Deployment instructions
	* Press CTRL+ALT+SHIFT+g, hit the command "war", it will create the war file in grails predefined directory.

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* @grails-coder, the owner and admin