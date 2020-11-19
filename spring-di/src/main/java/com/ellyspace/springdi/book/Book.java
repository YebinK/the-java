package com.ellyspace.springdi.book;

@MyAnnotation(value = "값값")
public class Book {
    private static String B = "BOOK";
    private static final String C = "COOK";

    private String a = "a";
    public String d = "d";
    protected String e = "e";

    public Book() {

    }

    public Book(String a, String d, String e) {
        this.a = a;
        this.d = d;
        this.e = e;
    }

    private void foo1() {
        System.out.println("foo1");
    }

    public void foo2() {
        System.out.println("foo2");
    }

    public int foo3() {
        System.out.println("foo3");
        return 3;
    }
}
