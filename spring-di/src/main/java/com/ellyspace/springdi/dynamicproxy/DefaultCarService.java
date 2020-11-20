package com.ellyspace.springdi.dynamicproxy;

public class DefaultCarService implements CarService {

    @Override
    public void go() {
        System.out.println("=============GO by DefaultCarService");
    }

    @Override
    public void stop() {
        System.out.println("=============STOP by DefaultCarService");
    }
}
