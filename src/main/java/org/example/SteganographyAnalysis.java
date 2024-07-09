package org.example;

import java.io.*;
import java.util.*;

public class SteganographyAnalysis {
    public static void main(String[] args) {
        // Путь к файлу с данными
        String filePath = "src/main/resources/рисунок/123-stego-histogram.txt";
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();
        double chiSquared = 0;

        try {
            // Чтение данных из файла
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                int key = Integer.parseInt(parts[0]);
                int value = Integer.parseInt(parts[1]);
                frequencyMap.put(key, value);
            }
            reader.close();

            // Вычисление χ²
            for (int j = 0; j < 128; j++) {
                double n2j = frequencyMap.getOrDefault(2*j+1, 0);
                double nj = frequencyMap.getOrDefault(2*j, 0);
                double pj = (n2j + nj) / 2.0;
                if (pj != 0) { // Проверка, чтобы избежать деления на ноль
                    chiSquared += Math.pow(n2j - pj, 2) / pj;
                }
                System.out.println("n2j = " + n2j + ", nj = " + nj + ", pj = " + pj);
            }

            // Вывод результата
            System.out.println("Значение χ²: " + chiSquared);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
