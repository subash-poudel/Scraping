package ebay;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import utils.PrintHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Subash on 8/15/16.
 */
public class Ui {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel inputFileLabel;
    private JLabel statusLabel;
    private JPanel inputFilePanel;
    private JPanel outputFilePanel;
    private JPanel runPanel;
    private JLabel outputFileLabel;
    private JButton inputFileButton;
    private JButton outputFileButton;
    private JButton runButton;

    private String inputFilePath;
    private String outPutfilePath;

    public void setUpUi() {
        prepareGUI();
        showFileChooserDemo();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Java Swing Examples");
        mainFrame.setSize(600, 600);
        mainFrame.setLayout(new GridLayout(7, 1));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        statusLabel = new JLabel("", JLabel.CENTER);
        headerLabel = new JLabel("", JLabel.CENTER);
        inputFileLabel = new JLabel("", JLabel.CENTER);
        outputFileLabel = new JLabel("", JLabel.CENTER);
        outputFileLabel.setSize(400, 100);
        inputFileLabel.setSize(400, 100);
        inputFileButton = new JButton("Open Input File");
        outputFileButton = new JButton("Open Output Directory");
        runButton = new JButton("Run");
        inputFilePanel = new JPanel();
        outputFilePanel = new JPanel();
        runPanel = new JPanel();
        outputFilePanel.setLayout(new FlowLayout());
        inputFilePanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(inputFilePanel);
        mainFrame.add(inputFileLabel);
        inputFilePanel.add(inputFileButton);
        mainFrame.add(outputFileLabel);
        mainFrame.add(outputFilePanel);
        outputFilePanel.add(outputFileButton);
        mainFrame.add(runPanel);
        runPanel.add(runButton);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }

    private void showFileChooserDemo() {
        headerLabel.setText("Choose input file: the search query should be in separate lines");
        outputFileLabel.setText("Choose the directory to save the output file.");
        final JFileChooser fileDialog = new JFileChooser();

        inputFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileDialog.showOpenDialog(mainFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = fileDialog.getSelectedFile();
                    inputFileLabel.setText("File Selected :"
                            + file.getName());
                    inputFilePath = file.getAbsolutePath();
                } else {
                    inputFileLabel.setText("Open command cancelled by user.");
                }
            }
        });

        final JFileChooser directoryChooser = new JFileChooser();
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        directoryChooser.setAcceptAllFileFilterUsed(false);
        outputFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = directoryChooser.showOpenDialog(mainFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = directoryChooser.getSelectedFile();
                    outputFileLabel.setText("File Selected :"
                            + file.getName());
                    outPutfilePath = file.getAbsolutePath();
                } else {
                    outputFileLabel.setText("Open command cancelled by user.");
                }
            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputFilePath == null || inputFilePath.isEmpty()) {
                    statusLabel.setText("Choose input file");
                    return;
                }
                if (!inputFilePath.endsWith(".csv")) {
                    statusLabel.setText("Input file should be .txt or .csv extension");
                    return;
                }
                if (outPutfilePath == null || outPutfilePath.isEmpty()) {
                    statusLabel.setText("Choose output directory");
                    return;
                }

                ArrayList<String> query = readCsv(inputFilePath);
                if (query != null && !query.isEmpty()) {
                    setStatus("running");
                    EbayScraper scraper = new EbayScraper();
                    String outputPath = scraper.parse(query, outPutfilePath);
                    setStatus("finished file at  " + outputPath);
                }
            }
        });
        mainFrame.setVisible(true);
    }

    private ArrayList<String> readCsv(String inputFilePath) {
        ArrayList<String> list = null;
        try {
            Reader in = new FileReader(inputFilePath);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
            list = new ArrayList<>(25);
            for (CSVRecord record : records) {
                if (record.size() <= 1) {
                    continue;
                }
                String topic = record.get(1);
                if (topic != null && !topic.isEmpty()) {
                    list.add(topic);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private synchronized void setStatus(String status) {
        statusLabel.setText(status);
    }
}
