package com.billing.poc.infra.aws.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.billing.poc.infra.aws.AwsCredentialType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class BillingS3Client {

    private static AmazonS3 s3Client;
    private static TransferManager transferManager;

    public AmazonS3 getClient(AwsCredentialType credentialType, String profileName, String region) {
        if(s3Client == null) {
            AmazonS3ClientBuilder builder = AmazonS3Client.builder().withRegion(Regions.fromName(region));
            switch (credentialType) {
                case ENVIRONMENT:
                    builder.withCredentials(new EnvironmentVariableCredentialsProvider());
                    break;
                case LOCAL:
                    builder.withCredentials(new ProfileCredentialsProvider(profileName));
                    break;
                case INSTANCE:
                    builder.withCredentials(InstanceProfileCredentialsProvider.getInstance());
                    break;
            }
            s3Client = builder.build();
            if(transferManager == null)
                transferManager =  TransferManagerBuilder.standard().withS3Client(s3Client).build();
        }
        return s3Client;
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Pair<byte[], Map<String, Object>> downloadFile(String bucketPath, String fileName, AwsCredentialType credentialType, String profileName, String region) throws AmazonClientException, InterruptedException, IOException {
        AmazonS3 client = getClient(credentialType, profileName, region);
        final GetObjectRequest request = new GetObjectRequest(bucketPath, fileName);
        S3Object s3Object = client.getObject(request);
        Map<String, Object> metadata = s3Object.getObjectMetadata().getRawMetadata();
        InputStream objectData = s3Object.getObjectContent();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[2048];
        while ((nRead = objectData.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        objectData.close();
        return new Pair(buffer.toByteArray(), metadata);
    }

    public boolean sendFile(File fileToSend, String bucketPath, String fileName, AwsCredentialType credentialType,
                            String profileName, String region) throws AmazonClientException, InterruptedException, IOException {
        AmazonS3 client = getClient(credentialType, profileName, region);
        PutObjectRequest request = new PutObjectRequest(bucketPath, fileName, fileToSend);
        if(request.getMetadata() == null)
            request.setMetadata(new ObjectMetadata());
        request.getMetadata().setContentType(Files.probeContentType(Paths.get(fileToSend.getAbsolutePath())));
        Upload upload = transferManager.upload(request);
        upload.waitForCompletion();
        return upload.isDone();
    }


    public void deleteFile(String bucketPath, String fileName, AwsCredentialType credentialType, String profileName, String region) throws AmazonClientException, InterruptedException {
        AmazonS3 client = getClient(credentialType, profileName, region);
        DeleteObjectRequest request = new DeleteObjectRequest(bucketPath, fileName);
        client.deleteObject(request);
    }

}
