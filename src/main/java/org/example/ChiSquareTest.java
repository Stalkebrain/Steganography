package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChiSquareTest {
    public static void main(String[] args) {
        String filePath = "E:\\Users\\Stalkerbrain\\Диплом\\Проги\\untitled\\histogram_data.txt"; // Замените на путь к вашему файлу
        Map<Integer, Integer> observedFrequencies = new HashMap<>();
        int totalPixels = 0;

        // Чтение файла и заполнение карты частот
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                int brightness = Integer.parseInt(parts[0]);
                int frequency = Integer.parseInt(parts[1]);
                observedFrequencies.put(brightness, frequency);
                totalPixels += frequency;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Вычисление ожидаемой частоты
        double expectedFrequency = (double) totalPixels / 256;

        // Вычисление хи-квадрат статистики
        double chiSquare = 0.0;
        for (int i = 0; i < 256; i++) {
            int observed = observedFrequencies.getOrDefault(i, 0);
            chiSquare += Math.pow(observed - expectedFrequency, 2) / expectedFrequency;
        }

        System.out.println("Хи-квадрат статистика: " + chiSquare);
    }
}
