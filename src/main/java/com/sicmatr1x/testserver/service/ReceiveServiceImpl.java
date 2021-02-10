package com.sicmatr1x.testserver.service;

import com.sicmatr1x.testserver.common.ResponseEntity;
import com.sicmatr1x.testserver.entity.SliceEntity;
import com.sicmatr1x.testserver.util.FileToBase64;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service("receiveService")
public class ReceiveServiceImpl implements ReceiveService {

    private Map<String, List<SliceEntity>> map;

    private Map<Integer, Integer> seqListMap;

    public ReceiveServiceImpl() {
        this.map = new HashMap<>();
        this.seqListMap = new HashMap<>();
    }

    @Override
    public ResponseEntity file(String filename, Integer size, SliceEntity sliceEntity) {
        ResponseEntity entity = null;
        if (!map.containsKey(filename)) {
            this.map.put(filename, new ArrayList<>());
        }
        List<SliceEntity> list = this.map.get(filename);
        int seq = sliceEntity.getSeq();
        if (!this.seqListMap.containsKey(seq)) { // 如果不包含当前数据段
            list.add(sliceEntity);
            this.seqListMap.put(seq, list.size()-1);
        } else {
            System.out.println("已包含seq=" + seq + ", 当前list.size()=" + list.size());
        }

        if (list.size() == size) { // 如果已经收到全部数据段
            Collections.sort(list);
            System.out.println("接收完毕: [" + list.size() + "/" + size + "]");
//            System.out.println(list);
            // 开始拼装base64
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                stringBuilder.append(list.get(i).getContext());
            }
            // generate response
            entity = new ResponseEntity("write to file", sliceEntity.getSeq());
            try {
                String base64Code = stringBuilder.toString();
                base64Code = base64Code.replaceAll("-rn-", "\r\n");
                base64Code = base64Code.replaceAll("-", "/");
                System.out.println("写入文件: " + filename + ", base64.length=" + base64Code.length());
                FileToBase64.decoderBase64File(base64Code, filename);
            } catch (IOException e) {
                entity.setSuccess(false);
                entity.setMessage(e.getMessage());
                entity.setData(e);
                e.printStackTrace();
            }
        } else {
            // generate response
            entity = new ResponseEntity("add to list", sliceEntity.getSeq());
        }
        return entity;
    }


}