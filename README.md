# Class-Scraper

[![Build Status](https://travis-ci.com/robert-vo/Class-Scraper.svg?token=MDyyKNy4sp8cUiysL5c6&branch=master)](https://travis-ci.com/robert-vo/Class-Scraper)

## About
Class-Scraper is a Java-based API that allows one to extract all of the classes from the University of Houston available at [http://classbrowser.uh.edu](classbrowser.uh.edu.). The data is then readily available to use for personal use.

Included is a GUI that allows one to select a term, scrape the classes and store it a relational database. The GUI was tested using a MySQL database.

## Setting up

###Dependencies
JSoup is required to run the Class-Scraper. JSoup can be download [here](https://jsoup.org/download) or imported using your favorite package manager tool. 

ClassScraper needs to imported either by downloading the project [here](https://github.com/robert-vo/Class-Scraper/archive/master.zip) or including the jar file, available [here](https://github.com/robert-vo/Class-Scraper/releases) in your project.

###Configuration
If using the GUI to store the classes in a database, be sure to edit the configuration file [ config.properties](config.properties) to make sure the data gets stored properly.

## Usage

```java
//Not to be confused with java.lang.Class, Class is a POJO that stores information about a class.
import com.scraper.main.Class;
import com.scraper.main.ClassScraper;

int year = 2016;
String semester = "Fall";
ClassScraper classScraper = new ClassScraper(year, semester);

//Optionally, a page limit can be set on the ClassScraper. 
int pageLimit = 10; //Has to be an integer greater than 0.
classScraper.setPageLimit(pageLimit);

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

