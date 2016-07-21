package ui;

import scraper.ClassScraper;
import scraper.ScraperRunner;
import scraper.Term;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class ScraperGUI extends JFrame {

    public  static final int                WIDTH               = 725;
    public  static final int                HEIGHT              = 700;
    private static       ScraperRunner      scraper;
    private              JScrollPane        loggerScrollPane;
    private static       JTextArea          loggerTextArea;
    private              JButton            startButton;
    private              JButton            stopButton;
    private              JButton            closeButton;
    private              JComboBox          termOptions;
    private              long               startTime;
    private              long               endTime;
    private        final String             TIME_FORMAT         = "HH:MM:SS";
    private        final SimpleDateFormat   simpleDateFormat    = new SimpleDateFormat(TIME_FORMAT);
    private        final Term[]             termDropDownOptions = Term.values();

    @Override
    protected void frameInit() {
        super.frameInit();
        setSize(WIDTH, HEIGHT);
        setTitle("Classbrowser ScraperRunner");
    }

    public ScraperGUI() throws IOException {
        JPanel entirePanel = generateButtons();
        termOptions = new JComboBox<>(termDropDownOptions);
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
        buttons.add(stopButton);
        buttons.add(closeButton);
        return buttons;
    }

    private void createAllButtons() throws IOException {
        startButton = new JButton("Start Scraping");
        stopButton = new JButton("Stop Scraping");
        stopButton.setEnabled(false);
        closeButton = new JButton("Close Program");
        startButton.addActionListener(e -> {
            try {
                onStart();
            } catch (IOException e1) {
                loggerTextArea.append(e1.toString());
                changeStatusOfButtons(false);
            }
        });
        stopButton.addActionListener(e -> onStop());
        closeButton.addActionListener(e -> onClose());
    }

    private void changeStatusOfButtons(boolean status) {
        startButton.setEnabled(status);
        stopButton.setEnabled(!status);
        closeButton.setEnabled(status);
        termOptions.setEnabled(status);
    }

    private void onStart() throws IOException {
        changeStatusOfButtons(false);
        ClassScraper classScraperAPI = new ClassScraper((Term) termOptions.getSelectedItem());

        scraper = new ScraperRunner((Term) termOptions.getSelectedItem());
        appendToLoggerTextArea("Starting the scraper for " + String.valueOf(termOptions.getSelectedItem()));
        startAndPrintTimer();

        classScraperAPI.startScraper();

        endAndPrintTimer();
        appendToLoggerTextArea("Time taken is " + String.valueOf(endTime - startTime) + "ms.");

        onStop();
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
    }

    private void onStop() {
        changeStatusOfButtons(true);
        appendToLoggerTextArea("Stopping the scraper.");
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
        loggerTextArea.append(message + "\n");
        System.out.println(message);
    }

    public static void main(String[] args) throws IOException {
        JFrame window = ScraperGUI.createScraperPanel();
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

}
