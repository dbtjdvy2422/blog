package com.cos.blog.dto;

import org.springframework.web.multipart.MultipartFile;

import com.cos.blog.model.Image;
import com.cos.blog.model.User;

import lombok.Data;

@Data
public class ImageUploadDto {
	
	private MultipartFile file;
	private String caption;
	
	public Image toEntity(String postImageUrl, User user) {
		return Image.builder()
				.caption(caption)
				.postImageUrl(postImageUrl)
				.user(user)
				.build();
	}
}
