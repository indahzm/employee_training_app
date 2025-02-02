package co.id.employeetrainingsecurity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rekening")
@Where(clause = "deleted_date is null")
@Getter
@Setter
public class RekeningEntity extends BaseEntity {
	
	@Column(name = "jenis")
	private String jenis;
	
	@Column(name = "nama")
	private String nama;
	
	@Column(name = "rekening")
	private String rekening;
	
	@Column(name = "alamat")
	private String alamat;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_karyawan")
	private KaryawanEntity karyawan;

}
