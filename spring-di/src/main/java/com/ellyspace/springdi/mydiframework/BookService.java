package com.ellyspace.springdi.mydiframework;

public class BookService {

    @Inject
    private BookRepository bookRepository;

    public BookRepository getBookRepository() {
        return bookRepository;
    }
}
