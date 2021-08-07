package com.sicmatr1x.testserver.service;

import com.sicmatr1x.testserver.common.ResponseEntity;
import com.sicmatr1x.testserver.entity.SliceEntity;

public interface FileService {
    /**
     * 获取文件信息
     * @param path
     * @return
     */
    public ResponseEntity getFileInfo(String path);


}
