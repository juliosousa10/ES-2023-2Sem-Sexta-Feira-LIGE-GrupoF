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

        ObjectMapper mapper = new ObjectMapper();
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
        CSVWriter writer = new CSVWriter(new FileWriter("output.csv"));
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
        }
    
    
    
}


