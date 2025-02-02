package co.id.employeetrainingsecurity.service;

import co.id.employeetrainingsecurity.entity.RekeningEntity;
import co.id.employeetrainingsecurity.entity.dto.ResponseDto;

public interface RekeningService {
	
	ResponseDto save(RekeningEntity rekening);
	ResponseDto findAll(Integer page, Integer size);
	ResponseDto findById(Long id);
	ResponseDto delete(Long id);
	
}
