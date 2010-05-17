package com.gwtpro.html5.fileapi.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.gwtpro.html5.fileapi.client.File;

public class FileUploadProgressEvent extends
        GwtEvent<FileUploadProgressEvent.FileUploadProgressEventHandler> {

    public interface FileUploadProgressEventHandler extends EventHandler {

        void onProgress(FileUploadProgressEvent event);
    }

    public static final Type<FileUploadProgressEventHandler> TYPE = new GwtEvent.Type<FileUploadProgressEventHandler>();
    private final File file;
    private final int progress;

    public FileUploadProgressEvent(File file, int progress) {
        this.file = file;
        this.progress = progress;
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<FileUploadProgressEventHandler> getAssociatedType() {
        return TYPE;
    }

    public File getFile() {
        return this.file;
    }

    public int getProgress() {
        return this.progress;
    }

    @Override
    protected void dispatch(FileUploadProgressEventHandler handler) {
        handler.onProgress(this);
    }
}
