package com.ellyspace.springdi;

public class Book2 {

    public static String A = "A";

    private String B = "B";

    public Book2() {

    }

    public Book2(String b) {
        B = b;
    }

    public void c() {
        System.err.println("C 메소드입니다.");
    }

    public int sum(int left, int right) {
        return left + right;
    }
}
