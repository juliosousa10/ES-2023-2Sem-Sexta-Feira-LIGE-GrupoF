package GrupoF.project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GUIAppTest {

    private GUIApp app;

    @BeforeEach
    void setUp() {
        app = new GUIApp();
    }

    @Test
    void testInitialization() {
        assertNotNull(app.dealer);
        assertNotNull(app.dealer1);
        assertNotNull(app.dealer2);
    }

    @Test
    void testOption1Button() {
        JButton option1 = new JButton("CSV -> JSon");
        option1.doClick();
        File file = new File("output.json");
        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertTrue(file.canRead());
    }

    @Test
    void testOption2Button() {
        JButton option2 = new JButton("JSon -> CSV");
        option2.doClick();
        File file = new File("output.csv");
        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertTrue(file.canRead());
    }

    @Test
    void testOption3Button() {
        JButton option3 = new JButton("CSV -> HTML");
        option3.doClick();
        File file = new File("output.html");
        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertTrue(file.canRead());
    }

    @Test
    void testCreateScheduleButton() throws IOException {
        JButton createSchedule = new JButton("Criar Horário");

        // Set up the frame and components that are needed for this test
        JFrame scheduleOptions = new JFrame();
        scheduleOptions.setBounds(100, 100, 500, 400);
        scheduleOptions.getContentPane().setLayout(null);
        JList<String> unitList = new JList<>(new String[]{"Teoria dos Jogos", "Arquitetura de Computadores", "Investimentos II"});
        unitList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        unitList.setBounds(50, 50, 200, 200);
        scheduleOptions.getContentPane().add(unitList);
        JRadioButton jsonButton = new JRadioButton("JSON", true);
        jsonButton.setBounds(300, 75, 100, 25);
        ButtonGroup formatGroup = new ButtonGroup();
        formatGroup.add(jsonButton);
        scheduleOptions.getContentPane().add(jsonButton);
        JRadioButton csvButton = new JRadioButton("CSV");
        csvButton.setBounds(300, 100, 100, 25);
        formatGroup.add(csvButton);
        scheduleOptions.getContentPane().add(csvButton);
        createSchedule.setBounds(175, 275, 150, 25);
        scheduleOptions.getContentPane().add(createSchedule);
        scheduleOptions.setVisible(true);

        createSchedule.doClick();

        ScheduleCreator creator = new ScheduleCreator();
        creator.selectUnits(new String[]{"Teoria dos Jogos", "Arquitetura de Computadores", "Investimentos II"});

        if (jsonButton.isSelected()) {
            creator.createSchedule("json");
        } else {
            creator.createSchedule("csv");
        }

        File file = new File("horario." + (jsonButton.isSelected() ? "json" : "csv"));
        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertTrue(file.canRead());

        // Clean up the test output file
        file.delete();
    }

}
