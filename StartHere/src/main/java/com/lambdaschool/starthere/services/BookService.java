package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService
{
    List<Book> findAll(Pageable pageable);

    Book findBookById(long id);

    void delete(long id);

    Book save (Book book, long bookid, long authorid);

    Book update(Book book, long id);
}
