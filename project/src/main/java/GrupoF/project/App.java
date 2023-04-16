package GrupoF.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class App {

    public static void main(String[] args) {

        // Define o nome do arquivo CSV de entrada e do arquivo JSON de saída
        String csvFile = "C:\\Users\\User\\Downloads\\horario_exemplo.csv";
        String jsonFile = "output.json";

        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";

        // Cria um objeto JSON para armazenar os dados do CSV
        JSONObject jsonObject = new JSONObject();

        // Cria um array JSON para armazenar as linhas do CSV
        JSONArray jsonArray = new JSONArray();

        try {

            // Lê o arquivo CSV e adiciona cada linha ao array JSON
            br = new BufferedReader(new FileReader(csvFile));

            // Ignora a primeira linha do arquivo CSV
            br.readLine();
