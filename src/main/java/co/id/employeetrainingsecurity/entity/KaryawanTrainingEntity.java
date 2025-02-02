package co.id.employeetrainingsecurity.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "karyawan_training")
@Where(clause = "deleted_date is null")
@Getter
@Setter
public class KaryawanTrainingEntity extends BaseEntity {
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "tanggal")
	private Date tanggal;
	
	@ManyToOne
	@JoinColumn(name = "id_karyawan")
	private KaryawanEntity karyawan;
	
	@ManyToOne
	@JoinColumn(name = "id_training")
	private TrainingEntity training;

}
