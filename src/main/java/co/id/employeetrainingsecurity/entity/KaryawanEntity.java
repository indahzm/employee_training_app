package co.id.employeetrainingsecurity.entity;

import java.util.Date;

import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "karyawan")
@Where(clause = "deleted_date is null")
@Getter
@Setter
public class KaryawanEntity extends BaseEntity {
	
	@Column(name = "alamat")
	private String alamat;
	
	@Column(name = "dob")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dob;
	
	@Column(name = "nama")
	private String nama;
	
	@Column(name = "status")
	private String status;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "detail_karyawan")
	private DetailKaryawanEntity detailKaryawan;

}
