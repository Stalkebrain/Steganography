package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FullImageSteganography {
    static float procant = 1F;

    public static void main(String[] args) {
        String imagePath = "E:\\Users\\Stalkerbrain\\Диплом\\Проги\\untitled\\джоджо\\jojo.bmp";
        String message = "22112121211212211212211212Ятут32333333322112121211212211212211212Ятут32333333333333333333333333333322112121211212211212211212Ятут32333333333333333333333333333322112121211212211212211212Ятут32333333333333333333333333333322112121211212211212211212Ятут32333333333333333333333333333322112121211212211212211212Ятут32333333333333333333333333333322112121211212211212211212Ятут323333333333333333333333333333333333333333333333333";
        String stegoImagePath = "E:\\Users\\Stalkerbrain\\Диплом\\Проги\\untitled\\джоджо\\jojo_stego.bmp";

        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            embedFullMessage(image, message);
            ImageIO.write(image, "bmp", new File(stegoImagePath));
            System.out.println("Сообщение встроено.");
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        try {
            BufferedImage stegoImage = ImageIO.read(new File(stegoImagePath));
            String extractedMessage = extractFullMessage(stegoImage);
            System.out.println("Извлеченное сообщение: " + extractedMessage);
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void embedFullMessage(BufferedImage img, String msg) {
        byte[] msgBytes = msg.getBytes();
        int msgBitIndex = 0;
        int msgLength = msgBytes.length * 8;
        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int procimageWidth = (int) (imageWidth * procant);

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < procimageWidth; x++) {
                int pixel = img.getRGB(x, y) & 0xFF;
                int lsb = (msgBitIndex < msgLength) ? (msgBytes[msgBitIndex / 8] >> (7 - (msgBitIndex % 8))) & 1 : 0;
                // Применяем правило стеганографии
                if (lsb == 0 && (pixel % 2 != 0)) {
                    // Если LSB сообщения равен 0 и оттенок нечётный, делаем его чётным
                    pixel = pixel - 1;
                } else if (lsb == 1 && (pixel % 2 == 0)) {
                    // Если LSB сообщения равен 1 и оттенок чётный, делаем его нечётным
                    pixel = pixel + 1;
                }
                // Устанавливаем новый пиксель
                int rgb = (pixel << 16) | (pixel << 8) | pixel;
                img.setRGB(x, y, rgb);
                msgBitIndex++;
                if (msgBitIndex == msgLength) msgBitIndex = 0; // Повторяем сообщение, если оно короче изображения
            }
        }
    }

    private static String extractFullMessage(BufferedImage img) {
        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int procimageWidth = (int) (imageWidth * procant);
        byte[] messageBytes = new byte[procimageWidth * imageHeight];
        int messageIndex = 0;

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < procimageWidth; x++) {
                int pixel = img.getRGB(x, y) & 0xFF;
                int lsb = pixel & 1;
                messageBytes[messageIndex / 8] = (byte)((messageBytes[messageIndex / 8] << 1) | lsb);
                messageIndex++;
                if (messageIndex / 8 == messageBytes.length) {
                    break; // Прекращаем чтение, если достигли конца массива
                }
            }
        }

        try {
            return new String(messageBytes, "UTF-8").trim();
        } catch (IOException e) {
            System.out.println("Ошибка при извлечении сообщения: " + e.getMessage());
            return "";
        }
    }
}
