package org.stego.stego;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MessageExtractor {

    public static class MessageData {
        private final float percent;
        private final String message;

        public MessageData(float percent, String message) {
            this.percent = percent;
            this.message = message;
        }

        public float getPercent() {
            return percent;
        }

        public String getMessage() {
            return message;
        }
    }

    public static MessageData[] extractMessages(BufferedImage stegoImage) {
        List<MessageData> messageDataList = new ArrayList<>();
        for (float percent = 0; percent <= 1; percent += 0.01) {
            String extractedMessage = extractMessage(stegoImage, percent);
            String previewMessage = extractedMessage.length() > 30 ? extractedMessage.substring(0, 30) : extractedMessage;
            messageDataList.add(new MessageData(percent, previewMessage));
        }
        return messageDataList.toArray(new MessageData[0]);
    }

    private static String extractMessage(BufferedImage img, float percent) {
        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int procImageWidth = (int) (imageWidth * percent);
        byte[] messageBytes = new byte[procImageWidth * imageHeight];
        int messageIndex = 0;

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < procImageWidth; x++) {
                int pixel = img.getRGB(x, y) & 0xFF;
                int lsb = pixel & 1;
                if (messageIndex / 8 < messageBytes.length) {
                    messageBytes[messageIndex / 8] = (byte) ((messageBytes[messageIndex / 8] << 1) | lsb);
                }
                messageIndex++;
                if (messageIndex / 8 >= messageBytes.length) {
                    break;
                }
            }
        }
        return new String(messageBytes).trim();
    }
}
