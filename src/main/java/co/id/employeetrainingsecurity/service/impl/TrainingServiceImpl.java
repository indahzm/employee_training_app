package co.id.employeetrainingsecurity.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.employeetrainingsecurity.entity.KaryawanTrainingEntity;
import co.id.employeetrainingsecurity.entity.TrainingEntity;
import co.id.employeetrainingsecurity.entity.dto.ResponseConstant;
import co.id.employeetrainingsecurity.entity.dto.ResponseDto;
import co.id.employeetrainingsecurity.repository.KaryawanTrainingRepository;
import co.id.employeetrainingsecurity.repository.TrainingRepository;
import co.id.employeetrainingsecurity.service.TrainingService;

@Service
@Transactional
public class TrainingServiceImpl implements TrainingService {
	
	@Autowired
	private TrainingRepository trainingRepository;
	
	@Autowired
	private KaryawanTrainingRepository karyawanTrainingRepository;

	@Override
	public ResponseDto save(TrainingEntity training) {
		if(training.getId() != null) {
			TrainingEntity trainingExist = trainingRepository.findById(training.getId()).orElse(null);
			if (trainingExist == null) {
				return new ResponseDto(ResponseConstant.STATUS_NOT_FOUD, ResponseConstant.DATA_NOT_FOUND.replace("${object}", "Training"), 404);
			}
			training.setCreatedDate(trainingExist.getCreatedDate());
		} else {
			training.setCreatedDate(new Date());
		} 
		training.setUpdatedDate(new Date());
		trainingRepository.save(training);
		return new ResponseDto(ResponseConstant.STATUS_SUKSES, training, 200);
	}

	@Override
	public ResponseDto findAll(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseDto(ResponseConstant.STATUS_SUKSES, trainingRepository.findAll(pageable), 200);
	}

	@Override
	public ResponseDto findById(Long id) {
		TrainingEntity training = trainingRepository.findById(id).orElse(null);
		if (training == null) {
			return new ResponseDto(ResponseConstant.STATUS_NOT_FOUD, ResponseConstant.DATA_NOT_FOUND.replace("${object}", "Training"), 404);
		}
		return new ResponseDto(ResponseConstant.STATUS_SUKSES, training, 200);
	}

	@Override
	public ResponseDto delete(Long id) {
		TrainingEntity training = trainingRepository.findById(id).orElse(null);
		if (training == null) {
			return new ResponseDto(ResponseConstant.STATUS_NOT_FOUD, ResponseConstant.DATA_NOT_FOUND.replace("${object}", "Training"), 404);
		}
		training.setDeletedDate(new Date());
		
		List<KaryawanTrainingEntity> karyawanTrainingList =  karyawanTrainingRepository.findAllByTrainingId(id);
		for (KaryawanTrainingEntity karyawanTraining : karyawanTrainingList) {
			karyawanTraining.setDeletedDate(new Date());
		}
		karyawanTrainingRepository.saveAll(karyawanTrainingList);
		
		trainingRepository.save(training);
		return new ResponseDto(ResponseConstant.STATUS_SUKSES, ResponseConstant.DATA_SUKSES, 200);
	}

}
