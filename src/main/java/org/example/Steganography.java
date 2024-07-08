package org.example;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Steganography {

    // Метод для встраивания сообщения в изображение
    public void embedMessage(File bmpImage, String message) throws IOException {
        BufferedImage image = ImageIO.read(bmpImage);
        byte[] msgBytes = message.getBytes();
        int msgLength = msgBytes.length;
        int bitIndex = 0;

        for (int i = 0; i < msgLength; i++) {
            for (int j = 7; j >= 0; j--) {
                int bitValue = (msgBytes[i] >> j) & 1;
                image.setRGB(bitIndex % image.getWidth(), bitIndex / image.getWidth(),
                        (image.getRGB(bitIndex % image.getWidth(), bitIndex / image.getWidth()) & 0xFFFFFFFE) | bitValue);
                bitIndex++;
            }
        }
        ImageIO.write(image, "bmp", new File("stego_image.bmp"));
    }

    // Метод для извлечения сообщения из изображения
    public String extractMessage(File stegoImage) throws IOException {
        BufferedImage image = ImageIO.read(stegoImage);
        StringBuilder message = new StringBuilder();
        int bitIndex = 0;

        while (true) {
            int charCode = 0;
            for (int i = 0; i < 8; i++) {
                charCode |= (image.getRGB(bitIndex % image.getWidth(), bitIndex / image.getWidth()) & 1) << (7 - i);
                bitIndex++;
            }
            if (charCode == 0) {
                break;
            }
            message.append((char) charCode);
        }
        return message.toString();
    }

    public static void main(String[] args) {
        Steganography steganography = new Steganography();
        try {
            File originalImage = new File("E:\\Users\\Stalkerbrain\\Диплом\\Проги\\untitled\\sample.bmp");
            String secretMessage = "aboba123456789111111111111111111111111111111111111234567891111111111111111111111111111111111111234567891111111111111111111111111111111111111234567891111111111111111111111111111111111111234567891111111111111111111111111111111111111234567891111111111111111111111111111111111111234567891111111111111111111111111111111111111111111";
            steganography.embedMessage(originalImage, secretMessage);

            File stegoImage = new File("stego_image.bmp");
            String extractedMessage = steganography.extractMessage(stegoImage);
            System.out.println("Извлеченное сообщение: " + extractedMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}