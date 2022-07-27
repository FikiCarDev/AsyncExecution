/*
 * Created by FikiCarDev
 * Copyright (c) 2022. All rights reserved.
 */

package com.fikicar.asyncexecution;

import java.util.concurrent.ExecutorService;

public abstract class AsyncTask<INPUT, PROGRESS, OUTPUT> {
    private boolean cancelled = false;

    public AsyncTask() { }

    public AsyncTask<INPUT, PROGRESS, OUTPUT> execute(final INPUT input) {
        onPreExecute();

        ExecutorService executorService = AsyncWorker.getInstance().getExecutorService();
        executorService.execute(() -> {
            try {
                final OUTPUT output = doInBackground(input);
                AsyncWorker.getInstance().getHandler().post(() -> onPostExecute(output));
            } catch (final Exception e) {
                e.printStackTrace();
                AsyncWorker.getInstance().getHandler().post(() -> onBackgroundError(e));
            }
        });

        return this;
    }

    protected void publishProgress(final PROGRESS progress) {
        AsyncWorker.getInstance().getHandler().post(() -> {
            if (onProgressListener != null) {
                onProgressListener.onProgress(progress);
            }
        });
    }

    public void cancel() {
        cancelled = true;
    }

    protected boolean isCancelled() {
        return cancelled;
    }

    protected void onCancelled() {
        AsyncWorker.getInstance().getHandler().post(() -> {
            if (onCancelledListener != null) {
                onCancelledListener.onCancelled();
            }
        });
    }

    /**
     * The code in overridden onPreExecute method will be executed on "main" thread with the rest
     * of application. From within this method, all the UI elements are accessible.
     */
    protected void onPreExecute() { }

    /**
     * The code in overridden doInBackground method will be executed on a thread generated in
     * background separated from the "main" thread. There is no way of communicating with the UI
     * elements from here, nor is it advised.
     *
     * It is possible to send data through via the generic INPUT parameter, this can also be empty.
     * Sending data from this method once the code is executed is possible via OUTPUT and
     * onPostExecute method.
     */
    protected abstract OUTPUT doInBackground(INPUT input) throws Exception;

    /**
     * The code in overridden onPostExecute method will be executed on "main" thread where, once
     * again, UI elements will be available.
     */
    protected void onPostExecute(OUTPUT output) { }

    protected abstract void onBackgroundError(Exception e);

    private OnProgressListener<PROGRESS> onProgressListener;

    public interface OnProgressListener<PROGRESS> {
        void onProgress(PROGRESS progress);
    }

    public void setOnProgressListener(OnProgressListener<PROGRESS> onProgressListener) {
        this.onProgressListener = onProgressListener;
    }

    private OnCancelledListener onCancelledListener;

    public interface OnCancelledListener {
        void onCancelled();
    }

    public void setOnCancelledListener(OnCancelledListener onCancelledListener) {
        this.onCancelledListener = onCancelledListener;
    }
}
