package eu.galjente.zooplus.currency;

import eu.galjente.zooplus.currency.dto.ConvertDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConverterHistoryService {

	private final ConverterHistoryRepository converterHistoryRepository;

	@Autowired
	public ConverterHistoryService(ConverterHistoryRepository converterHistoryRepository) {
		this.converterHistoryRepository = converterHistoryRepository;
	}

	public ConverterHistory saveHistory(ExchangeForm exchangeForm, ConvertDto convertedValue, String errorMessage) {
		ConverterHistory converterHistory = new ConverterHistory();
		converterHistory.setCurrencyFrom(exchangeForm.getCurrencyFrom());
		converterHistory.setCurrencyTo(exchangeForm.getCurrencyTo());
		converterHistory.setAmount(exchangeForm.getAmount());

		if (convertedValue != null) {
			converterHistory.setExchangeRate(convertedValue.getResponse());
		} else {
			converterHistory.setErrorMessage(errorMessage);
		}

		return converterHistoryRepository.save(converterHistory);
	}

	public List<ConverterHistory> getAllHistory() {
		return converterHistoryRepository.findAll(new Sort(Sort.Direction.DESC, "creationDate"));
	}
}
