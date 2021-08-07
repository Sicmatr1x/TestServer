package com.sicmatr1x.qrutil.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
     *
     * 最大GET大小
     * 暂时使用512
     */
    public static Integer max_request_size = 512;

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

    public static void main(String[] args) throws IOException {
        // certutil -hashfile D:\1.txt md5
//        String filepath = "MEMO20210210.006"; // 3537 c85292cf17743cbdd590a5752a30a4ac
        String filepath = "files/test.md";

        String base64Code = FileToBase64.encodeBase64File(filepath);
        base64Code = base64Code.replaceAll("\r\n", "-rn-");
        base64Code = base64Code.replaceAll("/", "-");
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
        // 销毁base64数组
        stringSliceToPostBody.destroy();

        // 以下为接收到base64数组后恢复成文件步骤
        // 开始拼装base64
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < stringSliceToPostBody.postBodyPayloadList.size(); i++) {
//            stringBuilder.append(stringSliceToPostBody.postBodyPayloadList.get(i).getContext());
//        }
//        try {
//            String base64 = stringBuilder.toString();
//            base64 = base64.replaceAll("%rn%", "\r\n");
//            FileToBase64.decoderBase64File(base64, "test2.png");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
}
