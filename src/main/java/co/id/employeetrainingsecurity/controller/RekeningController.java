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

import co.id.employeetrainingsecurity.entity.RekeningEntity;
import co.id.employeetrainingsecurity.entity.dto.ResponseDto;
import co.id.employeetrainingsecurity.service.RekeningService;

@RestController
@RequestMapping("/v1/idstar/rekening")
public class RekeningController {
	
	@Autowired
	private RekeningService rekeningService;
	
	@PostMapping("/save")
	public ResponseEntity<ResponseDto> save(@RequestBody RekeningEntity rekening) {
		ResponseDto rekeningResponse = rekeningService.save(rekening);
		return new ResponseEntity<ResponseDto>(rekeningResponse, HttpStatus.valueOf(rekeningResponse.getCode()));
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> update(@RequestBody RekeningEntity rekening) {
		ResponseDto rekeningResponse = rekeningService.save(rekening);
		return new ResponseEntity<ResponseDto>(rekeningResponse, HttpStatus.valueOf(rekeningResponse.getCode()));
	}
	
	@GetMapping("/list")
	public ResponseEntity<ResponseDto> getList(@RequestParam Integer page, @RequestParam Integer size) {
		ResponseDto rekeningResponse = rekeningService.findAll(page, size);
		return new ResponseEntity<ResponseDto>(rekeningResponse, HttpStatus.valueOf(rekeningResponse.getCode()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDto> getRekening(@PathVariable Long id) {
		ResponseDto rekeningResponse = rekeningService.findById(id);
		return new ResponseEntity<ResponseDto>(rekeningResponse, HttpStatus.valueOf(rekeningResponse.getCode()));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> delete(@RequestBody RekeningEntity rekening) {
		ResponseDto rekeningResponse = rekeningService.delete(rekening.getId());
		return new ResponseEntity<ResponseDto>(rekeningResponse, HttpStatus.valueOf(rekeningResponse.getCode()));
	}

}
