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
    private JButton seleniumButton;
    private JPanel seleniumPanel;
    private String inputFilePath;
    private String outPutfilePath;
    private String seleniumFilePath;

    public void setUpUi() {
        prepareGUI();
        showFileChooserDemo();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Java Swing Examples");
        mainFrame.setSize(600, 600);
        mainFrame.setLayout(new GridLayout(8, 1));
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
        seleniumButton = new JButton("Select Selenium Driver");
        seleniumPanel = new JPanel();
        runButton = new JButton("Run");
        inputFilePanel = new JPanel();
        outputFilePanel = new JPanel();
        runPanel = new JPanel();
        outputFilePanel.setLayout(new FlowLayout());
        inputFilePanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(seleniumPanel);
        seleniumPanel.add(seleniumButton);
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
                if (seleniumFilePath == null || seleniumFilePath.isEmpty()) {
                    statusLabel.setText("Set the selenium file");
                    return;
                }
                if (inputFilePath == null || inputFilePath.isEmpty()) {
                    statusLabel.setText("Choose input file");
                    return;
                }
                if (!inputFilePath.endsWith(".txt")) {
                    statusLabel.setText("Input file should be .txt");
                    return;
                }
                if (outPutfilePath == null || outPutfilePath.isEmpty()) {
                    statusLabel.setText("Choose output directory");
                    return;
                }
                setStatus("running");
                ResultCombiner.parse(inputFilePath, outPutfilePath, seleniumFilePath);
                setStatus("finished file at  " + outPutfilePath);
            }
        });
        seleniumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileDialog.showOpenDialog(mainFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = fileDialog.getSelectedFile();
                    statusLabel.setText("Selenium File Selected :"
                            + file.getName());
                    seleniumFilePath = file.getAbsolutePath();
                } else {
                    statusLabel.setText("Open command cancelled by user.");
                }
            }
        });
        mainFrame.setVisible(true);
    }

    private synchronized void setStatus(String status) {
        statusLabel.setText(status);
    }
}
