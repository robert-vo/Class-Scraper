package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ScraperGUI extends JFrame {

    public static final int WIDTH   = 1050;
    public static final int HEIGHT  = 325;
    private JPanel      scraperPanel;
    private JButton     startButton;
    private JButton     stopButton;
    private JButton     closeButton;
    private JComboBox   termOptions;
    private JLabel      message;

    //try to populate it from the list of enums
    private final String[] termDropDownOptions = {"Fall 2015", "Spring 2016", "Summer 2016", "Fall 2016"};

    @Override
    protected void frameInit() {
        super.frameInit();
        setSize(WIDTH, HEIGHT);
        setTitle("Classbrowser Scraper");
    }

    public ScraperGUI() throws IOException {
        JPanel entirePanel = generateButtons();
        message = new JLabel("Select an option.");
        entirePanel.add(message);

        termOptions = new JComboBox<>(termDropDownOptions);
        entirePanel.add(termOptions);

        add(entirePanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });
    }

    private JPanel generateButtons() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());

        createAllButtons();
        buttons.add(startButton);
        buttons.add(stopButton);
        buttons.add(closeButton);
        return buttons;
    }

    private void createAllButtons() {
        startButton = new JButton("Start Scraping");
        stopButton = new JButton("Stop Scraping");
        closeButton = new JButton("Close Program");

        startButton.addActionListener(e -> onStart());
        stopButton.addActionListener(e -> onStop());
        closeButton.addActionListener(e -> onClose());
    }

    private void flipButtons(boolean enabled) {
        startButton.setEnabled(enabled);
        stopButton.setEnabled(!enabled);
        closeButton.setEnabled(enabled);
        termOptions.setEnabled(enabled);
    }

    private void onStart() {
        flipButtons(false);
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        closeButton.setEnabled(false);
        message.setText("Starting the scraper...");
    }

    private void onStop() {
        flipButtons(true);
        message.setText("Stopping the scraper...");
    }

    private void onClose() {
        message.setText("Closing...");
        System.out.println("closing...");
        dispose();
        System.exit(0);
    }

    private static ScraperGUI createScraperPanel() throws IOException {
        ScraperGUI scraperGUI = new ScraperGUI();
        scraperGUI.setVisible(true);
        return scraperGUI;
    }

    public static void main(String[] args) throws IOException {
        JFrame window = ScraperGUI.createScraperPanel();
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

}
