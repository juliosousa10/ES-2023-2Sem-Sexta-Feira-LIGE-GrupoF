package GrupoF.project;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import com.opencsv.CSVWriter;
import javax.swing.JButton;   
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JsonToCSV {
	
	private JFrame converter_window = new JFrame();
	private JTextField jsonTextField;


	public boolean process_Json(String jsonFile) {
	
		if(!isJsonFile(jsonFile)) {
			JOptionPane.showMessageDialog(converter_window, "O formato do ficheiro de input não é válido");
			System.exit(0);
		}
		 // Read the JSON file
	    File jsonFile1 = new File(jsonFile);
	
	    ObjectMapper mapper = new ObjectMapper();
	    
	    
	    try {
	    JsonNode jsonNode = mapper.readTree(jsonFile1);
	
	    //System.out.println(jsonNode.get("Horário").toPrettyString());
	
	    JsonNode firstObject = jsonNode.get("Horário").get(0);
	    //System.out.println(firstObject.toPrettyString());
	    //System.out.println(firstObject.size());
	    String[] header = new String[firstObject.size()];
	
	    Iterator<JsonNode> iter = firstObject.elements();
	
	    int index = 0;
	    while(iter.hasNext()){
	      String fieldName = iter.next().asText();
	      
	      //System.out.println(fieldName);
	      header[index++] = fieldName;
	    }
	    // Create a CSV writer to write the data to a file
	    String csvPath = Paths.get(jsonFile).getParent().toString();
        String csvFile = csvPath + File.separator + "horario.csv";
	    
	    CSVWriter writer = new CSVWriter(new FileWriter(csvFile));
	    String[] separator = new String[1];
	    separator[0] = "sep=,";
	    writer.writeNext(separator);
	    writer.writeNext(header);
	
	    JsonNode h_Obj = jsonNode.get("Horário");
	
	    for(int i = 1; i < h_Obj.size(); i++){
	      JsonNode node = h_Obj.get(i);
	      String[] row = new String[node.size()];
	      //System.out.println(node.toPrettyString());
	
	      Iterator<JsonNode> iter_n = node.elements();
	
	      int j = 0;
	      while(iter_n.hasNext()){
	        String value = iter_n.next().asText();
	        //System.out.println(value);
	        row[j++] = value;
	      }
	      
	      writer.writeNext(row);
	    }
	
	
	    writer.close();
	
	    // Close the CSV writer
	    
	    } catch (IOException e) {
            e.printStackTrace();
        
        }
	    
	    return true;
	    
	    }
	
	public void dealJson_Csv() {
		
		converter_window.setBounds(100, 100, 500, 250);
    	converter_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    	converter_window.getContentPane().setLayout(null);

        JLabel lblCsvFile = new JLabel("File:");
        lblCsvFile.setBounds(65, 28, 70, 20);
        converter_window.getContentPane().add(lblCsvFile);

        jsonTextField = new JTextField();
        jsonTextField.setBounds(100, 30, 250, 20);
        converter_window.getContentPane().add(jsonTextField);
        jsonTextField.setColumns(10);

        JButton btnImport = new JButton("Import");
        btnImport.setBounds(360, 30, 80, 20);
        converter_window.getContentPane().add(btnImport);
        btnImport.addActionListener(new ActionListener() {
      	  public void actionPerformed(ActionEvent e) {
      	        JFileChooser fileChooser = new JFileChooser();
      	        fileChooser.setFileFilter(new FileNameExtensionFilter("Ficheiros Jason", "json"));
      	        int returnValue = fileChooser.showOpenDialog(null);
      	        if (returnValue == JFileChooser.APPROVE_OPTION) {
      	            File selectedFile = fileChooser.getSelectedFile();
      	            	jsonTextField.setText(selectedFile.getAbsolutePath());
              }
          }
      });

        JButton btnConvert = new JButton("Convert");
        btnConvert.setBounds(180, 80, 100, 25);
        converter_window.getContentPane().add(btnConvert);
        btnConvert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String jsonFile = jsonTextField.getText();
                if (!jsonFile.isEmpty() && (process_Json(jsonFile)))
                		{JOptionPane.showMessageDialog(converter_window, "Conversão JSON -> CSV feita com sucesso! \n O ficheiro CSV foi guardado em: " + Paths.get(jsonFile).getParent().toString());
                }
            }
        });

        converter_window.setVisible(true);
	}
	
	public boolean isJsonFile(String filePath) {
        Path path = Paths.get(filePath);
        File file = path.toFile();

        if (file.exists() && file.isFile()) {
            String fileName = file.getName();
            int extensionIndex = fileName.lastIndexOf(".");
            if (extensionIndex != -1) {
                String fileExtension = fileName.substring(extensionIndex + 1);
                return fileExtension.equalsIgnoreCase("json");
            }
        }

        return false;
    }
	}
