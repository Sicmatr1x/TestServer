package com.sicmatr1x.testserver.util;

import com.sicmatr1x.qrutil.util.MD5Util;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.Base64;

public class FileToBase64 {

    public static String encodeBase64String(String text) throws UnsupportedEncodingException {
        Base64.Encoder encoder = Base64.getEncoder();
        final byte[] textByte = text.getBytes("UTF-8");
        return encoder.encodeToString(textByte);
    }

    public static String decodeBase64String(String encodedText) throws UnsupportedEncodingException {
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(encodedText), "UTF-8");
    }

    /**
     * <p>将文件转成base64 字符串</p>
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(String path) throws IOException {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }

    /**
     * <p>将base64字符解码保存文件</p>
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static String decoderBase64File(String base64Code, String targetPath) throws IOException {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
        return MD5Util.getMD5Two(targetPath);
    }

    /**
     * <p>将base64字符保存文本文件</p>
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void toFile(String base64Code,String targetPath) throws IOException {
        byte[] buffer = base64Code.getBytes();
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }
}
