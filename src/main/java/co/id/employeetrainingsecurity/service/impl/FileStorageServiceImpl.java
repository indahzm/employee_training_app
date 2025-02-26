package co.id.employeetrainingsecurity.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import co.id.employeetrainingsecurity.service.FileStorageService;
import co.id.employeetrainingsecurity.util.FileStorageException;
import co.id.employeetrainingsecurity.util.FileStorageProperties;

@Service
public class FileStorageServiceImpl implements FileStorageService{
	
	private static final Logger logger = LoggerFactory.getLogger(FileStorageServiceImpl.class.getName());
	
	private final Path fileStorageLocation;
	Date date = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("ddMyyyyhhmmss");
	String strDate = formatter.format(date);

	public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
				.toAbsolutePath().normalize();
		
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (IOException e) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
		}
	}
	
	@Override
	public String storeFile(MultipartFile file) {
		logger.info(">> Start Store File <<");
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if(fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains d path sequence " + fileName);
			}
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch (IOException e) {
			logger.error(">> Error Store File : " + e.getMessage() + " <<");
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
		}
	}
	
	@Override
	public Resource loadFileAsResource(String fileName) {
		logger.info(">> Start Load File <<");
		Resource resource = null;
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			resource = new UrlResource(filePath.toUri()); 
			if(resource.exists()) {
				return resource;
			} else {
				logger.error(">> Error : File not found<<");
	            throw new FileStorageException("File not found " + fileName);
			}
		} catch (MalformedURLException e) {
			logger.error(">> Error : File not found<<");
			throw new FileStorageException("File not found " + fileName, e);
		}
	} 

}
