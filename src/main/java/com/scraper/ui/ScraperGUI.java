package com.scraper.ui;

import com.scraper.main.ClassScraper;
import com.scraper.main.DatabaseOperations;
import com.scraper.main.pojo.Term;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class ScraperGUI extends JFrame {

    public  static final int                WIDTH               = 800;
    public  static final int                HEIGHT              = 700;
    private static       ClassScraper       classScraper;
    private              JScrollPane        loggerScrollPane;
    private static       JTextArea          loggerTextArea;
    private static       JLabel             pageLimitLabel;
    private static       JTextField         pageLimitTextField;
    private              JButton            startButton;
    private              JButton            closeButton;
    private              JComboBox          termOptions;
    private              long               startTime;
    private              long               endTime;
    private              int                pageLimit;
    private        final String             TIME_FORMAT         = "HH:MM:SS";
    private        final SimpleDateFormat   simpleDateFormat    = new SimpleDateFormat(TIME_FORMAT);
    private        final Term[]             termDropDownOptions = Term.values();
    private              Thread             thread;

    @Override
    protected void frameInit() {
        super.frameInit();
        setSize(WIDTH, HEIGHT);
        setTitle("Class Scraper");
    }

    public ScraperGUI() throws IOException {
        JPanel entirePanel = generateButtons();
        termOptions = new JComboBox<>(termDropDownOptions);
        pageLimitTextField = new JTextField(4);
        pageLimitTextField.setText("0");
        pageLimitLabel = new JLabel();
        pageLimitLabel.setText("Enter a page limit. 0 indicates all pages.");
        entirePanel.add(pageLimitLabel);
        entirePanel.add(pageLimitTextField);
        entirePanel.add(termOptions);
        entirePanel.add(new JSeparator());
        loggerScrollPane = createLoggerPanel();
        entirePanel.add(loggerScrollPane);
        add(entirePanel);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });
    }

    private JScrollPane createLoggerPanel() {
        loggerTextArea = new JTextArea(35, 55);
        JScrollPane scrollPane = new JScrollPane(loggerTextArea);
        loggerTextArea.setEditable(false);
        return scrollPane;
    }

    private JPanel generateButtons() throws IOException {
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        createAllButtons();
        buttons.add(startButton);
        buttons.add(closeButton);
        return buttons;
    }

    private void createAllButtons() throws IOException {
        startButton = new JButton("Start Scraping");
        closeButton = new JButton("Close Program");
        startButton.addActionListener(e -> {
            try {
                onStart();
            } catch (IOException e1) {
                loggerTextArea.append(e1.toString());
                changeStatusOfButtons(false);
            }
        });
        closeButton.addActionListener(e -> onClose());
    }

    private void changeStatusOfButtons(boolean status) {
        startButton.setEnabled(status);
        termOptions.setEnabled(status);
    }

    private boolean validTextPageLimit() {
        try {
            pageLimit = Integer.parseInt(pageLimitTextField.getText());
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private void onStart() throws IOException {
        if(validTextPageLimit() && pageLimit > 0) {
            appendToLoggerTextArea("Scraping " + pageLimit + " pages.");
            runScraper();
        }
        else if(validTextPageLimit() && pageLimit < 0) {
            appendToLoggerTextArea("Invalid Page Limit. Please re-enter a valid page limit above zero.");
        }
        else if(validTextPageLimit() && pageLimit == 0) {
            appendToLoggerTextArea("Scraping all pages.");
            runScraper();
        }
        else {
            appendToLoggerTextArea("Invalid Page Limit. Please re-enter a valid page limit, or leave it empty to scrape all pages.");
        }
    }

    private void runScraper() {
        changeStatusOfButtons(false);
        classScraper = new ClassScraper((Term) termOptions.getSelectedItem());
        if(pageLimit != 0) {
            classScraper.setPageLimit(pageLimit);
        }
        appendToLoggerTextArea("Starting the scraper for " + classScraper.getTerm());

        thread = new Thread () {
            @Override public void run () {
                startAndPrintTimer();
                classScraper.startScraper();
                try {
                    appendToLoggerTextArea("The program will now attempt to insert/update the database with the classes.");

                    DatabaseOperations databaseOperations = new DatabaseOperations("config/config.properties");
                    databaseOperations.performUpdateOrInsertForAllClass(classScraper.getAllClasses());
                } catch (Exception e) {
                    appendToLoggerTextArea(e.getMessage());
                }
                appendToLoggerTextArea("Database updated. Please check the database to see the results of the scraping.");
                endAndPrintTimer();
            }
        };
        thread.start();
    }

    private void startAndPrintTimer() {
        startTime = System.currentTimeMillis();
        String dateText = simpleDateFormat.format(startTime);
        appendToLoggerTextArea("Start time is: " + dateText);
    }

    private void endAndPrintTimer() {
        endTime = System.currentTimeMillis();
        String dateText = simpleDateFormat.format(startTime);
        appendToLoggerTextArea("End time is: " + dateText);
        appendToLoggerTextArea("Time taken is " + String.valueOf(endTime - startTime) + "ms.");
        changeStatusOfButtons(true);
    }

    private void onClose() {
        appendToLoggerTextArea("Closing the program.");
        dispose();
        System.exit(0);
    }

    private static ScraperGUI createScraperPanel() throws IOException {
        ScraperGUI scraperGUI = new ScraperGUI();
        scraperGUI.setVisible(true);
        return scraperGUI;
    }

    public static void appendToLoggerTextArea(String message) {
        if(loggerTextArea != null) {
            loggerTextArea.append(message + "\n");
        }
        System.out.println(message);
    }

    public static void main(String[] args) throws IOException {
        JFrame window = ScraperGUI.createScraperPanel();
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

}
