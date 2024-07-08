package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PartialSteganography {

    public static void main(String[] args) {
        String imagePath = "sample.bmp";
        String message = "11111111111111111111111111111111111111";
        double percentage = 50; // Процент изображения для встраивания сообщения
        String stegoImagePath = "stego_path.bmp";

        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            embedPartialMessage(image, message, percentage);
            ImageIO.write(image, "bmp", new File(stegoImagePath));
            System.out.println("Сообщение встроено в указанный процент изображения.");
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void embedPartialMessage(BufferedImage img, String msg, double percent) {
        byte[] msgBytes = msg.getBytes();
        int msgBitIndex = 0;
        int msgLength = msgBytes.length * 8;
        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int totalPixels = imageWidth * imageHeight;
        int pixelsToEmbed = (int) (totalPixels * percent);

        for (int i = 0; i < pixelsToEmbed; i++) {
            if (msgBitIndex >= msgLength) break; // Прекращаем, если сообщение закончилось

            int x = i % imageWidth;
            int y = i / imageWidth;
            int pixel = img.getRGB(x, y);
            int lsb = (msgBytes[msgBitIndex / 8] >> (7 - (msgBitIndex % 8))) & 1;
            pixel = (pixel & 0xFFFFFFFE) | lsb;
            img.setRGB(x, y, pixel);
            msgBitIndex++;
        }
    }
}
