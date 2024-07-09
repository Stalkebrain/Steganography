package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;

// Класс для анализа изображения
public class ImageAnalysis {
    public static void main(String[] args) throws IOException {
        // Загрузка изображения
        BufferedImage image = ImageIO.read(new File("src/main/resources/рисунок/123-stego.bmp"));

        // Получение размеров изображения
        int width = image.getWidth();
        int height = image.getHeight();

        // Размер одной части
        int partWidth = width / 32;

        // Хи-квадрат для каждой части
        double[] chiSquaredArray = new double[32];
        int[] uniqueShadesArray = new int[32]; // Массив для хранения количества уникальных оттенков серого для каждой части

        for (int i = 0; i < 32; i++) {
            // Создание карты частот для текущей части
            HashMap<Integer, Integer> frequencyMap = new HashMap<>();
            // Подсчет частот оттенков в текущей части
            for (int x = i * partWidth; x < (i + 1) * partWidth; x++) {
                for (int y = 0; y < height; y++) {
                    int rgb = image.getRGB(x, y);
                    int grayScale = (rgb >> 16) & 0xff; // Преобразование в оттенки серого

                    frequencyMap.put(grayScale, frequencyMap.getOrDefault(grayScale, 0) + 1);
                }
            }

            int uniqueShades = frequencyMap.size();
            uniqueShadesArray[i] = uniqueShades; // Сохранение количества уникальных оттенков для текущей части

            // Применение формулы хи-квадрат
            double chiSquared = 0;
            for (int j = 0; j < 128; j++) {
                double n2j = frequencyMap.getOrDefault(2 * j + 1, 0);
                double nj = frequencyMap.getOrDefault(2 * j, 0);
                double pj = (n2j + nj) / 2.0;
                if (pj != 0) { // Проверка, чтобы избежать деления на ноль
                    chiSquared += Math.pow(n2j - pj, 2) / pj;
                }
            }

            chiSquaredArray[i] = chiSquared;
        }

        // Вывод результатов
        ChiSquaredDistribution chiSquaredDistribution = new ChiSquaredDistribution(128);

        // Вывод вероятностей для каждой части
        for (int i = 0; i < chiSquaredArray.length; i++) {
            double chiSquared = chiSquaredArray[i];
            int uniqueShades = uniqueShadesArray[i]; // Получение количества уникальных оттенков для текущей части
            // Вычисление вероятности (p-value)
            double pValue = 1 - chiSquaredDistribution.cumulativeProbability(chiSquared);
            System.out.println("Часть " + (i + 1) + ": Уникальных оттенков серого = " + uniqueShades/2 + ", Хи-квадрат = " + chiSquared + ", p-значение = " + pValue);
        }
    }
}
