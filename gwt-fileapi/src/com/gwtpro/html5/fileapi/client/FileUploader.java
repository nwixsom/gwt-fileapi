/*
 * Copyright 2010 Abed Tony BenBrahim
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gwtpro.html5.fileapi.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.gwtpro.html5.fileapi.client.events.FileUploadDoneEvent;
import com.gwtpro.html5.fileapi.client.events.FileUploadProgressEvent;
import com.gwtpro.html5.fileapi.client.events.HasFileUploadEvents;
import com.gwtpro.html5.fileapi.client.events.FileUploadDoneEvent.FileUploadDoneEventHandler;
import com.gwtpro.html5.fileapi.client.events.FileUploadProgressEvent.FileUploadProgressEventHandler;

public class FileUploader implements HasFileUploadEvents {

    public static native boolean isSupported()/*-{
        return !!(new XMLHttpRequest().upload);
    }-*/;

    @SuppressWarnings("unused")
    private JavaScriptObject xhr;
    private String url;
    private File file;
    private final HandlerManager handlerManager;

    public FileUploader() {
        this.handlerManager = new HandlerManager(this);
        initUploader();
    }

    @Override
    public HandlerRegistration addFileUploadDoneEventHandler(
            FileUploadDoneEventHandler handler) {
        return this.handlerManager
                .addHandler(FileUploadDoneEvent.TYPE, handler);
    }

    @Override
    public HandlerRegistration addFileUploadProgressEventHandler(
            FileUploadProgressEventHandler handler) {
        return this.handlerManager.addHandler(FileUploadProgressEvent.TYPE,
                handler);
    }

    public String getUrl() {
        return this.url;
    }

    public native void sendFile(File file)/*-{
        this.@com.gwtpro.html5.fileapi.client.FileUploader::file=file;
        var xhr=this.@com.gwtpro.html5.fileapi.client.FileUploader::xhr;
        xhr.open("post", this.@com.gwtpro.html5.fileapi.client.FileUploader::url, true);
        xhr.setRequestHeader("X-FileName",file.fileName);
        xhr.setRequestHeader("X-FileSize",file.fileSize);
        xhr.send(file);
    }-*/;

    public void setUrl(String url) {
        this.url = url;
    }

    private native void initUploader()/*-{
        xhr=new XMLHttpRequest();
        this.@com.gwtpro.html5.fileapi.client.FileUploader::xhr=xhr;
        var _this=this;
        if (xhr.upload){
            xhr.addEventListener("load", function(e){
                _this.@com.gwtpro.html5.fileapi.client.FileUploader::done(ILjava/lang/String;)(xhr.status, xhr.responseText);
            }, false);  
            xhr.addEventListener("error", function(e){
                _this.@com.gwtpro.html5.fileapi.client.FileUploader::done(ILjava/lang/String;)(xhr.status, xhr.responseText);
            }, false);  
            xhr.addEventListener("abort", function(e){
                _this.@com.gwtpro.html5.fileapi.client.FileUploader::done(ILjava/lang/String;)(0,"");
            }, false);  
            xhr.upload.addEventListener("progress",function(e){
                _this.@com.gwtpro.html5.fileapi.client.FileUploader::progress(I)(e.loaded);
            }, false);
        }
    }-*/;

    protected void done(int responseCode, String response) {
        this.handlerManager.fireEvent(new FileUploadDoneEvent(this.file,
                responseCode, response));
    }

    protected void progress(int loaded) {
        this.handlerManager.fireEvent(new FileUploadProgressEvent(this.file,
                loaded));
    }
}
