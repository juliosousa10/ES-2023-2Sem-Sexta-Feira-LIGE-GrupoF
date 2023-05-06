
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

public class GUIApp  {
	
	
    private CsvToJSon dealer;
    
    public GUIApp() {
    	dealer = new CsvToJSon();
    	
    }

    public static void main(String[] args) {
        GUIApp app = new GUIApp();
        app.initialize();
    }

    private void initialize() {
     JFrame options;
     JButton option1;
     JButton option2;
     JButton option3;
     JButton option4;
    	
    	options = new JFrame();
    	options.setBounds(100, 100, 500, 250);
    	options.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	options.getContentPane().setLayout(null);


        option1 = new JButton("CSV -> JSon");
        option1.setBounds(188, 30, 125, 25);
        options.getContentPane().add(option1);
        option1.addActionListener(new ActionListener() {
      	  public void actionPerformed(ActionEvent e) {
      		  dealer.dealCsv_Json(); 		  
          }
      });
        
        option2 = new JButton("JSon -> CSV");
        option2.setBounds(188, 65, 125, 25);
        options.getContentPane().add(option2);
        option2.addActionListener(new ActionListener() {
      	  public void actionPerformed(ActionEvent e) {
      		 //Chamar código que converte JSON em CSV
              
          }
      });
        
        option3 = new JButton("CSV -> HTML");
        option3.setBounds(188, 100, 125, 25);
        options.getContentPane().add(option3);
        option3.addActionListener(new ActionListener() {
      	  public void actionPerformed(ActionEvent e) {
      		 //Chamar código que converte CSV em HTML
              
          }
      });
        
        option4 = new JButton("JSON -> HTML");
        option4.setBounds(188, 135, 125, 25);
        options.getContentPane().add(option4);
        option4.addActionListener(new ActionListener() {
      	  public void actionPerformed(ActionEvent e) {
      		//Chamar código que converte JSON em HTML
              
          }
      });

        options.setVisible(true);
    }
}