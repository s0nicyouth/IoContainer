package com.syouth;

import com.syouth.iocontainer.ResolveAnnotation;

/**
 * Created by syouth on 15.03.15.
 */
public class PowerfulEngine implements IEngine {

    @ResolveAnnotation
    public PowerfulEngine() {}
    @Override
    public void ignite() {
        System.out.println("Ignition");
    }
}
