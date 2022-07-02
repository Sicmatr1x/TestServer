package com.sicmatr1x.qrutil.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 根据给定的长度切割字符串并按照顺序生成数组
 */
public class StringSliceToPostBody {

    private String sourceStr;

    private List<SliceEntity> postBodyPayloadList;

    public List<SliceEntity> getPostBodyPayloadList() {
        return postBodyPayloadList;
    }

    /**
     * 最大POST body大小
     * 单位: 1B（byte，字节）
     * Tomcat服务器对POST大小限制为2M = 2048KB = 2048 * 1024B
     * McAcfee限制上传目前已知1024, 512不行
     * <p>
     * 最大GET大小
     * 暂时使用512
     */
    public static Integer max_request_size = 250;

    public StringSliceToPostBody(String sourceStr) {
        this.sourceStr = sourceStr;
        this.postBodyPayloadList = new ArrayList<>();
    }

    public void run() {

        List<String> strList = this.getStrList(this.sourceStr, max_request_size);

        for (int i = 0; i < strList.size(); i++) {
            SliceEntity entity = new SliceEntity();
            entity.setSeq(i);
            entity.setContext(strList.get(i));
            this.postBodyPayloadList.add(entity);
        }
    }

    /**
     * 把原始字符串分割成指定长度的字符串列表
     *
     * @param inputString 原始字符串
     * @param length      指定长度
     * @return
     */
    private List<String> getStrList(String inputString, int length) {
        List<String> list = new ArrayList<>();
        for (int index = 0; ; index++) {
            String childStr = this.substring(inputString, index * length,
                    (index + 1) * length);
            if (childStr == null) {
                break;
            }
            list.add(childStr);
        }
        return list;
    }

    /**
     * 分割字符串，如果开始位置大于字符串长度，返回空
     *
     * @param str 原始字符串
     * @param f   开始位置
     * @param t   结束位置
     * @return
     */
    private String substring(String str, int f, int t) {
        if (f > str.length())
            return null;
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }

    /**
     * 销毁并释放该对象内部字段
     */
    public void destroy() {
        this.sourceStr = null;
        this.postBodyPayloadList.clear();
    }

    /**
     * 根据base64生成文件
     *
     * @param base64Code
     * @param filepath
     * @return MD5
     */
    public static String generateFileByBase64(String base64Code, String filepath) {
        try {
            String base64 = base64Code;
            base64 = base64.replaceAll("-rn-", "\r\n");
            base64 = base64.replaceAll("--", "/");
            FileToBase64.decoderBase64File(base64, filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return MD5Util.getMD5Two(filepath);
    }

    /**
     * 生成postman批量请求json文件
     *
     * @param filepath
     * @return base64Code
     * @throws IOException
     */
    public static String generatePostmanJsonFile(String filepath) throws IOException {
        String base64Code = FileToBase64.encodeBase64File(filepath);
        base64Code = base64Code.replaceAll("\r\n", "-rn-");
        base64Code = base64Code.replaceAll("/", "--");
//        System.out.println(base64Code);
        StringSliceToPostBody stringSliceToPostBody = new StringSliceToPostBody(base64Code);
        stringSliceToPostBody.run();
        for (int i = 0; i < stringSliceToPostBody.getPostBodyPayloadList().size(); i++) {
            System.out.println(stringSliceToPostBody.getPostBodyPayloadList().get(i).getContext());
            System.out.println("=============================================================================");
        }
        System.out.println("filepath=" + filepath + ", size=" + stringSliceToPostBody.getPostBodyPayloadList().size());

        // 转换成为json并写入文件
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(stringSliceToPostBody.postBodyPayloadList);
        FileOutputStream out = new FileOutputStream("postmanRunner.json");
        byte[] buffer = jsonString.getBytes();
        out.write(buffer);
        out.close();
        return base64Code;
    }

    public static void main(String[] args) throws IOException {
        // certutil -hashfile D:\1.txt md5
//        String filepath = "MEMO20210210.006"; // 3537 c85292cf17743cbdd590a5752a30a4ac
        String filename = "CPPLearn.7z"; // d90aa33bbafe68faf25f31916471eafd
        String folder = "files/";
        String filepath = folder + filename;

        // 生成postman批量请求json文件
        String base64Code = generatePostmanJsonFile(filepath);

        // 以下为接收到base64数组后恢复成文件步骤
        String md5 = generateFileByBase64(base64Code, folder + "dist_" + filename);
        System.out.println("MD5=" + md5);
    }
}

class GenerateFile {
    public static void main(String[] args) throws IOException {
        File postmanJsonFile = new File("postmanRunner.json");
        FileInputStream in = new FileInputStream(postmanJsonFile);
        byte[] buffer = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder();
        int len;
        while ((len = in.read(buffer)) != -1) {
            stringBuilder.append(new String(buffer, 0, len));
        }
        in.close();
//        System.out.println(stringBuilder.toString());
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerForListOf(SliceEntity.class);
        List<SliceEntity> list = reader.readValue(stringBuilder.toString());
        StringBuilder base64Builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            base64Builder.append(list.get(i).getContext());
        }
//        System.out.println(base64Builder.toString());
        String md5 = StringSliceToPostBody.generateFileByBase64(base64Builder.toString(), "files/" + "target_CPPLearn.7z");
        System.out.println("MD5=" + md5);
    }
}
