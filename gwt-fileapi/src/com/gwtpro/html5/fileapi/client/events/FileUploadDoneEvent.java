package com.gwtpro.html5.fileapi.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.gwtpro.html5.fileapi.client.File;


public class FileUploadDoneEvent extends GwtEvent<FileUploadDoneEvent.FileUploadDoneEventHandler>{
    
    public interface FileUploadDoneEventHandler extends EventHandler{
        public void onDone(FileUploadDoneEvent event);
    }

    public static final Type<FileUploadDoneEventHandler> TYPE = new Type<FileUploadDoneEventHandler>();
    private final File file;
    private final String response;
    private final boolean successful;
    private final int responseCode;

    public FileUploadDoneEvent(File file, int responseCode, String response){
        this.file=file;
        this.response=response;
        this.responseCode=responseCode;
        this.successful=responseCode==200;
    }
    
    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<FileUploadDoneEventHandler> getAssociatedType() {
        return TYPE;
    }
    
    public File getFile() {
        return this.file;
    }

    public String getResponse() {
        return this.response;
    }
    
    public int getResponseCode() {
        return this.responseCode;
    }

    public boolean isSuccessful() {
        return this.successful;
    }

    @Override
    protected void dispatch(FileUploadDoneEventHandler handler) {
        handler.onDone(this);
    }
}
