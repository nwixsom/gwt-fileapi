package com.gwtpro.html5.fileapi.client;

import com.google.gwt.xhr.client.XMLHttpRequest;


public class XMLHttpRequest2 extends XMLHttpRequest {
    
    protected XMLHttpRequest2(){
        super();
    }
    
    public final native void sendFile(File file)/*-{
        this.send(file);
    }-*/;
    
    public final native void setOnProgressHandler(UploadProgressHandler handler)/*-{
        var _this=this;
        if (this.upload){
            this.upload.addEventListener("progress",function(e){
                handler.@com.gwtpro.html5.fileapi.client.UploadProgressHandler::onProgress(Lcom/gwtpro/html5/fileapi/client/XMLHttpRequest2;I)(_this, e.loaded);
            }, false);
        }
    }-*/;
}
