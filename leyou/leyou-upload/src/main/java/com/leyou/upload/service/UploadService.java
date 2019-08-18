package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Pandy
 * @Version 1.0
 */
@Service
public class UploadService {

    @Autowired
    private FastFileStorageClient StorageClient;
    //自定义白名单
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/gif","image/jpeg","image/tiff","image/jpeg");
    //记录日志
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

    /**
     * 上传品牌图片
     * @param file
     * @return
     */
    public String uploadImage(MultipartFile file){
        String originalFilename = file.getOriginalFilename();

        //校验文件类型
        String contentType = file.getContentType();
        if (!CONTENT_TYPES.contains(contentType)){
            LOGGER.info("文件类型不合法:{}",originalFilename );
            return null;
        }
        //校验文件内容
        BufferedImage bufferImage = null;
        try {
            bufferImage = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bufferImage == null || bufferImage.getWidth() == 0 || bufferImage.getHeight() == 0){
            LOGGER.info("文件内容不合法:{}",originalFilename );
            return null;
        }
        //保存到文件服务器
        try {
            //file.transferTo(new File(new File("G:\\leyoumall\\image\\") + originalFilename));
            String ext = StringUtils.substringAfterLast(originalFilename, ".");
            StorePath storePath = this.StorageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);
            return "http://image.leyou.com/" + storePath.getFullPath();
        } catch (IOException e) {
            LOGGER.info("服务器内部错误:{}",originalFilename );
            e.printStackTrace();
        }
        //返回url 进行回显
        return null;
    }
}
