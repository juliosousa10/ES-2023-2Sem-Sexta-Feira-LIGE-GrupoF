package GrupoF.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.ArrayList;

public class CsvToHtml {
	
	private JFrame converter_window = new JFrame();
	private JTextField csvTextField;
	
		
		public boolean process_Csv(String csvFile) {
			
			if(!isCSVFile(csvFile)) {
				JOptionPane.showMessageDialog(converter_window, "O formato do ficheiro de input não é válido");
				System.exit(0);
			}
			
			// read lines of csv to a string array list
	        List<String> lines = new ArrayList<String>();
	        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
	            String firstline = reader.readLine();
	        	String currentLine;
	            while ((currentLine = reader.readLine()) != null) {
	                lines.add(currentLine);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        //embrace <td> and <tr> for lines and columns
	        for (int i = 0; i < lines.size(); i++) {
	            lines.set(i, "<tr><td>" + lines.get(i) + "</td></tr>");
	            lines.set(i, lines.get(i).replaceAll(",", "</td><td>"));
	        }
	        
	        // embrace <table> and </table>
	        lines.set(0, "<table border>" + lines.get(0));
	        lines.set(lines.size() - 1, lines.get(lines.size() - 1) + "</table>"); 
	        
	        // output result
	        
	        // Create a File writer to write the data to a html file
		    String csvPath = Paths.get(csvFile).getParent().toString();
	        String htmlFile = csvPath + File.separator + "horario.html";
	        
	        
	        try (FileWriter writer = new FileWriter(htmlFile)) {
	            for (String line : lines) {
	                writer.write(line + "\n");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return true;
	    }
			
		
		public boolean isCSVFile(String filePath) {
	        Path path = Paths.get(filePath);
	        File file = path.toFile();

	        if (file.exists() && file.isFile()) {
	            String fileName = file.getName();
	            int extensionIndex = fileName.lastIndexOf(".");
	            if (extensionIndex != -1) {
	                String fileExtension = fileName.substring(extensionIndex + 1);
	                return fileExtension.equalsIgnoreCase("csv");
	            }
	        }

	        return false;
	    }
		
		public void dealCsv_Html() {
			
			converter_window.setBounds(100, 100, 500, 250);
	    	converter_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    	converter_window.getContentPane().setLayout(null);

	        JLabel lblCsvFile = new JLabel("File:");
	        lblCsvFile.setBounds(65, 28, 70, 20);
	        converter_window.getContentPane().add(lblCsvFile);

	        csvTextField = new JTextField();
	        csvTextField.setBounds(100, 30, 250, 20);
	        converter_window.getContentPane().add(csvTextField);
	        csvTextField.setColumns(10);

	        JButton btnImport = new JButton("Import");
	        btnImport.setBounds(360, 30, 80, 20);
	        converter_window.getContentPane().add(btnImport);
	        btnImport.addActionListener(new ActionListener() {
	      	  public void actionPerformed(ActionEvent e) {
	      	        JFileChooser fileChooser = new JFileChooser();
	      	        fileChooser.setFileFilter(new FileNameExtensionFilter("Ficheiros CSV", "csv"));
	      	        int returnValue = fileChooser.showOpenDialog(null);
	      	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	      	            File selectedFile = fileChooser.getSelectedFile();
	      	            	csvTextField.setText(selectedFile.getAbsolutePath());
	              }
	          }
	      });

	        JButton btnConvert = new JButton("Convert");
	        btnConvert.setBounds(180, 80, 100, 25);
	        converter_window.getContentPane().add(btnConvert);
	        btnConvert.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                String csvFile = csvTextField.getText();
	                if (!csvFile.isEmpty() && (process_Csv(csvFile)))
	                		{JOptionPane.showMessageDialog(converter_window, "Conversão CSV -> HTML feita com sucesso! \n O ficheiro CSV foi guardado em: " + Paths.get(csvFile).getParent().toString());
	                }
	            }
	        });

	        converter_window.setVisible(true);
		}
		
		
	    
	    
	

}
