package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
public class Book
{
    @ApiModelProperty(name = "bookid", value = "Primary key for Book", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bookid;

    @ApiModelProperty(name = "title", value = "Book Title", required = true, example = "Some name")
    private String title;

    @ApiModelProperty(name = "ISBN", value = "Book ISBN", required = true, example = "Some number")
    private String ISBN;

    @ApiModelProperty(name = "copy", value = "Book Copy", required = true, example = "2010")
    private int copy;

    @ManyToOne
    @JoinColumn(name = "sectionid")
    @JsonIgnoreProperties("book")
    private Section section;

    @ManyToMany(mappedBy = "book")
    @JsonIgnoreProperties("books")
    private List<Author> authors = new ArrayList<>();

    public Book()
    {
    }

    public Book(String title, String ISBN, int copy, Section section)
    {
        this.title = title;
        this.ISBN = ISBN;
        this.copy = copy;
        this.section = section;
    }

    public long getBookid()
    {
        return bookid;
    }

    public void setBookid(long bookid)
    {
        this.bookid = bookid;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getISBN()
    {
        return ISBN;
    }

    public void setISBN(String ISBN)
    {
        this.ISBN = ISBN;
    }

    public int getCopy()
    {
        return copy;
    }

    public void setCopy(int copy)
    {
        this.copy = copy;
    }

    public Section getSection()
    {
        return section;
    }

    public void setSection(Section section)
    {
        this.section = section;
    }

    public List<Author> getAuthors()
    {
        return authors;
    }

    public void setAuthors(List<Author> authors)
    {
        this.authors = authors;
    }
}
