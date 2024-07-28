package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class LSBSteganography {
    private static final int NUM_SEGMENTS = 50; // Обновлено на 50

    // Встраивание текста в изображение с заданным процентом
    public static BufferedImage embedTextInImage(BufferedImage image, String text, double percentage) {
        int width = image.getWidth();
        int height = image.getHeight();
        int totalPixels = width * height;
        int pixelsToChange = (int) (totalPixels * (percentage / 100.0));
        int procImageWidth = (int) (width * (percentage / 100.0));

        // Встраиваем текст в изображение
        embedFullMessage(image, text, procImageWidth, height);

        return image;
    }

    // Функция для последовательного встраивания полного сообщения с использованием LSB
    private static void embedFullMessage(BufferedImage img, String msg, int procImageWidth, int imageHeight) {
        byte[] msgBytes = msg.getBytes();
        int msgBitIndex = 0;
        int msgLength = msgBytes.length * 8;

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < procImageWidth; x++) {
                if (msgBitIndex >= msgLength) {
                    return; // Если весь текст встроен
                }

                int color = img.getRGB(x, y);

                // Извлечение значений RGB
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = color & 0xFF;

                // Извлечение LSB сообщения для каждого канала
                int lsbR = (msgBitIndex < msgLength) ? (msgBytes[msgBitIndex / 8] >> (7 - (msgBitIndex % 8))) & 1 : 0;
                int lsbG = (msgBitIndex < msgLength) ? (msgBytes[msgBitIndex / 8] >> (7 - (msgBitIndex % 8))) & 1 : 0;
                int lsbB = (msgBitIndex < msgLength) ? (msgBytes[msgBitIndex / 8] >> (7 - (msgBitIndex % 8))) & 1 : 0;

                // Применяем правило стеганографии для каждого канала
                r = modifyColorChannel(r, lsbR);
                g = modifyColorChannel(g, lsbG);
                b = modifyColorChannel(b, lsbB);

                // Устанавливаем новый цвет пикселя
                color = (r << 16) | (g << 8) | b;
                img.setRGB(x, y, color);

                msgBitIndex++;
            }
        }
    }

    // Модификация значения цветового канала
    private static int modifyColorChannel(int color, int lsb) {
        if (lsb == 0 && (color % 2 != 0)) {
            // Если LSB сообщения равен 0 и оттенок нечётный, делаем его чётным
            color = color - 1;
        } else if (lsb == 1 && (color % 2 == 0)) {
            // Если LSB сообщения равен 1 и оттенок чётный, делаем его нечётным
            color = color + 1;
        }
        return color;
    }

    // Разделение изображения на 50 горизонтальных частей
    public static BufferedImage[] splitImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int numRows = NUM_SEGMENTS; // Количество частей по вертикали
        int segmentHeight = height / numRows; // Высота каждого сегмента

        BufferedImage[] segments = new BufferedImage[NUM_SEGMENTS];
        int index = 0;

        for (int y = 0; y < numRows; y++) {
            segments[index++] = image.getSubimage(0, y * segmentHeight, width, segmentHeight);
        }
        return segments;
    }

    // Расчет хи-квадрат
    public static double calculateChiSquared(BufferedImage segment) {
        // Используем HashMap для хранения частот
        Map<Integer, Integer> rFreq = new HashMap<>();
        Map<Integer, Integer> gFreq = new HashMap<>();
        Map<Integer, Integer> bFreq = new HashMap<>();

        int width = segment.getWidth();
        int height = segment.getHeight();
        int totalPixels = width * height;

        // Собираем частоты пикселей для каждого канала
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = segment.getRGB(x, y);
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = color & 0xFF;

                rFreq.put(r, rFreq.getOrDefault(r, 0) + 1);
                gFreq.put(g, gFreq.getOrDefault(g, 0) + 1);
                bFreq.put(b, bFreq.getOrDefault(b, 0) + 1);
            }
        }

        double chiSquared = 0;
        chiSquared += calculateChannelChiSquared(rFreq, totalPixels);
        chiSquared += calculateChannelChiSquared(gFreq, totalPixels);
        chiSquared += calculateChannelChiSquared(bFreq, totalPixels);

        return chiSquared;
    }

    // Вспомогательный метод для расчета хи-квадрат для одного канала
    private static double calculateChannelChiSquared(Map<Integer, Integer> freqMap, int totalPixels) {
        double chiSquared = 0;
        double expectedFrequency = totalPixels / 256.0;

        for (int j = 0; j < 256; j++) {
            double observedFrequency = freqMap.getOrDefault(j, 0);
            double difference = observedFrequency - expectedFrequency;
            chiSquared += (difference * difference) / expectedFrequency;
        }

        return chiSquared;
    }

    public static void main(String[] args) throws Exception {
        // Загрузка изображения
        File inputFile = new File("src/main/resources/jpg/123.jpg");
        BufferedImage image = ImageIO.read(inputFile);

        // Встраивание текста с регулировкой процента встраивания
        String text = "ыыыыыыыыыыыыыыыы";
        double percentage = 50.0; // Процент встраивания (0.0 до 100.0)
        BufferedImage stegoImage = embedTextInImage(image, text, percentage);

        // Разделение изображения на части
        BufferedImage[] segments = splitImage(stegoImage);

        // Расчет хи-квадрат для каждого сегмента
        for (int i = 0; i < segments.length; i++) {
            double chiSquared = calculateChiSquared(segments[i]);
            System.out.println("Chi-Squared for segment " + i + ": " + chiSquared);
        }

        // Сохранение измененного изображения
        File outputFile = new File("src/main/resources/jpg/123-stego.jpg");
        ImageIO.write(stegoImage, "jpg", outputFile);
    }
}
