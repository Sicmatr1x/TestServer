import util.QRCodeGenerator;
import util.FileToBase64;

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
        String text = "package com.sicmatr1x.testserver.util;\n" +
                "\n" +
                "import sun.misc.BASE64Decoder;\n" +
                "import sun.misc.BASE64Encoder;\n" +
                "\n" +
                "import java.io.*;\n" +
                "import java.util.Base64;\n" +
                "\n" +
                "public class FileToBase64 {\n" +
                "\n" +
                "    public static String encodeBase64String(String text) throws UnsupportedEncodingException {\n" +
                "        Base64.Encoder encoder = Base64.getEncoder();\n" +
                "        final byte[] textByte = text.getBytes(\"UTF-8\");\n" +
                "        return encoder.encodeToString(textByte);\n" +
                "    }\n" +
                "\n" +
                "    public static String decodeBase64String(String encodedText) throws UnsupportedEncodingException {\n" +
                "        Base64.Decoder decoder = Base64.getDecoder();\n" +
                "        return new String(decoder.decode(encodedText), \"UTF-8\");\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * <p>将文件转成base64 字符串</p>\n" +
                "     * @param path 文件路径\n" +
                "     * @return\n" +
                "     * @throws Exception\n" +
                "     */\n" +
                "    public static String encodeBase64File(String path) throws IOException {\n" +
                "        File file = new File(path);\n" +
                "        FileInputStream inputFile = new FileInputStream(file);\n" +
                "        byte[] buffer = new byte[(int)file.length()];\n" +
                "        inputFile.read(buffer);\n" +
                "        inputFile.close();\n" +
                "        return new BASE64Encoder().encode(buffer);\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * <p>将base64字符解码保存文件</p>\n" +
                "     * @param base64Code\n" +
                "     * @param targetPath\n" +
                "     * @throws Exception\n" +
                "     */\n" +
                "    public static void decoderBase64File(String base64Code,String targetPath) throws IOException {\n" +
                "        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);\n" +
                "        FileOutputStream out = new FileOutputStream(targetPath);\n" +
                "        out.write(buffer);\n" +
                "        out.close();\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * <p>将base64字符保存文本文件</p>\n" +
                "     * @param base64Code\n" +
                "     * @param targetPath\n" +
                "     * @throws Exception\n" +
                "     */\n" +
                "    public static void toFile(String base64Code,String targetPath) throws IOException {\n" +
                "        byte[] buffer = base64Code.getBytes();\n" +
                "        FileOutputStream out = new FileOutputStream(targetPath);\n" +
                "        out.write(buffer);\n" +
                "        out.close();\n" +
                "    }\n" +
                "}\n";

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
