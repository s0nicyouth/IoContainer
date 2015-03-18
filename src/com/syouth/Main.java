package com.syouth;

import com.syouth.iocontainer.IoCContainer;

public class Main {

    public static void main(String[] args) {
        try {
            IoCContainer.register(IEngine.class, PowerfulEngine.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FastCar car = new FastCar();
        car.engineStart();
    }
}
