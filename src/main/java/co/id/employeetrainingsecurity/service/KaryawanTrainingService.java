package co.id.employeetrainingsecurity.service;

import co.id.employeetrainingsecurity.entity.KaryawanTrainingEntity;
import co.id.employeetrainingsecurity.entity.dto.ResponseDto;

public interface KaryawanTrainingService {

	ResponseDto save(KaryawanTrainingEntity karyawanTraining);
	ResponseDto findAll(Integer page, Integer size);
	ResponseDto findById(Long id);
	ResponseDto delete(Long id);
	
}
