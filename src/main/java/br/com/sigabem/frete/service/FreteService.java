package br.com.sigabem.frete.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import br.com.sigabem.frete.model.Cep;
import br.com.sigabem.frete.model.Frete;
import br.com.sigabem.frete.model.dto.FreteInput;
import br.com.sigabem.frete.model.dto.FreteResponse;
import br.com.sigabem.frete.repository.FreteRepository;
import br.com.sigabem.frete.utils.CEPUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FreteService {

	@Autowired
	private FreteRepository freteRepository;

	private static final String VIACEP_URL = "https://viacep.com.br/ws/";

	public Cep findCep(String cepString) {
		Cep request = new RestTemplate().getForObject(VIACEP_URL + cepString + "/json", Cep.class);

		if (Objects.isNull(request)) {
			throw new IllegalArgumentException("Cep não é válido!");
		}

		log.info("[VIA CEP API] - [RESULTADO DA BUSCA: {}]", request.toString());

		return request;
	}

	public FreteResponse salvarFrete(FreteInput freteInput) {

		CEPUtils.validaCep(freteInput.getCepOrigem());
		CEPUtils.validaCep(freteInput.getCepDestino());

		String cepOrigemSemMascara = CEPUtils.removeMascaraCep(freteInput.getCepOrigem());
		String cepDestinoSemMascara = CEPUtils.removeMascaraCep(freteInput.getCepDestino());

		String cepOrigemMascarado = CEPUtils.mascararCep(freteInput.getCepOrigem());
		String cepDestinoMascarado = CEPUtils.mascararCep(freteInput.getCepDestino());

		var frete = calcularFrete(freteInput, cepOrigemSemMascara, cepDestinoSemMascara);

		freteRepository.save(frete);

		return FreteResponse.builder()
				.cepOrigem(cepOrigemMascarado)
				.cepDestino(cepDestinoMascarado)
				.dataPrevistaEntrega(frete.getDataPrevistaEntrega())
				.vlTotalFrete(frete.getVlTotalFrete())
				.build();
	}

	private Frete calcularFrete(FreteInput freteInput, String cepOrigemSemMascara, String cepDestinoSemMascara) {
		var frete = Frete.builder()
				.dataConsulta(LocalDateTime.now())
				.nomeDestinatario(freteInput.getNomeDestinatario())
				.peso(freteInput.getPeso())
				.cepOrigem(cepOrigemSemMascara)
				.cepDestino(cepDestinoSemMascara)
				.build();

		if (findCep(cepOrigemSemMascara).getDdd().equals(findCep(cepDestinoSemMascara).getDdd())) {
			frete.setVlTotalFrete(freteInput.getPeso() * 0.50D);
			frete.setDataPrevistaEntrega(LocalDate.now().plusDays(1));
		} else if (findCep(cepOrigemSemMascara).getUf().equals(findCep(cepDestinoSemMascara).getUf())) {
			frete.setVlTotalFrete(freteInput.getPeso() * 0.75D);
			frete.setDataPrevistaEntrega(LocalDate.now().plusDays(3));
		} else {
			frete.setVlTotalFrete(freteInput.getPeso());
			frete.setDataPrevistaEntrega(LocalDate.now().plusDays(10));
		}
		
		return frete;
	}

}
