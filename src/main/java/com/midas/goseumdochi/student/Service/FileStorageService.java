package com.midas.goseumdochi.student.Service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String bucketName = "studentprofile"; // GCS 버킷 이름
    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    public String uploadFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String blobId = UUID.randomUUID().toString() + "-" + originalFilename;
        BlobId blobInfo = BlobId.of(bucketName, blobId);
        Blob blob = storage.create(BlobInfo.newBuilder(blobInfo).build(), file.getBytes());

        return blob.getMediaLink(); // 업로드된 파일의 URL 반환
    }
}
