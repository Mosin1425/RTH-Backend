package com.mosin.rth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mosin.rth.dto.ResponseMessage;
import com.mosin.rth.service.ImageService;

@CrossOrigin(origins = "https://rajasthantenthouse.com/")
@RestController
@RequestMapping(value = "/api/v1")
public class ImageController {

	@Autowired
	ImageService imageService;

	@GetMapping("/test")
	public String test() {
		return "tested successfully...";
	}

	@PostMapping("/upload-image")
	public ResponseEntity<Object> uploadImage(@RequestParam("image") MultipartFile file) {
		if (file==null || file.isEmpty()) {
			return ResponseEntity.badRequest().body(new ResponseMessage("No file uploaded"));
		}
		return imageService.uploadImage(file);
	}

	@GetMapping("/get-image")
	public ResponseEntity<Object> getImage(@RequestParam Long id) {
		return imageService.getImage(id);
	}

	@DeleteMapping("/delete-image")
	public ResponseEntity<Object> deleteImage(@RequestParam Long id) {
		return imageService.deleteImage(id);
	}

	@GetMapping("/getAll")
	public ResponseEntity<Object> getAll() {
		return imageService.getAllImages();
	}
	
	@PutMapping("/update-image")
	public ResponseEntity<Object> updateImage(@RequestParam Long id, @RequestParam("image") MultipartFile file){
		if(file==null || file.isEmpty()) {
			return ResponseEntity.badRequest().body(new ResponseMessage("No file uploaded"));
		}
		return imageService.updateImage(id, file);
	}
}
