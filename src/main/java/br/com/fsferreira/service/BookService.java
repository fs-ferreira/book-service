package br.com.fsferreira.service;

import br.com.fsferreira.model.Book;
import br.com.fsferreira.proxy.CurrencyProxy;
import br.com.fsferreira.repository.BookRepository;
import br.com.fsferreira.response.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

@Service
public class BookService {
    @Autowired
    private Environment environment;
    @Autowired
    BookRepository repository;

    @Autowired
    CurrencyProxy proxy;

    public Book findByIdWithLocale(Long id, String currency) {
        var book = repository.findById(id).orElseThrow(() -> new RuntimeException("Book not found!"));
        var port = environment.getProperty("local.server.port");

        var localeCurrency = proxy.getCurrency(book.getPrice(), "USD", currency);

        book.setEnv("Book port " + port + " Currency port " + localeCurrency.getEnv());
        book.setPrice(localeCurrency.getConvertedValue());

        return book;
    }
}
