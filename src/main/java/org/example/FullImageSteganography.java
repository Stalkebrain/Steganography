package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;

public class FullImageSteganography {
    static float procent = 1F;

    public static void main(String[] args) {
        String imagePath = "src/main/resources/bibizyan/111111.bmp";
        String message = "mkdpoiasjdiopqwjdioanmi0dm0129md0192md091m398fnm  12389mf89   23mf8   923mnf8923mf238mf8m2    3f892m3f89m 2398fm289m  fwi 9emf9803m89mrf983m28f9m23f  273mf   7823m0f 7832mf  7823mf  08723f8333337333hf  87  23hnf0782   30nf872 3nf 238f238 nnfnnn8n723nfn32788237837278q387872474w75g785487g97548ng278459ng28745ng8745ng28475ng48725ng4758ng45897mb 458mv4895mj540,jg008j8ld45jgd8945jhd024589hj297h5n4.l089dhxn45897xh4.,857nxh0458nxh45hxn457,,n.tx54nxt5489,tn9540htn785hnt73h749th2923-ht89234th9374thnx973u45h57tn5439x,t3n45x9t,5,tx-7n92x,-n,9.x25x5-n9.t72,x52-tn9.78,25-.,n9x25t-,9.n2x5t-jn8,9.xj85g9-2j895g-0dl2ljk59g80k7j0gd5k2dh5g2hk7j08g54dhg28k50,2h078g5c,529c-g,29-85gjc,c982-5jg,29-85g,9-5hn2,d9hng52,97dhg529h7d52g9dh-25g89-h2dg5,d89-g2j5d-92h8g5,h-92d7g5,h7-9d2g5,h-5792dg,h7-9d2g5,h79-2dg5,dh-7925g,h79-d25g,h79-d2g5,h792dg5,h79-d2g5,hd79-2g5,hd9-72g5,h9-7d25g,h79-d52,h97-d2g5,h79-d2h9-27d5,h9-7d2g5,h2d79-g52h-7m9g5fmh79--h7m92 vh-v97m2 5h-97m v2-2h7m9g5-h9g-j8,9g52,-h8j92dg5,-h89dg25,h7-d925g,h7-9d2g5,h-792d5g,-h97d2,h-d2,-h9d2-,h9d-2,h9g58,-dh9275,-h792dg5,-h792d5g,-h97d2g5d,-h9725-,h79d2g5-h79k,d52-9h82dg-j9l8-lu8jl-8u52dgl-d8u92g5l-8dj252l-8j5-8,jv-8,h9m5b-vm8h9mn757943n5y7235y07m234654nm-v86723486,c42385uci23048257uc483026=4c23867c3249067u2340986.uc23.408ytuhj54.97c.thj574,c43h575mc3467845ed6s7frtg78yh8uj9ikdfc5rv6tb7ynu8imd54frvc6btg7yhn8ujm9if5crv6btg7yhn8ujm9ifc5rv6tb7yn8um9ixc54er7v6tb7yn8umx4ectrfvybguhnjmi5drf6tgybhunio0pu9oiy8utkjyhtgp.;iouli,ykum=0]dc5rfv6tgb7yn8umiol9o.,78im7,8im67un7,l896imun779.,o8im6un68,im7un5yb,im57uny6,8oim7u5ny6.8,oim7up.79o,8i6m7un7;9.,o86mi7un57,m8un6yb7jn56bt86kj7n5yhbt8k,oim7unybty54643ygri90kpo4tg0u9jiot3wu0j9ipomtk4geu9j-opmt4geu09jpoimt3wgeu-j9moptw3geuj-9opmgeu9j0-ompy4geuj9-mopy4gej90mopt3geuj9-mopt34wgej-9ompy4ge0j9mop30985j8,5u8u5-8f4u-,5834u68-f7j8-45j70-4w59.7ug4=bimkdpoiasjdiopqwjdioanmi0dm0129md0192md091m398fnm  12389mf89   23mf8   923mnf8923mf238mf8m2    3f892m3f89m 2398fm289m  fwi 9emf9803m89mrf983m28f9m23f  273mf   7823m0f 7832mf  7823mf  08723f8333337333hf  87  23hnf0782   30nf872 3nf 238f238 nnfnnn8n723nfn32788237837278q387872474w75g785487g97548ng278459ng28745ng8745ng28475ng48725ng4758ng45897mb 458mv4895mj540,jg008j8ld45jgd8945jhd024589hj297h5n4.l089dhxn45897xh4.,857nxh0458nxh45hxn457,,n.tx54nxt5489,tn9540htn785hnt73h749th2923-ht89234th9374thnx973u45h57tn5439x,t3n45x9t,5,tx-7n92x,-n,9.x25x5-n9.t72,x52-tn9.78,25-.,n9x25t-,9.n2x5t-jn8,9.xj85g9-2j895g-0dl2ljk59g80k7j0gd5k2dh5g2hk7j08g54dhg28k50,2h078g5c,529c-g,29-85gjc,c982-5jg,29-85g,9-5hn2,d9hng52,97dhg529h7d52g9dh-25g89-h2dg5,d89-g2j5d-92h8g5,h-92d7g5,h7-9d2g5,h-5792dg,h7-9d2g5,h79-2dg5,dh-7925g,h79-d25g,h79-d2g5,h792dg5,h79-d2g5,hd79-2g5,hd9-72g5,h9-7d25g,h79-d52,h97-d2g5,h79-d2h9-27d5,h9-7d2g5,h2d79-g52h-7m9g5fmh79--h7m92 vh-v97m2 5h-97m v2-2h7m9g5-h9g-j8,9g52,-h8j92dg5,-h89dg25,h7-d925g,h7-9d2g5,h-792d5g,-h97d2,h-d2,-h9d2-,h9d-2,h9g58,-dh9275,-h792dg5,-h792d5g,-h97d2g5d,-h9725-,h79d2g5-h79k,d52-9h82dg-j9l8-lu8jl-8u52dgl-d8u92g5l-8dj252l-8j5-8,jv-8,h9m5b-vm8h9mn757943n5y7235y07m234654nm-v86723486,c42385uci23048257uc483026=4c23867c3249067u2340986.uc23.408ytuhj54.97c.thj574,c43h575mc3467845ed6s7frtg78yh8uj9ikdfc5rv6tb7ynu8imd54frvc6btg7yhn8ujm9if5crv6btg7yhn8ujm9ifc5rv6tb7yn8um9ixc54er7v6tb7yn8umx4ectrfvybguhnjmi5drf6tgybhunio0pu9oiy8utkjyhtgp.;iouli,ykum=0]dc5rfv6tgb7yn8umiol9o.,78im7,8im67un7,l896imun779.,o8im6un68,im7un5yb,im57uny6,8oim7u5ny6.8,oim7up.79o,8i6m7un7;9.,o86mi7un57,m8un6yb7jn56bt86kj7n5yhbt8k,oim7unybty54643ygri90kpo4tg0u9jiot3wu0j9ipomtk4geu9j-opmt4geu09jpoimt3wgeu-j9moptw3geuj-9opmgeu9j0-ompy4geuj9-mopy4gej90mopt3geuj9-mopt34wgej-9ompy4ge0j9mop30985j8,5u8u5-8f4u-,5834u68-f7j8-45j70-4w59.7ug4=bi";
        String stegoImagePath = "src/main/resources/bibizyan/111111-stego.bmp";

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
        int procimageWidth = (int) (imageWidth * procent);

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
        int procimageWidth = (int) (imageWidth * procent);
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

        return new String(messageBytes, StandardCharsets.UTF_8).trim();
    }
}
