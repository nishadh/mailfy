# Introduction
Mailfy.net for OSGI Demo. Send yourself an email at your scheduled time.


# Meaning of the name Mailfy?
Selfy -> Taking your own photo
Mailfy -> Sending yourself an email

# Features
1. Developed on Felix OSGi Implementation
2. Tested to run on Apache Karaf
3. Uses Hibernate (JPA) and Container managed transactions
4. Apache aries blueprint for DI using annotations
5. Apache CXF for JAX RS Rest APIs
6. AngularJS for front-end (Free theme from http://startangular.com/)

# Install
1. mvn clean install
2. Add JNDI datasource with the name 'mailfy'
3. Deploy to karaf (feature:repo-add mvn:com.nishadh.mailfy/features/1.0-SNAPSHOT/xml)
