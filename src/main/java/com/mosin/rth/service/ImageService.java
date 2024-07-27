package com.mosin.rth.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	ResponseEntity<Object> getImage(Long id);

	ResponseEntity<Object> uploadImage(MultipartFile file);

	ResponseEntity<Object> deleteImage(Long id);

	ResponseEntity<Object> getAllImages();

	ResponseEntity<Object> updateImage(Long id, MultipartFile file);
}
