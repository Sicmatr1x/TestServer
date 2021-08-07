package com.sicmatr1x.testserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicmatr1x.testserver.common.ResponseEntity;
import com.sicmatr1x.testserver.entity.FileObject;
import org.springframework.stereotype.Service;

import java.io.File;

@Service("fileService")
public class FileServiceImpl implements FileService {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public ResponseEntity getFileInfo(String path) {
        ResponseEntity response = new ResponseEntity();
        File file = new File(path);
        if (file.exists()) {
            FileObject object = new FileObject(file);
            response.setSuccess(true);
            response.setData(object);
        } else {
            response.setSuccess(false);
            response.setMessage(file + " is not exist.");
        }
        return response;
    }

}
