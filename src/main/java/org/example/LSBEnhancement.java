package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LSBEnhancement {
    public static void main(String[] args) {
        try {
            // Загрузите изображение
            File originalImage = new File("E:\\Users\\Stalkerbrain\\Диплом\\Проги\\untitled\\path_to_stego_image_full.bmp");
            BufferedImage image = ImageIO.read(originalImage);

            // Усиление младших битов каждого пикселя
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = image.getRGB(x, y);
                    int lsbEnhancedPixel = enhanceLSB(pixel);
                    image.setRGB(x, y, lsbEnhancedPixel);
                }
            }

            // Сохраните усиленное изображение
            File enhancedImage = new File("stego_path.bmp");
            ImageIO.write(image, "bmp", enhancedImage);

            System.out.println("Усиление младших битов выполнено и изображение сохранено.");
        } catch (IOException e) {
            System.out.println("Ошибка при обработке изображения: " + e.getMessage());
        }
    }

    private static int enhanceLSB(int pixel) {
        // Усиление младших битов путем установки старших битов в 0 и увеличения контрастности младших битов
        return (pixel & 0xFFE0E0E0) | ((pixel & 0x1F1F1F) << 4);
    }
}
