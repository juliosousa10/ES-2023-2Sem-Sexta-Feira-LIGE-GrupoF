package GrupoF.project;
import java.util.*;  

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

/*
import com.aspose.cells.Cells;
import com.aspose.cells.JsonLayoutOptions;
import com.aspose.cells.JsonUtility;
import com.aspose.cells.License;
import com.aspose.cells.Workbook;
import java.nio.file.Files;
import java.nio.file.Paths;*/

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import com.opencsv.CSVWriter;

public class App {
	


    public static void main(String[] args) throws IOException {
   
        
     // Define o nome do arquivo CSV de entrada e do arquivo JSON de saída
    	Scanner sc= new Scanner(System.in);
        System.out.print("Enter an URL to load the file from: ");  
        String csvFile = sc.nextLine();
        
        String jsonFile = "output.json";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";

        // Cria um objeto JSON para armazenar os dados do CSV
        JSONObject jsonObject = new JSONObject();

        // Cria um array JSON para armazenar as linhas do CSV
        JSONArray jsonArray = new JSONArray();

        try {

            // Lê o arquivo CSV e adiciona cada linha ao array JSON
            br = new BufferedReader(new FileReader(csvFile));

            // Ignora a primeira linha do arquivo CSV
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);

                if (data.length == 11) {
                  JSONObject item = new JSONObject();
                  item.put("Curso:", data[0]);
                  item.put("Unidade Curricular:", data[1]);
                  item.put("Turno:", data[2]);
                  item.put("Turma:", data[3]);
                  item.put("Inscritos:", data[4]);
                  item.put("Dia de semana:", data[5]);
                  item.put("Hora início:", data[6]);
                  item.put("Hora fim:", data[7]);
                  item.put("Data:", data[8]);
                  item.put("Sala:", data[9]);
                  item.put("Lotação:", data[10]);
                  jsonArray.add(item);
                  
                }
              }
              
              // Adiciona o array JSON ao objeto JSON
              jsonObject.put("Horário", jsonArray);
              
              // Escreve o objeto JSON no arquivo JSON de saída
              FileWriter file = new FileWriter(jsonFile);
              file.write(jsonObject.toJSONString());
              file.flush();
              file.close();
              
              System.out.println("Arquivo JSON criado com sucesso!");
              //System.out.println(jsonObject.toJSONString()); 
              
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
    
    
        // Read the JSON file
        File jsonFile1 = new File("output.json");

        // Parse the JSON file into a JsonNode object using Jackson
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonFile1);

        // Get the keys from the first JSON object as the header row for CSV
        Iterator<JsonNode> iter = jsonNode.elements();
        JsonNode firstObject = iter.next();
        String[] header = new String[firstObject.size()];
        int index = 0;
        System.out.println("ola");
        //if (firstObject instanceof ObjectNode) {
            //ObjectNode firstObjNode = (ObjectNode) firstObject;
            
            Iterator<String> it = firstObject.fieldNames();
            
            while (it.hasNext()){
                String fieldName = it.next();
                System.out.println(fieldName);
                header[index++] = fieldName;
            }
            
        //}

        // Create a CSV writer to write the data to a file
        CSVWriter writer = new CSVWriter(new FileWriter("output.csv"));
        writer.writeNext(header); // write the header row

        // Write each JSON object as a CSV row
        iter = jsonNode.elements();
        while (iter.hasNext()) {
            JsonNode node = iter.next();
            String[] row = new String[node.size()];
            index = 0;
            
            //if (node instanceof ObjectNode) {
               // ObjectNode objNode = (ObjectNode) node;
                System.out.println(node.size());
                Iterator<String> it1 = node.fieldNames();
                
                while (it1.hasNext()){
                    String fieldName = it1.next();
                    //header[index++] = fieldName;
                    row[index++] = node.get(fieldName).asText();
                }
                
           // }
            writer.writeNext(row);
            System.out.print("ficheiro csv criado");
            
        }
        
        // Close the CSV writer
        writer.close();
        
        
    }
    
    
    
}


