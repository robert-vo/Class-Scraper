package scraper;

/*
For the classbrowser api, there must be a term parameter passed through the URL.
Attempting to pass anything else will result in 0 objects returned.
*/

public class URLBuilder {

    final static private String BASE_URL = "http://classbrowser.uh.edu/classes?";

    public static StringBuilder createNewURL() {
        return new StringBuilder(BASE_URL);
    }

    public static String createURLForTermOnly(Term term) {
        return createNewURL().append(generateParameter(URLParameters.term, term.getTermID())).toString();
    }

    public static String createURLForTermAndSubject(Term term, String subject) {
        return createURLForTermOnly(term) +
                generateParameter(URLParameters.subject, subject);
    }

    public static String generateParameter(URLParameters parameter, String value) {
        StringBuilder newParameter = new StringBuilder();

        if(!parameter.equals(URLParameters.term)) {
            newParameter.append("&");
        }

        newParameter.append(parameter.toString())
                    .append("=")
                    .append(value);

        return newParameter.toString();
    }

    public static String incrementPageNumber(String url) {
        if(!url.contains("page")) {
            return url + generateParameter(URLParameters.page, "2");
        }
        else {
            int currentPage = Integer.parseInt(url.substring(url.indexOf("page")).split("=")[1]);
            return url.substring(0, url.indexOf("page"))  + "page=" + (currentPage + 1);
        }
    }

    public static String changePageNumber(String url, int requestPageNumber) {
        if(!url.contains("page")) {
            return url + generateParameter(URLParameters.page, String.valueOf(requestPageNumber));
        }
        else {
            return url.substring(0, url.indexOf("page"))  + "page=" + requestPageNumber;
        }
    }

    public static int extractTermParameter(String url) {
        return Integer.parseInt(url.split("\\?")[1].split("&")[0].split("=")[1]);
    }
}
