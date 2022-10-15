# TestServer

test some java code in springboot

### Controller

- GET /mcafee/hello
- GET /f/{filename}/s/{size}/seq/{seq}/b/{context} 每一次传输文件切片base64编码数据段
- GET /check/f/{filename}/s/{size} 获取当前文件在内存中缺失的数据片编号
