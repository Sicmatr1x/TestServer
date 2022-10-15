package com.sicmatr1x.testserver.util;

import com.sicmatr1x.qrutil.util.QRCodeGenerator;
import com.sicmatr1x.qrutil.util.FileToBase64;

public class SendThroughQRCode {

}

class SendFileThrowQRCode {
    public static void main(String[] args) {
        String filePath = "test.png";
        String qrCodeImgPath = "D:/buf/repo/QRGenerate/MyQRCode.png";

        try {
            String base64Code = FileToBase64.encodeBase64File(filePath);
            System.out.println(base64Code);
            QRCodeGenerator.generateQRCodeImage(base64Code, 350, 350, qrCodeImgPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class SendURLThrowQRCode {
    public static void main(String[] args) {
        // https://manhua.dmzj.com/monkeypeak/80059.shtml#@page=18
        String text = "https://www.bishijie.com/shendu/166270.html";
        String qrCodeImgPath = "D:/buf/repo/QRGenerate/MyQRCode.png";

        try {
            QRCodeGenerator.generateQRCodeImage(text, 350, 350, qrCodeImgPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class SendTextThrowQRCode {
    public static void main(String[] args) {
        String text = "";

        String qrCodeImgPath = "D:/buf/repo/QRGenerate/MyQRCode.png";

        try {
            String base64Code = FileToBase64.encodeBase64String(text);
            System.out.println(base64Code);
            QRCodeGenerator.generateQRCodeImage(base64Code, 350, 350, qrCodeImgPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
