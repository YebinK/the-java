package com.ellyspace.springdi.dynamicproxy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
}