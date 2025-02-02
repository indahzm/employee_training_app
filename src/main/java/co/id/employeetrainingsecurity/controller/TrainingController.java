package co.id.employeetrainingsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.id.employeetrainingsecurity.entity.TrainingEntity;
import co.id.employeetrainingsecurity.entity.dto.ResponseDto;
import co.id.employeetrainingsecurity.service.TrainingService;

@RestController
@RequestMapping("/v1/idstar/training")
public class TrainingController {
	
	@Autowired
	private TrainingService trainingService;
	
	@PostMapping("/save")
	public ResponseEntity<ResponseDto> save(@RequestBody TrainingEntity training) {
		ResponseDto trainingResponse = trainingService.save(training);
		return new ResponseEntity<ResponseDto>(trainingResponse, HttpStatus.valueOf(trainingResponse.getCode()));
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> update(@RequestBody TrainingEntity training) {
		ResponseDto trainingResponse = trainingService.save(training);
		return new ResponseEntity<ResponseDto>(trainingResponse, HttpStatus.valueOf(trainingResponse.getCode()));
	}
	
	@GetMapping("/list")
	public ResponseEntity<ResponseDto> getList(@RequestParam Integer page, @RequestParam Integer size) {
		ResponseDto trainingResponse = trainingService.findAll(page, size);
		return new ResponseEntity<ResponseDto>(trainingResponse, HttpStatus.valueOf(trainingResponse.getCode()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDto> getTraining(@PathVariable Long id) {
		ResponseDto trainingResponse = trainingService.findById(id);
		return new ResponseEntity<ResponseDto>(trainingResponse, HttpStatus.valueOf(trainingResponse.getCode()));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> delete(@RequestBody TrainingEntity training) {
		ResponseDto trainingResponse = trainingService.delete(training.getId());
		return new ResponseEntity<ResponseDto>(trainingResponse, HttpStatus.valueOf(trainingResponse.getCode()));
	}
}
