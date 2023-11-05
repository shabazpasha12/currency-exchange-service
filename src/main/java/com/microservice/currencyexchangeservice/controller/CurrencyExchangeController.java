package com.microservice.currencyexchangeservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.currencyexchangeservice.entity.CurrencyExchange;
import com.microservice.currencyexchangeservice.repository.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {

	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepo;

	@Autowired
	private Environment environment;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable(value = "from") String from,
			@PathVariable(value = "to") String to) {
		CurrencyExchange exchangeFromAndTo = currencyExchangeRepo.findByFromAndTo(from, to);
		if (exchangeFromAndTo == null) {
			throw new RuntimeException("Unable to find data" + from + "and" + to);
		}
		String port = environment.getProperty("local.server.port");
		exchangeFromAndTo.setEnvironment(port);
		return exchangeFromAndTo;
	}

}
