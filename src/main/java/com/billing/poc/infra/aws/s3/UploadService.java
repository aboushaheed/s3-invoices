package com.billing.poc.infra.aws.s3;

import com.amazonaws.AmazonClientException;
import com.billing.poc.interfaces.http.exception.UploadErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class UploadService {

    private S3Service s3service;

    @Autowired
    public UploadService(S3Service s3service) {
        this.s3service = s3service;
    }

    public boolean upload(MultipartFile file, String filename) {
        File temp = null;

        try {
            temp = Files.createTempFile("uploads3_" + System.currentTimeMillis(), ".tmp").toFile();
            file.transferTo(temp);
            if(!s3service.uploadFile(Paths.get(temp.getAbsolutePath()), filename))
                throw new UploadErrorException("Invalid file definition");
        } catch (InvalidUploadS3Arguments e) {
            throw new UploadErrorException(e.getMessage());
        } catch (AmazonClientException | InterruptedException | IOException e) {
            throw new UploadErrorException("Amazon error during upload process " + e.getMessage());
        } finally {
            if(temp != null && temp.exists()) {
                temp.delete();
            }
        }
        return true;
    }
}
