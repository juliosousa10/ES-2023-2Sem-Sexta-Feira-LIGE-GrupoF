package GrupoF.project;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JsonToHtml extends JFrame{

	private JTextField jsonFilePathField;
    private JTextField outputFilePathField;
	
    public static void main(String[] args) {
    	JsonToHtml converter = new JsonToHtml();
        //converter.convertJsonToHtml();
    	converter.setVisible(true);
    }

    public JsonToHtml() {
        setTitle("JSON to HTML Converter");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createComponents();
    }

    private void createComponents() {
        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));

        // Create the JSON file path components
        JPanel jsonFilePanel = new JPanel(new FlowLayout());
        JLabel jsonFileLabel = new JLabel("JSON File:");
        jsonFilePathField = new JTextField(20);
        JButton jsonFileButton = new JButton("Browse...");
        jsonFileButton.addActionListener(new JsonFileButtonListener());
        jsonFilePanel.add(jsonFileLabel);
        jsonFilePanel.add(jsonFilePathField);
        jsonFilePanel.add(jsonFileButton);

        // Create the output file path components
        JPanel outputPanel = new JPanel(new FlowLayout());
        JLabel outputLabel = new JLabel("Output File:");
        outputFilePathField = new JTextField(20);
        JButton outputFileButton = new JButton("Browse...");
        outputFileButton.addActionListener((ActionListener) new OutputFileButtonListener());
        outputPanel.add(outputLabel);
        outputPanel.add(outputFilePathField);
        outputPanel.add(outputFileButton);

        // Create the convert button
        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(new ConvertButtonListener());

        // Add the components to the main panel
        mainPanel.add(jsonFilePanel);
        mainPanel.add(outputPanel);
        mainPanel.add(convertButton);

        // Add the main panel to the frame
        add(mainPanel);
    }

    private class JsonFileButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select JSON File");
            fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                jsonFilePathField.setText(selectedFile.getAbsolutePath());
            }
        }
    }

    private class OutputFileButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Output File");
            fileChooser.setFileFilter(new FileNameExtensionFilter("HTML Files", ".html"));
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                
                String filePath = selectedFile.getAbsolutePath()+".html";//todos os ficheiros terem a extensão .html no fim
                outputFilePathField.setText(filePath);
                
                //outputFilePathField.setText(selectedFile.getAbsolutePath());
            }
        }
    }

    private class ConvertButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String jsonFilePath = jsonFilePathField.getText();
            String outputFilePath = outputFilePathField.getText();

            try {
                // Read the JSON file
                BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath));
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Object json = gson.fromJson(reader, Object.class);
                reader.close();

                // Convert JSON to HTML
                String html = convertToHtml(json);

                // Save HTML to the specified file path
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
                writer.write(html);
                writer.close();

                JOptionPane.showMessageDialog(null, "Conversion completed successfully.");
            } catch (IOException e1) {
                System.out.println("Error reading the JSON file: " + e1.getMessage());
            }
        }
        
    private String convertToHtml(Object json) {
    	StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<table>");

        if (json instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) json;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                htmlBuilder.append("<tr><th>").append(entry.getKey()).append("</th><td>");
                htmlBuilder.append(convertToHtml(entry.getValue()));
                htmlBuilder.append("</td></tr>");
            }
        } else if (json instanceof List) {
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) json;
            for (Object item : list) {
                htmlBuilder.append("<tr><td>");
                htmlBuilder.append(convertToHtml(item));
                htmlBuilder.append("</td></tr>");
            }
        } else {
            htmlBuilder.append("<tr><td>").append(json).append("</td></tr>");
        }

        htmlBuilder.append("</table>");
        return htmlBuilder.toString();
     }
   }
}