package com.sicmatr1x.testserver.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class StringSliceToPostBody {

    private String sourceStr;

    private List<SliceEntity> postBodyPayloadList;

    public List<SliceEntity> getPostBodyPayloadList() {
        return postBodyPayloadList;
    }

    /**
     * 最大POST body大小
     * 单位: 1B（byte，字节）
     * <p>
     * Tomcat服务器对POST大小限制为2M = 2048KB = 2048 * 1024B
     * McAcfee限制上传目前已知1024, 512不行
     */
//    public static Integer max_request_size = 2048 * 1024;
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
//        String filepath = "MEMO20210210.005"; // 5749 520470eb8c16876e8a7573ab1f68df55
//        String filepath = "MEMO20210210.004"; // 5749 4a946aaee632d2c51ad0f4146a3269d8
//        String filepath = "MEMO20210210.003"; // 5749 7f968abfcb1b8ca9c4832b31bf923054
//        String filepath = "MEMO20210210.002"; // 5749 f1921eaf50f2872dd954a531e8b3a3cc
//        String filepath = "MEMO20210210.001"; // 5749 8e8e437a9969ce6d5513e891eae854a5
        String filepath = "test.png"; // 31

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

        //转换成为json并写入文件
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(stringSliceToPostBody.postBodyPayloadList);
        FileOutputStream out = new FileOutputStream("postmanRunner.json");
        byte[] buffer = jsonString.getBytes();
        out.write(buffer);
        out.close();

        // 开始拼装base64
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < stringSliceToPostBody.postBodyPayloadList.size(); i++) {
            stringBuilder.append(stringSliceToPostBody.postBodyPayloadList.get(i).getContext());
        }
        try {
            String base64 = stringBuilder.toString();
            base64 = base64.replaceAll("%rn%", "\r\n");
            FileToBase64.decoderBase64File(base64, "test2.png");
        } catch (IOException e) {
            e.printStackTrace();
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
}
