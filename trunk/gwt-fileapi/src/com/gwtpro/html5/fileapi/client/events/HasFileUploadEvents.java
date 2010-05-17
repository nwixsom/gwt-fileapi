package com.gwtpro.html5.fileapi.client.events;

import com.google.gwt.event.shared.HandlerRegistration;
import com.gwtpro.html5.fileapi.client.events.FileUploadDoneEvent.FileUploadDoneEventHandler;
import com.gwtpro.html5.fileapi.client.events.FileUploadProgressEvent.FileUploadProgressEventHandler;

public interface HasFileUploadEvents {

    HandlerRegistration addFileUploadDoneEventHandler(
            FileUploadDoneEventHandler handler);

    HandlerRegistration addFileUploadProgressEventHandler(
            FileUploadProgressEventHandler handler);
}
