#  BookingGo Techincal Test Submission

## Setup
This project is a Maven Spring-Boot Project, written in Java using IntelliJ IDEA Community Edition 2018.2.1. I therefore recommend that you import these projects through IntelliJ.
1) Download this GitHub Project.
2) Each folder in the root of this GitHub repository is a seperate project folder. The steps below guide you through importing said projects.
  a) Open IntelliJ
  b) On the welcome screen, select  'Import Project'
  c) Select the project folder that you are importing, click OK. 
  d) Select 'Import project from external model', select 'Maven', click 'Next'.
  e) For the next three steps: Keep default settings, click 'Next'.
  h) Keep default settings, click 'Finish'.

The dependencies should be automatically downloaded by Maven. 

Also within the root of this GitHub repository are three .JAR files. These are pre-packaged versions of my applications. The below steps guide you through running these applications using the pre-packaged .JAR files.
You can also run these applications through IntelliJ. 

## Part 1

### Console application to print the search results for Dave's Taxis
cd bookingGo_technical_task
java -jar Part1-PrintSearchResultsForDavesTaxis.jar

### Console application to filter by number of passengers
cd bookingGo_technical_task
java -jar Part1-PrintSearchResultsWithFilter.jar

## Part 2
### Starting the API:
cd bookingGo_technical_task
java -jar Part2-RestAPI.jar

### Sample request
NOTE: This has been pre-packaged to use port '9090'. If this is unsuitable, you are able to change it in the 'application.properties' file within the project folder. You will then be able to run it from IntelliJ.
http://localhost:9090/getCheapestTaxis?pickuplat=4&pickuplong=2&dropofflat=9&dropofflong=4&capacity=4
