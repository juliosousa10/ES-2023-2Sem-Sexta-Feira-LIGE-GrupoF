
package GrupoF.project;


import javax.swing.JButton;

import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIApp  {
	
    private CsvToJSon dealer;
    private JsonToCSV dealer1;
    private CsvToHtml dealer2;
    private JsonToHtml dealer3;
    private scheduleCreatorHtml dealer4;
    
    public GUIApp() {
    	dealer = new CsvToJSon();
    	dealer1 = new JsonToCSV();
    	dealer2 = new CsvToHtml();
    	dealer3 = new JsonToHtml();
    	dealer4 = new scheduleCreatorHtml();
    }
    	

    //private JsonToHtml dealer3 = new JsonToHtml();//mudança1
    
    /*public GUIApp() {
    	dealer = new CsvToJSon();
    }*/
    
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
     JButton option5;
    	
    	options = new JFrame();
    	options.setBounds(100, 100, 500, 250);
    	//options.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
      		 dealer2.dealCsv_Html();
              
          }
      });
        
        option4 = new JButton("JSON -> HTML");
        option4.setBounds(188, 135, 125, 25);
        options.getContentPane().add(option4);
        option4.addActionListener(new ActionListener() {
      	  public void actionPerformed(ActionEvent e) {
      		//Chamar código que converte JSON em HTML
             dealer3.setVisible(true);//mudança2
          }
      });

        option5 = new JButton("Create a schedule");
        
        option5.setBounds(148, 175, 200, 25);
        
        //option5.setBounds(188, 175, 125, 25);
        options.getContentPane().add(option5);
        option5.addActionListener(new ActionListener() {
      	  public void actionPerformed(ActionEvent e) {
      		 //Chamar código que cria um horario e salva num html
              dealer4.setVisible(true);
              
          }
      });
        
        options.setVisible(true);
    }
}