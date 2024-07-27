package com.mosin.rth.serviceImpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mosin.rth.dto.ResponseMessage;
import com.mosin.rth.entities.Image;
import com.mosin.rth.repository.ImageRepository;
import com.mosin.rth.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

	@Autowired
	ImageRepository imageRepository;

	@Override
	public ResponseEntity<Object> getImage(Long id) {
		try {
			logger.info("[ImageServiceImpl] [getImage] In get Image...");

			return ResponseEntity.ok().body(imageRepository.findById(id));
		} catch (Exception e) {
			logger.info("[ImageServiceImpl] [getImage] Error while getting image..." + e.getMessage());

			return ResponseEntity.ok().body(new ResponseMessage("Error while getting the image..."));
		}
	}

	@Override
	public ResponseEntity<Object> uploadImage(MultipartFile file) {
		try {
			Image image = new Image();

			image.setName(file.getOriginalFilename());
			image.setImageType(file.getContentType());
			image.setImageData(file.getBytes());

			imageRepository.save(image);
			logger.info("[ImageServiceImpl] [uploadImage] Image uploaded successfully... ");

			return ResponseEntity.ok().body(image);
		}

		catch (Exception e) {
			logger.error("[ImageServiceImpl] [uploadImage] Error: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.ok().body(new ResponseMessage("Error while uploading the image..."));
		}
	}

	@Override
	public ResponseEntity<Object> deleteImage(Long id) {
		try {
			imageRepository.deleteById(id);
			logger.info("[ImageServiceImpl] [deleteImage] Image deleted successfully... ");

			return ResponseEntity.ok().body(new ResponseMessage("Image deleted succesfully from the DB..."));

		} catch (Exception e) {
			logger.error("[ImageServiceImpl] [deleteImage] Error: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.ok().body(new ResponseMessage("Error while deleting the image..."));
		}
	}

	@Override
	public ResponseEntity<Object> getAllImages() {
		try {
			logger.info("[ImageServiceImpl] [getAllImages] In get all images...");
			return ResponseEntity.ok().body(imageRepository.findAll());

		} catch (Exception e) {
			logger.error("[ImageServiceImpl] [getAllImages] Error: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.ok().body(new ResponseMessage("Error while getting all images data..."));
		}

	}

	@Override
	public ResponseEntity<Object> updateImage(Long id, MultipartFile file) {
		logger.info("[ImageServiceImpl] [updateImage] In get all images...");
		Optional<Image> image = imageRepository.findById(id);
		
		if(!image.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Image Not Found"));
		}
		
		try {
			Image newImage = image.get();
			newImage.setImageType(file.getContentType());
			newImage.setName(file.getOriginalFilename());
			newImage.setImageData(file.getBytes());
			
			imageRepository.save(newImage);
			
            return ResponseEntity.ok(new ResponseMessage("Image updated successfully"));

		} catch (Exception e) {
			logger.error("[ImageServiceImpl] [updateImage] Error: " + e.getMessage());
			return ResponseEntity.ok().body(new ResponseMessage("Error while updating the image..."));
		}
	}
}
