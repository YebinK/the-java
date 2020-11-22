package com.ellyspace.springdi.dynamicproxy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static net.bytebuddy.matcher.ElementMatchers.named;

class CarServiceTest {

    //CarService의 프록시를 만든다. !!주의: 자바는 클래스가 아니라 인터페이스 기반의 프록시만 만들 수 있다. 그래서 DefaultCarService로는 못 만든다.
    CarService carService = (CarService) Proxy.newProxyInstance(CarService.class.getClassLoader(),
            new Class[]{CarService.class},
            new InvocationHandler() { //이 프록시의 메소드가 호출될 때 어떻게 처리할지 정의 (프록시의 부가기능 추가)
                CarService carService = new DefaultCarService();

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (method.getName().equals("go")) {
                        System.out.println("========BEFORE GO");
                        Object invoke = method.invoke(carService, args);
                        System.out.println("========AFTER GO");
                        return invoke;
                    }
                    return method.invoke(carService, args);
                }
            });

    @Test
    void di() {
        Car car = new Car();
        car.setColor("GREEN");
        carService.go();
        carService.stop();
    }

    @Test
    void di_with_class() { //인터페이스가 아닌 클래스의 프록시 만들기 (with cglib)
        MethodInterceptor handler = new MethodInterceptor() {
            DefaultCarService carService = new DefaultCarService();
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                return method.invoke(carService, args);
            }
        };
        DefaultCarService carService = (DefaultCarService) Enhancer.create(DefaultCarService.class, handler);

        carService.go();
        carService.stop();
    }

    @Test
    void di_with_byte_buddy() throws Exception { //인터페이스가 아닌 클래스의 프록시 만들기 (with byte buddy)
        Class<? extends DefaultCarService> proxyClass = new ByteBuddy().subclass(DefaultCarService.class)
                .method(named("go")).intercept(InvocationHandlerAdapter.of(new InvocationHandler() { //go라는 네임의 메소드에만 지정
                    DefaultCarService carService = new DefaultCarService();
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("aaa");
                        Object invoke = method.invoke(carService, args);
                        System.out.println("bbb");
                        return invoke;
                    }
                }))
                .make().load(CarService.class.getClassLoader()).getLoaded();
        DefaultCarService carService = proxyClass.getConstructor(null).newInstance();

        Car car = new Car();
        car.setColor("green");
        carService.go();
        carService.goWithColor(car);
    }
}