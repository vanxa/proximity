package com.vanxacloud.appstudio.proximity.app.proxy;

import java.util.Random;

public final class ProximityProxy implements Runnable {

    private Random random = new Random();

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isInitialized() {
        return random.nextInt(0, 10) == 7;
    }

    public boolean isReady() {
        return random.nextInt(0, 10) == 2;
    }
}
