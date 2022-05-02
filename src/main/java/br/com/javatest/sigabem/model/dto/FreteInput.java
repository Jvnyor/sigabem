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

	@Schema(description = "cepOrigem", required = true)
	@NotEmpty
	private String cepOrigem;

	@Schema(description = "cepDestino", required = true)
	@NotEmpty
	private String cepDestino;

	@Schema(description = "nomeDestinatario", required = true)
	@NotEmpty
	private String nomeDestinatario;

}
