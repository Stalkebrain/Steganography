package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class StegoLSB {
    public static void main(String[] args) {
        try {
            // Загрузка изображения
            BufferedImage image = ImageIO.read(new File("E:\\Users\\Stalkerbrain\\Диплом\\Проги\\untitled\\джоджо\\jojo.bmp"));

            // Проверка размеров изображения
            if (image.getWidth() != 512 || image.getHeight() != 512) {
                System.out.println("Изображение должно быть размером 512x512 пикселей.");
                return;
            }

            // Проход по всем пикселям изображения
            for (int i = 0; i < 512; i++) {
                for (int j = 0; j < 512; j++) {
                    // Получение текущего оттенка серого
                    int color = image.getRGB(i, j);
                    int alpha = (color >> 24) & 0xff;
                    int gray = color & 0xff;

                    // Применение правила стеговложения
                    gray = applyStegoRule(gray);

                    // Установка нового оттенка серого
                    int newColor = (alpha << 24) | (gray << 16) | (gray << 8) | gray;
                    image.setRGB(i, j, newColor);
                }
            }

            // Сохранение изменённого изображения
            ImageIO.write(image, "bmp", new File("E:\\Users\\Stalkerbrain\\Диплом\\Проги\\untitled\\джоджо\\jojo_stego.bmp"));
            System.out.println("Стеговложение выполнено и изображение сохранено.");

        } catch (Exception e) {
            System.out.println("Ошибка при обработке изображения.");
            e.printStackTrace();
        }
    }

    // Метод для применения правила стеговложения
    private static int applyStegoRule(int grayValue) {
        if (grayValue % 2 == 0) {
            // Если LSB исходного оттенка равен 0, то оставляем его чётным
            return grayValue;
        } else {
            // Если LSB исходного оттенка равен 1, то делаем его чётным
            return grayValue - 1;
        }
    }
}

