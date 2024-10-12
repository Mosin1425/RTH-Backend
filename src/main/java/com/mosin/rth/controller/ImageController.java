package com.mosin.rth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.mosin.rth.entities.Image;
import com.mosin.rth.service.ImageService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "https://rajasthantenthouse.com/")
//@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping(value = "/api/v1")
public class ImageController {

	@Autowired
	ImageService imageService;
	
	private final Map<String, String> validUsers = Map.of(
	        "mosin", "Mosin@4433",
	        "shahrukh", "Shahrukh@4433"
	    );

	    @GetMapping("/test")
	    public String test() {
	        return "tested successfully...";
	    }

	    @PostMapping("/login")
	    public ResponseEntity<Map<String, String>> login(
	            @RequestParam("username") String username,
	            @RequestParam("password") String password,
	            HttpSession session) {
	        Map<String, String> response = new HashMap<>();

	        // Check if the username exists and password matches
	        if (validUsers.containsKey(username) && validUsers.get(username).equals(password)) {
	            session.setAttribute("username", username);
	            response.put("message", "Login successful");
	            return ResponseEntity.ok(response);
	        } else {
	            response.put("message", "Invalid credentials");
	            return ResponseEntity.status(401).body(response); 
	        }
	    }

    @GetMapping("/current-user")
    public ResponseEntity<Map<String, String>> currentUser(HttpSession session) {
        Map<String, String> response = new HashMap<>();

        // Check if the user is logged in
        String username = (String) session.getAttribute("username");
        if (username != null) {
            response.put("username", username);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "User not logged in");
            return ResponseEntity.status(401).body(response);  // Unauthorized status
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate session
        request.getSession().invalidate();
        return ResponseEntity.ok(Map.of("message", "Logout successful"));
    }

	
	@PostMapping("/upload-image")
	public ResponseEntity<Object> uploadImage(@RequestParam("image") MultipartFile file, HttpSession session) {
		if (file == null || file.isEmpty()) {
			return ResponseEntity.badRequest().body(new ResponseMessage("No file uploaded"));
		}
		if(file.getSize()>1000000) {
			return ResponseEntity.badRequest().body(new ResponseMessage("File size exceeds"));
		}
		System.out.println(file.getSize());
		String userName = (String) session.getAttribute("username");
		if (userName != null && !userName.isEmpty()) {
			return imageService.uploadImage(file, userName);
		}
		return ResponseEntity.internalServerError().body("No User Found...");
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
	public ResponseEntity<Object> getAll(HttpSession session) {
		
		String userName = (String) session.getAttribute("username");
		return imageService.getAllImages(userName);
	}
	
	@PutMapping("/update-image")
	public ResponseEntity<Object> updateImage(@RequestParam Long id, @RequestParam("image") MultipartFile file,
			HttpSession session){
		if(file==null || file.isEmpty()) {
			return ResponseEntity.badRequest().body(new ResponseMessage("No file uploaded"));
		}
		
		String userName = (String) session.getAttribute("username");
		if (userName != null && !userName.isEmpty()) {
			return imageService.updateImage(id, file, userName);
		}
		return ResponseEntity.internalServerError().body("No User Found...");
	}
	
	@GetMapping("/images")
	public ResponseEntity<Page<Image>> getAllImages(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "8") int size) {
	    return imageService.getAllData(page, size);
	}
}
