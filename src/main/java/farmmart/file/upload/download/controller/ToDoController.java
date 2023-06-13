package farmmart.file.upload.download.controller;

import com.amazonaws.services.s3.model.S3Object;
import farmmart.file.upload.download.service.ToDoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ToDoController {
    @Autowired
    ToDoService toDoService;

    private static String UPLOADED_FOLDER =
            "/home/user/Desktop/files/";
    @GetMapping("/index")
    public String hello(Model model) {
//        model.addAttribute("files", toDoService.findAll());
        var files = toDoService.list();
        model.addAttribute("files", files);
        return "uploader";
    }

    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file")MultipartFile file) {
        return toDoService.upload(file);
    }
//    @GetMapping("/download/{id}")
//    public S3Object download(@PathVariable Long id) {
//        return toDoService.download(id);
//    }

    @GetMapping("download/{id}")
    @ResponseBody
    public HttpEntity<byte[]> download(Model model, @PathVariable Long id, HttpServletResponse response) throws
            IOException {

        S3Object s3Object = toDoService.download(id);
        String contentType = s3Object.getObjectMetadata().getContentType();
        var bytes = s3Object.getObjectContent().readAllBytes();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(contentType));
        header.setContentLength(bytes.length);

        return new HttpEntity<byte[]>(bytes, header);
    }
}
