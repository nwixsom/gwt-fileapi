package com.gwtpro.html5.fileapi.client.events;

import com.google.gwt.event.shared.HandlerRegistration;


public interface HasFileEvents {
    HandlerRegistration addFileEventHandler(FileEvent.FileEventHandler handler);
}
