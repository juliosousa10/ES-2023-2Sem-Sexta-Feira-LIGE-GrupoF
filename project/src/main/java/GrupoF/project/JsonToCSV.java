package proj;

import java.io.File;
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

public class JsonToCSV {

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
    }
	
}
