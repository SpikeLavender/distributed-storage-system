package com.natsumes.fdfs.controller;

import com.natsumes.fdfs.service.FastDFSClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
@RequestMapping("/fastdfs")
public class FastDFSController {


    @Autowired
    private FastDFSClientService fastDFSClientService;

    @RequestMapping("/upload")
    public String uploadFile(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String originalFileName = file.getOriginalFilename();
        String extension = Objects.requireNonNull(originalFileName).substring(originalFileName.lastIndexOf(".") + 1);
        String fileName = file.getName(); long fileSize = file.getSize();
        System.out.println(originalFileName + ":" + fileName + ":" + fileSize + ":" + extension + ":" + bytes.length);
        return fastDFSClientService.uploadFile(bytes, fileSize, extension);
    }

    @RequestMapping("/download")
    public void downloadFile(String fileUrl, HttpServletResponse response) throws IOException {
        byte[] bytes = fastDFSClientService.downloadFile(fileUrl);
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileUrl, StandardCharsets.UTF_8));
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert outputStream != null;
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/delete")
    public void deleteFile(String fileUrl) {
        fastDFSClientService.deleteFile(fileUrl);
    }

}
