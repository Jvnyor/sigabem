package br.com.javatest.sigabem.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.javatest.sigabem.model.Cep;
import br.com.javatest.sigabem.model.Frete;
import br.com.javatest.sigabem.model.dto.FreteInput;
import br.com.javatest.sigabem.model.dto.FreteResponse;
import br.com.javatest.sigabem.repository.FreteRepository;
import br.com.javatest.sigabem.utils.CEPUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FreteService {

	@Autowired
	private FreteRepository freteRepository;

	private static final String VIACEP_URL = "https://viacep.com.br/ws/";

	public Cep findCep(String cepString) {

		try {

			Cep request = new RestTemplate().getForObject(VIACEP_URL + cepString + "/json", Cep.class);

			log.info("[VIA CEP API] - [RESULTADO DA BUSCA: {}]", request.toString());

			return request;

		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}

	public FreteResponse calcularFreteESalvar(FreteInput freteInput) {

		CEPUtils.validaCep(freteInput.getCepOrigem());
		CEPUtils.validaCep(freteInput.getCepDestino());

		String cepOrigemSemMascara = CEPUtils.removeMascaraCep(freteInput.getCepOrigem());
		String cepDestinoSemMascara = CEPUtils.removeMascaraCep(freteInput.getCepDestino());

		String cepOrigemMascarado = CEPUtils.mascararCep(freteInput.getCepOrigem());
		String cepDestinoMascarado = CEPUtils.mascararCep(freteInput.getCepDestino());

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

		freteRepository.save(frete);

		return FreteResponse.builder()
				.cepOrigem(cepOrigemMascarado)
				.cepDestino(cepDestinoMascarado)
				.dataPrevistaEntrega(frete.getDataPrevistaEntrega())
				.vlTotalFrete(frete.getVlTotalFrete())
				.build();
	}

}
