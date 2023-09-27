package com.ssn.aso.control;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssn.aso.common.Result;
import com.ssn.aso.common.UploadModel;
import com.ssn.aso.service.FileStorageService;

@RestController
@RequestMapping("/api/upload")
public class UploadController extends BaseController {

	@Autowired
	private FileStorageService fileStorageService;

	@PostMapping("")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile) {

		Result result = new Result();

		if (uploadfile.isEmpty()) {
			return ResponseEntity.ok().body("please select a file!");
		}

		try {
			String[] fileUrls = saveUploadedFiles(Arrays.asList(uploadfile));
			result.setMessage(getApiUrl() + fileUrls[0]);
		} catch (IOException e) {
			return ResponseEntity.badRequest().build();
		}

		result.setstatusCode(200);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/api/upload/multi")
	public ResponseEntity<?> uploadFileMulti(@RequestParam("extraField") String extraField,
			@RequestParam("files") MultipartFile[] uploadfiles) {

		String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
				.filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(","));

		if (StringUtils.isEmpty(uploadedFileName)) {
			return ResponseEntity.ok().body("please select a file!");
		}

		try {
			saveUploadedFiles(Arrays.asList(uploadfiles));
		} catch (IOException e) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok().body("Successfully uploaded - " + uploadedFileName);
	}

	@PostMapping("/api/upload/multi/model")
	public ResponseEntity<?> multiUploadFileModel(@ModelAttribute UploadModel model) {

		try {
			saveUploadedFiles(Arrays.asList(model.getFiles()));
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok().body("Successfully uploaded!");
	}

	private String[] saveUploadedFiles(List<MultipartFile> files) throws IOException {
		String[] fileUrls = new String[files.size()];
		int index = 0;
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue;
			}

			fileUrls[index] = fileStorageService.storeFile(file);
			index++;
		}
		return fileUrls;
	}

}