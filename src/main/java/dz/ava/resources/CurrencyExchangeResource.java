package dz.ava.resources;

import dz.ava.domain.ExchangeValue;
import dz.ava.repositories.ExchangeValueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@RestController
public class CurrencyExchangeResource {

    private Environment environment;

    private final ExchangeValueRepository exchangeValueRepository;

    public CurrencyExchangeResource(Environment environment, ExchangeValueRepository exchangeValueRepository) {
        this.environment = environment;
        this.exchangeValueRepository = exchangeValueRepository;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable String from,
                                               @PathVariable String to) {
        Optional<ExchangeValue> optionalExchangeValue = exchangeValueRepository.findByFromAndTo(from, to);

        if (!optionalExchangeValue.isPresent()) {
            throw new RuntimeException("Not Found");
        }

        ExchangeValue exchangeValue = optionalExchangeValue.get();
        exchangeValue.setPort(Integer.valueOf(environment.getProperty("server.port")));

        return exchangeValue;
    }
}
