package com.billing.poc.infra.aws.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class DownloadService {
    private S3Service s3service;

    @Autowired
    public DownloadService(S3Service s3service) {
        this.s3service = s3service;
    }


    public Pair<byte[], Map<String, Object>> downloadFile(String fileName) throws IOException, InterruptedException {

        return s3service.downloadFile(fileName);
    }

}
