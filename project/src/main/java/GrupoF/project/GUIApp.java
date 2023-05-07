
package GrupoF.project;


import javax.swing.JButton;

import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class GUIApp {
    public CsvToJSon dealer;
    public JsonToCSV dealer1;
    public CsvToHtml dealer2;

    public GUIApp() {
        dealer = new CsvToJSon();
        dealer1 = new JsonToCSV();
        dealer2 = new CsvToHtml();
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
        JButton createSchedule;
        JButton btnSobreposicao;

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
        
        ScheduleCreator sobreposicao = new ScheduleCreator();
        btnSobreposicao = new JButton("Visualizar aulas sobrepostas");
        btnSobreposicao.setBounds(188, 30, 125, 25);
        options.getContentPane().add(btnSobreposicao);
        btnSobreposicao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sobreposicao.getOverlappingLessons();
            }
        });
        btnSobreposicao.setVisible(true);

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
            }
        });

        createSchedule = new JButton("Criar Horário");
        createSchedule.setBounds(30, 30, 125, 25);
        options.getContentPane().add(createSchedule);
        createSchedule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abrir a janela para selecionar as "Unidade Curricular", o formato do ficheiro de output e um botão para a criação do horário
                JFrame scheduleCreatorWindow = new JFrame();
                scheduleCreatorWindow.setBounds(200, 200, 500, 500);
                scheduleCreatorWindow.getContentPane().setLayout(null);

                // Adicionar os componentes para selecionar as "Unidade Curricular", o formato do ficheiro de output e um botão para a criação do horário
                // ...
                JFrame scheduleOptions;
                JButton createScheduleButton;
                JList<String> unitList;
                ButtonGroup formatGroup;
                JRadioButton jsonButton, csvButton;

                scheduleOptions = new JFrame();
                scheduleOptions.setBounds(100, 100, 500, 400);
                scheduleOptions.getContentPane().setLayout(null);

                // Adicionar lista de seleção para escolher as unidades curriculares
                String[] units = {/*"Teoria dos Jogos", "Arquitetura de Computadores", "Investimentos II"*/};
                unitList = new JList<String>(units);
                unitList.setBounds(50, 50, 200, 200);
                unitList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                scheduleOptions.getContentPane().add(unitList);

                // Adicionar grupo de botões de opção para escolher o formato do ficheiro de output
                formatGroup = new ButtonGroup();
                jsonButton = new JRadioButton("JSON", true);
                jsonButton.setBounds(300, 75, 100, 25);
                formatGroup.add(jsonButton);
                scheduleOptions.getContentPane().add(jsonButton);

                csvButton = new JRadioButton("CSV");
                csvButton.setBounds(300, 100, 100, 25);
                formatGroup.add(csvButton);
                scheduleOptions.getContentPane().add(csvButton);

                // Adicionar botão para criar o horário com as opções selecionadas
                createScheduleButton = new JButton("Criar Horário");
                createScheduleButton.setBounds(175, 275, 150, 25);
                scheduleOptions.getContentPane().add(createScheduleButton);
                createScheduleButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Chamar o método createSchedule() da classe ScheduleCreator com as unidades curriculares selecionadas
                        String[] selectedUnits = unitList.getSelectedValuesList().toArray(new String[0]);
                        ScheduleCreator creator = new ScheduleCreator();
                        creator.selectUnits(selectedUnits);

                        // Verificar qual o formato de output selecionado e chamar o método correspondente da classe ScheduleCreator
                        try {
                        	if (jsonButton.isSelected()) {

                        		creator.createSchedule("json");
                        	} else {
                        		creator.createSchedule("csv");
                        	}
                        } catch (IOException e1) {
                        	// TODO Auto-generated catch block
                        	e1.printStackTrace();
                        }


                        // Fechar a janela de opções de horário
                        scheduleOptions.dispose();
                    }
                });

                scheduleOptions.setVisible(true);


                scheduleCreatorWindow.setVisible(true);
            }
        });

      	  public void actionPerformed(ActionEvent e) {
      		//Chamar código que converte JSON em HTML
             
          }
      });


        options.setVisible(true);
    }

}