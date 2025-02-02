package co.id.employeetrainingsecurity.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.employeetrainingsecurity.entity.DetailKaryawanEntity;
import co.id.employeetrainingsecurity.entity.KaryawanEntity;
import co.id.employeetrainingsecurity.entity.KaryawanTrainingEntity;
import co.id.employeetrainingsecurity.entity.RekeningEntity;
import co.id.employeetrainingsecurity.entity.dto.ResponseDto;
import co.id.employeetrainingsecurity.entity.dto.ResponseConstant;
import co.id.employeetrainingsecurity.repository.DetailKaryawanRepository;
import co.id.employeetrainingsecurity.repository.KaryawanRepository;
import co.id.employeetrainingsecurity.repository.KaryawanTrainingRepository;
import co.id.employeetrainingsecurity.repository.RekeningRepository;
import co.id.employeetrainingsecurity.service.KaryawanService;

@Service
@Transactional
public class KaryawanServiceImpl implements KaryawanService {
	
	@Autowired
	private KaryawanRepository karyawanRepository;
	
	@Autowired
	private DetailKaryawanRepository detailKaryawanRepository;
	
	@Autowired
	private KaryawanTrainingRepository karyawanTrainingRepository;
	
	@Autowired
	private RekeningRepository rekeningRepository;

	@Override
	public ResponseDto save(KaryawanEntity karyawan) {
		DetailKaryawanEntity detailKaryawanEntity = karyawan.getDetailKaryawan();
		if (karyawan.getId() != null) {
			KaryawanEntity karyawanExist = karyawanRepository.findById(karyawan.getId()).orElse(null);
			if (karyawanExist != null) {
				karyawan.setCreatedDate(karyawanExist.getCreatedDate());
				detailKaryawanEntity.setCreatedDate(karyawanExist.getDetailKaryawan().getCreatedDate());
			} else {
				return new ResponseDto(ResponseConstant.STATUS_NOT_FOUD, ResponseConstant.DATA_NOT_FOUND.replace("${object}", "Karyawan"), 404);
			}
		} else {
			karyawan.setCreatedDate(new Date());
			detailKaryawanEntity.setCreatedDate(new Date());
		}
		karyawan.setUpdatedDate(new Date());
		detailKaryawanEntity.setUpdatedDate(new Date());
		
		karyawan = karyawanRepository.save(karyawan);
		detailKaryawanEntity = detailKaryawanRepository.save(detailKaryawanEntity);
		return new ResponseDto(ResponseConstant.STATUS_SUKSES, karyawan, 200);
	}
	
	@Override
	public ResponseDto findAll(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseDto(ResponseConstant.STATUS_SUKSES, karyawanRepository.findAll(pageable), 200);
	}

	@Override
	public ResponseDto findById(Long id) {
		KaryawanEntity karyawan = karyawanRepository.findById(id).orElse(null);
		if (karyawan == null) {
			return new ResponseDto(ResponseConstant.STATUS_NOT_FOUD, ResponseConstant.DATA_NOT_FOUND.replace("${object}", "Karyawan"), 404);			
		}
		return new ResponseDto(ResponseConstant.STATUS_SUKSES, karyawan, 200);
	}
	
	@Override
	public ResponseDto delete(Long id) {
		KaryawanEntity karyawan = karyawanRepository.findById(id).orElse(null);
		if (karyawan == null)
			return new ResponseDto(ResponseConstant.STATUS_NOT_FOUD, ResponseConstant.DATA_NOT_FOUND.replace("${object}", "Karyawan"), 404);
		
		karyawan.setDeletedDate(new Date());
		DetailKaryawanEntity detailKaryawan = karyawan.getDetailKaryawan();
		detailKaryawan.setDeletedDate(new Date());
		
		List<KaryawanTrainingEntity> karyawanTrainingList =  karyawanTrainingRepository.findAllByKaryawanId(id);
		for (KaryawanTrainingEntity karyawanTraining : karyawanTrainingList) {
			karyawanTraining.setDeletedDate(new Date());
		}
		karyawanTrainingRepository.saveAll(karyawanTrainingList);
		
		List<RekeningEntity> rekeningList =  rekeningRepository.findAllByKaryawanId(id);
		for (RekeningEntity rekening : rekeningList) {
			rekening.setDeletedDate(new Date());
		}
		rekeningRepository.saveAll(rekeningList);
		
		karyawanRepository.save(karyawan);
		detailKaryawanRepository.save(detailKaryawan);
		return new ResponseDto(ResponseConstant.STATUS_SUKSES, ResponseConstant.DATA_SUKSES, 200);
	}
	

}
