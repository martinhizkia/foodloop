package com.foodloop.foodloopapps.ui.camera;

import android.os.Build;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UploadObject {
    public static void uploadObject(String objectName, String filePath) throws IOException {
        // The ID of your GCP project
        String projectId = "foodloop-313715";

        // The ID of your GCS bucket
        String bucketName = "foodloopimg";

        // The ID of your GCS object
        // String objectName = "your-object-name";

        // The path to your file to upload
        // String filePath = "path/to/your/file"

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));
        }

        System.out.println(
                "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
    }
}