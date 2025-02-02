package co.id.employeetrainingsecurity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "detail_karyawan")
@Where(clause = "deleted_date is null")
@Getter
@Setter
public class DetailKaryawanEntity extends BaseEntity {
	
	@Column(name = "nik")
	private String nik;
	
	@Column(name = "npwp")
	private String npwp;

}
