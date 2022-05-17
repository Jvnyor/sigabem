package br.com.sigabem.frete.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sigabem.frete.model.dto.FreteInput;
import br.com.sigabem.frete.model.dto.FreteResponse;
import br.com.sigabem.frete.service.FreteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Sistema de calculo de frete SigaBem")
@RequestMapping("/sigabem/")
public class FreteController {

	@Autowired
	private FreteService freteService;

	@PostMapping("/calcular-frete")
	@Operation(summary = "Calcular frete e persistir no banco de dados", description = "Calculo do frete")
	public ResponseEntity<FreteResponse> calcularFreteESalvar(FreteInput freteInput) {
		return ResponseEntity.ok(freteService.calcularFreteESalvar(freteInput));
	}
}
