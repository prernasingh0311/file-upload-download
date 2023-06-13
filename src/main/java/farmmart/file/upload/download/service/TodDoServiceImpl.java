package farmmart.file.upload.download.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import farmmart.file.upload.download.configuration.BucketName;
import farmmart.file.upload.download.entity.File;
import farmmart.file.upload.download.repository.ToDoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@AllArgsConstructor
public class TodDoServiceImpl implements ToDoService{


    @Autowired
    private final ToDoRepository toDoRepository;

    @Autowired
    private final FileStore fileStore;

    @Autowired
    private AmazonS3 amazonS3;
    @Override
    public ResponseEntity upload(MultipartFile file) {
        if(file.isEmpty())  return ResponseEntity.ok("empty file cannot be uploaded");
        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            return ResponseEntity.ok("file format not supported");
        }
        HashMap<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
    //Save Image in S3 and then save Todo in the database
        String path = String.format("%s/%s", BucketName.TODO_IMAGE.getBucketName(), UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
        fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
    } catch (IOException e) {
        return ResponseEntity.ok("failed to upload");
    }
    File todo = File.builder()
            .file_path(path)
            .file_name(fileName)
            .build();
        System.out.println(path);
        toDoRepository.save(todo);
        return ResponseEntity.ok("File successfully uploaded");
    }

    @Override
    public S3Object download(Long id) {
        File file = toDoRepository.findById(id).get();
        return fileStore.download(file.getFile_path(), file.getFile_name());
    }

    @Override
    public File findAll() {
        return (File) toDoRepository.findAll();
    }
    @Override
    public String generatePreSignedUrl(String filePath, String bucketName, HttpMethod httpRequest) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10);
        return amazonS3.generatePresignedUrl(bucketName, filePath, calendar.getTime(), httpRequest).toString();
    }

    @Override
    public List<File> list() {
        List<File> metas = new ArrayList<>();
        toDoRepository.findAll().forEach(metas::add);
        return metas;
    }
}
