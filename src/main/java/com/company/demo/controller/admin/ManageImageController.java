package com.company.demo.controller.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.UUID;

import com.company.demo.entity.Image;
import com.company.demo.exception.BadRequestException;
import com.company.demo.exception.InternalServerException;
import com.company.demo.exception.NotFoundException;
import com.company.demo.security.CustomUserDetails;
import com.company.demo.service.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ManageImageController {
    private static String UPLOAD_DIR = System.getProperty("user.home") + "/media/upload";

    @Autowired
    private ImageService imageService;

    @PostMapping("/api/upload-file")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        // Create folder to save file if not exist
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        ;
        if (originalFilename != null && originalFilename.length() > 0) {
            if (!extension.equals("png") && !extension.equals("jpg") && !extension.equals("gif")
                    && !extension.equals("svg") && !extension.equals("jpeg")) {
                throw new BadRequestException("Kh??ng h??? tr??? ?????nh d???ng file n??y");
            }
            try {
                Image img = new Image();
                img.setName(file.getName());
                img.setSize(file.getSize());
                img.setType(extension);
                img.setUploadedAt(new Timestamp(System.currentTimeMillis()));
                img.setUploadedBy(
                        ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                                .getUser());
                img.setId(UUID.randomUUID().toString());
                String link = "/media/static/" + img.getId() + "." + extension;
                img.setLink(link);

                // Create file
                File serverFile = new File(UPLOAD_DIR + "/" + img.getId() + "." + extension);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(file.getBytes());
                stream.close();

                imageService.save(img);
                return ResponseEntity.ok(link);
            } catch (Exception e) {
                throw new InternalServerException("L???i khi upload file");
            }
        }

        throw new BadRequestException("File kh??ng h???p l???");
    }

    @GetMapping("/media/static/{filename:.+}")
    public ResponseEntity<?> download(@PathVariable String filename) {
        File file = new File(UPLOAD_DIR + "/" + filename);
        if (!file.exists()) {
            throw new NotFoundException("File kh??ng t???n t???i");
        }

        UrlResource resource;
        try {
            resource = new UrlResource(file.toURI());
        } catch (MalformedURLException e) {
            throw new NotFoundException("File kh??ng t???n t???i");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }

    @DeleteMapping("/api/delete-image/{filename:.+}")
    public ResponseEntity<?> deleteFile(@PathVariable String filename) {
        imageService.deleteImage(UPLOAD_DIR, filename);
        return ResponseEntity.ok("X??a th??nh c??ng");
    }
}
