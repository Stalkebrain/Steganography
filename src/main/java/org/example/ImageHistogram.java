package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ImageHistogram {
    public static void main(String[] args) {
        try {
            // Загрузка изображения
            BufferedImage image = ImageIO.read(new File("src/main/resources/рисунок/123-stego.bmp")); // Укажите путь к изображению

            // Проверка размеров изображения
            if (image.getWidth() != 512 || image.getHeight() != 512) {
                System.out.println("Изображение должно быть размером 512x512 пикселей.");
                return;
            }

            // Создание гистограммы
            int[] histogram = new int[256];
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    int brightness = new Color(image.getRGB(i, j)).getRed();
                    histogram[brightness]++;
                }
            }

            // Запись данных гистограммы в файл
            try (FileWriter writer = new FileWriter("src/main/resources/рисунок/123-stego-histogram.txt")) {
                for (int i = 0; i < histogram.length; i++) {
                    writer.write(i + ":" + histogram[i] + "\n");
                }
            }

            // Вывод гистограммы на экран (опционально)
            for (int i = 0; i < histogram.length; i++) {
                System.out.println(i + ": " + histogram[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
