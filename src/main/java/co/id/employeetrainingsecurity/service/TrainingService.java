package co.id.employeetrainingsecurity.service;

import co.id.employeetrainingsecurity.entity.TrainingEntity;
import co.id.employeetrainingsecurity.entity.dto.ResponseDto;

public interface TrainingService {

	ResponseDto save(TrainingEntity training);
	ResponseDto findAll(Integer page, Integer size);
	ResponseDto findById(Long id);
	ResponseDto delete(Long id);
	
}
