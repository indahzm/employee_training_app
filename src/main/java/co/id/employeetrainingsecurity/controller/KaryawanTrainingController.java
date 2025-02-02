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

import co.id.employeetrainingsecurity.entity.KaryawanTrainingEntity;
import co.id.employeetrainingsecurity.entity.dto.ResponseDto;
import co.id.employeetrainingsecurity.service.KaryawanTrainingService;

@RestController
@RequestMapping("/v1/idstar/karyawan-training")
public class KaryawanTrainingController {
	
	@Autowired
	private KaryawanTrainingService karywanTrainingService;
	
	@PostMapping("/save")
	public ResponseEntity<ResponseDto> save(@RequestBody KaryawanTrainingEntity karyawanTraining) {
		ResponseDto karyawanTrainingResponse = karywanTrainingService.save(karyawanTraining);
		return new ResponseEntity<ResponseDto>(karyawanTrainingResponse, HttpStatus.valueOf(karyawanTrainingResponse.getCode()));
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> update(@RequestBody KaryawanTrainingEntity karyawanTraining) {
		ResponseDto karyawanTrainingResponse = karywanTrainingService.save(karyawanTraining);
		return new ResponseEntity<ResponseDto>(karyawanTrainingResponse, HttpStatus.valueOf(karyawanTrainingResponse.getCode()));
	}
	
	@GetMapping("/list")
	public ResponseEntity<ResponseDto> getList(@RequestParam Integer page, @RequestParam Integer size) {
		ResponseDto karyawanTrainingResponse = karywanTrainingService.findAll(page, size);
		return new ResponseEntity<ResponseDto>(karyawanTrainingResponse, HttpStatus.valueOf(karyawanTrainingResponse.getCode()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDto> getKaryawanTraining(@PathVariable Long id) {
		ResponseDto karyawanTrainingResponse = karywanTrainingService.findById(id);
		return new ResponseEntity<ResponseDto>(karyawanTrainingResponse, HttpStatus.valueOf(karyawanTrainingResponse.getCode()));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> delete(@RequestBody KaryawanTrainingEntity karyawanTraining) {
		ResponseDto karyawanTrainingResponse = karywanTrainingService.delete(karyawanTraining.getId());
		return new ResponseEntity<ResponseDto>(karyawanTrainingResponse, HttpStatus.valueOf(karyawanTrainingResponse.getCode()));
	}

}
