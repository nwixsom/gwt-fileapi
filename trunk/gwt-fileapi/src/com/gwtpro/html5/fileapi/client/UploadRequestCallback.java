package com.gwtpro.html5.fileapi.client;

public interface UploadRequestCallback {
    void onError(UploadRequest request, Throwable exception);
    void onResponseReceived(UploadRequest request, UploadResponse response);
    void onUploadProgress(UploadRequest request,int bytesUploaded );
}
