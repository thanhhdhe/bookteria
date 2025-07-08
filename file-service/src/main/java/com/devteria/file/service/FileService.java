package com.devteria.file.service;

import com.devteria.file.dto.response.FileData;
import com.devteria.file.dto.response.FileResponse;
import com.devteria.file.exception.AppException;
import com.devteria.file.exception.ErrorCode;
import com.devteria.file.mapper.FileMgmtMapper;
import com.devteria.file.repository.FileMgmtRepository;
import com.devteria.file.repository.FileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;

import java.io.IOException;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
public class FileService {
    FileRepository fileRepository;
    FileMgmtRepository fileMgmtRepository;
    FileMgmtMapper fileMgmtMapper;
    public FileResponse uploadFile(MultipartFile file) throws IOException {
        var fileInfo = fileRepository.store(file);
        var fileMgmt = fileMgmtMapper.toFileMgmt(fileInfo);
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        fileMgmt.setOwnerId(userId);
        fileMgmt = fileMgmtRepository.save(fileMgmt);

        return FileResponse.builder()
                .url(fileInfo.getUrl())
                .originalFileName(file.getOriginalFilename())
                .build();
    }

    public FileData download(String filename) throws IOException {
        var fileMgmt = fileMgmtRepository.findById(filename).orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));

        var resourse = fileRepository.read(fileMgmt);
        return new FileData(fileMgmt.getContentType(), resourse);
    }
}
