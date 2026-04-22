package com.vanxacloud.appstudio.proximity.app.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public final class ProximityProxy implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ProximityProxy.class);
    private Random random = new Random();
    private volatile boolean running;

    @Override
    public void run() {
        log.info("Starting proxy");
        running = true;
        while (!Thread.currentThread().isInterrupted() && running) {
            log.trace("Proxy is running");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isInitialized() {
//        return random.nextInt(0, 5) == 3;
        return true;
    }

    public boolean isReady() {
        return random.nextInt(0, 5) == 2;
    }

    public void stop() {
        log.info("Stopping proxy");
        running = false;
    }
}
