package com.toxin.shorten.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.toxin.shorten.entity.Linker;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

@Service
public class QRService {

    private final static int QR_SIZE = 32;

    private final static String PATH = "";
    private final static String TYPE = "png";

    public String load(Linker linker, String hash) {
        byte[] qr = linker.getQr();

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(qr);
            BufferedImage bufferedImage = ImageIO.read(bis);

            File file = new File(PATH + hash + "." + TYPE);
            ImageIO.write(bufferedImage, TYPE, file);

            return file.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public byte[] generate(String data) {
        try {
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, QR_SIZE, QR_SIZE, hintMap);

            int width = byteMatrix.getWidth();
            int height = byteMatrix.getHeight();

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
            ImageIO.write(image, TYPE, byteOutStream);

            return  byteOutStream.toByteArray();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
