package org.example;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Hashtable;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, WriterException {
        String qrCodeText = "https://docs.google.com/presentation/d/1vHuCvjrqWpPxC-xg7hVoC4_ial5DeAJ9/edit?usp=sharing&ouid=116657400503670788975&rtpof=true&sd=true";
        String filePath = "QR_Dan.png";
        int size = 300;
        String fileType = "png";
        File qrFile = new File(filePath);
        createQRImage(qrFile, qrCodeText, size, fileType);
        System.out.println("DONE");
    }

    private static void createQRImage(File qrFile, String qrCodeText, int size, String fileType)
            throws WriterException, IOException {
        // Create the ByteMatrix for the QR-Code that encodes the given String
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
        // Make the BufferedImage that are to hold the QRCode
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        float[] colorsBack = getColorRGBtoHSB(241,241,231);
        graphics.setColor(Color.getHSBColor(colorsBack[0], colorsBack[1], colorsBack[2]));//fondo
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // Paint and save the image using the ByteMatrix
        float[] colorsQR = getColorRGBtoHSB(207,163,44);
        graphics.setColor(Color.getHSBColor(colorsQR[0], colorsQR[1], colorsQR[2]));//QR

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        ImageIO.write(image, fileType, qrFile);
    }

    public static float[] getColorRGBtoHSB(int r, int g, int b){
        float[] hsbValuesBackground = Color.RGBtoHSB(r,g,b,null);
        float hue = hsbValuesBackground[0] * 360;
        float saturation = hsbValuesBackground[0] * 360;
        float value = hsbValuesBackground[0] * 360;
        float[] colors = {hue,saturation,value};
        return colors;
    }
}
