package com.demo.demoapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.boot.web.client.RestTemplateBuilder;
import java.util.List;

import com.demo.demoapp.domain.ExchangeRate;
import com.demo.demoapp.service.ExchangeRateService;
import com.demo.demoapp.handler.RestTemplateResponseErrorHandler;

@RestController
@RequestMapping("/exchangerates")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    private final RestTemplate restTemplate;
    @Value("${web_api_key}")
    private String webApiKey;

    public ExchangeRateController(ExchangeRateService exchangeRateService, RestTemplateBuilder restTemplateBuilder) {
        this.exchangeRateService = exchangeRateService;
        this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
    }

    @GetMapping
    public ResponseEntity<Page<ExchangeRate>> getAllExchangeRates(Pageable pageable, @RequestParam(name = "usedb", required = true) boolean useDB) {
        if (!useDB) {
            String url = "https://webapi.developers.erstegroup.com/api/csas/public/sandbox/v2/rates/exchangerates";
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("web-api-key", webApiKey);

            ResponseEntity<List<ExchangeRate>> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ExchangeRate>>(){});

            List<ExchangeRate> exchangeRates = response.getBody();
            exchangeRateService.saveAllExchangeRates(exchangeRates);
            return ResponseEntity.ok().build();
        }

        Page<ExchangeRate> exchangeRates = exchangeRateService.getAllExchangeRates(pageable);
        return new ResponseEntity<>(exchangeRates, HttpStatus.OK);
    }
}