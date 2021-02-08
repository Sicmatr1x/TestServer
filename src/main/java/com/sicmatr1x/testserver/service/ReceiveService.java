package com.sicmatr1x.testserver.service;

import com.sicmatr1x.testserver.common.ResponseEntity;
import com.sicmatr1x.testserver.entity.SliceEntity;

public interface ReceiveService {
    /**
     * 每一次传输文件切片base64编码数据段
     * @param sliceEntity 文件切片base64编码数据段
     * @param size 文件总共含多少个数据段
     * @return 是否添加到列表成功
     */
    public ResponseEntity file(String filename, Integer size, SliceEntity sliceEntity);


}