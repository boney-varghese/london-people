## Overall Architecture

This project is a Multi Stage Docker Spring Boot Java application to retrieve the list of people who are listed as either living in London, or whose current coordinates are within 50 miles of London using Haversine formula.

## Build & Deploy Process

Either download the application or use git to clone the application:
git clone https://github.com/boneyrp/london-people.git

### Build Docker Image

Run the below command to build the docker image: 
##### docker build -t london .



If this succeeds, run the below command:
##### docker run -p 8081:8081 london



Once the application is up and running the API details are available at http://localhost:8081/swagger-ui.html#/people-controller
