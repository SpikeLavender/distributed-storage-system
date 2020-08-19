package com.natsumes;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

public class FastDFSTest {

    @Test
    public void testUpload() {
        try {
            //加载配置文件
            ClientGlobal.initByProperties("fdfs_client.properties");
            //创建tracker客户端
            TrackerClient tc = new TrackerClient();
            //根据tracker客户端创建连接 获取到跟踪服务器对象
            TrackerServer ts = tc.getConnection();
            StorageServer ss = null;
            //定义storage客户端
            StorageClient1 client = new StorageClient1(ts, ss);
            //文件元信息
            NameValuePair[] list = new NameValuePair[1];
            list[0] = new NameValuePair("fileName", "1.png");
            // 上传，返回fileId
            String fileId = client.upload_file1("1.png", "png", list);
            //group1/M00/00/00/rBEACF8850GAU00QAAHqeFj-bEg101.png
            //group1/M00/00/00/rBEACF8859aABnTpAAHqeFj-bEg488.png
            //String fileId = client.upload_file1("****.png", "png", list);
            System.out.println(fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQuery() {
        //加载配置文件
        try {
            ClientGlobal.initByProperties("fdfs_client.properties");
            //创建tracker客户端
            TrackerClient tc = new TrackerClient();
            //根据tracker客户端创建连接 获取到跟踪服务器对象
            TrackerServer ts = tc.getConnection();
            StorageServer ss = null;
            //定义storage客户端
            StorageClient1 client = new StorageClient1(ts, ss);
            // 查询文件信息
            FileInfo fileInfo = client.query_file_info1 ("group1/M00/00/00/rBEACF8859aABnTpAAHqeFj-bEg488.png");
            System.out.println(fileInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testDownload() {
        //加载配置文件
        try {
            ClientGlobal.initByProperties("fdfs_client.properties");
            //创建tracker客户端
            TrackerClient tc = new TrackerClient();
            //根据tracker客户端创建连接 获取到跟踪服务器对象
            TrackerServer ts = tc.getConnection();
            StorageServer ss = null;
            //定义storage客户端
            StorageClient1 client = new StorageClient1(ts, ss);
            // 下载
            byte[] bs = client.download_file1 ("group1/M00/00/00/rBEACF8859aABnTpAAHqeFj-bEg488.png");
            FileOutputStream fos = new FileOutputStream(new File("test.png"));
            fos.write(bs);
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
