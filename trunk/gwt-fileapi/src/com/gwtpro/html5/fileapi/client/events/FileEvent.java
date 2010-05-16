package com.gwtpro.html5.fileapi.client.events;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.gwtpro.html5.fileapi.client.File;

public class FileEvent extends GwtEvent<FileEvent.FileEventHandler> {

    public interface FileEventHandler extends EventHandler {

        void onFiles(FileEvent event);
    }

    public static final Type<FileEventHandler> TYPE = new GwtEvent.Type<FileEventHandler>();
    private final JsArray<File> files;

    public FileEvent(JsArray<File> files){
        this.files=files;
    }

    @Override
    public GwtEvent.Type<FileEventHandler> getAssociatedType() {
        return TYPE;
    }

    public JsArray<File> getFiles() {
        return this.files;
    }

    @Override
    protected void dispatch(FileEventHandler handler) {
        handler.onFiles(this);
    }
}
