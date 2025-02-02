package co.id.employeetrainingsecurity.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.employeetrainingsecurity.entity.KaryawanEntity;
import co.id.employeetrainingsecurity.entity.KaryawanTrainingEntity;
import co.id.employeetrainingsecurity.entity.TrainingEntity;
import co.id.employeetrainingsecurity.entity.dto.ResponseDto;
import co.id.employeetrainingsecurity.entity.dto.ResponseConstant;
import co.id.employeetrainingsecurity.repository.KaryawanRepository;
import co.id.employeetrainingsecurity.repository.KaryawanTrainingRepository;
import co.id.employeetrainingsecurity.repository.TrainingRepository;
import co.id.employeetrainingsecurity.service.KaryawanTrainingService;

@Service
@Transactional
public class KaryawanTrainingServiceImpl implements KaryawanTrainingService {
	
	@Autowired
	private KaryawanTrainingRepository karyawanTrainingRepository;

	@Autowired
	private KaryawanRepository karyawanRepository;
	
	@Autowired
	private TrainingRepository trainingRepository;
	
	@Override
	public ResponseDto save(KaryawanTrainingEntity karyawanTraining) {
		if(karyawanTraining.getId() != null) {
			KaryawanTrainingEntity karyawanTrainingExist = karyawanTrainingRepository.findById(karyawanTraining.getId()).orElse(null);
			if(karyawanTrainingExist != null) {
				if (!karyawanTraining.getKaryawan().getId().equals(karyawanTrainingExist.getKaryawan().getId())) {
					KaryawanEntity karyawan = getKaryawan(karyawanTraining.getKaryawan().getId());
					if (karyawan == null) {
						return new ResponseDto(ResponseConstant.STATUS_NOT_FOUD, ResponseConstant.DATA_NOT_FOUND.replace("${object}", "Karyawan"), 404); 
					}
					karyawanTraining.setKaryawan(karyawan);
				} else {
					karyawanTraining.setKaryawan(karyawanTrainingExist.getKaryawan());
				}
				if (!karyawanTraining.getTraining().getId().equals(karyawanTrainingExist.getTraining().getId())) {
					TrainingEntity training = getTraining(karyawanTraining.getTraining().getId());
					if(training == null) {
						return new ResponseDto(ResponseConstant.STATUS_NOT_FOUD, ResponseConstant.DATA_NOT_FOUND.replace("${object}", "Training"), 404); 
					}
					karyawanTraining.setTraining(training);
				} else {
					karyawanTraining.setTraining(karyawanTrainingExist.getTraining());
				}
				
				karyawanTraining.setCreatedDate(karyawanTrainingExist.getCreatedDate());
			} else {
				return new ResponseDto(ResponseConstant.STATUS_NOT_FOUD, ResponseConstant.DATA_NOT_FOUND.replace("${object}", "Karyawan Training"), 404);
			}
		} else {
			KaryawanEntity karyawan = getKaryawan(karyawanTraining.getKaryawan().getId());
			if (karyawan == null) {
				return new ResponseDto(ResponseConstant.STATUS_NOT_FOUD, ResponseConstant.DATA_NOT_FOUND.replace("${object}", "Karyawan"), 404); 
			}
			karyawanTraining.setKaryawan(karyawan);
			TrainingEntity training = getTraining(karyawanTraining.getTraining().getId());
			if(training == null) {
				return new ResponseDto(ResponseConstant.STATUS_NOT_FOUD, ResponseConstant.DATA_NOT_FOUND.replace("${object}", "Training"), 404); 
			}
			karyawanTraining.setTraining(training);
			karyawanTraining.setCreatedDate(new Date());
		}
		karyawanTraining.setUpdatedDate(new Date());
		karyawanTraining = karyawanTrainingRepository.save(karyawanTraining);
		return new ResponseDto(ResponseConstant.STATUS_SUKSES, karyawanTraining, 200);
	}
	
	private KaryawanEntity getKaryawan(Long id) {
		KaryawanEntity karyawan = karyawanRepository.findById(id).orElse(null);
		return karyawan;
	}
	
	private TrainingEntity getTraining(Long id) {
		TrainingEntity trainingExist = trainingRepository.findById(id).orElse(null);
		return trainingExist;
	}

	@Override
	public ResponseDto findAll(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseDto(ResponseConstant.STATUS_SUKSES, karyawanTrainingRepository.findAll(pageable), 200);
	}

	@Override
	public ResponseDto findById(Long id) {
		KaryawanTrainingEntity karyawanTraining = karyawanTrainingRepository.findById(id).orElse(null);
		if (karyawanTraining == null) {
			return new ResponseDto(ResponseConstant.STATUS_NOT_FOUD, ResponseConstant.DATA_NOT_FOUND.replace("${object}", "Karyawan Training"), 404);
		}
		return new ResponseDto(ResponseConstant.STATUS_SUKSES, karyawanTraining, 200);	

	}

	@Override
	public ResponseDto delete(Long id) {
		KaryawanTrainingEntity karyawanTraining = karyawanTrainingRepository.findById(id).orElse(null);
		if (karyawanTraining == null) {
			return new ResponseDto(ResponseConstant.STATUS_NOT_FOUD, ResponseConstant.DATA_NOT_FOUND.replace("${object}", "Karyawan Training"), 404);
		}
		karyawanTraining.setDeletedDate(new Date());
		karyawanTrainingRepository.save(karyawanTraining);
		return new ResponseDto(ResponseConstant.STATUS_SUKSES, ResponseConstant.DATA_SUKSES, 200);
	}

}
