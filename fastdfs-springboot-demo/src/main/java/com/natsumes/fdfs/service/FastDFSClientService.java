package com.natsumes.fdfs.service;


import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface FastDFSClientService {

    /**
     * 文件上传
     *
     * @param bytes 文件字节 *
     * @param fileSize 文件大小
     * @param extension 文件扩展名 *
     * @return fastDfs路径
     */
    String uploadFile(byte[] bytes, long fileSize, String extension);

    /**
     * 下载文件
     *
     * @param fileUrl 文件URL
     * @return 文件字节
     * @throws IOException IOException
     */
    byte[] downloadFile(String fileUrl) throws IOException;

    /**
     * 删除文件
     *
     * @param fileUrl 文件URL
     */
    void deleteFile(String fileUrl);
}
