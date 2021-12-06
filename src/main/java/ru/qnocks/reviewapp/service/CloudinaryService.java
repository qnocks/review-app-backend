package ru.qnocks.reviewapp.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String upload(MultipartFile multipartFile) {
        try {
            File file = toFile(multipartFile);
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());

            if (file.delete()) {
                log.info("File successfully deleted");
            } else {
                log.error("File doesn't exist");
            }

            return uploadResult.get("url").toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private File toFile(MultipartFile multipartFile) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }
}
