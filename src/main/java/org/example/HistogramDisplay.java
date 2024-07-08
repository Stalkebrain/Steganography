package org.example;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HistogramDisplay extends Canvas {
    private int[] values;
    private double scale = 1.0; // Масштабирование гистограммы
    private int columnWidth; // Ширина столбца гистограммы

    public HistogramDisplay(String dataFilePath, double scale, int columnWidth) {
        this.scale = scale;
        this.columnWidth = columnWidth; // Установка ширины столбца
        this.values = new int[256]; // Предполагаем, что у нас 256 значений
        try (BufferedReader br = new BufferedReader(new FileReader(dataFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                int index = Integer.parseInt(parts[0]);
                int value = Integer.parseInt(parts[1]);
                values[index] = value;
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    @Override
    public void paint(Graphics g) {
        int width = (getWidth() - 40) / values.length; // Расстояние между столбцами
        int columnPadding = (width - this.columnWidth) / 2; // Отступы с каждой стороны столбца
        boolean firstNonZeroFound = false; // Флаг для отслеживания первого ненулевого столбца

        for (int i = 0; i < values.length; i++) {
            if (values[i] == 0 && !firstNonZeroFound) {
                continue; // Пропускаем нулевые столбцы до первого ненулевого
            }
            firstNonZeroFound = true; // Первый ненулевой столбец найден, флаг устанавливается в true

            int value = (int)(values[i] * scale);
            int columnX = i * width + columnPadding;
            int newColumnWidth = this.columnWidth / 2; // Делим ширину столбца пополам
            g.setColor(Color.BLACK);
            g.fillRect(columnX, getHeight() - value - 30, newColumnWidth, value); // Рисуем столбец гистограммы черным цветом

            g.setColor(new Color(i, i, i));
            g.fillRect(columnX, getHeight() - 20, this.columnWidth, 20); // Рисуем прямоугольник с оттенком серого
        }
    }

    public static void main(String[] args) {
        double scale = 0.1; // Установите нужный масштаб
        int columnWidth = 10; // Установите нужную ширину столбца
        JFrame frame = new JFrame("Гистограмма");
        HistogramDisplay histogram = new HistogramDisplay("src/main/resources/джоджо/jojo-stego_histogram_data.txt", scale, columnWidth); // Замените на путь к вашему файлу данных

        // Устанавливаем окно на весь экран
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.add(histogram);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
