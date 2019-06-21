package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Book;
import com.lambdaschool.starthere.repository.BookRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "bookService")
public class BookServiceImpl implements BookService
{
    @Autowired
    private BookRespository bookrepos;

    @Override
    public List<Book> findAll(Pageable pageable)
    {
        List<Book> list = new ArrayList<>();
        bookrepos.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Book findBookById(long id) throws EntityNotFoundException
    {
        return bookrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
    }

    @Transactional
    @Override
    public void delete(long id) throws EntityNotFoundException
    {
        if (bookrepos.findById(id).isPresent())
        {
            bookrepos.deleteById(id);
        } else
        {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public Book save(Book book, long bookid, long authorid)
    {
        Book newBook = bookrepos.findById(bookid)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(bookid)));

        if (book.getTitle() != null)
        {
            bookrepos.insertWrote(bookid, authorid);
        }

        return bookrepos.save(newBook);
    }

    @Override
    public Book update(Book book, long id)
    {
        Book currentBook = bookrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));

        if (book.getTitle() != null)
        {
            currentBook.setTitle(book.getTitle());
            currentBook.setISBN(book.getISBN());
            currentBook.setCopy(book.getCopy());
        }

        return bookrepos.save(currentBook);
    }
}
