package com.ssn.aso.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ssn.aso.exceptions.FileStorageException;
import com.ssn.aso.property.FileStorageProperties;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory.", ex);
		}
	}

	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Invalid sequence " + fileName);
			}
			long TICKS_AT_EPOCH = 621355968000000000L;
			long tick = System.currentTimeMillis() * 10000 + TICKS_AT_EPOCH;
			fileName = String.valueOf(tick).concat("_").concat(file.getOriginalFilename());
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			String absolutePath = targetLocation.toString();
			String rootPath = this.fileStorageLocation.getParent().toAbsolutePath().toString();
			String relativeURL = absolutePath.replace(rootPath, "");
			return relativeURL;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileStorageException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new FileStorageException("File not found " + fileName);
		}
	}
}