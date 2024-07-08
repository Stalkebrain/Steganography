package org.example;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.UnsupportedEncodingException;

public class Steganography2 {

    public static void main(String[] args) {
        // Загрузите изображение BMP
        BufferedImage image = loadImage("E:\\Users\\Stalkerbrain\\Диплом\\Проги\\untitled\\sample.bmp");

        // Сообщение для встраивания
        String message = "4333333333333334343333333333333333333333333333333ссссссссссссссссссссссссссdddddddddddddddddddddddddddddddddddddddddddd";
        // Процент встраивания
        double percentage = 1; // 10%

        // Встраивание сообщения
        embedMessage(image, message, percentage);

        // Извлечение сообщения
        String extractedMessage = extractMessage(image, percentage);
        System.out.println("Извлеченное сообщение: " + extractedMessage);
    }

    private static BufferedImage loadImage(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке изображения.");
            e.printStackTrace();
        }
        return img;
    }

    private static void embedMessage(BufferedImage img, String msg, double percentage) {
        byte[] msgBytes = msg.getBytes();
        int messageLength = msgBytes.length;
        int imageCapacity = img.getWidth() * img.getHeight();
        int bitsToEmbed = (int) (imageCapacity * percentage);
        int bitIndex = 0;

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                if (bitIndex < bitsToEmbed) {
                    // Получаем текущий пиксель
                    int pixel = img.getRGB(i, j);
                    // Получаем бит сообщения
                    int bit = (bitIndex < messageLength * 8) ? (msgBytes[bitIndex / 8] >> (7 - bitIndex % 8)) & 1 : 0;
                    // Устанавливаем LSB в пикселе
                    int newPixel = (pixel & 0xFFFFFFFE) | bit;
                    img.setRGB(i, j, newPixel);
                    bitIndex++;
                }
            }
        }
    }

    private static String extractMessage(BufferedImage img, double percentage) {
        int imageCapacity = img.getWidth() * img.getHeight();
        int bitsToExtract = (int) (imageCapacity * percentage);
        byte[] message = new byte[bitsToExtract / 8];
        int bitIndex = 0;

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                if (bitIndex < bitsToExtract) {
                    // Получаем текущий пиксель
                    int pixel = img.getRGB(i, j);
                    // Получаем LSB из пикселя
                    int bit = pixel & 1;
                    // Устанавливаем бит в массив сообщения
                    message[bitIndex / 8] = (byte) ((message[bitIndex / 8] << 1) | bit);
                    bitIndex++;
                }
            }
        }

        // Преобразуем байты в строку
        try {
            return new String(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Ошибка кодировки: " + e.getMessage());
            return "";
        }
    }
}
