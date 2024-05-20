package com.example.demo;
import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@WebServlet(name = "lab3_json", value = "/rep")
public class lab3_json extends HttpServlet {
    private static final String FILE_PATH = "C:\\Users\\4\\IdeaProjects\\demo\\src\\main\\java\\com\\example\\demo\\rep.json";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String Json = readCarsFromFile();
        response.getWriter().write(Json);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder jsonRequest = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonRequest.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ошибка");
            return;
        }

        JSONObject newCarJson = new JSONObject(jsonRequest.toString());
        JSONArray carsJsonArray = new JSONArray(readCarsFromFile());
        carsJsonArray.put(newCarJson);

        writeCarsToFile(carsJsonArray.toString());
        response.getWriter().write(carsJsonArray.toString());
    }

    private String readCarsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "[]";
        }
    }
    private void writeCarsToFile(String carsJson) {
        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            fileWriter.write(carsJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}