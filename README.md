# Technical Details

Our technical approach includes file storage, CI/CD, web development etc. This section will discuss the technology we use in this project and explain why we choose this technology to achieve corresponding function or target. 

## Service Architecture

- Staff Portal uses front and back-end separation technology. The back-end interacts with the database and provides the interface to the front-end. The front-end renders the pages.
- MySQL is used for data storage, *AliyunOSS* for file storage, and Redis-Cluster for temporary data storage. *MyCAT* is also used for database migration and synchronization.
- Since UCD only provides a separate external port. So we additionally provide the reverse proxy service, Use *NodeJS* service as a gateway to provide the service. And run the front end through NodeJS as a service.
- We also use AliyunOSS to provide file object storage and *WeChat + OAuth2* for third party authorization.


![system_Service_Architecture](https://github.com/Keviniscaiji/Instrument-Online-Shopping-Backend/assets/74641290/554bd17f-7e67-4cb2-93cc-5224c59d3206)

## File Storage

An official website should not store large files such as pictures and videos on the server. We use *Alibaba Cloud Object Storage Service (OSS)* for image and video storage.

- Save image or video files to *Buckets*, store the address in the database.
- Get the address from the database and get pictures or video files by address.
    
## CI/CD Pipeline

A mature project should have a test environment for integrated development and deployment, and we use *Jenkins* to build a *CI/CD pipeline*. Code quality review with SonarCube. Package the front-end and back-end code separately and build it into a Docker image for release and deployment on the test server. 

This can greatly improve the efficiency of code deployment and save time.

<img width="665" alt="pipeline" src="https://github.com/Keviniscaiji/Instrument-Online-Shopping-Backend/assets/74641290/eb017ad9-176d-4f51-a437-7463f9fdf234">


## Swagger+Druid

A complete backend system also monitors data persistence information and interface availability. We use Druid to monitor the database, Swagger to test the interface. In the staff portal.

- Build tests for all API and embed Swagger in *SpringBoot*.
- Add SQL monitoring and logging, block and filter built-in common.js advertisements, integrate Druid into SpringBoot.
- Identifying and processing information, then add the processed information to the database.

## Logical Foreign Keys

One of our database design decision is to use logical foreign keys instead of physical foreign keys.

- Physical foreign key means the fields of a table using a foreign key to associate with another table or field. They use a restricted engine called *InnoDB* to create connections between forms. They have performance problems. They need the database to maintain the internal management of foreign keys. That causes some unintended sequences.
- Due to the problems mentioned above, therefore, we use logical foreign keys for database association to ensure the final consistency of data through transactions.
- The logical foreign key is a technique in which thing foreign key but do not use foreign keys, the use of syntax (code) to produce a logical association resulting in a foreign key.
- We avoid large transactions and try to split large transactions into multiple smaller transactions to deal with them, which also have less chance of lock conflicts.


## Front-end Permission Control of Staff-Portal

1. Access to the back-end through the URL, through the request interceptor to determine whether there is permission to access the page if not legal then jump to Index.
2. Scan the QR code login to the middlePage and wait for the third-party authorization authentication callback.
3. MiddlePage processing and receive token, if there is a token to use the global Login, forced to position Index. no token directly forced to position Index.
4. Permission control plugin based on token to determine whether the right to release.
5. Permission controller puts token in request header, requests user information object from back-end, puts information into cookie (parses JWT).
6. Update the global user permissions for permission authentication use.
7. Determine whether it is the first login.
8. Get user information and permissions to be saved in a cookie.

## Redis Cluster

In Redis, we use the sentinel pattern. The structure of sentinel mode is one master, two slaves, and three sentinels. When deploying Redis, if Redis is down, the cache will not be available. Redis provides a sentinel mode in which if the master instance is down, the sentinel will upgrade a slave to the master, which ensures high availability of Redis and reduces the possibility of downtime. 

We use Redis technology in the verification process, and this Redis structure can greatly improve the stability of the system.

![system_redis](https://github.com/Keviniscaiji/Instrument-Online-Shopping-Backend/assets/74641290/587aa99a-5847-4811-aacd-6512cdcf1e42)


## Deployment Process

Our Staff Portal and Customer Portal use different technologies. Staff Portal uses front and back-end separation technology. Customer Portal uses Flask And Staff Portal uses *SpringBoot + Vue*. Hence, the single port deployment relies on the following components with restricted permissions.

### Gateway

Because we can only use one externally exposed interface. So in the deployment, we use

 to provide a reverse proxy similar to the "gateway". The gateway is implemented through *Express + Http-Proxy*, which implements load balancing through simple random number logic.

### Customer Portal

Flask project is used for the simplest runtime environment deployment. Keeping session through Screen.

### Staff Portal Back End

Jenkins automatically pulls projects from GitLab and performs quality checks. Maven is used for packaging, and the corresponding Jar package is transferred to the UCD server via ssh to run automatically in the background via sh script.

### Staff Portal Front End

Jenkins automatically pulls projects from GitLab and performs quality checks. Packaged via *Webpack*, the corresponding dist file is run as a service in the background via *Express*.

### MySQL

Since UCD's server, MySQL only allows localhost access. So we use another cloud database for the development/testing environment. The test environment database is synchronized to the UCD server via *MyCAT*.

### Redis-Cluster

Since part of the project uses Redis as cache, the server cannot deploy Redis, so we build a Redis high availability sentinel cluster on another server with *CDN service*. This ensures the availability of the Redis service.



## Project Testing

### Interface Testing

Interface testing is a type of testing that tests the interfaces between system components. Interface testing is used to detect the interaction points between external systems and systems and between internal subsystems. The focus of testing is to check the exchange of data, the transfer and control management processes, and the inter-logical dependencies between systems.

We embedded *Swagger2* with the code. The entire interface is tested by swagger. Simulation tests at large data levels are also performed using *Apifox*. Ensure the availability and reliability of the interfaces.

### Code Quality Testing

We built a test environment on the team's personal server from the beginning of the project. We also built the corresponding CI/CD pipeline. The pipeline uses *SonarQube* to perform quality testing for each version of the code. SonarQube is mainly used for quality assessment in terms of reliability, security, maintainability, coverage, and repetition rate. We can optimize and improve the code quality in the next release based on the results of SonarQube's code evaluation.

<img width="1431" alt="sonar 1" src="https://github.com/Keviniscaiji/Instrument-Online-Shopping-Backend/assets/74641290/42b9a8ed-79c4-4704-ade9-e44eaa4666f8">

