package br.com.fsferreira.proxy;

import br.com.fsferreira.response.Currency;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-service", url = "localhost:8000")
public interface CurrencyProxy {
    @GetMapping(value = "/currency-service/{amount}/{from}/{to}")
    public Currency getCurrency(
            @PathVariable("amount") Double amount,
            @PathVariable("from") String from,
            @PathVariable("to") String to
    );

}
