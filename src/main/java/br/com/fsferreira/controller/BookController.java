package br.com.fsferreira.controller;

import br.com.fsferreira.model.Book;
import br.com.fsferreira.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book-service")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping(value = "/{id}/{currency}")
    public Book findBookLocale(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ) {
        return bookService.findByIdWithLocale(id, currency);
    }
}
