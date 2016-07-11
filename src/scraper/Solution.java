package scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

/**
 * Created by Robert on 6/15/16.
 */
public class Solution {
    public static void main(String[] args) throws IOException {
        String url = "http://classbrowser.uh.edu/classes?term=1990-MIN&subject=&location=&mode=&page=2";
        Document doc = Jsoup.connect(url).get();
//        String url = "http://classbrowser.uh.edu/classes?distance=0&term=2000&session=1&subject=COSC";
//        File input = new File("resources/coscPageOne.html");
//        Document doc = Jsoup.parse(input, "UTF-8", "");
//
        Elements allClasses = doc.select("ul[id=accordion] > li");

        if(allClasses.size() != 0) {
            System.out.println("yay classes!");

//            printClassInformation(allClasses.get(0));
            for(Element aClass : allClasses) {
                printClassInformation(aClass);
            }
        }
        else {
            System.out.println("no classes");
        }

    }

    private static void print(String msg, Element args) {
        System.out.println(String.format("%s: %s", msg, args.text()));
    }

    private static void printClassInformation(Element aClass) {
        //gets class number and section number, e.g. COSC 1300 (15240). use classNumberAndSectionNumber.text() to retrieve text
        Element classNumberAndSectionNumber = aClass.select("h3[class=class-title] > strong").first();
        print("Class Number (Section Number)", classNumberAndSectionNumber);

        //gets class title
        Element classTitle = aClass.select("span[class=class-title--course]").first();
        print("Class Title", classTitle);

        //gets class status (open/closed) (number enrolled / seat total)
        Element classStatus = aClass.select("span[class^=class--status]").first();
        print("Class Status", classStatus);

        //gets class attributes list (core, off campus, etc). work on this
        Elements classAttributesList = aClass.select("small[class=class--attributesList] > span");

        if(classAttributesList.isEmpty()) {
            System.out.println("No Class Attributes");
        }
        else {
           for (Element classAttribute : classAttributesList) {
                print("Class Attribute", classAttribute);
            }
        }

        Elements classDatesTimeInstructorLocationInformation = aClass.select("li[class=float-list--row]");

        Element classDatesTime = classDatesTimeInstructorLocationInformation.get(0);

        Element classDates = classDatesTime.select("span[class$=body]").first();
        print("Class Dates", classDates);

        Element classDaysAndTimes = classDatesTime.select("span[class$=body]").get(1);
        print("Class Days and Times", classDaysAndTimes);

        Element classTimeInstructorLocationInformation = classDatesTimeInstructorLocationInformation.get(1);

        Element classInstructorName = classTimeInstructorLocationInformation.select("span[class$=body]").first();
        print("Instructor Name", classInstructorName);

        Element classInstructorEmail = classTimeInstructorLocationInformation.select("span[class$=body] > a").first();
        String email = classInstructorEmail.attr("href").replaceAll("[\\t\\n]", "").substring(7);
        System.out.println("Instructor's Email: " + email);

        Element classLocation = classTimeInstructorLocationInformation.select("span[class$=body]").get(1);
        print("Class Location", classLocation);

        //Class room shows up for non-mini classes for some reason
//        Element classRoom = classTimeInstructorLocationInformation.select("span[class$=body]").get(2);
//        print("Class Room", classRoom);

        Element classFormat = classTimeInstructorLocationInformation.select("span[class$=body]").get(2);
        print("Class Format", classFormat);

        Elements classDescriptionSessionAndSyllabus = aClass.select("div[class=panel-collapse  collapse  in  class--body");

        Element classDescription = classDescriptionSessionAndSyllabus.select("li").first();
        print("Class Description", classDescription);

        Elements classSessionAndSyllabus = classDescriptionSessionAndSyllabus.select("ul[class=list-unstyled  class-desc--2col]");

        Element classNumber = classSessionAndSyllabus.get(0).select("li").get(0);
        print("Class Number", classNumber);

        Element classSession = classSessionAndSyllabus.get(0).select("li").get(1);
        print("Class Session", classSession);

        Element classSyllabus = classSessionAndSyllabus.get(0).select("li").get(2);
        print("Syllabus", classSyllabus);


        if(classSessionAndSyllabus.get(0).select("li").size() != 4) {
            System.out.println("No information on last day to add found yet");
        }
        else {
            Element classLastDayToAdd = classSessionAndSyllabus.get(0).select("li").get(3);
            print("Last Day to Add", classLastDayToAdd);
        }

        Element classDuration = classSessionAndSyllabus.get(1).select("li").get(0);
        print("Class Duration", classDuration);

        Element classComponent = classSessionAndSyllabus.get(1).select("li").get(1);
        print("Class Component", classComponent);

        if(classSessionAndSyllabus.get(1).select("li").size() != 3) {
            System.out.println("No information on last day to drop found yet");
        }
        else {
            Element classLastDayToDrop = classSessionAndSyllabus.get(1).select("li").get(3);
            print("Last Day to Drop", classLastDayToDrop);
        }

    }
}
/*
CLASS 1234 (12345) CLASS_NAME √
OPEN/CLOSED (Seats Taken / Seats Available) √
Class Attributes (if applicable) √
Dates: start dates - end dates √
Days/Times: MoFr 9am-5pm √
Instructor: Teacher √
Instructor Email: e@e.e √
Location: UH √
Room: Roomroom √
Format: OnlineOrNot √

Description: Something (remove the word 'Description')
Class Number: Something (remove the word 'Class Number')
Duration: 123123 Weeks √ (remove the word 'Duration')
Session: 1 (remove the word 'Session')
Class Component: LEC √ (remove the words 'Class Component')
Syllabus: Link (remove the word 'Syllabus')
Notes:
*/