package com.mosin.rth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mosin.rth.entities.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{

	List<Image> findByUserName(String userName);
}
