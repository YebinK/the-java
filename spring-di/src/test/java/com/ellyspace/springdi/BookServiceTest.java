package com.ellyspace.springdi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    void di() {
        assertThat(bookService).isNotNull();
        assertThat(bookService.bookRepository).isNotNull();
        //이게 어떻게 통과하는지 의문을 가져보세요.
    }
}