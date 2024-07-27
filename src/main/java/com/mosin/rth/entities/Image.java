package com.mosin.rth.entities;

import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "IMAGES")
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "IMAGE_NAME")
	private String name;

	@Column(name = "IMAGE_TYPE")
	private String imageType;

	@Column(name = "IMAGE_DATA", columnDefinition = "LONGBLOB")
	@Lob
	private byte[] imageData;

	public Image(Long id, String name, String imageType, byte[] imageData) {
		super();
		this.id = id;
		this.name = name;
		this.imageType = imageType;
		this.imageData = imageData;
	}

	public Image() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", name=" + name + ", imageData=" + Arrays.toString(imageData) + "]";
	}
}
