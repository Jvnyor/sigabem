package br.com.sigabem.frete.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.sigabem.frete.exception.ViaCepException;
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
			throw new ViaCepException("Cep não é válido!");
		}

		log.info("[VIA CEP API] - [RESULTADO DA BUSCA: {}]", request.toString());

		return request;
	}

	public FreteResponse salvarFrete(FreteInput freteInput) {

		CEPUtils.validaCep(freteInput.getCepOrigem());
		CEPUtils.validaCep(freteInput.getCepDestino());

		var frete = calcularFrete(freteInput, 
				CEPUtils.removeMascaraCep(freteInput.getCepOrigem()),
				CEPUtils.removeMascaraCep(freteInput.getCepDestino()));

		freteRepository.save(frete);

		return FreteResponse.builder()
				.cepOrigem(CEPUtils.mascararCep(freteInput.getCepOrigem()))
				.cepDestino(CEPUtils.mascararCep(freteInput.getCepDestino()))
				.dataPrevistaEntrega(frete.getDataPrevistaEntrega())
				.vlTotalFrete(frete.getVlTotalFrete())
				.build();
	}

	private Frete calcularFrete(FreteInput freteInput, String cepOrigem, String cepDestino) {
		var frete = Frete.builder()
				.dataConsulta(LocalDateTime.now())
				.nomeDestinatario(freteInput.getNomeDestinatario())
				.peso(freteInput.getPeso())
				.cepOrigem(cepOrigem)
				.cepDestino(cepDestino)
				.build();

		if (findCep(cepOrigem).getDdd().equals(findCep(cepDestino).getDdd())) {
			frete.setVlTotalFrete(freteInput.getPeso() * 0.50D);
			frete.setDataPrevistaEntrega(LocalDate.now().plusDays(1));
		} else if (findCep(cepOrigem).getUf().equals(findCep(cepDestino).getUf())) {
			frete.setVlTotalFrete(freteInput.getPeso() * 0.75D);
			frete.setDataPrevistaEntrega(LocalDate.now().plusDays(3));
		} else {
			frete.setVlTotalFrete(freteInput.getPeso());
			frete.setDataPrevistaEntrega(LocalDate.now().plusDays(10));
		}

		return frete;
	}

}
