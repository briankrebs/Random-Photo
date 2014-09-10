package com.brianckrebs.randomphoto.utils;

import com.squareup.otto.Bus;

/**
 * Created by BrianK on 9/6/2014.
 */
public final class BusProvider {
    private BusProvider() { }

    private static Bus instance;

    public static Bus getInstance() {
        if (instance == null) {
            instance = new Bus();
        }
        return instance;
    }

    public static void post(Object event) {
        getInstance().post(event);
    }

    public static void register(Object o) {
        getInstance().register(o);
    }

    public static void unregister(Object o) {
        getInstance().unregister(o);
    }

}
