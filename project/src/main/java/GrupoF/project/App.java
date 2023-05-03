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

public class App {

    public static void main(String[] args) {
   
        
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
          }
}



/*
public class ConvertJSONtoCSVInJava {
    public static void main(String[] args) throws Exception { // main method to convert JSON to comma-separated value file in Java

    // Set Aspose.Cells library license to remove trial version watermark after converting JSON to CSV
    License licenseToConvertJSON = new License();
    licenseToConvertJSON.setLicense("Aspose.Cells.lic");

    // Read input JSON file
    String content = new String(Files.readAllBytes(Paths.get("output.json")));

    // Initialize a Workbook class instance which will hold output CSV data read from JSON string
    Workbook workbook = new Workbook();

    // Access the cells
    Cells cells = workbook.getWorksheets().get(0).getCells();

    // Set JsonLayoutOptions properties
    JsonLayoutOptions options = new JsonLayoutOptions();
    options.setConvertNumericOrDate(true);
    options.setArrayAsTable(true);
    options.setIgnoreArrayTitle(true);
    options.setIgnoreObjectTitle(true);
    JsonUtility.importData(content, cells, 0, 0, options);

    // Save output CSV file
    workbook.save("Output.csv");
    }
}
}*/