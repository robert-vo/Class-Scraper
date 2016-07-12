package ui;

import scraper.Scraper;
import scraper.Term;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ScraperGUI extends JFrame {

    public static final int WIDTH   = 725;
    public static final int HEIGHT  = 700;
    private static  Scraper     scraper;
    private         JScrollPane loggerScrollPane;
    private static  JTextArea   loggerTextArea;
    private         JButton     startButton;
    private         JButton     stopButton;
    private         JButton     closeButton;
    private         JComboBox   termOptions;

    private final Term[] termDropDownOptions = Term.values();

    @Override
    protected void frameInit() {
        super.frameInit();
        setSize(WIDTH, HEIGHT);
        setTitle("Classbrowser Scraper");
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
        scraper = new Scraper((Term) termOptions.getSelectedItem());
        appendToLoggerTextArea("Starting the scraper for " + String.valueOf(termOptions.getSelectedItem()));

        if(Scraper.verifyValidDocument(scraper.getWebsiteToScrape())) {
            appendToLoggerTextArea("valid document");
            scraper.startScraper((Term) termOptions.getSelectedItem());
        }
        else {
            appendToLoggerTextArea("invalid document");
            onStop();
        }
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
