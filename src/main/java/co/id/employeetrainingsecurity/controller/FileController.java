package co.id.employeetrainingsecurity.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import co.id.employeetrainingsecurity.entity.dto.UploadFileResponse;
import co.id.employeetrainingsecurity.service.FileStorageService;
import javax.servlet.http.HttpServletRequest;

@RestController
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Value("${app.uploadto.cdn}")
	private String UPLOADED_FOLDER;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@PostMapping(value = "/v1/upload", consumes = {"multipart/form-data", "application/json"})
	public UploadFileResponse uploadFile(@RequestParam MultipartFile file) throws IOException {
		Date date = new Date();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("ddMMyyyyhhmmss");
		String strDate = simpleDateformat.format(date);
		String nameFormat = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") ); 
	    if(nameFormat.isEmpty()){
	    	nameFormat = ".png";
	    } 
	    		           
		String fileName = UPLOADED_FOLDER + strDate + file.getOriginalFilename();
		String downloadFileName = strDate + file.getOriginalFilename();
		Path path = Paths.get(fileName);
		
		try {
			Files.copy(file.getInputStream(), path);
		} catch (Exception e) {
			e.printStackTrace();
			return new UploadFileResponse(fileName, null, file.getContentType(), file.getSize(), e.getMessage());
		}
		
		String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/v1/showFile/")
				.path(downloadFileName)
				.toUriString();
		return new UploadFileResponse(downloadFileName, fileDownloadUrl, file.getContentType(), file.getSize(), "false");
	}
	
	@GetMapping("v1/showFile/{fileName:.+}")
	public ResponseEntity<Resource> showFile(@PathVariable String fileName, HttpServletRequest request) {
		Resource resource = fileStorageService.loadFileAsResource(fileName);
		String contentType = null;
		
		try {
			System.out.println("resource.getFile().getAbsolutePath" + 
					resource.getFile().getAbsolutePath());
			
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			logger.info("Could not determine file type."); 
		}
		
		if (contentType == null) {
			contentType = "application/octet-stream";
		} 
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"") 
                .body(resource); 
	}
	
	@PostMapping("v1/upload-multiple-files")
	public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) throws IOException {
		return Arrays.asList(files)
				.stream() 
                .map(file -> { 
                    try {
                        return uploadFile(file); 
                    } catch (IOException e) { 
                        e.printStackTrace(); 
                    } 
                    return null; 
                }) 
                .collect(Collectors.toList());
	}
	
	
	
}
