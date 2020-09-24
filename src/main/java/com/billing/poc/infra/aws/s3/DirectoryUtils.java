package com.billing.poc.infra.aws.s3;

import java.io.File;

public class DirectoryUtils {
    /**
     * Check if directory in path exists, otherwise create it
     * @param path the path of the directory to check
     */
    public static void checkDirectory(String path) {
        File file = new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
    }
}
