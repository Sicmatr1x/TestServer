package com.sicmatr1x.testserver.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class FileObject {
    private String fileName;
    private String path;
    private Boolean isDictionary;
    /**
     * The size, in bytes
     */
    private Long fileSize;
    private List<FileObject> children;

    public FileObject() {
        children = new ArrayList<>();
    }

    public FileObject(File file) {
        this.children = new ArrayList<>();
        this.fileName = file.getName();
        this.path = file.getPath();
        this.isDictionary = file.isDirectory();
        if (!this.isDictionary) {
            this.fileSize = getFileSize(file);
        }
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                this.children.add(new FileObject(child, false));
            }
        }
    }

    public FileObject(File file, boolean isScanChildren) {
        this.children = new ArrayList<>();
        this.fileName = file.getName();
        this.path = file.getPath();
        this.isDictionary = file.isDirectory();
        if (!this.isDictionary) {
            this.fileSize = getFileSize(file);
        }
        if (isScanChildren) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    this.children.add(new FileObject(child));
                }
            }
        }
    }

    /**
     * 根据java.nio.*的流获取文件大小
     * @param file
     */
    public Long getFileSize(File file){
        FileChannel fc = null;
        try {
            if(file.exists() && file.isFile()){
                String fileName = file.getName();
                FileInputStream fis = new FileInputStream(file);
                fc = fis.getChannel();
                return fc.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != fc){
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0L;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getDictionary() {
        return isDictionary;
    }

    public void setDictionary(Boolean dictionary) {
        isDictionary = dictionary;
    }

    public List<FileObject> getChildren() {
        return children;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
