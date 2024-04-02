package entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Agenda implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "nome_paciente", length = 40, nullable = false)
	private String paciente;
	private String medico;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraConsulta;

	
	public Agenda() {
	}
	
	public Agenda(Integer id, String paciente, String medico, Date dataHoraConsulta) {
		this.id = id;
		this.paciente = paciente;
		this.medico = medico;
		this.dataHoraConsulta = dataHoraConsulta;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPaciente() {
		return paciente;
	}
	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}
	public String getMedico() {
		return medico;
	}
	public void setMedico(String medico) {
		this.medico = medico;
	}
	public Date getDataHoraConsulta() {
		return dataHoraConsulta;
	}
	public void setDataHoraConsulta(Date dataHoraConsulta) {
		this.dataHoraConsulta = dataHoraConsulta;
	}
}
