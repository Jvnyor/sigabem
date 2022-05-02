package br.com.javatest.sigabem.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FreteResponse {

	private String cepOrigem;

	private String cepDestino;

	private double vlTotalFrete;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataPrevistaEntrega;

}
