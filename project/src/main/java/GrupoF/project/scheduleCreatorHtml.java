package GrupoF.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class scheduleCreatorHtml extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public scheduleCreatorHtml() {
        setTitle("Schedule Creator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Curso");
        tableModel.addColumn("Unidade Curricular");
        tableModel.addColumn("Turno");
        tableModel.addColumn("Turma");
        tableModel.addColumn("Inscrito no turno");
        tableModel.addColumn("Dia da semana");
        tableModel.addColumn("Hora de início da aula");
        tableModel.addColumn("Hora de fim da aula");
        tableModel.addColumn("Data da aula");
        tableModel.addColumn("Sala atribuída à aula");
        tableModel.addColumn("Lotação");

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRow();
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveSchedule();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        //setVisible(true);
    }

    private void addRow() {
        String[] rowData = new String[11];
        rowData[0] = JOptionPane.showInputDialog("Curso");
        rowData[1] = JOptionPane.showInputDialog("Unidade Curricular");
        rowData[2] = JOptionPane.showInputDialog("Turno");
        rowData[3] = JOptionPane.showInputDialog("Turma");
        rowData[4] = JOptionPane.showInputDialog("Inscrito no turno");
        rowData[5] = JOptionPane.showInputDialog("Dia da semana");
        rowData[6] = JOptionPane.showInputDialog("Hora de início da aula");
        rowData[7] = JOptionPane.showInputDialog("Hora de fim da aula");
        rowData[8] = JOptionPane.showInputDialog("Data da aula");
        rowData[9] = JOptionPane.showInputDialog("Sala atribuída à aula");
        rowData[10] = JOptionPane.showInputDialog("Lotação");

        tableModel.addRow(rowData);
    }

    private void saveSchedule() {
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            writeHTML(filePath);
        }
    }

    private void writeHTML(String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath+".html"));
            writer.write("<html><body>");
            writer.write("<table border='1'>");
            writer.write("<tr>");
            for (int column = 0; column < tableModel.getColumnCount(); column++) {
                writer.write("<th>" + tableModel.getColumnName(column) + "</th>");
            }
            writer.write("</tr>");

            for (int row = 0; row < tableModel.getRowCount(); row++) {
                writer.write("<tr>");
                for (int column = 0; column < tableModel.getColumnCount(); column++) {
                    writer.write("<td>" + tableModel.getValueAt(row, column) + "</td>");
                }
                writer.write("</tr>");
            }
            
            writer.write("</table>");
            writer.write("</body></html>");
            writer.close();
            
            JOptionPane.showMessageDialog(this, "Schedule saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving schedule: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new scheduleCreatorHtml().setVisible(true);
                
            }
        });
    }
}

