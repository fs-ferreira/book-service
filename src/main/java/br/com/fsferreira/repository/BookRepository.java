package br.com.fsferreira.repository;

import br.com.fsferreira.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
