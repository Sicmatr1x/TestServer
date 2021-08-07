package com.sicmatr1x.qrutil.util;

/**
 * 批量发送POST请求body参数json转换对象
 */
public class SliceEntity {
    /**
     * 当前切片所在序号
     */
    private Integer seq;
    /**
     * 当前切片内容
     */
    private String context;

    public SliceEntity() {
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "SliceEntity{" +
                "seq=" + seq +
                ", context='" + context + '\'' +
                '}';
    }
}
