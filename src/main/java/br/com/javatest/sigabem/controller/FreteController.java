package br.com.javatest.sigabem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.javatest.sigabem.model.dto.FreteInput;
import br.com.javatest.sigabem.model.dto.FreteResponse;
import br.com.javatest.sigabem.service.FreteService;
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
