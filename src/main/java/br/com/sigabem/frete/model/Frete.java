package br.com.sigabem.frete.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class Frete {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "peso")
	private float peso;

	@Column(name = "cep_origem")
	private String cepOrigem;

	@Column(name = "cep_destino")
	private String cepDestino;

	@Column(name = "nome_destinatario")
	private String nomeDestinatario;

	@Column(name = "vl_total_frete")
	private double vlTotalFrete;

	@Column(name = "data_prevista_entrega")
	private LocalDate dataPrevistaEntrega;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "data_consulta")
	private LocalDateTime dataConsulta;

}
