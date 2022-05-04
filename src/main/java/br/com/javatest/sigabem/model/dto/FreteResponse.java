package br.com.javatest.sigabem.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FreteResponse {

	@Schema(example = "11111-000")
	private String cepOrigem;

	@Schema(example = "22222-000")
	private String cepDestino;

	private double vlTotalFrete;

	@JsonFormat(pattern = "dd/MM/yyyy")
	@Schema(pattern = "dd/MM/yyyy")
	private LocalDate dataPrevistaEntrega;

}
