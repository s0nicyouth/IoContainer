package com.syouth;

import com.syouth.iocontainer.IoCContainer;

/**
 * Created by syouth on 15.03.15.
 */
public class FastCar implements ICar {

    public FastCar() {
    }
    @Override
    public void engineStart() {
        try {
            IoCContainer.resolve(IEngine.class).ignite();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
