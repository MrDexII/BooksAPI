package com.andrzej.bookDatabase.Repository;

import com.andrzej.bookDatabase.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("bookRepository")
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);

    Book findById(long id);
}
