package com.lambdaschool.starthere.repository;

import com.lambdaschool.starthere.models.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BookRespository extends PagingAndSortingRepository<Book, Long>
{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Wrote (bookid, authorid) values (:bookid, :authorid)", nativeQuery = true)
    void insertWrote(long bookid, long authorid);
}
