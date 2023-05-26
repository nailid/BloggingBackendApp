package com.backend.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.blog.services.FileService;

@Service
@Transactional
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		//file name like abc.png
		String name = file.getOriginalFilename(); 
		//random name generate file
		String randomID = UUID.randomUUID().toString();
		String fileNameString = randomID.concat(name.substring(name.lastIndexOf(".")));
		//for full path
		String filePath = path + File.separator + fileNameString;
		//create folder if not created 
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		//copy file 
		Files.copy(file.getInputStream(), Paths.get(filePath));
		return name;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPathString = path + File.separator +fileName;
		InputStream is = new FileInputStream(fullPathString);
		//db logic to return inputStream
		return is;
	}
}
