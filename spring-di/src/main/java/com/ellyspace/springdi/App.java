package com.ellyspace.springdi;

import java.util.Arrays;

public class App {
    public static void main(String[] args) throws ClassNotFoundException {
        //클래스에서 Class 접근하기
        Class<Book> bookClass = Book.class;

        //인스턴스에서 Class 접근하기
        Book book = new Book();
        Class<? extends Book> instanceWay = book.getClass();

        //String으로 Class 접근하기
        Class<?> stringWay = Class.forName("com.ellyspace.springdi.Book");

        //어떻게든 접근을 했다면...
        Arrays.stream(bookClass.getFields()).forEach(System.err::println); //public으로 정의된 d밖에 안 보인다.
        System.err.println("====================");
        Arrays.stream(bookClass.getDeclaredFields()).forEach(System.err::println); //다 가져온다.

        System.err.println("====================");

        //인스턴스의 값을 가져오고 싶다?
        Arrays.stream(bookClass.getDeclaredFields()).forEach(field -> {
            try {
                field.setAccessible(true);
                System.err.printf("%s 의 값 : %s \n", field, field.get(new Book())); //인스턴스가 있어야 한다.
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

    }
}
