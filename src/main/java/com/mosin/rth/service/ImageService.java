package com.mosin.rth.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	ResponseEntity<Object> getImage(Long id);

	ResponseEntity<Object> uploadImage(MultipartFile file, String userName);

	ResponseEntity<Object> deleteImage(Long id);

	ResponseEntity<Object> getAllImages(String userName);

	ResponseEntity<Object> updateImage(Long id, MultipartFile file, String userName);
}
