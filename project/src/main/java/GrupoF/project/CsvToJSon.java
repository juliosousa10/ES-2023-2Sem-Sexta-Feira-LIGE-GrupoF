package pt.iscte_iul.ista.Horario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

import javax.swing.JButton;   
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class CsvToJSon {
	
	private JFrame converter_window = new JFrame();
	private JTextField csvTextField;


	public boolean processCSV(String csvFile) {
		
		if(!isCSVFile(csvFile)) {
			JOptionPane.showMessageDialog(converter_window, "O formato do ficheiro de input não é válido");
			System.exit(0);
		}
			
		
        BufferedReader br = null;
        String line = "";
        String topics="";
        String csvSplitBy = ";";

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            br = new BufferedReader(new FileReader(csvFile));
           
            br.readLine(); // Ignorar a primeira linha do arquivo CSV
            topics = br.readLine();
            String[] aux = topics.split(csvSplitBy);

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);

                if (data.length ==11) {
                	LinkedHashMap<String, Object> item = new LinkedHashMap<>();
                    
                    item.put(aux[0], data[0]);
                    item.put(aux[1], data[1]);
                    item.put(aux[2], data[2]);
                    item.put(aux[3], data[3]);
                    item.put(aux[4], data[4]);
                    item.put(aux[5], data[5]);
                    item.put(aux[6], data[6]);
                    item.put(aux[7], data[7]);
                    item.put(aux[8], data[8]);
                    item.put(aux[9], data[9]);
                    item.put(aux[10], data[10]);
                    
                    jsonArray.add(item);
                }	
            }

            jsonObject.put("Horário", jsonArray);
            
            String csvPath = Paths.get(csvFile).getParent().toString();
            String jsonFile = csvPath + File.separator + "output.json";

            FileWriter file = new FileWriter(jsonFile);
            file.write(JSONValue.toJSONString(jsonObject));
            file.flush();
            file.close();

            System.out.println(jsonObject.toJSONString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
 