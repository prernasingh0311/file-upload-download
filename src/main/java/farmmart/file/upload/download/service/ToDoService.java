package farmmart.file.upload.download.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.S3Object;
import farmmart.file.upload.download.entity.File;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ToDoService {
    public ResponseEntity upload(MultipartFile file);

    public File findAll() ;
    public S3Object download(Long id);

    public List<File> list();

    public String generatePreSignedUrl(String filePath, String bucketName, HttpMethod httpRequest);

}
