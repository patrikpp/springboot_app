package com.demo.demoapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

import com.demo.demoapp.domain.ExchangeRate;
import com.demo.demoapp.db.repository.ExchangeRateRepository;

@Service
public class ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public Page<ExchangeRate> getAllExchangeRates(Pageable pageable) {
        return exchangeRateRepository.getAll(pageable);
    }

    public void saveAllExchangeRates(List<ExchangeRate> exchangeRates) {
        exchangeRateRepository.saveAll(exchangeRates);
    }
}
