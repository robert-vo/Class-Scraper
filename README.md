# Class-Scraper [![Build Status](https://travis-ci.org/robert-vo/Class-Scraper.svg?branch=master)](https://travis-ci.org/robert-vo/Class-Scraper)

## About
Class-Scraper is a Java-based API that allows one to extract all of the classes from the **University of Houston** available at [http://classbrowser.uh.edu](http://classbrowser.uh.edu). The data is then readily available for personal use.

Included is a GUI that allows one to select a term, scrape the classes and store it in a relational database. The GUI was tested using a MySQL database.

## Setting up

###Dependencies
**JSoup** is required to run the Class-Scraper. JSoup can be downloaded [here](https://jsoup.org/download) or imported using your favorite package manager tool.

**ClassScraper** needs to imported either by downloading the project [here](https://github.com/robert-vo/Class-Scraper/archive/master.zip) or including the jar file, [available here](https://github.com/robert-vo/Class-Scraper/releases) in your project.

###Configuration
If using the GUI to store the classes in a database, be sure to edit the configuration file ```config.properties``` to make sure the data gets stored properly.

## Usage

```java
//Not to be confused with java.lang.Class, Class is a POJO that stores information about a class.
import com.scraper.main.Class;
import com.scraper.main.ClassScraper;

int year = 2016;
String semester = "Fall";
ClassScraper classScraper = new ClassScraper(year, semester);


/**
 * Optionally, a page limit constraint can be set on the ClassScraper. 
 * When a page limit constraint is set on the ClassScraper, the scraper 
 * will only scrape through the number of pages specified.
 */
int pageLimit = 10;
classScraper.setPageLimit(pageLimit);

/**
 * Optionally, a subject constraint can be set on the ClassScraper. 
 * When a subject constraint is set on the ClassScraper, the scraper 
 * will only scrape through the specified subject.
 */
Subject mathSubject = Subject.MATH; 
classScraper.setSubjectOnScraper(mathSubject);

/**
 * Optionally, a session constraint can be set on the ClassScraper. 
 * When a session constraint is set on the ClassScraper, the scraper 
 * will only scrape through the specified session.
 */
Session miniSession = Session.MINI_SESSION;
classScraper.setSessionOnScraper(miniSession);

/**
 * Optionally, an initial page number constraint can be set on the ClassScraper. 
 * When a initial page number constraint is set on the ClassScraper, the scraper 
 * will start at the specified page.
 */
int initialPageNumber = 5;
classScraper.setInitialPageNumber(initialPageNumber);

//Scrapes all of the classes given a term.
classScraper.startScraper();

//All of the classes can be accessed using the getAllClasses() method.
List<Class> allClasses = classScraper.getAllClasses();

//Prints out the title of each class.
allClasses
    .stream()
    .map(Class::getClassTitle)
    .forEach(System.out::println);
    
//Prints out all class titles for classes that are on Monday and Wednesday.
allClasses
    .stream()
    .filter(Class::isMondayClass)
    .filter(Class::isWednesdayClass)
    .map(Class::getClassTitle)
    .forEach(System.out::println);

//Prints out all links for classes that has a syllabus.
allClasses
    .stream()
    .filter(e -> !e.getSyllabus().equals("Unavailable"))
    .map(Class::getSyllabus)
    .forEach(System.out::println);
  
```
## GUI
The GUI, available at ```/src/main/java/com/scraper/ui/ScraperGUI.java```, can be executed to perform the scraping. Be sure to have ```properties.config``` updated before the scraping is initiated. 

### GUI Usage
Select a desired term, by choosing on the drop down menu on the upper right hand side. Optionally, enter  a number greater than 0 in the text box to limit the pages scraped. Press the ```'Start Scraping'``` button to initiate the scraping. After the scraping is done, the data will be inserted into the database specified in ```config.properties```.

## GUI Screenshots

### Initial GUI shown.
![Alt text](/screenshots/1 - Initial GUI.png?raw=true)

### GUI shown during the scraping process.
![Alt text](/screenshots/2 - During the Scraping Process.png?raw=true)

### GUI shown during the database process.
![Alt text](/screenshots/3 - During the Database Action Process.png?raw=true)

### GUI shown after completion of both the scraping and database processes. 
![Alt text](/screenshots/4 - Completed Database Process.png?raw=true)

##Thanks for checking out the Class-Scraper!
#Robert
