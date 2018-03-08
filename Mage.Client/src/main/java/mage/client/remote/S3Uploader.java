package mage.client.remote;

import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import org.apache.log4j.Logger;

import javax.xml.crypto.Data;


public class S3Uploader {
    private static final Logger logger = Logger.getLogger(S3Uploader.class);

    public static Boolean upload(String filePath, String keyName) throws Exception {
        String existingBucketName = System.getenv("S3_BUCKET") != null ? System.getenv("S3_BUCKET")
                                                                       : "xmage-game-logs-dev";

        String accessKeyId = System.getenv("AWS_ACCESS_ID");
        String secretKeyId = System.getenv("AWS_SECRET_KEY");

        if(accessKeyId == "" || secretKeyId == "" || existingBucketName == "") {
            logger.info("Aborting json log sync.");
            return false;
        }

        String path = new File("./" + filePath).getCanonicalPath();
        logger.info("Syncing " + path + " to bucket: " + existingBucketName + " with AWS Access Id: " + accessKeyId);

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretKeyId);
        TransferManager tm = new TransferManager(awsCreds);
        Upload upload = tm.upload(existingBucketName, "/game/" + keyName + ".json", new File(path));

        try {
            upload.waitForUploadResult();
            logger.info("Sync Complete For " + path + " to bucket: " + existingBucketName + " with AWS Access Id: " + accessKeyId);
            new File(path);
            return true;
        } catch (AmazonClientException amazonClientException) {
            System.out.println("Unable to upload file, upload was aborted.");
            amazonClientException.printStackTrace();
            return false;
        }
    }
}
