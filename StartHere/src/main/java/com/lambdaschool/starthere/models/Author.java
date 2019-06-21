package com.lambdaschool.starthere.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Author", description = "Author Entity")
@Entity
@Table(name = "author")
public class Author
{
    @ApiModelProperty(name = "authorid", value = "Primary key for Author", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long    authorid;

    @ApiModelProperty(name = "fname", value = "Student first name", required = true, example = "Some name")
    private String fname;

    @ApiModelProperty(name = "lname", value = "Student last name", required = true, example = "Some name")
    private String lname;

    @ManyToMany
    @JoinTable(name = "wrote",
        joinColumns = {@JoinColumn(name = "bookid")},
        inverseJoinColumns = {@JoinColumn(name = "authorid")})
    @JsonIgnoreProperties("author")
    private List<Book> books = new ArrayList<>();

    public Author()
    {
    }

    public Author(String fname, String lname)
    {
        this.fname = fname;
        this.lname = lname;
    }

    public long getAuthorid()
    {
        return authorid;
    }

    public void setAuthorid(long authorid)
    {
        this.authorid = authorid;
    }

    public String getFname()
    {
        return fname;
    }

    public void setFname(String fname)
    {
        this.fname = fname;
    }

    public String getLname()
    {
        return lname;
    }

    public void setLname(String lname)
    {
        this.lname = lname;
    }

    public List<Book> getBooks()
    {
        return books;
    }

    public void setBooks(List<Book> books)
    {
        this.books = books;
    }
}
