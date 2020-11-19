package com.ellyspace.springdi.book;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws Exception {
        //클래스에서 Class 접근하기
        Class<Book> bookClass = Book.class;

        //인스턴스에서 Class 접근하기
        Book book = new Book();
        Class<? extends Book> instanceWay = book.getClass();

        //String으로 Class 접근하기
        Class<?> stringWay = Class.forName("com.ellyspace.springdi.book.Book");

        //어떻게든 접근을 했다면...
        Arrays.stream(bookClass.getFields()).forEach(System.err::println); //public으로 정의된 d밖에 안 보인다.
        System.err.println("==========1==========");
        Arrays.stream(bookClass.getDeclaredFields()).forEach(System.err::println); //다 가져온다.

        System.err.println("==========2==========");

        //인스턴스의 필드 가져오기
        Arrays.stream(bookClass.getDeclaredFields()).forEach(field -> {
            try {
                field.setAccessible(true);
                System.err.printf("%s 의 값 : %s \n", field, field.get(new Book())); //인스턴스가 있어야 한다.
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        System.err.println("==========3==========");

        //인스턴스의 메소드 가져오기
        Arrays.stream(bookClass.getMethods()).forEach(System.err::println);
        System.err.println("==========4==========");
        Arrays.stream(bookClass.getDeclaredMethods()).forEach(System.err::println);
        System.err.println("==========5==========");

        //인스턴스의 생성자 가져오기
        Arrays.stream(bookClass.getDeclaredConstructors()).forEach(System.err::println);
        System.err.println("==========6==========");

        //상위 클래스 가져오기
        Class<? super MyBook> superclass = MyBook.class.getSuperclass();
        System.err.println(superclass);
        System.err.println("==========7==========");

        //인터페이스 가져오기
        Arrays.stream(MyBook.class.getInterfaces()).forEach(System.err::println);
        System.err.println("==========8==========");

        //modifier(ex.접근제어자) 확인하기
        Arrays.stream(bookClass.getDeclaredFields()).forEach(field -> {
            int modifiers = field.getModifiers();
            System.err.println(modifiers);
            System.err.printf("%s 의 접근제어자가 private: %s, public: %s, static: %s \n",
                    field, Modifier.isPrivate(modifiers), Modifier.isPublic(modifiers), Modifier.isStatic(modifiers));
        });
        System.err.println("==========9==========");

        //어노테이션 조회하기
        Arrays.stream(Book.class.getAnnotations()).forEach(System.err::println);
        System.err.println("==========10==========");

        //어노테이션의 값들 가져오기
        Arrays.stream(Book.class.getAnnotations()).forEach(annotation -> {
            MyAnnotation myAnnotation = (MyAnnotation) annotation;
            System.err.println(myAnnotation.name());
            System.err.println(myAnnotation.number());
            System.err.println(myAnnotation.value());
        });
        System.err.println("==========11==========");


        //상위 클래스의 어노테이션 조회하기 (@Inherit 기능)
        Arrays.stream(MyBook.class.getAnnotations()).forEach(System.err::println);
        System.err.println("==========12==========");

        //클래스 정보 수정 및 실행
        modify();
        System.err.println("==========13==========");
    }


    private static void modify() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Class<?> bookClass = Class.forName("com.ellyspace.springdi.book.Book2");
        Constructor<?> constructor = bookClass.getConstructor(String.class);
        Book2 book2 = (Book2) constructor.newInstance("BB_BB");

        //필드 값 접근, 변경
        Field a = Book2.class.getDeclaredField("A");
        System.err.println("기존 값: " + a.get(null)); //a는 static 변수라서 인스턴스가 필요 없음. -> null
        a.set(null, "AAA");
        System.err.println("변경된 값: " + a.get(null));

        Field b = Book2.class.getDeclaredField("B");
        b.setAccessible(true);
        System.err.println("기존 값: " + b.get(book2)); //b는 인스턴스가 필요함.
        b.set(book2, "BBBBB");
        System.err.println("변경된 값: " + b.get(book2));

        //메소드 실행
        Method c = Book2.class.getDeclaredMethod("c");
        c.setAccessible(true);
        c.invoke(book2); //c 메소드를 호출한다.

        Method sum = Book2.class.getDeclaredMethod("sum", int.class, int.class);
        int invoke = (int) sum.invoke(book2, 1, 2);
        System.err.println(invoke);
    }
}
