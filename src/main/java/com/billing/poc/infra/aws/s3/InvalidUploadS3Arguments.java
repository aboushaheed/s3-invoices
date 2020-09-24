package com.billing.poc.infra.aws.s3;

public class InvalidUploadS3Arguments  extends Exception{

    public InvalidUploadS3Arguments(String message) {
        super(message);
    }
}
