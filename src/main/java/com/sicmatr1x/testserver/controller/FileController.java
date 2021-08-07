package com.sicmatr1x.testserver.controller;

import com.sicmatr1x.testserver.common.ResponseEntity;
import com.sicmatr1x.testserver.entity.SliceEntity;
import com.sicmatr1x.testserver.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/file")
public class FileController {

    static String WATCHED_FOLDER_PATH = "/files";

    @Autowired
    private FileService fileService;

    @GetMapping("/hello")
    public ResponseEntity helloGet() {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setSuccess(true);
        responseEntity.setMessage("test get request");
        Map<String, String> map = new HashMap<>();
        // 设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 获取当前系统时间
        map.put("time", df.format(new Date()));
        responseEntity.setData(map);
        return responseEntity;
    }

    @PostMapping("/info")
    public ResponseEntity getFileInfo(@RequestBody(required = false) Map<String, String> params) {
        String file = params.get("file");
        if (file == null || "".equals(file)) {
            file = WATCHED_FOLDER_PATH;
        } else {
            if (!(file.startsWith("/") || file.startsWith("\\"))) {
                file = "/" + file;
            }
        }
        String path = System.getProperty("user.dir") + file;
        System.out.println("path=" + path);
        return this.fileService.getFileInfo(path);
    }

    @PostMapping("/path")
    public ResponseEntity getFileInfoByAbsolutePath(@RequestBody(required = false) Map<String, String> params) {
        String path = params.get("path");
        System.out.println("path=" + path);
        return this.fileService.getFileInfo(path);
    }

    /**
     * @param response 客户端响应
     * @throws IOException io异常
     */
    @PostMapping("/download")
    public void downLoad(HttpServletResponse response, @RequestBody(required = false) Map<String, String> params) throws Throwable {
        String downloadUrl = params.get("path");
        if (Objects.isNull(downloadUrl)) {
            // 如果接收参数为空则抛出异常，由全局异常处理类去处理。
            throw new NullPointerException("下载地址为空");
        }
        // 读文件
        File file = new File(downloadUrl);
        if (!file.exists()) {
            System.out.println("下载文件的地址不存在:{}" + file.getPath());
            // 如果不存在则抛出异常，由全局异常处理类去处理。
            throw new HttpMediaTypeNotAcceptableException("文件不存在");
        }
        // 获取用户名
        String fileName = file.getName();
        // 重置response
        response.reset();
        // ContentType，即告诉客户端所发送的数据属于什么类型
        response.setContentType("application/octet-stream; charset=UTF-8");
        // 获得文件的长度
        response.setHeader("Content-Length", String.valueOf(file.length()));
        // 设置编码格式
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
        // 发送给客户端的数据
        OutputStream outputStream = response.getOutputStream();
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        // 读取文件
        bis = new BufferedInputStream(new FileInputStream(new File(downloadUrl)));
        int i = bis.read(buff);
        // 只要能读到，则一直读取
        while (i != -1) {
            // 将文件写出
            outputStream.write(buff, 0, buff.length);
            // 刷出
            outputStream.flush();
            i = bis.read(buff);
        }
    }
}
