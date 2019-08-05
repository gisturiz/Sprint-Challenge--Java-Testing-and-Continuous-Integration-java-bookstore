package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.models.Book;
import com.lambdaschool.starthere.models.ErrorDetail;
import com.lambdaschool.starthere.services.BookService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController
{
    @Autowired
    private BookService bookService;

    @ApiOperation(value = "return all books", response = Book.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integr", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Student Found", response = Book.class),
            @ApiResponse(code = 404, message = "Student not found", response = ErrorDetail.class)
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_USERS, ROLE_DATA')")
    @GetMapping(value = "/books", produces = {"application/json"})
    public ResponseEntity<?> listAllBooks(@PageableDefault(page = 0,
            size = 5)
                                                    Pageable pageable)
    {
        List<Book> myBooks = bookService.findAll(pageable);
        return new ResponseEntity<>(myBooks, HttpStatus.OK);
    }


    @ApiOperation(value = "Adds new book", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New book added", response = Book.class),
            @ApiResponse(code = 404, message = "New book not added", response = ErrorDetail.class)
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/data/books/{bookid}/authors/{authorid}",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewBook(@Valid
                                           @RequestBody
                                                   Book newBook, @PathVariable long bookid, @PathVariable long authorid) throws URISyntaxException
    {
        newBook = bookService.save(newBook, bookid, authorid);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newBookURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{bookid}").buildAndExpand(newBook.getBookid()).toUri();
        responseHeaders.setLocation(newBookURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Updates a current book associated with given bookid", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Book updated", response = Book.class),
            @ApiResponse(code = 404, message = "Book not found", response = ErrorDetail.class)
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_DATA')")
    @PutMapping(value = "/data/books/{bookid}")
    public ResponseEntity<?> updateStudent(
            @RequestBody
                    Book updateBook,
            @PathVariable
                    long bookid)
    {
        bookService.update(updateBook, bookid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "Deletes a current book associated with given bookid", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Book deleted", response = Book.class),
            @ApiResponse(code = 404, message = "Book not found", response = ErrorDetail.class)
    })

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/data/books/{bookid}")
    public ResponseEntity<?> deleteBookById(@PathVariable
                                                      long bookid)
    {
        bookService.delete(bookid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
