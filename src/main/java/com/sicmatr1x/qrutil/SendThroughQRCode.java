package com.sicmatr1x.qrutil;

import com.sicmatr1x.qrutil.util.FileToBase64;
import com.sicmatr1x.qrutil.util.QRCodeGenerator;

public class SendThroughQRCode {
    /**
     * QR码生成文件夹
     */
    static String QRCODE_IMG_FOLDER = "D:/buf/repo/TestServer/files";
}

/**
 * 通过QR码发送文件
 */
class SendFileThrowQRCode {
    public static void main(String[] args) {
        String filePath = SendThroughQRCode.QRCODE_IMG_FOLDER + "/test.md";
        String qrCodeImgPath = SendThroughQRCode.QRCODE_IMG_FOLDER + "/MyQRCode.png";

        try {
            String base64Code = FileToBase64.encodeBase64File(filePath);
            System.out.println(base64Code);
            QRCodeGenerator.generateQRCodeImage(base64Code, 350, 350, qrCodeImgPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * URL直接转QR码
 */
class SendURLThrowQRCode {
    public static void main(String[] args) {
//        String text = "https://www.zhihu.com/question/448488800/answer/1776200016";
//        String text = "https://www.zhihu.com/question/473806295";
        String text = "https://www.zhihu.com/question/319414486/answer/1412435049";
        String qrCodeImgPath = SendThroughQRCode.QRCODE_IMG_FOLDER + "/MyQRCode.png";

        try {
            QRCodeGenerator.generateQRCodeImage(text, 350, 350, qrCodeImgPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * 文本转base64再转QR码
 */
class SendTextThrowQRCode {
    public static void main(String[] args) {
        String text = "https://t.me/bishijieguanfang";

        String qrCodeImgPath = SendThroughQRCode.QRCODE_IMG_FOLDER + "/MyQRCode.png";

        try {
            String base64Code = FileToBase64.encodeBase64String(text);
            System.out.println(base64Code);
            QRCodeGenerator.generateQRCodeImage(base64Code, 350, 350, qrCodeImgPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
