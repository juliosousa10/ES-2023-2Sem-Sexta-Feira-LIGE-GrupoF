package GrupoF.project;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class App {
  
  public static void main(String[] args) {
    
    // Define o nome do arquivo CSV de entrada e do arquivo JSON de saída
    String csvFile = "C:\\Users\\User\\Downloads\\horario_exemplo.csv";
    String jsonFile = "output.json";
    
    BufferedReader br = null;
    String line = "";
    String csvSplitBy = ",";
    
    // Cria um objeto JSON para armazenar os dados do CSV
    JSONObject jsonObject = new JSONObject();
    
    // Cria um array JSON para armazenar as linhas do CSV
    JSONArray jsonArray = new JSONArray();
    
    try {
      
      // Lê o arquivo CSV e adiciona cada linha ao array JSON
      br = new BufferedReader(new FileReader(csvFile));
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
