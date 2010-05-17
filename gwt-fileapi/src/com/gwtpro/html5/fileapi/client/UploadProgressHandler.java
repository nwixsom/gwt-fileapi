package com.gwtpro.html5.fileapi.client;


public interface UploadProgressHandler {
    void onProgress(XMLHttpRequest2 xmlHttpRequest, int bytesUploaded);
}
