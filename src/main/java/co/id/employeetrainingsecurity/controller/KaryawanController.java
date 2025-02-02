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

import co.id.employeetrainingsecurity.entity.KaryawanEntity;
import co.id.employeetrainingsecurity.entity.dto.ResponseDto;
import co.id.employeetrainingsecurity.service.KaryawanService;

@RestController
@RequestMapping("/v1/idstar/karyawan")
public class KaryawanController {
	
	@Autowired
	private KaryawanService karyawanService;
	
	@PostMapping("/save")
	public ResponseEntity<ResponseDto> save(@RequestBody KaryawanEntity karyawan) {
		ResponseDto karyawanResponse = karyawanService.save(karyawan);
		return new ResponseEntity<ResponseDto>(karyawanResponse, HttpStatus.valueOf(karyawanResponse.getCode()));
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> update(@RequestBody KaryawanEntity karyawan) {
		ResponseDto karyawanResponse = karyawanService.save(karyawan);
		return new ResponseEntity<ResponseDto>(karyawanResponse, HttpStatus.valueOf(karyawanResponse.getCode()));
	}
	
	@GetMapping("/list")
	public ResponseEntity<ResponseDto> getList(@RequestParam Integer page, @RequestParam Integer size) {
		ResponseDto karyawanResponse = karyawanService.findAll(page, size);
		return new ResponseEntity<ResponseDto>(karyawanResponse, HttpStatus.valueOf(karyawanResponse.getCode()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDto> getKaryawan(@PathVariable Long id) {
		ResponseDto karyawanResponse = karyawanService.findById(id);
		return new ResponseEntity<ResponseDto>(karyawanResponse, HttpStatus.valueOf(karyawanResponse.getCode()));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> delete(@RequestBody KaryawanEntity karyawan) {
		ResponseDto karyawanResponse = karyawanService.delete(karyawan.getId());
		return new ResponseEntity<ResponseDto>(karyawanResponse, HttpStatus.valueOf(karyawanResponse.getCode()));
	}
	
}
