package com.natsumes.fdfs.service.impl;


import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.natsumes.fdfs.service.FastDFSClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class FastDFSClientServiceImpl implements FastDFSClientService {


    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    /**
     * 文件上传
     *
     * @param bytes     文件字节 *
     * @param fileSize  文件大小
     * @param extension 文件扩展名 *
     * @return fastDfs路径
     */
    @Override
    public String uploadFile(byte[] bytes, long fileSize, String extension) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        StorePath storePath = fastFileStorageClient.uploadFile(byteArrayInputStream, fileSize, extension, null);
        System.out.println(storePath.getGroup() + ":" + storePath.getPath() + ":" + storePath.getFullPath());
        return storePath.getFullPath();
    }

    /**
     * 下载文件
     *
     * @param fileUrl 文件URL
     * @return 文件字节
     * @throws IOException IOException
     */
    @Override
    public byte[] downloadFile(String fileUrl) throws IOException {
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes;
        bytes = fastFileStorageClient.downloadFile(group, path, downloadByteArray);
        return bytes;
    }

    /**
     * 删除文件
     *
     * @param fileUrl 文件URL
     */
    @Override
    public void deleteFile(String fileUrl)  {
        fastFileStorageClient.deleteFile(fileUrl);
    }
}
