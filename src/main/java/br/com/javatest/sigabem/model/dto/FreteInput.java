package br.com.javatest.sigabem.model.dto;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreteInput {

	@Schema(description = "peso", required = true)
	@NotEmpty
	private float peso;

	@Schema(description = "cepOrigem", required = true, example = "11111-000")
	@NotEmpty
	private String cepOrigem;

	@Schema(description = "cepDestino", required = true, example = "22222-000")
	@NotEmpty
	private String cepDestino;

	@Schema(description = "nomeDestinatario", required = true, example = "Nome Sobrenome")
	@NotEmpty
	private String nomeDestinatario;

}
