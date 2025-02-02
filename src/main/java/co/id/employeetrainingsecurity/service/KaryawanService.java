package co.id.employeetrainingsecurity.service;

import co.id.employeetrainingsecurity.entity.KaryawanEntity;
import co.id.employeetrainingsecurity.entity.dto.ResponseDto;

public interface KaryawanService {
	
	ResponseDto save(KaryawanEntity karyawan);
	ResponseDto findAll(Integer page, Integer size);
	ResponseDto findById(Long id);
	ResponseDto delete(Long id);

}
