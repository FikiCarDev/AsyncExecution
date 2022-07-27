/*
 * Created by FikiCarDev
 * Copyright (c) 2022. All rights reserved.
 */

package com.fikicar.asyncexecution;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AsyncWorker class is responsible for creating pool of threads and their execution for AsyncTask
 * classes.
 */
public class AsyncWorker {
    private static final AsyncWorker instance = new AsyncWorker();
    private static final int NUMBER_OF_THREADS = 4;

    private final ExecutorService executorService;
    private final Handler handler;

    private AsyncWorker() {
        executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        handler = new android.os.Handler(Looper.getMainLooper());
    }

    public static AsyncWorker getInstance() {
        return instance;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public Handler getHandler() {
        return handler;
    }
}
