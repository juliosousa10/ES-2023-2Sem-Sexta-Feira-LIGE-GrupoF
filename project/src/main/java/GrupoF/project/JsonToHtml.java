package GrupoF.project;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class JsonToHtml{

    public static void main(String[] args) {
    	JsonToHtml converter = new JsonToHtml();
        converter.convertJsonToHtml();
    }

    public void convertJsonToHtml() {
        // Get the path to the JSON file from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path to the JSON file: ");
        String jsonFilePath = scanner.nextLine();
        scanner.close();

        try {
            // Read the JSON file
            BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Object json = gson.fromJson(reader, Object.class);
            reader.close();

            // Convert JSON to HTML
            String html = convertToHtml(json);

            // Print the generated HTML
            System.out.println(html);
        } catch (IOException e) {
            System.out.println("Error reading the JSON file: " + e.getMessage());
        }
    }

    private String convertToHtml(Object json) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<!DOCTYPE html>\n");
        htmlBuilder.append("<html>\n");
        htmlBuilder.append("<head>\n");
        htmlBuilder.append("<title>JSON to HTML</title>\n");
        htmlBuilder.append("</head>\n");
        htmlBuilder.append("<body>\n");
        htmlBuilder.append("<pre>\n");
        htmlBuilder.append(new GsonBuilder().setPrettyPrinting().create().toJson(json));
        htmlBuilder.append("</pre>\n");
        htmlBuilder.append("</body>\n");
        htmlBuilder.append("</html>");

        return htmlBuilder.toString();
    }
}
