package com.billing.poc.infra.aws.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.billing.poc.infra.aws.AwsCredentialType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

@Service
public class S3Service {
	@Value("${aws.s3.credential_type}")
	private String credentialTypeString;
	@Value("${aws.s3.main_bucket}")
	private String mainBucket;
	@Value("${aws.s3.sub_bucket}")
	private String subBucket;
	@Value("${aws.s3.region}")
	private String s3RegionName;
	@Value("${aws.profile.name}")
	private String profileName;


	/**
	 * upload file to S3
	 * @param fileUploaded the file to be uploaded
	 * @param fileName the name of the file to be uploaded, the file must be in the "s3.input.toprocess" directory
	 * @return the url to uploaded file or null if the file cannot be uploaded
	 * @throws InvalidUploadS3Arguments
	 * @throws AmazonClientException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public boolean uploadFile(Path fileUploaded, String fileName) throws InvalidUploadS3Arguments, AmazonClientException, InterruptedException, IOException {
		BillingS3Client client = new BillingS3Client();
		String bucketPath = String.join("/", mainBucket,subBucket);
		if(fileUploaded == null || !fileUploaded.toFile().exists())
			throw new InvalidUploadS3Arguments("No file to download");
		return client.sendFile(fileUploaded.toFile(), bucketPath, fileName,
				AwsCredentialType.valueOf(credentialTypeString), profileName, s3RegionName);
	}

	/**
	 * Download file to s3
	 * @param fileName the filename to be downloaded
	 * @return a pair object containing file content and file metat data
	 * @throws AmazonServiceException thrown when arguments are incorrect
	 * @throws AmazonClientException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public Pair<byte[], Map<String, Object>> downloadFile(String fileName)
			throws AmazonClientException, InterruptedException, IOException {
		BillingS3Client client = new BillingS3Client();
		String bucketPath = String.join("/", mainBucket,subBucket);
		return client.downloadFile(bucketPath, fileName, AwsCredentialType.valueOf(credentialTypeString), profileName, s3RegionName);
	}



}
