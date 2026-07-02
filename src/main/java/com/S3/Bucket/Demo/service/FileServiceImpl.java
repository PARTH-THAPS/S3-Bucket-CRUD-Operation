package com.S3.Bucket.Demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public interface FileServiceImpl {

	
	String saveFile(MultipartFile file);
	
	byte[] downloadFile(String filename);
	
	String deletefile(String filename);
	
	List<String> listAllFiles();
}
