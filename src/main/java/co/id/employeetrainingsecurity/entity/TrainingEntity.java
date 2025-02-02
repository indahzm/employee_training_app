package co.id.employeetrainingsecurity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "training")
@Where(clause = "deleted_date is null")
@Getter
@Setter
public class TrainingEntity extends BaseEntity {
	
	@Column(name = "pengajar")
	private String pengajar;
	
	@Column(name = "tema")
	private String tema;
	
}
