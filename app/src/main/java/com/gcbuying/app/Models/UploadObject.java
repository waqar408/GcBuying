package com.gcbuying.app.Models;

/**
 * Created by robert on 8/16/17.
 */

public class UploadObject {
    private int status;

    public UploadObject(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
