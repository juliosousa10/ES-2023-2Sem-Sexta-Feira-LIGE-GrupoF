package GrupoF.project;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.json.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws JSONException 
    {
      //  System.out.println( "Hello World!" );
    
    	String jsonArrayString = "{\fileName\": [{\\\"first name\\\": \\\"GrupoF\\\",\\\"location\\\": \\\"gitHub\\\"}]}"; 
    	JSONObject output;
    	try {
    		JSONArray docs = output.getJSONArray("filename");
    		File file = new File("File.csv");
    		String csv = CDL.toString(docs);
    		FileUtils.writeStringToFile(file, csv);
    		System.out.println(csv);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    }
}
