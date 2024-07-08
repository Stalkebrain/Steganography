package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class stego2 {

    public static void main(String[] args) {
        // Путь к исходному и измененному изображениям
        String sourceImagePath = "E:\\Users\\Stalkerbrain\\Диплом\\Проги\\untitled\\sample.bmp";
        String stegoImagePath = "path_to_stego_image1.bmp";
        String message = "Ваше секретное сообщение здесьВаше секретное сообщение здесь";

        // Встраивание сообщения
        try {
            BufferedImage image = ImageIO.read(new File(sourceImagePath));
            embedMessage(image, message);
            ImageIO.write(image, "bmp", new File(stegoImagePath));
            System.out.println("Сообщение встроено в изображение.");
        } catch (IOException e) {
            System.out.println("Ошибка при встраивании сообщения: " + e.getMessage());
        }

        // Извлечение сообщения
        try {
            BufferedImage stegoImage = ImageIO.read(new File(stegoImagePath));
            String extractedMessage = extractMessage(stegoImage);
            System.out.println("Извлеченное сообщение: " + extractedMessage);
        } catch (IOException e) {
            System.out.println("Ошибка при извлечении сообщения: " + e.getMessage());
        }
    }

    private static void embedMessage(BufferedImage img, String msg) {
        // Конвертация сообщения в бинарный формат
        byte[] msgBytes = msg.getBytes();
        int msgLength = msgBytes.length;
        int bitIndex = 0;

        // Встраивание длины сообщения в первые 32 пикселя
        for (int i = 0; i < 32; i++) {
            int pixel = img.getRGB(i, 0);
            int lsb = (msgLength >> i) & 1;
            pixel = (pixel & 0xFFFFFFFE) | lsb;
            img.setRGB(i, 0, pixel);
        }

        // Встраивание самого сообщения
        for (byte b : msgBytes) {
            for (int i = 0; i < 8; i++) {
                int pixel = img.getRGB(bitIndex + 32, 0);
                int lsb = (b >> i) & 1;
                pixel = (pixel & 0xFFFFFFFE) | lsb;
                img.setRGB(bitIndex + 32, 0, pixel);
                bitIndex++;
            }
        }
    }

    private static String extractMessage(BufferedImage img) {
        // Извлечение длины сообщения
        int msgLength = 0;
        for (int i = 0; i < 32; i++) {
            int pixel = img.getRGB(i, 0);
            int lsb = pixel & 1;
            msgLength |= (lsb << i);
        }

        // Извлечение самого сообщения
        byte[] msgBytes = new byte[msgLength];
        for (int byteIndex = 0; byteIndex < msgLength; byteIndex++) {
            for (int bitIndex = 0; bitIndex < 8; bitIndex++) {
                int pixel = img.getRGB(bitIndex + 32 + byteIndex * 8, 0);
                int lsb = pixel & 1;
                msgBytes[byteIndex] |= (byte) (lsb << bitIndex);
            }
        }

        return new String(msgBytes);
    }
}
