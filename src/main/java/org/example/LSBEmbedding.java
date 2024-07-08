package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class LSBEmbedding {
    public static void main(String[] args) {
        try {
            // Загрузка изображения
            File inputFile = new File("E:\\Users\\Stalkerbrain\\Диплом\\Проги\\untitled\\джоджо\\jojo.bmp");
            BufferedImage image = ImageIO.read(inputFile);

            // Проверка размеров изображения
            if (image.getWidth() != 512 || image.getHeight() != 512) {
                System.out.println("Изображение должно быть размером 512x512 пикселей.");
                return;
            }

            // Сообщение для встраивания
            String message = "Ваше сообщение здесь";
            byte[] messageBytes = message.getBytes();
            int messageLength = messageBytes.length;

            // Процент изображения для встраивания сообщения
            double embeddingPercentage = 1; // 10% от изображения
            int totalPixels = 512 * 512;
            int embeddingPixels = (int)(totalPixels * embeddingPercentage);
            int bitIndex = 0;

            // Встраивание сообщения в изображение
            for (int i = 0; i < messageLength; i++) {
                for (int bit = 7; bit >= 0; bit--) {
                    if (bitIndex >= embeddingPixels) break; // Прекращаем встраивание после достижения заданного процента

                    int bitValue = (messageBytes[i] >> bit) & 1;
                    int x = bitIndex % 512;
                    int y = bitIndex / 512;
                    int currentPixel = image.getRGB(x, y);
                    int newPixel;

                    if (bitValue == 1) {
                        newPixel = currentPixel | 1; // Установка LSB в 1
                    } else {
                        newPixel = currentPixel & ~1; // Установка LSB в 0
                    }

                    image.setRGB(x, y, newPixel);
                    bitIndex++;
                }
            }

            // Сохранение измененного изображения
            File outputFile = new File("E:\\Users\\Stalkerbrain\\Диплом\\Проги\\untitled\\джоджо\\jojo_stego.bmp");
            ImageIO.write(image, "bmp", outputFile);
            System.out.println("Сообщение встроено в " + embeddingPercentage * 100 + "% изображения.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
