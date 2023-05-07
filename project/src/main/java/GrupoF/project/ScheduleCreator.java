package GrupoF.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class ScheduleCreator {

    private static final String CSV_SEPARATOR = ",";
    private List<String> selectedUnits;
    private List<String[]> csvData;

	
    public ScheduleCreator() {
        selectedUnits = new ArrayList<String>();
        csvData = new ArrayList<String[]>();
    }

    public void selectUnits(String[] selection) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the units you want to add to the schedule (one per line), or 'done' when finished:");
        String input = "";
        while (!input.equals("done")) {
            input = scanner.nextLine();
            if (!input.equals("done")) {
                selectedUnits.add(input);
            }
        }
        System.out.println("Ficheiro guardado com sucesso");
        scanner.close();
    }

    public void importCsv(String csvFile) throws IOException {
    	
    	csvFile = "/Users/salvadorlopes/Downloads/horario_exemplo.csv";
			
		
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

    public void createSchedule(String outputFormat) throws IOException {
    	JSONArray scheduleArray = new JSONArray();
    	for (String[] fields : csvData) {
    		if (selectedUnits.contains(fields[0])) {
    			JSONObject scheduleObj = new JSONObject();
    			scheduleObj.put("Unit", fields[0]);
    			scheduleObj.put("Day", fields[1]);
    			scheduleObj.put("Time", fields[2]);
    			scheduleArray.add(scheduleObj);
    		}
    	}
    	String outputFilePath = "/Users/salvadorlopes/Downloads/schedule." + outputFormat;
    	if (outputFormat.equals("json")) {
    		FileWriter writer = new FileWriter(outputFilePath);
    		writer.write(scheduleArray.toJSONString());
    		writer.close();
    	} else if (outputFormat.equals("csv")) {
    		FileWriter writer = new FileWriter(outputFilePath);
    		for (Object obj : scheduleArray) {
    			JSONObject scheduleObj = (JSONObject) obj;
    			String[] fields = new String[3];
    			fields[0] = (String) scheduleObj.get("Unit");
    			fields[1] = (String) scheduleObj.get("Day");
    			fields[2] = (String) scheduleObj.get("Time");
    			writer.write(String.join(CSV_SEPARATOR, fields) + "\n");
    		}
    		writer.close();
    	}
    }
    
    public List<String[]> getOverlappingLessons() {
        List<String[]> overlappingLessons = new ArrayList<>();
        Map<String, List<String[]>> dayToLessons = new HashMap<>();
        
        // organize the lessons by day
        for (String[] fields : csvData) {
            List<String[]> lessons = dayToLessons.getOrDefault(fields[1], new ArrayList<>());
            lessons.add(fields);
            dayToLessons.put(fields[1], lessons);
        }
        
        // check for overlapping lessons for each day
        for (List<String[]> lessons : dayToLessons.values()) {
            for (int i = 0; i < lessons.size() - 1; i++) {
                for (int j = i + 1; j < lessons.size(); j++) {
                    String[] lesson1 = lessons.get(i);
                    String[] lesson2 = lessons.get(j);
                    if (lesson1[2].equals(lesson2[2])) { // same time
                        String start1 = lesson1[3];
                        String end1 = lesson1[4];
                        String start2 = lesson2[3];
                        String end2 = lesson2[4];
                        if ((start1.compareTo(start2) <= 0 && end1.compareTo(start2) > 0) || // lesson1 starts before lesson2 and ends during lesson2
                                (start1.compareTo(end2) < 0 && end1.compareTo(end2) >= 0) || // lesson1 starts during lesson2 and ends after lesson2
                                (start2.compareTo(start1) <= 0 && end2.compareTo(start1) > 0) || // lesson2 starts before lesson1 and ends during lesson1
                                (start2.compareTo(end1) < 0 && end2.compareTo(end1) >= 0)) { // lesson2 starts during lesson1 and ends after lesson1
                            overlappingLessons.add(lesson1);
                            overlappingLessons.add(lesson2);
                        }
                    }
                }
            }
        }
        
        return overlappingLessons;
    }

    
   
}
