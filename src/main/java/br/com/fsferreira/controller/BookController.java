package br.com.fsferreira.controller;

import br.com.fsferreira.model.Book;
import br.com.fsferreira.service.BookService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Tag(name = "Book Endpoint")
@RestController
@RequestMapping("/book-service")
public class BookController {

    @Autowired
    BookService bookService;

    @Operation(summary = "Find a book by ID, returning his Price by locale currency.")
    @GetMapping(value = "/{id}/{currency}")
    public Book findBookLocale(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ) {
        return bookService.findByIdWithLocale(id, currency);
    }

    @GetMapping(value = "/error")
    //@Retry(name = "default",fallbackMethod = "fallbackMethod")
    //@CircuitBreaker(name = "default",fallbackMethod = "fallbackMethod")
    //@Bulkhead(name = "default")
    @RateLimiter(name = "default",fallbackMethod = "fallbackMaxLimitMethod")
    public String testError(
    ) {
        var response = new RestTemplate().getForEntity("https://localhost:8080/error", String.class);
        return response.getBody();
    }

    public String fallbackMethod(Exception ex){
        return "Service unavailable";
    }

    public String fallbackMaxLimitMethod(Exception ex){
        return "Too many requests. Try again later";
    }
}
