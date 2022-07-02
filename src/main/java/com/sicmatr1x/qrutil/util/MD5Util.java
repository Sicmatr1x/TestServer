package com.sicmatr1x.qrutil.util;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static void main(String[] args) {
        String filename = "MyQRCode.7z";
        String folder = "files/";
        String filepath = folder + filename;
        System.out.println(MD5Util.getMD5Two(filepath)); // certutil -hashfile MyQRCode.7z MD5
        System.out.println(MD5Util.md5HashCode(filepath));
        System.out.println(MD5Util.getMD5Two("files/MyQRCode1.7z"));
    }
    //获取文件的MD5值
    public static String getMD5Two(String path) {
        StringBuffer sb = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            File file = new File(path);
            FileInputStream inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int)file.length()];
            inputFile.read(buffer);
            inputFile.close();
            md.update(buffer);
            byte b[] = md.digest();
            int d;
            for (int i = 0; i < b.length; i++) {
                d = b[i];
                if (d < 0) {
                    d = b[i] & 0xff;
                }
                if (d < 16)
                    sb.append("0");
                sb.append(Integer.toHexString(d));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    /**
     * 获取文件md5值
     */
    public static String md5HashCode(String filePath) {
        try {
            InputStream fis = new FileInputStream(filePath);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            fis.close(); //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes = md.digest();
            BigInteger bigInt = new BigInteger(1, md5Bytes);
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
